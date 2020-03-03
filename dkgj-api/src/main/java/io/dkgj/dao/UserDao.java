package io.dkgj.dao;

import io.dkgj.entity.UserEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-04-08 16:45:11
 */
@Mapper
public interface UserDao extends BaseMapper<UserEntity> {

    void saveAndReturn(UserEntity user);

    List<UserEntity> findTodayUserByMobile(@Param("mobile") String mobile);
}
