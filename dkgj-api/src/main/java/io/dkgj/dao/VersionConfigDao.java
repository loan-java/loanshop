package io.dkgj.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.dkgj.entity.VersionConfigEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-02-25 11:19:36
 */
@Mapper
public interface VersionConfigDao extends BaseMapper<VersionConfigEntity> {

    VersionConfigEntity findNewsVersion(@Param("type") Integer type, @Param("chCode") String chCode);

    VersionConfigEntity findGeneralNewsVersion(@Param("type") Integer type);
}
