package io.dkgj.modules.sys.controller;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.dkgj.common.utils.DateUtils;
import io.dkgj.common.validator.ValidatorUtils;
import io.dkgj.modules.sys.entity.LoanEntity;
import io.dkgj.modules.sys.service.LoanService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.dkgj.modules.sys.entity.LogloanEntity;
import io.dkgj.modules.sys.service.LogloanService;
import io.dkgj.common.utils.PageUtils;
import io.dkgj.common.utils.R;


/**
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-03-27 10:12:15
 */
@RestController
@RequestMapping("sys/logloan")
public class LogloanController {
    @Autowired
    private LogloanService logloanService;

    @Autowired
    private LoanService loanService;


    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:logloan:list")
    public R list(@RequestParam Map<String, Object> params) {
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
        PageUtils page = logloanService.queryPage(params);
        page.setParams(params);

        List<LogloanEntity> tmp = (List<LogloanEntity>) page.getList();
        List<LogloanEntity> data = new ArrayList<>();
        for (LogloanEntity tmpEntity : tmp) {
            LogloanEntity entity = new LogloanEntity();
            BeanUtils.copyProperties(tmpEntity, entity);
            if(null == entity.getRelid()) {
                continue;
            }
            LoanEntity loanEntity = loanService.getById(entity.getRelid());
            entity.setLoanName(loanEntity.getTitle());
            data.add(entity);
        }
        page.setList(data);
        params.remove("page");
        params.remove("limit");
        Map<String, Object> result = logloanService.selectSum(params);
        if (page != null) {
            page.setUvNum(0);
            page.setPvNum(0);
            page.setTodayUvSum(0);
            page.setTodayPvSum(0);
            if (result != null && result.get("uvNum") != null) {
                page.setUvNum(((BigDecimal) result.get("uvNum")).intValue());
            }
            if (result != null && result.get("pvNum") != null) {
                page.setPvNum(((BigDecimal) result.get("pvNum")).intValue());
            }
            if (result != null && result.get("todayUvSum") != null) {
                page.setTodayUvSum(((BigDecimal) result.get("todayUvSum")).intValue());
            }
            if (result != null && result.get("todayPvSum") != null) {
                page.setTodayPvSum(((BigDecimal) result.get("todayPvSum")).intValue());
            }
        }
        return R.ok().put("page", page);
    }

    @RequestMapping("/get_options")
    public R getOptions() {
        List<LoanEntity> list = loanService.list(new QueryWrapper<LoanEntity>().eq(
                "state", 1
        ));
        return R.ok().put("data", list);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:logloan:info")
    public R info(@PathVariable("id") Integer id) {
        LogloanEntity logloan = logloanService.getById(id);

        return R.ok().put("logloan", logloan);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:logloan:save")
    public R save(@RequestBody LogloanEntity logloan) {
        logloanService.save(logloan);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:logloan:update")
    public R update(@RequestBody LogloanEntity logloan) {
        ValidatorUtils.validateEntity(logloan);
        logloanService.updateById(logloan);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:logloan:delete")
    public R delete(@RequestBody Integer[] ids) {
        logloanService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
