/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://www.demo.com
 * <p>
 * 版权所有，侵权必究！
 */

package io.dkgj.controller;


import com.alibaba.fastjson.JSONObject;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import io.dkgj.annotation.Login;
import io.dkgj.common.config.AppProperties;
import io.dkgj.common.utils.*;
import io.dkgj.common.validator.ValidatorUtils;
import io.dkgj.config.RedisKeyConfig;
import io.dkgj.form.LoginForm;
import io.dkgj.form.MobileForm;
import io.dkgj.service.TokenService;
import io.dkgj.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Map;

/**
 * 登录接口
 *
 * @author Mark sunlightcs@gmail.com
 */
@RestController
@RequestMapping("/api")
@Api(tags = "登录接口")
@Slf4j
public class ApiLoginController {
    @Autowired
    private UserService userService;
    @Autowired
    private TokenService tokenService;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private AppProperties appProperties;

    @Autowired
    private Producer producer;

    @RequestMapping("captcha.jpg")
    @ApiOperation("生成图片验证码")
    public void captcha(HttpServletResponse response, HttpServletRequest request, @RequestParam("uuid") String uuid) throws IOException {
        HttpSession session = request.getSession();
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");
        //生成文字验证码
        String text = producer.createText();
        //生成图片验证码
        BufferedImage image = producer.createImage(text);

        redisUtils.set(Constants.KAPTCHA_SESSION_KEY + ":" + uuid, text);

        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);
    }

    @PostMapping("v1/getVcode")
//    @ApiOperation("最新手机号登录")
    public R v1getVcode(@RequestParam String mobile,
                        @RequestParam(defaultValue = "rhh", required = false) String chCode,
                        @RequestParam(defaultValue = "易财钱包", required = false) String chName,
                        @RequestParam(defaultValue = "0", required = false) String isH5) {
        log.error(String.format("v1用户：%s，验证码：%s", mobile, "请获取最新版本APP！"));
        return R.error("请获取最新版本APP！");
    }


    @PostMapping("v2/getVcode")
    @ApiOperation("最新手机号登录")
    public R v2getVcode(@RequestParam String mobile,
                        @RequestParam(defaultValue = "rhh", required = false) String chCode,
                        @RequestParam(defaultValue = "易财钱包", required = false) String chName,
                        @RequestParam(defaultValue = "0", required = false) String isH5,
                        @RequestParam String uuid,
                        @RequestParam String imgCode, HttpServletRequest request) {

        String redisImgCode = redisUtils.get(Constants.KAPTCHA_SESSION_KEY + ":" + uuid);
        if (StringUtils.isEmpty(redisImgCode)) {
            return R.error("请先获取图形验证码！");
        }

        if (StringUtils.isEmpty(imgCode)) {
            return R.error("请输入图形验证码！");
        }

        if (!imgCode.equals(redisImgCode)) {
            return R.error("图形验证码不正确！");
        }

        String code = SmsBaseUtils.createRandom(true, 6);
        try {
            if ("1".equals(isH5)) {
                mobile = DESUtil.decode("dkmzz123", "dkmzz123".getBytes(), URLDecoder.decode(mobile, "utf-8"));
            } else {
                mobile = DESUtil.decode("dkmzz123", "dkmzz123".getBytes(), mobile);
            }
        } catch (Exception e) {
            return R.error("解密失败！");
        }
        log.info(String.format("v1用户：%s，验证码：%s", mobile, code));
        try {
            Integer app = appProperties.getApp();
            JSONObject json = null;

            switch (app) {
                case 1:
                    if (StringUtils.isNotBlank(chCode)) {
                        if (chCode.toUpperCase().equals("XLH")) {
                            json = Sms2Utils.sendQdzSms(mobile, chName, String.valueOf(code));
                        } else if (chCode.toUpperCase().equals("MHFQ")) {
                            json = Sms3Utils.sendQdzSms(mobile, chName, String.valueOf(code));
                        } if (chCode.toUpperCase().equals("MDXY")){
                            SmsUtils.sendMdxXinYunSms(mobile,chName,String.valueOf(code));
                        } else {
                            json = SmsUtils.sendQdzSms(mobile, chName, String.valueOf(code));
                        }
                    } else {
                        json = SmsUtils.sendQdzSms(mobile, chName, String.valueOf(code));
                    }

                    String key = String.format("%s%s", RedisKeyConfig.LOGIN_KEY, mobile);
                    //短信验证码第三方
                    redisUtils.set(key, code);
                    if (chCode.toUpperCase().equals("MDXY")){
                        if ("000000".equals(json.getString("errorCode"))) {
                            return R.ok();
                        } else {
                            return R.error(json.getString("errorMsg"));
                        }
                    }else {
                        if ("0".equals(json.getString("code"))) {
                            return R.ok();
                        } else {
                            return R.error(json.getString("msg"));
                        }
                    }
                case 2:
                    if ("秒花呗".equals(chName)) {
                        json = SmsDkmUtils.sendQdzSms(mobile, chName, code);
                    } else {
                        json = SmsDkmUtils.sendQdzSms(mobile, chName, code);
                    }
                    key = String.format("%s%s", RedisKeyConfig.LOGIN_KEY, mobile);

                    //短信验证码第三方
                    redisUtils.set(key, code);
                    if ("0".equals(json.getString("code"))) {
                        return R.ok();
                    } else {
                        return R.error(json.getString("msg"));
                    }
                case 3:
                    json = SmsUtils.sendBeeSms(mobile, chName, String.valueOf(code));
                    key = String.format("%s%s", RedisKeyConfig.LOGIN_KEY, mobile);
                    //短信验证码第三方
                    redisUtils.set(key, code);
                    if ("0".equals(json.getString("code"))) {
                        return R.ok();
                    } else {
                        return R.error(json.getString("message"));
                    }
                case 4:
                    json = SmsUtils.sendMdxXinYunSms(mobile, chName, String.valueOf(code));
                    key = String.format("%s%s", RedisKeyConfig.LOGIN_KEY, mobile);
                    //短信验证码第三方
                    redisUtils.set(key, code);
                    if ("000000".equals(json.getString("errorCode"))) {
                        return R.ok();
                    } else {
                        return R.error(json.getString("errorMsg"));
                    }
                default:
                    json = SmsUtils.sendXunbdSms(appProperties.getAccesskey(), appProperties.getAccessSecret(),
                            appProperties.getSign(), mobile, String.valueOf(code));
                    key = String.format("%s%s", RedisKeyConfig.LOGIN_KEY, mobile);
                    //短信验证码第三方
                    redisUtils.set(key, code);
                    if ("0".equals(json.getString("code"))) {
                        return R.ok();
                    } else {
                        return R.error(json.getString("message"));
                    }
            }

        } catch (Exception e) {
            return R.error(e.getMessage());
        }
    }


    @PostMapping("getVcode")
