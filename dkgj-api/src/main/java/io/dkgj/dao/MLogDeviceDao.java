package io.dkgj.dao;

import io.dkgj.entity.MLogDeviceEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-04-08 15:09:22
 */
@Mapper
public interface MLogDeviceDao extends BaseMapper<MLogDeviceEntity> {

    void saveUnique(MLogDeviceEntity mLogDeviceEntity);

    MLogDeviceEntity getOneForUpdate(String uuid);
}
