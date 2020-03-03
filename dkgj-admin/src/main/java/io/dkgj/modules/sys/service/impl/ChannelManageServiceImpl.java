package io.dkgj.modules.sys.service.impl;

import io.dkgj.commons.dynamic.datasource.annotation.DataSource;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.dkgj.common.utils.PageUtils;
import io.dkgj.common.utils.Query;

import io.dkgj.modules.sys.dao.ChannelManageDao;
import io.dkgj.modules.sys.entity.ChannelManageEntity;
import io.dkgj.modules.sys.service.ChannelManageService;


@Service("channelManageService")
public class ChannelManageServiceImpl extends ServiceImpl<ChannelManageDao, ChannelManageEntity> implements ChannelManageService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {

        String id = (String) params.get("id");
        String channelcode = (String) params.get("channelcode");
        String channelname = (String) params.get("channelname");
        String state = (String) params.get("state");


        IPage<ChannelManageEntity> page = this.page(
                new Query<ChannelManageEntity>().getPage(params),
                new QueryWrapper<ChannelManageEntity>()
                        .eq(StringUtils.isNotBlank(id),"id", id)
                        .eq(StringUtils.isNotBlank(channelcode),"channelcode", channelcode)
                        .eq(StringUtils.isNotBlank(channelname),"channelname", channelname)
                        .eq(StringUtils.isNotBlank(state),"state", state)
        );

        return new PageUtils(page);
    }

}
