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

import io.dkgj.modules.sys.entity.MarketEntity;
import io.dkgj.modules.sys.service.MarketService;
import io.dkgj.common.utils.PageUtils;
import io.dkgj.common.utils.R;



/**
 * 
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-06-28 15:05:52
 */
@RestController
@RequestMapping("sys/market")
public class MarketController {
    @Autowired
    private MarketService marketService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:market:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = marketService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:market:info")
    public R info(@PathVariable("id") Long id){
        MarketEntity market = marketService.getById(id);

        return R.ok().put("market", market);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:market:save")
    public R save(@RequestBody MarketEntity market){
        market.setCreateDate(new Date());
        marketService.save(market);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:market:update")
    public R update(@RequestBody MarketEntity market){
        ValidatorUtils.validateEntity(market);
        market.setUpdateDate(new Date());
        marketService.updateById(market);
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:market:delete")
    public R delete(@RequestBody Long[] ids){
        marketService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
