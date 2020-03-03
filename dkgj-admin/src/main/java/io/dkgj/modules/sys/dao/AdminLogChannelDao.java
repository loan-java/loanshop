package io.dkgj.modules.sys.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.dkgj.modules.sys.entity.AdminLogChannelEntity;
import io.dkgj.modules.sys.entity.LogChannelEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-03-27 15:10:37
 */
@Mapper
public interface AdminLogChannelDao extends BaseMapper<AdminLogChannelEntity> {
    List<AdminLogChannelEntity> selectAdminLogChannelList(Map<String, Object> params);
}
