package io.dkgj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.dkgj.common.utils.PageUtils;
import io.dkgj.entity.MLogDeviceEntity;

import java.util.Map;

/**
 * 
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-04-08 15:09:22
 */
public interface MLogDeviceService extends IService<MLogDeviceEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void saveUnique(MLogDeviceEntity mLogDeviceEntity);

    MLogDeviceEntity getOneForUpdate(String uuid);
}

