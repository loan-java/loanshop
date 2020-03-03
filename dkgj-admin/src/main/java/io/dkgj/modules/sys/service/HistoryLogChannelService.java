package io.dkgj.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.dkgj.common.utils.PageUtils;
import io.dkgj.modules.sys.entity.HistoryLogChannelEntity;

import java.util.Map;

/**
 * 
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-04-16 12:04:03
 */
public interface HistoryLogChannelService extends IService<HistoryLogChannelEntity> {

    PageUtils queryPage(Map<String, Object> params);

    Map<String, Object> selectSum(Map<String, Object> params);

    void copyData(Map<String, Object> dataParams);
}

