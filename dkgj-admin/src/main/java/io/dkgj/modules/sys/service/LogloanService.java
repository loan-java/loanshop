package io.dkgj.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.dkgj.common.utils.PageUtils;
import io.dkgj.modules.sys.entity.LogloanEntity;

import java.util.Map;

/**
 * 
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-03-27 10:12:15
 */
public interface LogloanService extends IService<LogloanEntity> {

    PageUtils queryPage(Map<String, Object> params);

    Map<String, Object> selectSum(Map<String, Object> params);
}

