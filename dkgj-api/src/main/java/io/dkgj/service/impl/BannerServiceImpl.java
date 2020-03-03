package io.dkgj.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.dkgj.common.utils.PageUtils;
import io.dkgj.common.utils.Query;
import io.dkgj.dao.BannerDao;
import io.dkgj.entity.BannerEntity;
import io.dkgj.entity.LoanEntity;
import io.dkgj.service.BannerService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("bannerService")
public class BannerServiceImpl extends ServiceImpl<BannerDao, BannerEntity> implements BannerService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<BannerEntity> page = this.page(
                new Query<BannerEntity>().getPage(params),
                new QueryWrapper<BannerEntity>().eq("state",1)
        );

        return new PageUtils(page);
    }
}
