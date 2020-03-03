package io.dkgj.dao;

import io.dkgj.entity.LogChannelTodayVistEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 *
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-06-12 19:37:31
 */
@Mapper
public interface LogChannelTodayVistDao extends BaseMapper<LogChannelTodayVistEntity> {

    void saveUnique(LogChannelTodayVistEntity channelTmp);

    void updatePv(Long id);

    void updateUv(Long id);
}
