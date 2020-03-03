package io.dkgj.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.dkgj.common.utils.PageUtils;
import io.dkgj.common.utils.Query;

import io.dkgj.dao.LogrouteDao;
import io.dkgj.entity.LogrouteEntity;
import io.dkgj.service.LogrouteService;


@Service("logrouteService")
public class LogrouteServiceImpl extends ServiceImpl<LogrouteDao, LogrouteEntity> implements LogrouteService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<LogrouteEntity> page = this.page(
                new Query<LogrouteEntity>().getPage(params),
                new QueryWrapper<LogrouteEntity>()
        );

        return new PageUtils(page);
    }

}
