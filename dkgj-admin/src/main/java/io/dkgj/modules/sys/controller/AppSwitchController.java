package io.dkgj.modules.sys.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;


import io.dkgj.common.validator.ValidatorUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.dkgj.modules.sys.entity.AppSwitchEntity;
import io.dkgj.modules.sys.service.AppSwitchService;
import io.dkgj.common.utils.PageUtils;
import io.dkgj.common.utils.R;


/**
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-07-01 17:48:42
 */
@RestController
@RequestMapping("sys/appswitch")
public class AppSwitchController {
    @Autowired
    private AppSwitchService appSwitchService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:appswitch:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = appSwitchService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:appswitch:info")
    public R info(@PathVariable("id") Long id) {
        AppSwitchEntity appSwitch = appSwitchService.getById(id);

        return R.ok().put("appSwitch", appSwitch);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:appswitch:save")
    public R save(@RequestBody AppSwitchEntity appSwitch) {
        appSwitch.setCreateDate(new Date());
        appSwitchService.save(appSwitch);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:appswitch:update")
    public R update(@RequestBody AppSwitchEntity appSwitch) {
        ValidatorUtils.validateEntity(appSwitch);
        appSwitch.setUpdateDate(new Date());
        appSwitchService.updateById(appSwitch);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:appswitch:delete")
    public R delete(@RequestBody Long[] ids) {
        appSwitchService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
