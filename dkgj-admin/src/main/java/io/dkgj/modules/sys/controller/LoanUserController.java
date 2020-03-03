package io.dkgj.modules.sys.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.dkgj.common.utils.DateUtils;
import io.dkgj.common.utils.MakeExcel;
import io.dkgj.common.validator.ValidatorUtils;
import io.dkgj.modules.sys.entity.LogAsoXmEntity;
import jxl.write.WriteException;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.dkgj.modules.sys.entity.UserEntity;
import io.dkgj.modules.sys.service.UserService;
import io.dkgj.common.utils.PageUtils;
import io.dkgj.common.utils.R;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-03-27 14:11:39
 */
@RestController
@RequestMapping("sys/loanuser")
public class LoanUserController {
    @Autowired
    private UserService userService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:user:list")
    public R list(@RequestParam Map<String, Object> params) {


        String sidx = (String) params.get("sidx");
        if (StringUtils.isBlank(sidx)) {
            params.put("sidx", "created_at");
            params.put("order", "desc");
        }

        PageUtils page = userService.queryPage(params);
        page.setParams(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:user:info")
    public R info(@PathVariable("id") Integer id) {
        UserEntity user = userService.getById(id);

        return R.ok().put("user", user);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:user:save")
    public R save(@RequestBody UserEntity user) {
        userService.save(user);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:user:update")
    public R update(@RequestBody UserEntity user) {
        ValidatorUtils.validateEntity(user);
        userService.updateById(user);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:user:delete")
    public R delete(@RequestBody Integer[] ids) {
        userService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

    @GetMapping("/export")
    public void exportExcel(@RequestParam Map<String, Object> params, HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 指定一个地方临时存放生成的 excel 文件，然后后面调用浏览器接口下载完后再删除
        OutputStream os = response.getOutputStream();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/x-download");
        String name = "USER" + new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date());
        String fileName = name + ".xls";
        fileName = URLEncoder.encode(fileName, "UTF-8");
        response.addHeader("Content-Disposition", "attachment;filename=" + fileName);

        // 首行表头信息
        List<String> ll = new ArrayList<>();
        ll.add("ID");
        ll.add("手机号");
        ll.add("渠道");
        ll.add("注册IP");
        ll.add("最后登录IP");
        ll.add("最后登录时间");
        ll.add("最后登录OS");
        ll.add("创建时间");
        // 获取所有用户信息
        String id = (String) params.get("id");
        String mobile = (String) params.get("mobile");
        String wxId = (String) params.get("wxId");
        String state = (String) params.get("state");
        String createdAt = (String) params.get("createdAt");

        Date beginDate = null;
        Date endDate = null;
        if (StringUtils.isNotBlank(createdAt)) {
            String[] dates = createdAt.split("-");
            beginDate = DateUtils.getStartDayDateTime(dates[0]);
            endDate = DateUtils.getStartDayDateTime(dates[1]);
        }

        List<UserEntity> allList = userService.list(new QueryWrapper<UserEntity>()
                .eq(StringUtils.isNotBlank(id), "id", id)
                .like(StringUtils.isNotBlank(mobile), "mobile", mobile)
                .like(StringUtils.isNotBlank(wxId), "wx_id", wxId)
                .eq(StringUtils.isNotBlank(state), "state", state)
                .between(beginDate != null && endDate != null, "created_at", beginDate, endDate)
        );
        // 将用户的相关信息遍历到 List<Map<String, Object>> 中
        List<Map<String, Object>> list = new ArrayList<>();
        for (UserEntity entity : allList) {
            Map<String, Object> map = new HashMap<>();
            map.put("ID", entity.getId());
            map.put("手机号", entity.getMobile());
            map.put("渠道", entity.getWxId());
            map.put("注册IP", entity.getIp());
            map.put("最后登录IP", entity.getLastLoginIp());
            map.put("最后登录时间", entity.getLastLoginTime());
            map.put("最后登录OS", entity.getLastLoginOs());
            map.put("创建时间", entity.getCreatedAt());
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
