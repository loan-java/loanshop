package io.dkgj.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.dkgj.common.utils.PageUtils;
import io.dkgj.common.utils.Query;

import io.dkgj.dao.LogAppLoginDao;
import io.dkgj.entity.LogAppLoginEntity;
import io.dkgj.service.LogAppLoginService;


@Service("logAppLoginService")
public class LogAppLoginServiceImpl extends ServiceImpl<LogAppLoginDao, LogAppLoginEntity> implements LogAppLoginService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<LogAppLoginEntity> page = this.page(
                new Query<LogAppLoginEntity>().getPage(params),
                new QueryWrapper<LogAppLoginEntity>()
        );

        return new PageUtils(page);
    }

}
