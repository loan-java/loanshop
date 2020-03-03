package io.dkgj.modules.sys.controller;

import java.math.BigDecimal;
import java.util.*;

import io.dkgj.common.utils.DateUtils;
import io.dkgj.common.validator.ValidatorUtils;
import io.dkgj.modules.sys.entity.*;
import io.dkgj.modules.sys.service.ChannelManageService;
import io.dkgj.modules.sys.shiro.ShiroUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.dkgj.modules.sys.service.LogChannelService;
import io.dkgj.common.utils.PageUtils;
import io.dkgj.common.utils.R;


/**
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-03-27 15:10:37
 */
@RestController
@RequestMapping("sys/logchannel")
public class LogChannelController {
    @Autowired
    private LogChannelService logChannelService;

    @Autowired
    private ChannelManageService channelManageService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:logchannel:list")
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

        PageUtils page = logChannelService.queryPage(params);
        page.setParams(params);

        if (loginUser.getChannelId() != null && loginUser.getChannelId() > 0) {
            page.setChannel(true);
        }
        Integer status = 0;
        List<LogChannelEntity> tmp = (List<LogChannelEntity>) page.getList();
        List<LogChannelEntity> data = new ArrayList<>();
        for (LogChannelEntity tmpEntity : tmp) {
            LogChannelEntity entity = new LogChannelEntity();
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
            data.add(entity);
        }
        page.setList(data);

        params.remove("page");
        params.remove("limit");

        Map<String, Object> result = logChannelService.selectSum(params);

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


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:logchannel:info")
    public R info(@PathVariable("id") Integer id) {
        LogChannelEntity logChannel = logChannelService.getById(id);

        return R.ok().put("logChannel", logChannel);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:logchannel:save")
    public R save(@RequestBody LogChannelEntity logChannel) {
        logChannelService.save(logChannel);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:logchannel:update")
    public R update(@RequestBody LogChannelEntity logChannel) {
        ValidatorUtils.validateEntity(logChannel);
        logChannelService.updateById(logChannel);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:logchannel:delete")
    public R delete(@RequestBody Integer[] ids) {
        logChannelService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
