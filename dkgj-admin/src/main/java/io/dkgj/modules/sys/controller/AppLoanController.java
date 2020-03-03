package io.dkgj.modules.sys.controller;

import java.util.*;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.dkgj.common.validator.ValidatorUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.dkgj.modules.sys.entity.LoanEntity;
import io.dkgj.modules.sys.service.LoanService;
import io.dkgj.common.utils.PageUtils;
import io.dkgj.common.utils.R;


/**
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-03-25 15:45:22
 */
@RestController
@RequestMapping("sys/apploan")
public class AppLoanController {
    @Autowired
    private LoanService loanService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:apploan:list")
    public R list(@RequestParam Map<String, Object> params) {

        String sidx = (String) params.get("sidx");
        if (StringUtils.isBlank(sidx)) {
            params.put("sidx", "createdAt");
            params.put("order", "desc");
        }
        params.put("market", "market");
        PageUtils page = loanService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:apploan:info")
    public R info(@PathVariable("id") Integer id) {
        LoanEntity loan = loanService.getById(id);

        return R.ok().put("loan", loan);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:apploan:save")
    public R save(@RequestBody LoanEntity loan) {

        if (StringUtils.isBlank(loan.getMarket())) {
            return R.error("应用市场对应渠道编码必填");
        }

        loan.setCreatedat(new Date());
        loan.setUpdatedat(new Date());
        loan.setApplyurl(StringEscapeUtils.unescapeHtml(loan.getApplyurl().trim()));
        loanService.save(loan);
        //更新后面产品顺序

        List<LoanEntity> tmp = new ArrayList<LoanEntity>();
        List<LoanEntity> list = loanService.list(new QueryWrapper<LoanEntity>().ge("weights", String.valueOf(Integer.valueOf(loan.getWeights()) + 1)));
        if (list.size() > 0) {
            for (LoanEntity entity : list) {
                entity.setWeights(String.valueOf(Integer.valueOf(entity.getWeights()) + 1));
                tmp.add(entity);
            }
            loanService.updateBatchById(tmp);
        }

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:apploan:update")
    public R update(@RequestBody LoanEntity loan) {
        if (StringUtils.isBlank(loan.getMarket())) {
            return R.error("应用市场对应渠道编码必填");
        }
        ValidatorUtils.validateEntity(loan);
        loan.setUpdatedat(new Date());
        loan.setApplyurl(StringEscapeUtils.unescapeHtml(loan.getApplyurl().trim()));

        loanService.updateById(loan);
        //更新后面产品顺序
        List<LoanEntity> tmp = new ArrayList<LoanEntity>();
        List<LoanEntity> list = loanService.list(new QueryWrapper<LoanEntity>().ge("weights", String.valueOf(Integer.valueOf(loan.getWeights()) + 1)));
        if (list.size() > 0) {
            for (LoanEntity entity : list) {
                entity.setWeights(String.valueOf(Integer.valueOf(entity.getWeights()) + 1));
                tmp.add(entity);
            }
            loanService.updateBatchById(tmp);
        }
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:apploan:delete")
    public R delete(@RequestBody Integer[] ids) {
        loanService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
