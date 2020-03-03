package io.dkgj.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.dkgj.common.utils.PageUtils;
import io.dkgj.common.utils.Query;

import io.dkgj.dao.LogupsertDao;
import io.dkgj.entity.LogupsertEntity;
import io.dkgj.service.LogupsertService;


@Service("logupsertService")
public class LogupsertServiceImpl extends ServiceImpl<LogupsertDao, LogupsertEntity> implements LogupsertService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<LogupsertEntity> page = this.page(
                new Query<LogupsertEntity>().getPage(params),
                new QueryWrapper<LogupsertEntity>()
        );

        return new PageUtils(page);
    }

}
