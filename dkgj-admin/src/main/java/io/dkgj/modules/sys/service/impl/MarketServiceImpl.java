package io.dkgj.modules.sys.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.dkgj.common.utils.PageUtils;
import io.dkgj.common.utils.Query;

import io.dkgj.modules.sys.dao.MarketDao;
import io.dkgj.modules.sys.entity.MarketEntity;
import io.dkgj.modules.sys.service.MarketService;


@Service("marketService")
public class MarketServiceImpl extends ServiceImpl<MarketDao, MarketEntity> implements MarketService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MarketEntity> page = this.page(
                new Query<MarketEntity>().getPage(params),
                new QueryWrapper<MarketEntity>()
        );

        return new PageUtils(page);
    }

}
