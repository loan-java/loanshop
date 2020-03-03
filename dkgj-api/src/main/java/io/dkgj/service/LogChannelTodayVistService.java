package io.dkgj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.dkgj.common.utils.PageUtils;
import io.dkgj.entity.LogChannelTodayVistEntity;

import java.util.Map;

/**
 *
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-06-12 19:37:31
 */
public interface LogChannelTodayVistService extends IService<LogChannelTodayVistEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveUnique(LogChannelTodayVistEntity channelTmp);

    void updatePv(Long id);

    void updateUv(Long id);
}

