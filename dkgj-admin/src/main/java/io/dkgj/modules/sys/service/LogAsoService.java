package io.dkgj.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.dkgj.common.utils.PageUtils;
import io.dkgj.modules.sys.entity.LogAsoEntity;

import java.util.Map;

/**
 * 
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-06-24 10:59:11
 */
public interface LogAsoService extends IService<LogAsoEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

