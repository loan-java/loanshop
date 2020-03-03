package io.dkgj.modules.sys.dao;

import io.dkgj.modules.sys.entity.HistoryLogChannelEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

/**
 * 
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-04-16 12:04:03
 */
@Mapper
public interface HistoryLogChannelDao extends BaseMapper<HistoryLogChannelEntity> {

    Map<String, Object> selectSum(Map<String, Object> params);

    void copyData(Map<String, Object> dataParams);
}
