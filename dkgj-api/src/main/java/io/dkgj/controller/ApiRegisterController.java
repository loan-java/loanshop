/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://www.demo.com
 * <p>
 * 版权所有，侵权必究！
 */

package io.dkgj.controller;

import io.dkgj.common.utils.IPUtils;
import io.dkgj.common.utils.R;
import io.dkgj.common.validator.ValidatorUtils;
import io.dkgj.entity.UserEntity;
import io.dkgj.form.RegisterForm;
import io.dkgj.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 注册接口
 *
 * @author Mark sunlightcs@gmail.com
 */
@RestController
@RequestMapping("/api")
@Api(tags = "注册接口")
public class ApiRegisterController {
    @Autowired
    private UserService userService;

    @PostMapping("register")
    @ApiOperation("注册")
    public R register(@RequestBody RegisterForm form, HttpServletRequest request) {
        //表单校验
        ValidatorUtils.validateEntity(form);

        UserEntity user = new UserEntity();
        user.setMobile(form.getMobile());
        user.setPsw(DigestUtils.sha256Hex(form.getPassword()));
        user.setCreatedAt(new Date());
        String ip = IPUtils.getIpAddr(request);
        user.setIp(ip);
        userService.save(user);

        return R.ok();
    }
}
