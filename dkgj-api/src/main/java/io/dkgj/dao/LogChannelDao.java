package io.dkgj.dao;

import io.dkgj.entity.LogChannelEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-04-08 14:47:08
 */
@Mapper
public interface LogChannelDao extends BaseMapper<LogChannelEntity> {

    void saveUnique(LogChannelEntity entity);

    LogChannelEntity getOneForUpdate(@Param("channelId") Integer channelId, @Param("nowDay") String nowDay);

    void updateUv(Integer id);

    void updateClickNum(Integer id);

    void updateAppOpenNum(Integer id);

    void updateClickDownNum(Integer id);

    void updateRegNum(Integer id);
}
