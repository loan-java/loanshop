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

import io.dkgj.modules.sys.dao.UserDao;
import io.dkgj.modules.sys.entity.UserEntity;
import io.dkgj.modules.sys.service.UserService;


@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDao, UserEntity> implements UserService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {

        String id = (String) params.get("id");
        String mobile = (String) params.get("mobile");
        String wxId = (String) params.get("wxId");
        String state = (String) params.get("state");
        String createdAt = (String) params.get("createdAt");

        Date beginDate = null;
        Date endDate = null;
        if (StringUtils.isNotBlank(createdAt)) {
            String[] dates = createdAt.split("-");
            beginDate = DateUtils.getStartDayDateTime(dates[0]);
            endDate = DateUtils.getStartDayDateTime(dates[1]);
        }

        IPage<UserEntity> page = this.page(
                new Query<UserEntity>().getPage(params),
                new QueryWrapper<UserEntity>()
                        .eq(StringUtils.isNotBlank(id), "id", id)
                        .like(StringUtils.isNotBlank(mobile), "mobile", mobile)
                        .like(StringUtils.isNotBlank(wxId), "wx_id", wxId)
                        .eq(StringUtils.isNotBlank(state), "state", state)
                        .between(beginDate != null && endDate != null, "created_at", beginDate, endDate)
        );

        return new PageUtils(page);
    }

}
