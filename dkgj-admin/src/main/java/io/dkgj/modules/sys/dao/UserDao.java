package io.dkgj.modules.sys.dao;

import io.dkgj.modules.sys.entity.UserEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-03-27 14:11:39
 */
@Mapper
public interface UserDao extends BaseMapper<UserEntity> {
	
}
