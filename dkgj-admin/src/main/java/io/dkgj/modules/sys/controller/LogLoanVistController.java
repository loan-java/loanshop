package io.dkgj.modules.sys.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.dkgj.common.utils.*;
import io.dkgj.common.validator.ValidatorUtils;
import io.dkgj.modules.sys.entity.LoanEntity;
import io.dkgj.modules.sys.entity.UserEntity;
import io.dkgj.modules.sys.service.LoanService;
import io.dkgj.modules.sys.service.UserService;
import jxl.write.WriteException;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.dkgj.modules.sys.entity.LogLoanVistEntity;
import io.dkgj.modules.sys.service.LogLoanVistService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-07-08 15:21:07
 */
@RestController
@RequestMapping("sys/logloanvist")
public class LogLoanVistController {
    @Autowired
    private LogLoanVistService logLoanVistService;

    @Autowired
    private UserService userService;

    @Autowired
    private LoanService loanService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:logloanvist:list")
    public R list(@RequestParam Map<String, Object> params) {
        String createDate = (String) params.get("createDate");
        if (StringUtils.isBlank(createDate)) {
            createDate = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
            params.put("createDate", createDate);
        }

        PageUtils page = logLoanVistService.queryPage(params);
        List<LogLoanVistEntity> tmpList = new ArrayList<>();
        List<LogLoanVistEntity> list = (List<LogLoanVistEntity>) page.getList();
        for (LogLoanVistEntity entity : list) {
            UserEntity userEntity = userService.getById(entity.getUserId());
            if (userEntity != null) {
                entity.setMobile(userEntity.getMobile());
            }
            LoanEntity loanEntity = loanService.getById(entity.getLoanId());
            if (loanEntity != null) {
                entity.setLoanName(loanEntity.getTitle());
            }
            tmpList.add(entity);
        }
        page.setList(tmpList);
        page.setParams(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:logloanvist:info")
    public R info(@PathVariable("id") Long id) {
        LogLoanVistEntity logLoanVist = logLoanVistService.getById(id);

        return R.ok().put("logLoanVist", logLoanVist);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:logloanvist:save")
    public R save(@RequestBody LogLoanVistEntity logLoanVist) {
        logLoanVistService.save(logLoanVist);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:logloanvist:update")
    public R update(@RequestBody LogLoanVistEntity logLoanVist) {
        ValidatorUtils.validateEntity(logLoanVist);
        logLoanVistService.updateById(logLoanVist);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:logloanvist:delete")
    public R delete(@RequestBody Long[] ids) {
        logLoanVistService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }


    @GetMapping("/export")
    public void exportExcel(@RequestParam Map<String, Object> params, HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 指定一个地方临时存放生成的 excel 文件，然后后面调用浏览器接口下载完后再删除
        OutputStream os = response.getOutputStream();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/x-download");
        String name = "LOGLOANVIST" + new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
        String fileName = name + ".xls";
        fileName = URLEncoder.encode(fileName, "UTF-8");
        response.addHeader("Content-Disposition", "attachment;filename=" + fileName);

        // 首行表头信息
        List<String> ll = new ArrayList<>();
        ll.add("ID");
        ll.add("产品名称");
        ll.add("手机号");
        ll.add("点击IP");
        ll.add("用户渠道");
        ll.add("点击时间");

        // 获取所有用户信息
        String createDate = (String) params.get("createDate");
        if (StringUtils.isBlank(createDate)) {
            createDate = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
            params.put("createDate", createDate);
        }

        List<LogLoanVistEntity> allList = logLoanVistService.listExport(params);
        // 将用户的相关信息遍历到 List<Map<String, Object>> 中
        List<Map<String, Object>> list = new ArrayList<>();
        for (LogLoanVistEntity entity : allList) {
            Map<String, Object> map = new HashMap<>();
            map.put("ID", entity.getId());
            map.put("产品名称", entity.getLoanName());
            map.put("手机号", entity.getMobile());
            map.put("点击IP", entity.getIp());
            map.put("用户渠道", entity.getChannel());
            map.put("点击时间", entity.getCreateTime());
            list.add(map);
        }
        try {
            // 第一个参数：表格中的数据
            // 第二个参数：表格保存的路径
            // 第三个参数：表格第二行的列信息
            // 第四个参数：表格第一行的表头信息
            // 参照效果图看会清楚些

            MakeExcel.CreateExcelFile(fileName, os, list, ll, name);
            response.setContentType("application/octet-stream");
            os.flush();
            os.close();
        } catch (WriteException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
