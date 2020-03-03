package io.dkgj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.dkgj.common.utils.PageUtils;
import io.dkgj.entity.PopupEntity;

import java.util.Map;

/**
 * 
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-06-05 19:04:34
 */
public interface PopupService extends IService<PopupEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

