package io.dkgj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.dkgj.common.utils.PageUtils;
import io.dkgj.entity.LogChannelEntity;

import java.util.Map;

/**
 * 
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-04-08 14:47:08
 */
public interface LogChannelService extends IService<LogChannelEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveUnique(LogChannelEntity entity);

    LogChannelEntity getOneForUpdate(Integer id, String format);

    void updateClickNum(Integer id);

    void updateUv(Integer id);

    void updateAppOpenNum(Integer id);

    void updateClickDownNum(Integer id);

    void updateRegNum(Integer id);
}

