package io.dkgj.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.dkgj.common.utils.PageUtils;
import io.dkgj.common.utils.Query;

import io.dkgj.dao.LogVisitSourceDao;
import io.dkgj.entity.LogVisitSourceEntity;
import io.dkgj.service.LogVisitSourceService;


@Service("logVisitSourceService")
public class LogVisitSourceServiceImpl extends ServiceImpl<LogVisitSourceDao, LogVisitSourceEntity> implements LogVisitSourceService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<LogVisitSourceEntity> page = this.page(
                new Query<LogVisitSourceEntity>().getPage(params),
                new QueryWrapper<LogVisitSourceEntity>()
        );

        return new PageUtils(page);
    }

}
