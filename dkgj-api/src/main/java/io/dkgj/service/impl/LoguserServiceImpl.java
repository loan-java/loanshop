package io.dkgj.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.dkgj.common.utils.PageUtils;
import io.dkgj.common.utils.Query;

import io.dkgj.dao.LoguserDao;
import io.dkgj.entity.LoguserEntity;
import io.dkgj.service.LoguserService;


@Service("loguserService")
public class LoguserServiceImpl extends ServiceImpl<LoguserDao, LoguserEntity> implements LoguserService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<LoguserEntity> page = this.page(
                new Query<LoguserEntity>().getPage(params),
                new QueryWrapper<LoguserEntity>()
        );

        return new PageUtils(page);
    }

}
