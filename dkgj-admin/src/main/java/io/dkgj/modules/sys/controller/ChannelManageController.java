package io.dkgj.modules.sys.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.dkgj.common.config.AppProperties;
import io.dkgj.common.utils.DateUtils;
import io.dkgj.common.validator.ValidatorUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.dkgj.modules.sys.entity.ChannelManageEntity;
import io.dkgj.modules.sys.service.ChannelManageService;
import io.dkgj.common.utils.PageUtils;
import io.dkgj.common.utils.R;


/**
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-03-27 15:10:37
 */
@RestController
@RequestMapping("sys/channelmanage")
public class ChannelManageController {
    @Autowired
    private ChannelManageService channelManageService;

    @Autowired
    AppProperties appProperties;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:channelmanage:list")
    public R list(@RequestParam Map<String, Object> params) {

        params.put("sidx", "createdAt");
        params.put("order", "desc");

        PageUtils page = channelManageService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:channelmanage:info")
    public R info(@PathVariable("id") Integer id) {
        ChannelManageEntity channelManage = channelManageService.getById(id);
        channelManage.setDeductDateStr(DateUtils.format(channelManage.getDeductdate(), "yyyy/MM/dd"));
        return R.ok().put("channelManage", channelManage);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:channelmanage:save")
    public R save(@RequestBody ChannelManageEntity channelManage) {

        ValidatorUtils.validateEntity(channelManage);

        ChannelManageEntity tmp = channelManageService.getOne(new QueryWrapper<ChannelManageEntity>()
                .eq("channelCode", channelManage.getChannelcode()));
        if (tmp != null) {
            return R.error("渠道编码已存在！");
        }

        channelManage.setDeductdate(DateUtils.stringToDate(channelManage.getDeductDateStr(), "yyyy/MM/dd"));
        channelManage.setCreatedat(new Date());
        channelManage.setUpdatedat(new Date());
        switch (appProperties.getApp()) {
            case 1:
                channelManage.setChannelurl(String.format("%s%s", "http://35.241.105.201/#/?channelCode=", channelManage.getChannelcode()));
                break;
        }
        channelManageService.save(channelManage);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:channelmanage:update")
    public R update(@RequestBody ChannelManageEntity channelManage) {
        channelManage.setDeductdate(DateUtils.stringToDate(channelManage.getDeductDateStr(), "yyyy/MM/dd"));
        ValidatorUtils.validateEntity(channelManage);
        switch (appProperties.getApp()) {
            case 1:
                channelManage.setChannelurl(String.format("%s%s", "http://register.whwen.cn/#/?channelCode=", channelManage.getChannelcode()));
                break;
        }
        channelManageService.updateById(channelManage);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:channelmanage:delete")
    public R delete(@RequestBody Integer[] ids) {
        channelManageService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
