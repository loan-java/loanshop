package io.dkgj.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.dkgj.common.utils.PageUtils;
import io.dkgj.modules.sys.entity.AdminLogChannelEntity;
import io.dkgj.modules.sys.entity.LogChannelEntity;

import java.util.Map;

/**
 * 
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-03-27 15:10:37
 */
public interface AdminLogChannelService extends IService<AdminLogChannelEntity> {

    PageUtils queryPage(Map<String, Object> params);

    Map<String, Object> selectSum(Map<String, Object> params);

}

