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

import io.dkgj.modules.sys.entity.VersionConfigEntity;
import io.dkgj.modules.sys.service.VersionConfigService;
import io.dkgj.common.utils.PageUtils;
import io.dkgj.common.utils.R;


/**
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-07-01 17:48:42
 */
@RestController
@RequestMapping("sys/versionconfig")
public class VersionConfigController {
    @Autowired
    private VersionConfigService versionConfigService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:versionconfig:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = versionConfigService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:versionconfig:info")
    public R info(@PathVariable("id") Long id) {
        VersionConfigEntity versionConfig = versionConfigService.getById(id);

        return R.ok().put("versionConfig", versionConfig);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:versionconfig:save")
    public R save(@RequestBody VersionConfigEntity versionConfig) {
        versionConfig.setCreateDate(new Date());
        versionConfigService.save(versionConfig);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:versionconfig:update")
    public R update(@RequestBody VersionConfigEntity versionConfig) {
        ValidatorUtils.validateEntity(versionConfig);
        versionConfig.setUpdateDate(new Date());
        versionConfigService.updateById(versionConfig);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:versionconfig:delete")
    public R delete(@RequestBody Long[] ids) {
        versionConfigService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
