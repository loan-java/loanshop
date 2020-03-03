package io.dkgj.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.dkgj.common.utils.PageUtils;
import io.dkgj.common.utils.Query;

import io.dkgj.dao.LogAsoDao;
import io.dkgj.entity.LogAsoEntity;
import io.dkgj.service.LogAsoService;


@Service("logAsoService")
public class LogAsoServiceImpl extends ServiceImpl<LogAsoDao, LogAsoEntity> implements LogAsoService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<LogAsoEntity> page = this.page(
                new Query<LogAsoEntity>().getPage(params),
                new QueryWrapper<LogAsoEntity>()
        );

        return new PageUtils(page);
    }

}
