package io.dkgj.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.dkgj.common.utils.PageUtils;
import io.dkgj.common.utils.Query;

import io.dkgj.dao.LogdeviceDao;
import io.dkgj.entity.LogdeviceEntity;
import io.dkgj.service.LogdeviceService;


@Service("logdeviceService")
public class LogdeviceServiceImpl extends ServiceImpl<LogdeviceDao, LogdeviceEntity> implements LogdeviceService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<LogdeviceEntity> page = this.page(
                new Query<LogdeviceEntity>().getPage(params),
                new QueryWrapper<LogdeviceEntity>()
        );

        return new PageUtils(page);
    }

}
