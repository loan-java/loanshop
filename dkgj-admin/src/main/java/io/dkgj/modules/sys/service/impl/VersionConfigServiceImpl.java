package io.dkgj.modules.sys.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.dkgj.common.utils.PageUtils;
import io.dkgj.common.utils.Query;

import io.dkgj.modules.sys.dao.VersionConfigDao;
import io.dkgj.modules.sys.entity.VersionConfigEntity;
import io.dkgj.modules.sys.service.VersionConfigService;


@Service("versionConfigService")
public class VersionConfigServiceImpl extends ServiceImpl<VersionConfigDao, VersionConfigEntity> implements VersionConfigService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<VersionConfigEntity> page = this.page(
                new Query<VersionConfigEntity>().getPage(params),
                new QueryWrapper<VersionConfigEntity>()
        );

        return new PageUtils(page);
    }

}
