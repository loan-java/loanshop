package io.dkgj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.dkgj.common.utils.PageUtils;
import io.dkgj.entity.MarketEntity;

import java.util.Map;

/**
 * 
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-06-28 15:05:52
 */
public interface MarketService extends IService<MarketEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

