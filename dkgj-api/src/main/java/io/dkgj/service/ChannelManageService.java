package io.dkgj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.dkgj.common.utils.PageUtils;
import io.dkgj.entity.ChannelManageEntity;

import java.util.Map;

/**
 * 
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-04-16 15:16:27
 */
public interface ChannelManageService extends IService<ChannelManageEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

