package io.dkgj.modules.sys.controller;

import io.dkgj.common.utils.DateUtils;
import io.dkgj.common.utils.PageUtils;
import io.dkgj.common.utils.R;
import io.dkgj.modules.sys.entity.AdminLogChannelEntity;
import io.dkgj.modules.sys.entity.ChannelManageEntity;
import io.dkgj.modules.sys.entity.SysUserEntity;
import io.dkgj.modules.sys.service.AdminLogChannelService;
import io.dkgj.modules.sys.service.ChannelManageService;
import io.dkgj.modules.sys.shiro.ShiroUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-03-27 15:10:37
 */
@RestController
@RequestMapping("sys/adminlogchannel")
public class AdminLogChannelController {
    @Autowired
    private AdminLogChannelService adminLogChannelService;

    @Autowired
    private ChannelManageService channelManageService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:adminlogchannel:list")
    public R list(@RequestParam Map<String, Object> params) {

        SysUserEntity loginUser = ShiroUtils.getUserEntity();
        if (loginUser.getChannelId() != null && loginUser.getChannelId() > 0) {
            params.put("channelId", loginUser.getChannelId()); //渠道用户
        }
        String createdAt = (String) params.get("createdAt");
        if (StringUtils.isBlank(createdAt)) {
            String pattern = "yyyy/MM/dd";
            createdAt = String.format("%s%s%s", DateUtils.format(new Date(), pattern), "-", DateUtils.format(DateUtils.addDateDays(new Date(), 1), pattern));
            params.put("createdAt", createdAt);
        }

        String sidx = (String) params.get("sidx");
        if (StringUtils.isBlank(sidx)) {
            params.put("sidx", "createdAt");
            params.put("order", "desc");
        }

        PageUtils page = adminLogChannelService.queryPage(params);
        page.setParams(params);

        if (loginUser.getChannelId() != null && loginUser.getChannelId() > 0) {
            page.setChannel(true);
        }
        Integer status = 0;
        List<AdminLogChannelEntity> tmp = (List<AdminLogChannelEntity>) page.getList();
        List<AdminLogChannelEntity> data = new ArrayList<>();
        for (AdminLogChannelEntity tmpEntity : tmp) {
            AdminLogChannelEntity entity = new AdminLogChannelEntity();
            BeanUtils.copyProperties(tmpEntity, entity);
            ChannelManageEntity channelManageEntity = channelManageService.getById(entity.getChannel());
            entity.setChannelName(channelManageEntity.getChannelname());
            status = channelManageEntity.getDeductstatus();
            if (status == 1) {
                Integer baseNum = channelManageEntity.getDeductbase();
                BigDecimal ratio = channelManageEntity.getDeductratio();
                Integer regNum = tmpEntity.getChannelRegNum();
                entity.setChannelRegNum(regNum == null ? 0 : regNum);
                if (regNum != null && regNum > baseNum) {
                    entity.setRegnumTmp(new BigDecimal(regNum).subtract(new BigDecimal(baseNum)).multiply(new BigDecimal("1").subtract(ratio)).add(new BigDecimal(baseNum)).intValue());
                } else {
                    entity.setRegnumTmp(regNum == null ? 0 : regNum);
                }
            } else {
                entity.setClicknumTmp(entity.getClicknum());
                entity.setChannelRegNum(tmpEntity.getChannelRegNum() == null ? 0 : tmpEntity.getChannelRegNum());
                entity.setRegnumTmp(tmpEntity.getChannelRegNum() == null ? 0 : tmpEntity.getChannelRegNum());
                entity.setUvNumTmp(entity.getUvNum());
                entity.setLoanUvNum(entity.getLoanUvNum());
            }

            if (null != entity.getChannelRegNum() && entity.getLoanUvNum() != null) {
                entity.setZcrjdjs(new BigDecimal(entity.getLoanUvNum() / (float) entity.getChannelRegNum()).setScale(2));
            }

            if (null != entity.getUvNum()
                    && entity.getUvNum() > 0
                    && null != entity.getChannelRegNum()
            ) {
                entity.setUvzcl(new BigDecimal(entity.getChannelRegNum() / entity.getUvNum() * 100).setScale(2) + "%");
            }

            data.add(entity);
        }
        page.setList(data);

        params.remove("page");
        params.remove("limit");

        Map<String, Object> result = adminLogChannelService.selectSum(params);

        if (!page.isChannel() && page != null && result != null) {
            if (result.get("clickNum") != null) {
                page.setClickNum(((BigDecimal) result.get("clickNum")).intValue());
            } else {
                page.setClickNum(0);
            }
            if (result.get("regNum") != null) {
                page.setRegNum(((BigDecimal) result.get("regNum")).intValue());
            } else {
                page.setRegNum(0);
            }
            if (result.get("uvNum") != null) {
                page.setUvNum(((BigDecimal) result.get("uvNum")).intValue());
            } else {
                page.setUvNum(0);
            }
            if (result.get("loanUvNum") != null) {
                Long loanUvNumTmp = (Long) result.get("loanUvNum");
                page.setLoanUvNum(loanUvNumTmp);
            } else {
                page.setLoanUvNum(0L);
            }
            if (result.get("appDownNum") != null) {
                page.setAppDownNum(((BigDecimal) result.get("appDownNum")).intValue());
            } else {
                page.setAppDownNum(0);
            }
            if (result.get("appOpenNum") != null) {
                page.setAppOpenNum(((BigDecimal) result.get("appOpenNum")).intValue());
            } else {
                page.setAppOpenNum(0);
            }
        }

        return R.ok().put("page", page);
    }

}
