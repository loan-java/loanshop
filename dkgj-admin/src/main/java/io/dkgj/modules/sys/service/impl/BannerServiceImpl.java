package io.dkgj.modules.sys.service.impl;

import io.dkgj.common.utils.DateUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.dkgj.common.utils.PageUtils;
import io.dkgj.common.utils.Query;

import io.dkgj.modules.sys.dao.BannerDao;
import io.dkgj.modules.sys.entity.BannerEntity;
import io.dkgj.modules.sys.service.BannerService;


@Service("bannerService")
public class BannerServiceImpl extends ServiceImpl<BannerDao, BannerEntity> implements BannerService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {

        String id = (String) params.get("id");
        String title = (String) params.get("title");
        String state = (String) params.get("state");
        String createdAt = (String) params.get("createdAt");
        Date beginDate = null;
        Date endDate = null;
        if (StringUtils.isNotBlank(createdAt)) {
            String[] dates = createdAt.split("-");
            beginDate = DateUtils.getStartDayDateTime(dates[0]);
            endDate = DateUtils.getEndDayDateTime(dates[1]);
        }

        IPage<BannerEntity> page = this.page(
                new Query<BannerEntity>().getPage(params),
                new QueryWrapper<BannerEntity>()
                        .eq(StringUtils.isNotBlank(id),"id", id)
                        .eq(StringUtils.isNotBlank(title),"title", title)
                        .eq(StringUtils.isNotBlank(state),"state", state)
                        .between(beginDate != null && endDate != null, "createdat", beginDate, endDate)
        );

        return new PageUtils(page);
    }

    @Override
    public int updateStateByLoanId(Integer loanId, Integer state) {
        if(loanId == null || state == null) {
            return 0;
        }
        return this.baseMapper.updateStateByLoanId(loanId, state);
    }

}
