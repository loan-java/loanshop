package io.dkgj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.dkgj.common.utils.PageUtils;
import io.dkgj.entity.LogAppLoginEntity;

import java.util.Map;

/**
 *
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-06-12 14:19:52
 */
public interface LogAppLoginService extends IService<LogAppLoginEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

