package io.dkgj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.dkgj.common.utils.PageUtils;
import io.dkgj.entity.LogChannelVisitEntity;

import java.util.Map;

/**
 * 
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-04-24 21:28:45
 */
public interface LogChannelVisitService extends IService<LogChannelVisitEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveUnique(LogChannelVisitEntity logChannelVisitEntity);
}

