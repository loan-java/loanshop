package io.dkgj.modules.sys.controller;

import io.dkgj.common.utils.DateUtils;
import io.dkgj.common.utils.PageUtils;
import io.dkgj.common.utils.R;
import io.dkgj.common.validator.ValidatorUtils;
import io.dkgj.modules.sys.entity.ChannelManageEntity;
import io.dkgj.modules.sys.entity.HistoryLogChannelEntity;
import io.dkgj.modules.sys.entity.SysUserEntity;
import io.dkgj.modules.sys.service.ChannelManageService;
import io.dkgj.modules.sys.service.HistoryLogChannelService;
import io.dkgj.modules.sys.shiro.ShiroUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;


/**
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-04-16 12:04:03
 */
@RestController
@RequestMapping("sys/historylogchannel")
public class HistoryLogChannelController {
    @Autowired
    private HistoryLogChannelService historyLogChannelService;

    @Autowired
    private ChannelManageService channelManageService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:historylogchannel:list")
    public R list(@RequestParam Map<String, Object> params) {
        SysUserEntity loginUser = ShiroUtils.getUserEntity();
        if (loginUser.getChannelId() != null && loginUser.getChannelId() > 0) {
            params.put("channelId", loginUser.getChannelId()); //渠道用户
        }

        String createdAt = (String) params.get("createdAt");
        if (StringUtils.isBlank(createdAt)) {
            String pattern = "yyyy/MM/dd";
            createdAt = String.format("%s%s%s", DateUtils.format(DateUtils.addDateDays(new Date(), -1), pattern), "-", DateUtils.format(new Date(), pattern));
            params.put("createdAt", createdAt);
        }

        String sidx = (String) params.get("sidx");
        if (StringUtils.isBlank(sidx)) {
            params.put("sidx", "createdAt");
            params.put("order", "desc");
        }

        PageUtils page = historyLogChannelService.queryPage(params);
        page.setParams(params);

        if (loginUser.getChannelId() != null && loginUser.getChannelId() > 0) {
            page.setChannel(true);
        }
        Integer status = 0;
        List<HistoryLogChannelEntity> tmp = (List<HistoryLogChannelEntity>) page.getList();
        List<HistoryLogChannelEntity> data = new ArrayList<>();
        for (HistoryLogChannelEntity tmpEntity : tmp) {
            HistoryLogChannelEntity entity = new HistoryLogChannelEntity();
            BeanUtils.copyProperties(tmpEntity, entity);
            ChannelManageEntity channelManageEntity = channelManageService.getById(entity.getChannel());
            entity.setChannelName(channelManageEntity.getChannelname());

            entity.setClicknumTmp(tmpEntity.getClicknum());
            entity.setRegnumTmp(tmpEntity.getRegnum());
            entity.setUvNumTmp(tmpEntity.getUvNum());
            page.setClickNum(entity.getClickdownnumTmp());

            page.setRegNum(entity.getRegnum());
            page.setUvNum(entity.getUvNum());
            page.setAppOpenNum(tmpEntity.getAppOpenNum());
            page.setAppDownNum(tmpEntity.getClickdownnum());

            data.add(entity);
        }
        page.setList(data);

        params.remove("page");
        params.remove("limit");

        Map<String, Object> result = historyLogChannelService.selectSum(params);

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
    @RequiresPermissions("sys:historylogchannel:info")
    public R info(@PathVariable("id") Integer id) {
        HistoryLogChannelEntity historyLogChannel = historyLogChannelService.getById(id);

        return R.ok().put("historyLogChannel", historyLogChannel);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:historylogchannel:save")
    public R save(@RequestBody HistoryLogChannelEntity historyLogChannel) {
        historyLogChannelService.save(historyLogChannel);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:historylogchannel:update")
    public R update(@RequestBody HistoryLogChannelEntity historyLogChannel) {
        ValidatorUtils.validateEntity(historyLogChannel);
        historyLogChannelService.updateById(historyLogChannel);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:historylogchannel:delete")
    public R delete(@RequestBody Integer[] ids) {
        historyLogChannelService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
