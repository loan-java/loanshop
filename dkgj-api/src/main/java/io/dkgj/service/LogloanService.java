package io.dkgj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.dkgj.common.utils.PageUtils;
import io.dkgj.entity.LogloanEntity;

import java.util.Map;

/**
 * 
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-04-08 14:47:08
 */
public interface LogloanService extends IService<LogloanEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveUnique(LogloanEntity entity);

    LogloanEntity getOneForUpdate(Integer relId, String format);

    void updatePv(Integer id);

    void updateTodayPv(Integer id);

    void updateUv(Integer id);

    void updateTodayUv(Integer id);
}

