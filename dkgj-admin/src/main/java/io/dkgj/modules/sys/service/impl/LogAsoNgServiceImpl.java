package io.dkgj.modules.sys.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.dkgj.common.utils.PageUtils;
import io.dkgj.common.utils.Query;

import io.dkgj.modules.sys.dao.LogAsoNgDao;
import io.dkgj.modules.sys.entity.LogAsoNgEntity;
import io.dkgj.modules.sys.service.LogAsoNgService;


@Service("logAsoNgService")
public class LogAsoNgServiceImpl extends ServiceImpl<LogAsoNgDao, LogAsoNgEntity> implements LogAsoNgService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<LogAsoNgEntity> page = this.page(
                new Query<LogAsoNgEntity>().getPage(params),
                new QueryWrapper<LogAsoNgEntity>()
        );

        return new PageUtils(page);
    }

}
