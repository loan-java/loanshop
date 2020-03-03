package io.dkgj.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.dkgj.common.utils.PageUtils;
import io.dkgj.common.utils.Query;

import io.dkgj.dao.ChannelManageDao;
import io.dkgj.entity.ChannelManageEntity;
import io.dkgj.service.ChannelManageService;


@Service("channelManageService")
public class ChannelManageServiceImpl extends ServiceImpl<ChannelManageDao, ChannelManageEntity> implements ChannelManageService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ChannelManageEntity> page = this.page(
                new Query<ChannelManageEntity>().getPage(params),
                new QueryWrapper<ChannelManageEntity>()
        );

        return new PageUtils(page);
    }

}
