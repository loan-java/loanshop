package io.dkgj.modules.sys.service.impl;

import io.dkgj.common.utils.DateUtils;
import io.dkgj.commons.dynamic.datasource.annotation.DataSource;
import io.dkgj.modules.sys.entity.LogChannelEntity;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.dkgj.common.utils.PageUtils;
import io.dkgj.common.utils.Query;

import io.dkgj.modules.sys.dao.HistoryLogChannelDao;
import io.dkgj.modules.sys.entity.HistoryLogChannelEntity;
import io.dkgj.modules.sys.service.HistoryLogChannelService;


@Service("historyLogChannelService")
public class HistoryLogChannelServiceImpl extends ServiceImpl<HistoryLogChannelDao, HistoryLogChannelEntity> implements HistoryLogChannelService {

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
        IPage<HistoryLogChannelEntity> page = this.page(
                new Query<HistoryLogChannelEntity>().getPage(params),
                new QueryWrapper<HistoryLogChannelEntity>()
                        .eq(id != null && StringUtils.isNotBlank(id), "id", id)
                        .eq(channel != null && StringUtils.isNotBlank(channel), "channel", channel)
                        .between(beginDate != null && endDate != null, "createdat", beginDate, endDate)
                        .eq(channelId != null && channelId > 0, "channel", channelId)
                        .inSql("channel", "SELECT id FROM channel_manage WHERE state = 0")
        );

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

    @Override
    public void copyData(Map<String, Object> dataParams) {
        this.baseMapper.copyData(dataParams);
    }

}
