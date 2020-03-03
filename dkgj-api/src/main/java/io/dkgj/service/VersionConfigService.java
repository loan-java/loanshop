package io.dkgj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.dkgj.common.utils.PageUtils;
import io.dkgj.entity.VersionConfigEntity;

import java.util.Map;

/**
 * 
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-02-25 11:19:36
 */
public interface VersionConfigService extends IService<VersionConfigEntity> {

    PageUtils queryPage(Map<String, Object> params);

    VersionConfigEntity findNewsVersion(Integer type,String chCode);

    VersionConfigEntity findNewsVersion(Integer type);
}

