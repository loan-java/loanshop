package io.dkgj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.dkgj.common.utils.PageUtils;
import io.dkgj.entity.LogVisitSourceEntity;

import java.util.Map;

/**
 * 
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-06-06 16:12:44
 */
public interface LogVisitSourceService extends IService<LogVisitSourceEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

