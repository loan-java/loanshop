package io.dkgj.modules.sys.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.dkgj.common.utils.MakeExcel;
import io.dkgj.common.validator.ValidatorUtils;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import org.apache.catalina.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.dkgj.modules.sys.entity.LogAsoEntity;
import io.dkgj.modules.sys.service.LogAsoService;
import io.dkgj.common.utils.PageUtils;
import io.dkgj.common.utils.R;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-06-24 10:59:11
 */
@RestController
@RequestMapping("sys/logaso")
public class LogAsoController {
    @Autowired
    private LogAsoService logAsoService;

    @GetMapping("/export")
    public void exportExcel(@RequestParam Map<String, Object> params, HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 指定一个地方临时存放生成的 excel 文件，然后后面调用浏览器接口下载完后再删除
        OutputStream os = response.getOutputStream();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/x-download");
        String name = "IDFA" + new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
        String fileName = name + ".xls";
        fileName = URLEncoder.encode(fileName, "UTF-8");
        response.addHeader("Content-Disposition", "attachment;filename=" + fileName);

        // 首行表头信息
        List<String> ll = new ArrayList<>();
        ll.add("ID");
        ll.add("IDFA");
        ll.add("AppID");
        ll.add("IP");
        ll.add("DEVICE");
        ll.add("OS");
        ll.add("KEYWORD");
        ll.add("CHANNEL");
        ll.add("创建时间");
        ll.add("事件");
        // 获取所有用户信息
        String state = (String) params.get("state");
        String createDate = (String) params.get("createDate");
        List<LogAsoEntity> allList = logAsoService.list(new QueryWrapper<LogAsoEntity>()
                .eq(StringUtils.isNotBlank(state), "status", state)
                .eq(StringUtils.isNotBlank(createDate), "date_format(create_date,'%Y/%m/%d')", createDate)
        );
        // 将用户的相关信息遍历到 List<Map<String, Object>> 中
        List<Map<String, Object>> list = new ArrayList<>();
        for (LogAsoEntity entity : allList) {
            Map<String, Object> map = new HashMap<>();
            map.put("ID", entity.getId());
            map.put("IDFA", entity.getIdfa());
            map.put("AppID", entity.getAppid());
            map.put("IP", entity.getIp());
            map.put("DEVICE", entity.getDevice());
            map.put("OS", entity.getOs());
            map.put("KEYWORD", entity.getKeyword());
            map.put("CHANNEL", entity.getChannel());
            map.put("创建时间", entity.getCreateDate());
            map.put("事件", entity.getStatus());
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


    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:logaso:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = logAsoService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:logaso:info")
    public R info(@PathVariable("id") Long id) {
        LogAsoEntity logAso = logAsoService.getById(id);

        return R.ok().put("logAso", logAso);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:logaso:save")
    public R save(@RequestBody LogAsoEntity logAso) {
        logAsoService.save(logAso);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:logaso:update")
    public R update(@RequestBody LogAsoEntity logAso) {
        ValidatorUtils.validateEntity(logAso);
        logAsoService.updateById(logAso);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:logaso:delete")
    public R delete(@RequestBody Long[] ids) {
        logAsoService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
