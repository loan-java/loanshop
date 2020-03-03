package io.dkgj.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.dkgj.common.utils.PageUtils;
import io.dkgj.common.utils.Query;

import io.dkgj.dao.LogVisitDao;
import io.dkgj.entity.LogVisitEntity;
import io.dkgj.service.LogVisitService;


@Service("logVisitService")
public class LogVisitServiceImpl extends ServiceImpl<LogVisitDao, LogVisitEntity> implements LogVisitService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<LogVisitEntity> page = this.page(
                new Query<LogVisitEntity>().getPage(params),
                new QueryWrapper<LogVisitEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveUnique(LogVisitEntity logVisitEntity) {
        this.baseMapper.saveUnique(logVisitEntity);
    }

}