//    @ApiOperation("手机号登录")
    public R getVcode(@RequestParam String mobile, @RequestParam(defaultValue = "bee", required = false) String chCode, @RequestParam(defaultValue = "米豆信用", required = false) String chName) {
        log.error(String.format("v0用户：%s，验证码：%s", mobile, "请获取最新版本APP！"));
        return R.error("请获取最新版本APP！");
    }

    @PostMapping("mobileRegister")
    @ApiOperation("手机号登录")
    public R mobileRegister(@RequestParam String mobile, @RequestParam String channel, HttpServletRequest request) {
        String ip = IPUtils.getIpAddr(request);
        //用户登录
        Map<String, Object> map = userService.mobileRegister(mobile, channel, ip);
        if (map == null) {
            return R.error("重复提交数据");
        }
        return R.ok(map);
    }

    @PostMapping("mobileLogin")
    @ApiOperation("手机号登录")
    public R mobileLogin(@RequestBody MobileForm form, HttpServletRequest request) {
        //表单校验
        ValidatorUtils.validateEntity(form);
        String ip = IPUtils.getIpAddr(request);

        //用户登录
        Map<String, Object> map = userService.mobileLogin(form, ip);

        return R.ok(map);
    }

    @PostMapping("passwdLogin")
    @ApiOperation("密码登录")
    public R passwdLogin(@RequestBody LoginForm form) {
        //表单校验
        ValidatorUtils.validateEntity(form);

        //用户登录
        Map<String, Object> map = userService.passwdLogin(form);

        return R.ok(map);
    }

    @Login
    @PostMapping("logout")
    @ApiOperation("退出")
    public R logout(@ApiIgnore @RequestAttribute("userId") long userId) {
        tokenService.expireToken(userId);
        return R.ok();
    }

}
