package io.dkgj.modules.sys.service.impl;

import io.dkgj.common.utils.Constant;
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

import io.dkgj.modules.sys.dao.MLogDeviceDao;
import io.dkgj.modules.sys.entity.MLogDeviceEntity;
import io.dkgj.modules.sys.service.MLogDeviceService;


@Service("mLogDeviceService")
public class MLogDeviceServiceImpl extends ServiceImpl<MLogDeviceDao, MLogDeviceEntity> implements MLogDeviceService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String id = (String) params.get("id");
        String appid = (String) params.get("appid");
        String uuid = (String) params.get("uuid");
        String createdat = (String) params.get("createdat");

        Date beginDate = null;
        Date endDate = null;
        if (StringUtils.isNotBlank(createdat)) {
            String[] dates = createdat.split("-");
            beginDate = DateUtils.getStartDayDateTime(dates[0]);
            endDate = DateUtils.getStartDayDateTime(dates[1]);
        }

        IPage<MLogDeviceEntity> page = this.page(
                new Query<MLogDeviceEntity>().getPage(params),
                new QueryWrapper<MLogDeviceEntity>()
                        .eq(StringUtils.isNotBlank(id), "id", id)
                        .eq(StringUtils.isNotBlank(appid), "appid", appid)
                        .eq(StringUtils.isNotBlank(uuid), "uuid", uuid)
                        .between(beginDate != null && endDate != null, "createdat", beginDate, endDate)
        );

        return new PageUtils(page);
    }

}
