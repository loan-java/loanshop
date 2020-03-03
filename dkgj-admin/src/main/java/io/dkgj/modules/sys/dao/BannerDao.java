package io.dkgj.modules.sys.dao;

import io.dkgj.modules.sys.entity.BannerEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * 
 * 
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-03-27 15:10:37
 */
@Mapper
public interface BannerDao extends BaseMapper<BannerEntity> {
    @Update("update banner set state = #{state} where loan_id = #{loanId}")
    int updateStateByLoanId(@Param("loanId") Integer loanId, @Param("state")  Integer state);
}
