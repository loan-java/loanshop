package io.dkgj.modules.sys.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.dkgj.common.utils.PageUtils;
import io.dkgj.common.utils.Query;

import io.dkgj.modules.sys.dao.LogLoanVistDao;
import io.dkgj.modules.sys.entity.LogLoanVistEntity;
import io.dkgj.modules.sys.service.LogLoanVistService;


@Service("logLoanVistService")
public class LogLoanVistServiceImpl extends ServiceImpl<LogLoanVistDao, LogLoanVistEntity> implements LogLoanVistService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String createDate = (String) params.get("createDate");
        String mobile = (String) params.get("mobile");
        String ip = (String) params.get("ip");
        String loanName = (String) params.get("loanName");
        String cd = (String) params.get("cd");

        IPage<LogLoanVistEntity> page = this.page(
                new Query<LogLoanVistEntity>().getPage(params),
                new QueryWrapper<LogLoanVistEntity>()
                        .inSql(StringUtils.isNotBlank(mobile), "user_id", "select id from user where mobile like '%" + mobile + "%'")
                        .eq(StringUtils.isNotBlank(ip), "ip", ip)
                        .eq(org.apache.commons.lang3.StringUtils.isNotBlank(createDate), "date_format(create_time,'%Y/%m/%d')", createDate)
                        .inSql(StringUtils.isNotBlank(loanName), "loan_id", "select id from loan where title like '%" + loanName + "%'")
                        .like(StringUtils.isNotBlank(cd),"channel",cd)

        );

        return new PageUtils(page);
    }

    @Override
    public List<LogLoanVistEntity> listExport(Map<String, Object> params) {
        return baseMapper.listExport(params);
    }

}
