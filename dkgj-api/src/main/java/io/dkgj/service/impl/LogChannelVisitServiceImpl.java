package io.dkgj.service.impl;

import org.springframework.stereotype.Service;

import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.dkgj.common.utils.PageUtils;
import io.dkgj.common.utils.Query;

import io.dkgj.dao.LogChannelVisitDao;
import io.dkgj.entity.LogChannelVisitEntity;
import io.dkgj.service.LogChannelVisitService;


@Service("logChannelVisitService")
public class LogChannelVisitServiceImpl extends ServiceImpl<LogChannelVisitDao, LogChannelVisitEntity> implements LogChannelVisitService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<LogChannelVisitEntity> page = this.page(
                new Query<LogChannelVisitEntity>().getPage(params),
                new QueryWrapper<LogChannelVisitEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveUnique(LogChannelVisitEntity logChannelVisitEntity) {
        this.baseMapper.saveUnique(logChannelVisitEntity);
    }

}
