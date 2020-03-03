package io.dkgj.modules.sys.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.dkgj.common.utils.PageUtils;
import io.dkgj.common.utils.Query;

import io.dkgj.modules.sys.dao.AppSwitchDao;
import io.dkgj.modules.sys.entity.AppSwitchEntity;
import io.dkgj.modules.sys.service.AppSwitchService;


@Service("appSwitchService")
public class AppSwitchServiceImpl extends ServiceImpl<AppSwitchDao, AppSwitchEntity> implements AppSwitchService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AppSwitchEntity> page = this.page(
                new Query<AppSwitchEntity>().getPage(params),
                new QueryWrapper<AppSwitchEntity>()
        );

        return new PageUtils(page);
    }

}
