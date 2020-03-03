package io.dkgj.modules.sys.dao;

import io.dkgj.modules.sys.entity.LogloanEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-03-27 10:12:15
 */
@Mapper
public interface LogloanDao extends BaseMapper<LogloanEntity> {

    Map<String, Object> selectSum(Map<String, Object> params);

    List<LogloanEntity> selectLogloanlList(Map<String, Object> params);
}
