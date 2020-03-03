package io.dkgj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.dkgj.common.utils.PageUtils;
import io.dkgj.entity.LogAsoNgEntity;

import java.util.Map;

/**
 * 
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-07-08 10:23:57
 */
public interface LogAsoXmService extends IService<LogAsoNgEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

