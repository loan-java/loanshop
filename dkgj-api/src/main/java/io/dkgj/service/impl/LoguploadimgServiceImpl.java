package io.dkgj.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.dkgj.common.utils.PageUtils;
import io.dkgj.common.utils.Query;

import io.dkgj.dao.LoguploadimgDao;
import io.dkgj.entity.LoguploadimgEntity;
import io.dkgj.service.LoguploadimgService;


@Service("loguploadimgService")
public class LoguploadimgServiceImpl extends ServiceImpl<LoguploadimgDao, LoguploadimgEntity> implements LoguploadimgService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<LoguploadimgEntity> page = this.page(
                new Query<LoguploadimgEntity>().getPage(params),
                new QueryWrapper<LoguploadimgEntity>()
        );

        return new PageUtils(page);
    }

}
