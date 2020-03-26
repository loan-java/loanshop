/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://www.demo.com
 * <p>
 * 版权所有，侵权必究！
 */

package io.dkgj.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.dkgj.common.exception.RRException;
import io.dkgj.common.utils.RedisUtils;
import io.dkgj.common.validator.Assert;
import io.dkgj.config.RedisKeyConfig;
import io.dkgj.dao.UserDao;
import io.dkgj.entity.ChannelManageEntity;
import io.dkgj.entity.LogChannelEntity;
import io.dkgj.entity.TokenEntity;
import io.dkgj.entity.UserEntity;
import io.dkgj.form.LoginForm;
import io.dkgj.form.MobileForm;
import io.dkgj.service.ChannelManageService;
import io.dkgj.service.LogChannelService;
import io.dkgj.service.TokenService;
import io.dkgj.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDao, UserEntity> implements UserService {
    @Autowired
    private TokenService tokenService;

    @Autowired
    private ChannelManageService channelManageService;

    @Autowired
    private LogChannelService logChannelService;

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public UserEntity queryByMobile(String mobile) {
        List<UserEntity> list = baseMapper.selectList(new QueryWrapper<UserEntity>().eq("mobile", mobile));
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }


    @Override
    public Map<String, Object> mobileLogin(MobileForm form, String ip) {
        String key = String.format("%s%s", RedisKeyConfig.LOGIN_KEY, form.getMobile());
        String redisCode = redisUtils.get(key);
        if (StringUtils.isBlank(redisCode)) {
            throw new RRException("请输入短信验证码");
        }
        //密码错误
        if (!redisCode.equals(form.getVcode())) {
            throw new RRException("短信验证码错误");
        }

        UserEntity user = queryByMobile(form.getMobile());

        if (user == null) { //用户注册

            //渠道用户注册数据上传
            ChannelManageEntity channelManageEntity = channelManageService.getOne(new QueryWrapper<ChannelManageEntity>().eq("channelCode", form.getChannel()));
            user = new UserEntity();
            user.setMobile(form.getMobile());
            user.setVcode(form.getVcode());

            if (channelManageEntity != null) {
                user.setWxId(channelManageEntity.getChannelcode());
            } else {
                user.setWxId("");
            }
            user.setCreatedAt(new Date());
            user.setUpdatedat(new Date());
            user.setIp(ip);


            baseMapper.saveAndReturn(user);
            if (channelManageEntity != null) {
                LogChannelEntity entity = logChannelService.getOneForUpdate(channelManageEntity.getId(), new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                if (entity == null) {
                    entity = new LogChannelEntity();
                    entity.setChannel(String.valueOf(channelManageEntity.getId()));
                    entity.setCreatedat(new Date());
                    entity.setUpdatedat(new Date());
                    entity.setRegnum(1);
                    entity.setClicknum(1);
                    entity.setClickdownnum(1);
                    entity.setAppOpenNum(1);
                    entity.setUvNum(1);
                    logChannelService.saveUnique(entity);
                } else {

                    //entity.setRegnum((entity.getRegnum() == null ? 0 : entity.getRegnum()) + 1);
                    logChannelService.updateRegNum(entity.getId());
                    //logChannelService.updateById(entity);
                }
            }
        }
        Assert.isNull(user, "手机号或密码错误");
        user.setLastLoginIp(ip);
        user.setLastLoginTime(new Date());
        user.setLastLoginOs(form.getOs());
        user.setUpdatedat(new Date());
        baseMapper.updateById(user);
        //获取登录token
        TokenEntity tokenEntity = tokenService.createToken(user.getId());

        Map<String, Object> map = new HashMap<>(2);
        map.put("token", tokenEntity.getToken());
        map.put("expire", tokenEntity.getExpireTime().getTime() - System.currentTimeMillis());
        return map;
    }

    @Override
    public boolean isTodayUser(String mobile) {
        List<UserEntity> list = this.baseMapper.findTodayUserByMobile(mobile);
        return list.size() > 0;
    }

    @Override
    public Map<String, Object> passwdLogin(LoginForm form) {
        UserEntity user = queryByMobile(form.getMobile());
        Assert.isNull(user, "手机号或密码错误");

        //密码错误
        if (!user.getPsw().equals(DigestUtils.sha256Hex(form.getPassword()))) {
            throw new RRException("手机号或密码错误");
        }

        //获取登录token
        TokenEntity tokenEntity = tokenService.createToken(user.getId());

        Map<String, Object> map = new HashMap<>(2);
        map.put("token", tokenEntity.getToken());
        map.put("expire", tokenEntity.getExpireTime().getTime() - System.currentTimeMillis());

        return map;
    }

    @Override
    public Map<String, Object> mobileRegister(String mobile, String channel, String ip) {
        UserEntity user = queryByMobile(mobile);

        if (user == null) { //用户注册

            //渠道用户注册数据上传
            ChannelManageEntity channelManageEntity = channelManageService.getOne(new QueryWrapper<ChannelManageEntity>().eq("channelCode", channel));
            user = new UserEntity();
            user.setMobile(mobile);

            if (channelManageEntity != null) {
                user.setWxId(channelManageEntity.getChannelcode());
            } else {
                user.setWxId("");
            }
            user.setCreatedAt(new Date());
            user.setUpdatedat(new Date());
            user.setIp(ip);


            baseMapper.saveAndReturn(user);
            if (channelManageEntity != null) {
                LogChannelEntity entity = logChannelService.getOneForUpdate(channelManageEntity.getId(), new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                if (entity == null) {
                    entity = new LogChannelEntity();
                    entity.setChannel(String.valueOf(channelManageEntity.getId()));
                    entity.setCreatedat(new Date());
                    entity.setUpdatedat(new Date());
                    entity.setRegnum(1);
                    entity.setClicknum(1);
                    entity.setClickdownnum(1);
                    entity.setAppOpenNum(1);
                    entity.setUvNum(1);
                    logChannelService.saveUnique(entity);
                } else {

                    //entity.setRegnum((entity.getRegnum() == null ? 0 : entity.getRegnum()) + 1);
                    logChannelService.updateRegNum(entity.getId());
                    //logChannelService.updateById(entity);
                }
            }
        }
        Assert.isNull(user, "手机号或密码错误");
        user.setLastLoginIp(ip);
        user.setLastLoginTime(new Date());
        user.setLastLoginOs(1);
        user.setUpdatedat(new Date());
        if (Objects.isNull(user.getWxId())) {
            ChannelManageEntity channelManageEntity = channelManageService.getOne(new QueryWrapper<ChannelManageEntity>().eq("channelCode", channel));
            if (channelManageEntity != null) {
                user.setWxId(channelManageEntity.getChannelcode());
            }
        }
        baseMapper.updateById(user);
        //获取登录token
        TokenEntity tokenEntity = tokenService.createToken(user.getId());

        Map<String, Object> map = new HashMap<>(2);
        map.put("token", tokenEntity.getToken());
        map.put("expire", tokenEntity.getExpireTime().getTime() - System.currentTimeMillis());
        return map;
    }

}
