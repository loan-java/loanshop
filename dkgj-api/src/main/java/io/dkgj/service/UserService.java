/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.demo.com
 *
 * 版权所有，侵权必究！
 */

package io.dkgj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.dkgj.entity.UserEntity;
import io.dkgj.form.LoginForm;
import io.dkgj.form.MobileForm;

import java.util.Map;

/**
 * 用户
 *
 * @author Mark sunlightcs@gmail.com
 */
public interface UserService extends IService<UserEntity> {

	UserEntity queryByMobile(String mobile);

	/**
	 * 用户登录
	 * @param form    登录表单
	 * @return        返回登录信息
	 */
	Map<String, Object> passwdLogin(LoginForm form);

	Map<String, Object> mobileLogin(MobileForm form,String ip);

	boolean isTodayUser(String mobile);

	Map<String, Object> mobileRegister(String mobile, String channel,String ip);
}
