package io.dkgj.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.dkgj.common.utils.PageUtils;
import io.dkgj.common.utils.Query;

import io.dkgj.dao.LogChannelTodayVistDao;
import io.dkgj.entity.LogChannelTodayVistEntity;
import io.dkgj.service.LogChannelTodayVistService;


@Service("logChannelTodayVistService")
public class LogChannelTodayVistServiceImpl extends ServiceImpl<LogChannelTodayVistDao, LogChannelTodayVistEntity> implements LogChannelTodayVistService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<LogChannelTodayVistEntity> page = this.page(
                new Query<LogChannelTodayVistEntity>().getPage(params),
                new QueryWrapper<LogChannelTodayVistEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveUnique(LogChannelTodayVistEntity channelTmp) {
        this.baseMapper.saveUnique(channelTmp);
    }

    @Override
    public void updatePv(Long id) {
        this.baseMapper.updatePv(id);
    }

    @Override
    public void updateUv(Long id) {
        this.baseMapper.updateUv(id);
    }

}
