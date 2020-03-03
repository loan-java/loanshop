package io.dkgj.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.dkgj.common.utils.PageUtils;
import io.dkgj.common.utils.Query;

import io.dkgj.dao.LogChannelDao;
import io.dkgj.entity.LogChannelEntity;
import io.dkgj.service.LogChannelService;


@Service("logChannelService")
public class LogChannelServiceImpl extends ServiceImpl<LogChannelDao, LogChannelEntity> implements LogChannelService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<LogChannelEntity> page = this.page(
                new Query<LogChannelEntity>().getPage(params),
                new QueryWrapper<LogChannelEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveUnique(LogChannelEntity entity) {
        this.baseMapper.saveUnique(entity);
    }

    @Override
    public LogChannelEntity getOneForUpdate(Integer channelId, String nowDay) {
        return this.baseMapper.getOneForUpdate(channelId,nowDay);
    }

    @Override
    public void updateClickNum(Integer id) {
        this.baseMapper.updateClickNum(id);
    }

    @Override
    public void updateUv(Integer id) {
        this.baseMapper.updateUv(id);
    }

    @Override
    public void updateAppOpenNum(Integer id) {
        this.baseMapper.updateAppOpenNum(id);
    }

    @Override
    public void updateClickDownNum(Integer id) {
        this.baseMapper.updateClickDownNum(id);
    }

    @Override
    public void updateRegNum(Integer id) {
        this.baseMapper.updateRegNum(id);
    }

}
