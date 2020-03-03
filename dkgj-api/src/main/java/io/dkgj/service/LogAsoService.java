package io.dkgj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.dkgj.common.utils.PageUtils;
import io.dkgj.entity.LogAsoEntity;

import java.util.Map;

/**
 * 
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-06-19 14:35:49
 */
public interface LogAsoService extends IService<LogAsoEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

