package io.dkgj.modules.sys.dao;

import io.dkgj.modules.sys.entity.LogLoanVistEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * 
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-07-08 15:21:07
 */
@Mapper
public interface LogLoanVistDao extends BaseMapper<LogLoanVistEntity> {

    List<LogLoanVistEntity> listExport(Map<String, Object> params);
}
