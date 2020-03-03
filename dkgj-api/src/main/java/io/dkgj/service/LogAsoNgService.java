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
 * @date 2019-07-17 14:20:46
 */
public interface LogAsoNgService extends IService<LogAsoNgEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

