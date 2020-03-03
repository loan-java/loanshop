package io.dkgj.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.dkgj.common.utils.PageUtils;
import io.dkgj.common.utils.Query;

import io.dkgj.dao.LogAsoXmDao;
import io.dkgj.entity.LogAsoNgEntity;
import io.dkgj.service.LogAsoXmService;


@Service("logAsoXmService")
public class LogAsoXmServiceImpl extends ServiceImpl<LogAsoXmDao, LogAsoNgEntity> implements LogAsoXmService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<LogAsoNgEntity> page = this.page(
                new Query<LogAsoNgEntity>().getPage(params),
                new QueryWrapper<LogAsoNgEntity>()
        );

        return new PageUtils(page);
    }

}
