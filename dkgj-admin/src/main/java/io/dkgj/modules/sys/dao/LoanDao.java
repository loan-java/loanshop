package io.dkgj.modules.sys.dao;

import io.dkgj.modules.sys.entity.LoanEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-03-25 15:45:22
 */
@Mapper
public interface LoanDao extends BaseMapper<LoanEntity> {

    void updateMarketNull(Integer id);
}
