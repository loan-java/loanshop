package io.dkgj.dao;

import io.dkgj.entity.LogloanEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-04-08 14:47:08
 */
@Mapper
public interface LogloanDao extends BaseMapper<LogloanEntity> {

    void saveUnique(LogloanEntity entity);

    LogloanEntity getOneForUpdate(@Param("relId") Integer relId, @Param("nowDay") String nowDay);

    void updatePv(Integer id);

    void updateTodayPv(Integer id);

    void updateUv(Integer id);
    void downLoanIfUvMax(Integer id);

    void updateTodayUv(Integer id);
}
