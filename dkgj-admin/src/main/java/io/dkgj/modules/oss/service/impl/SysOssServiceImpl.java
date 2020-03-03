/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 *
 * https://www.demo.com
 *
 * 版权所有，侵权必究！
 */

package io.dkgj.modules.oss.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.dkgj.common.utils.PageUtils;
import io.dkgj.common.utils.Query;
import io.dkgj.commons.dynamic.datasource.annotation.DataSource;
import io.dkgj.modules.oss.dao.SysOssDao;
import io.dkgj.modules.oss.entity.SysOssEntity;
import io.dkgj.modules.oss.service.SysOssService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("sysOssService")
@DataSource("admin")
public class SysOssServiceImpl extends ServiceImpl<SysOssDao, SysOssEntity> implements SysOssService {

	@Override
	public PageUtils queryPage(Map<String, Object> params) {
		IPage<SysOssEntity> page = this.page(
			new Query<SysOssEntity>().getPage(params)
		);

		return new PageUtils(page);
	}
	
}
