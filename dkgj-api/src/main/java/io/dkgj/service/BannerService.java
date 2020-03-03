package io.dkgj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.dkgj.common.utils.PageUtils;
import io.dkgj.entity.BannerEntity;

import java.util.Map;

/**
 * 
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-04-08 10:59:05
 */
public interface BannerService extends IService<BannerEntity> {
    public PageUtils queryPage(Map<String, Object> params);
}

