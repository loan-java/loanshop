/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://www.demo.com
 * <p>
 * 版权所有，侵权必究！
 */

package io.dkgj.controller;

import com.alibaba.fastjson.JSONObject;
import io.dkgj.annotation.Login;
import io.dkgj.annotation.LoginUser;
import io.dkgj.common.utils.R;
import io.dkgj.controller.vo.ResponseVO;
import io.dkgj.entity.UserEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 测试接口
 *
 * @author Mark sunlightcs@gmail.com
 */
@RestController
@RequestMapping("/api")
@Api(tags = "测试接口")
@Slf4j
public class ApiTestController {

    @Login
    @GetMapping("userInfo")
    @ApiOperation(value = "获取用户信息", response = UserEntity.class)
    public R userInfo(@ApiIgnore @LoginUser UserEntity user) {
        return R.ok().put("user", user);
    }

    @Login
    @GetMapping("userId")
    @ApiOperation("获取用户ID")
    public R userInfo(@ApiIgnore @RequestAttribute("userId") Integer userId) {
        return R.ok().put("userId", userId);
    }

    @GetMapping("notToken")
    @ApiOperation("忽略Token验证测试")
    public R notToken() {
        return R.ok().put("msg", "无需token也能访问。。。");
    }

    @PostMapping("notifyXinYan")
    public String notifyXinYan(@RequestBody ResponseVO vo) {
        log.info(JSONObject.toJSONString(vo));
        return "200";
    }
}
