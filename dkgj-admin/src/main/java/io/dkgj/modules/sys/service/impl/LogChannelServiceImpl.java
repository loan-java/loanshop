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

import io.dkgj.modules.sys.dao.LogChannelDao;
import io.dkgj.modules.sys.entity.LogChannelEntity;
import io.dkgj.modules.sys.service.LogChannelService;


@Service("logChannelService")
public class LogChannelServiceImpl extends ServiceImpl<LogChannelDao, LogChannelEntity> implements LogChannelService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String id = (String) params.get("id");
        String channel = (String) params.get("channel");
        String createdAt = (String) params.get("createdAt");
        Long channelId = (Long) params.get("channelId");

        Date beginDate = null;
        Date endDate = null;
        if (StringUtils.isNotBlank(createdAt)) {
            String[] dates = createdAt.split("-");
            beginDate = DateUtils.getStartDayDateTime(dates[0]);
            endDate = DateUtils.getStartDayDateTime(dates[1]);
        }

        Date today = DateUtils.stringToDate(
                String.format("%s %s", DateUtils.format(new Date(), "yyyy-MM-dd"), "00:00:00"),
                "yyyy-MM-dd HH:mm:ss"
        );

        if (beginDate.getTime() < today.getTime()) {
            beginDate = today;
        }

        params.put("beginDate", beginDate);
        params.put("endDate", endDate);

        IPage<LogChannelEntity> page = this.page(
                new Query<LogChannelEntity>().getPage(params)
        ).setRecords(this.baseMapper.selectLogChannelList(params));

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

        Date today = DateUtils.stringToDate(
                String.format("%s %s", DateUtils.format(new Date(), "yyyy-MM-dd"), "00:00:00"),
                "yyyy-MM-dd HH:mm:ss"
        );

        if (beginDate.getTime() < today.getTime()) {
            beginDate = today;
            params.put("beginTime", beginDate);
        }

        return this.baseMapper.selectSum(params);
    }

}
