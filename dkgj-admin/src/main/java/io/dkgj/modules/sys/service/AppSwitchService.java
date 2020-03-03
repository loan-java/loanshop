package io.dkgj.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.dkgj.common.utils.PageUtils;
import io.dkgj.modules.sys.entity.AppSwitchEntity;

import java.util.Map;

/**
 * 
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-07-01 17:48:42
 */
public interface AppSwitchService extends IService<AppSwitchEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

