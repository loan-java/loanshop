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

import io.dkgj.modules.sys.dao.LogloanDao;
import io.dkgj.modules.sys.entity.LogloanEntity;
import io.dkgj.modules.sys.service.LogloanService;


@Service("logloanService")
public class LogloanServiceImpl extends ServiceImpl<LogloanDao, LogloanEntity> implements LogloanService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String id = (String) params.get("id");
        String relId = (String) params.get("relId");
        String refer = (String) params.get("refer");
        String createdAt = (String) params.get("createdAt");

        Date beginDate = null;
        Date endDate = null;
        if (StringUtils.isNotBlank(createdAt)) {
            String[] dates = createdAt.split("-");
            beginDate = DateUtils.getStartDayDateTime(dates[0]);
            endDate = DateUtils.getStartDayDateTime(dates[1]);
            params.put("beginTime",beginDate);
            params.put("endTime",endDate);
        }
        IPage<LogloanEntity> page = this.page(
                new Query<LogloanEntity>().getPage(params, "createdAt", false),
                new QueryWrapper<LogloanEntity>()
                        .eq("id", 0)
        ).setRecords(this.baseMapper.selectLogloanlList(params));

        return new PageUtils(page);
    }

    @Override
    public Map<String, Object> selectSum(Map<String, Object> params) {

        String createdAt = (String) params.get("createdAt");

        Date beginDate = null;
        Date endDate = null;
        if (StringUtils.isNotBlank(createdAt)) {
            String[] dates = createdAt.split("-");
            beginDate = DateUtils.getStartDayDateTime(dates[0]);
            endDate = DateUtils.getStartDayDateTime(dates[1]);
            params.put("beginTime", beginDate);
            params.put("endTime", endDate);
        }
        return this.baseMapper.selectSum(params);
    }

}
