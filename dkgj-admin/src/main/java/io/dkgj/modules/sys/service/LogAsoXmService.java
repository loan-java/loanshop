package io.dkgj.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.dkgj.common.utils.PageUtils;
import io.dkgj.modules.sys.entity.LogAsoXmEntity;

import java.util.Map;

/**
 * 
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-07-08 10:23:57
 */
public interface LogAsoXmService extends IService<LogAsoXmEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

