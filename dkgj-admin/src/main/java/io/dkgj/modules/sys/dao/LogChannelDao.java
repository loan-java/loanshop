package io.dkgj.modules.sys.dao;

import io.dkgj.common.utils.PageUtils;
import io.dkgj.modules.sys.entity.LogChannelEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import javafx.scene.control.Pagination;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-03-27 15:10:37
 */
@Mapper
public interface LogChannelDao extends BaseMapper<LogChannelEntity> {

    Map<String, Object> selectSum(Map<String, Object> params);

    List<LogChannelEntity> selectLogChannelList(Map<String, Object> params);
}
