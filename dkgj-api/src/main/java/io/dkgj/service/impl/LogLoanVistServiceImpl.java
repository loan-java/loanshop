package io.dkgj.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.dkgj.common.utils.PageUtils;
import io.dkgj.common.utils.Query;

import io.dkgj.dao.LogLoanVistDao;
import io.dkgj.entity.LogLoanVistEntity;
import io.dkgj.service.LogLoanVistService;


@Service("logLoanVistService")
public class LogLoanVistServiceImpl extends ServiceImpl<LogLoanVistDao, LogLoanVistEntity> implements LogLoanVistService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<LogLoanVistEntity> page = this.page(
                new Query<LogLoanVistEntity>().getPage(params),
                new QueryWrapper<LogLoanVistEntity>()
        );

        return new PageUtils(page);
    }

}
