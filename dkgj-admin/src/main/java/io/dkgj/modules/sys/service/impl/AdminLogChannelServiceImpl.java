package io.dkgj.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.dkgj.common.utils.DateUtils;
import io.dkgj.common.utils.PageUtils;
import io.dkgj.common.utils.Query;
import io.dkgj.modules.sys.dao.AdminLogChannelDao;
import io.dkgj.modules.sys.dao.LogChannelDao;
import io.dkgj.modules.sys.entity.AdminLogChannelEntity;
import io.dkgj.modules.sys.entity.LogChannelEntity;
import io.dkgj.modules.sys.service.AdminLogChannelService;
import io.dkgj.modules.sys.service.LogChannelService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;


@Service("adminLogChannelService")
public class AdminLogChannelServiceImpl extends ServiceImpl<AdminLogChannelDao, AdminLogChannelEntity> implements AdminLogChannelService {

    @Autowired
    private LogChannelDao logChannelDao;

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

        params.put("beginDate", beginDate);
        params.put("endDate", endDate);

        IPage<AdminLogChannelEntity> page = this.page(
                new Query<AdminLogChannelEntity>().getPage(params)
        ).setRecords(this.baseMapper.selectAdminLogChannelList(params));

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

        return logChannelDao.selectSum(params);
    }

}
