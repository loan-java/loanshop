package io.dkgj.modules.sys.controller;

import java.util.Arrays;
import java.util.Map;

import io.dkgj.common.validator.ValidatorUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.dkgj.modules.sys.entity.MLogDeviceEntity;
import io.dkgj.modules.sys.service.MLogDeviceService;
import io.dkgj.common.utils.PageUtils;
import io.dkgj.common.utils.R;



/**
 * 
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-03-27 14:11:39
 */
@RestController
@RequestMapping("sys/mlogdevice")
public class MLogDeviceController {
    @Autowired
    private MLogDeviceService mLogDeviceService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:mlogdevice:list")
    public R list(@RequestParam Map<String, Object> params){

        params.put("sidx", "createdAt");
        params.put("order","desc");
        PageUtils page = mLogDeviceService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:mlogdevice:info")
    public R info(@PathVariable("id") Integer id){
        MLogDeviceEntity mLogDevice = mLogDeviceService.getById(id);

        return R.ok().put("mLogDevice", mLogDevice);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:mlogdevice:save")
    public R save(@RequestBody MLogDeviceEntity mLogDevice){
        mLogDeviceService.save(mLogDevice);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:mlogdevice:update")
    public R update(@RequestBody MLogDeviceEntity mLogDevice){
        ValidatorUtils.validateEntity(mLogDevice);
        mLogDeviceService.updateById(mLogDevice);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:mlogdevice:delete")
    public R delete(@RequestBody Integer[] ids){
        mLogDeviceService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
