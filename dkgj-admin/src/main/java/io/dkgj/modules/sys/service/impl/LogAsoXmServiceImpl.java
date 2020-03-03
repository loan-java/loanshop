package io.dkgj.modules.sys.service.impl;

import io.dkgj.modules.sys.entity.LogAsoEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.dkgj.common.utils.PageUtils;
import io.dkgj.common.utils.Query;

import io.dkgj.modules.sys.dao.LogAsoXmDao;
import io.dkgj.modules.sys.entity.LogAsoXmEntity;
import io.dkgj.modules.sys.service.LogAsoXmService;


@Service("logAsoXmService")
public class LogAsoXmServiceImpl extends ServiceImpl<LogAsoXmDao, LogAsoXmEntity> implements LogAsoXmService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String state = (String) params.get("state");
        String createDate = (String) params.get("createDate");

        IPage<LogAsoXmEntity> page = this.page(
                new Query<LogAsoXmEntity>().getPage(params),
                new QueryWrapper<LogAsoXmEntity>()
                        .eq(StringUtils.isNotBlank(state), "status", state)
                        .eq(StringUtils.isNotBlank(createDate), "date_format(create_date,'%Y/%m/%d')", createDate)
        );

        return new PageUtils(page);
    }

}
