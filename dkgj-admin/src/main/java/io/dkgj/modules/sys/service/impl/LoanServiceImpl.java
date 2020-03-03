package io.dkgj.modules.sys.service.impl;

import io.dkgj.common.utils.DateUtils;
import io.dkgj.commons.dynamic.datasource.annotation.DataSource;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.dkgj.common.utils.PageUtils;
import io.dkgj.common.utils.Query;

import io.dkgj.modules.sys.dao.LoanDao;
import io.dkgj.modules.sys.entity.LoanEntity;
import io.dkgj.modules.sys.service.LoanService;


@Service("loanService")
public class LoanServiceImpl extends ServiceImpl<LoanDao, LoanEntity> implements LoanService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String id = (String) params.get("id");
        String title = (String) params.get("title");
        String state = (String) params.get("state");
        String applyurl = (String) params.get("applyurl");
        String badge = (String) params.get("badge");
        String tags = (String) params.get("tags");
        String createdAt = (String) params.get("createdAt");

        String market = (String) params.get("market");

        Date beginDate = null;
        Date endDate = null;
        if (StringUtils.isNotBlank(createdAt)) {
            String[] dates = createdAt.split("-");
            beginDate = DateUtils.getStartDayDateTime(dates[0]);
            endDate = DateUtils.getStartDayDateTime(dates[1]);
        }
        IPage<LoanEntity> page = null;

        if (StringUtils.isBlank(market)) {
            page = this.page(
                    new Query<LoanEntity>().getPage(params),
                    new QueryWrapper<LoanEntity>()
                            .eq(StringUtils.isNotBlank(id), "id", id)
                            .like(StringUtils.isNotBlank(title), "title", "%" + title + "%")
                            .eq(StringUtils.isNotBlank(state), "state", state)
                            .eq(StringUtils.isNotBlank(applyurl), "applyUrl", applyurl)
                            .eq(StringUtils.isNotBlank(badge), "badge", badge)
                            .eq(StringUtils.isNotBlank(tags), "tags", tags)
                            .isNull("market")
                            .between(beginDate != null && endDate != null, "createdat", beginDate, endDate)

            );
        } else {
            page = this.page(
                    new Query<LoanEntity>().getPage(params),
                    new QueryWrapper<LoanEntity>()
                            .eq(StringUtils.isNotBlank(id), "id", id)
                            .like(StringUtils.isNotBlank(title), "title", "%" + title + "%")
                            .eq(StringUtils.isNotBlank(state), "state", state)
                            .eq(StringUtils.isNotBlank(applyurl), "applyUrl", applyurl)
                            .eq(StringUtils.isNotBlank(badge), "badge", badge)
                            .eq(StringUtils.isNotBlank(tags), "tags", tags)
                            .isNotNull("market")
                            .between(beginDate != null && endDate != null, "createdat", beginDate, endDate)

            );
        }

        return new PageUtils(page);
    }

    @Override
    public void updateMarketNull(Integer id) {
        this.baseMapper.updateMarketNull(id);
    }

}
