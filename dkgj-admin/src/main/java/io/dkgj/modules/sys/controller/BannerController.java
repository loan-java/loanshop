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

import io.dkgj.modules.sys.entity.BannerEntity;
import io.dkgj.modules.sys.service.BannerService;
import io.dkgj.common.utils.PageUtils;
import io.dkgj.common.utils.R;



/**
 * 
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-03-27 15:10:37
 */
@RestController
@RequestMapping("sys/banner")
public class BannerController {
    @Autowired
    private BannerService bannerService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:banner:list")
    public R list(@RequestParam Map<String, Object> params){
        params.put("sidx", "createdAt");
        params.put("order", "desc");
        PageUtils page = bannerService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:banner:info")
    public R info(@PathVariable("id") Integer id){
        BannerEntity banner = bannerService.getById(id);

        return R.ok().put("banner", banner);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:banner:save")
    public R save(@RequestBody BannerEntity banner){
        banner.setCreatedat(new Date());
        bannerService.save(banner);
        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:banner:update")
    public R update(@RequestBody BannerEntity banner){
        ValidatorUtils.validateEntity(banner);
        banner.setUpdatedat(new Date());
        bannerService.updateById(banner);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:banner:delete")
    public R delete(@RequestBody Integer[] ids){
        bannerService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
