package io.dkgj.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.dkgj.common.utils.PageUtils;
import io.dkgj.modules.sys.entity.MLogDeviceEntity;

import java.util.Map;

/**
 * 
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-03-27 14:11:39
 */
public interface MLogDeviceService extends IService<MLogDeviceEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

