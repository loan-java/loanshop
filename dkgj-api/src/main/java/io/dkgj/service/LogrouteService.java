package io.dkgj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.dkgj.common.utils.PageUtils;
import io.dkgj.entity.LogrouteEntity;

import java.util.Map;

/**
 * 
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-04-08 14:47:08
 */
public interface LogrouteService extends IService<LogrouteEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

