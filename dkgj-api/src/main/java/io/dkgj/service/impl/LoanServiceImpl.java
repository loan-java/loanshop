package io.dkgj.service.impl;

import io.dkgj.common.utils.Query;
import io.dkgj.controller.vo.SortVO;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.dkgj.common.utils.PageUtils;

import io.dkgj.dao.LoanDao;
import io.dkgj.entity.LoanEntity;
import io.dkgj.service.LoanService;


@Service("loanService")
public class LoanServiceImpl extends ServiceImpl<LoanDao, LoanEntity> implements LoanService {

    private static Map tagsMap = new HashMap();

    static {
        tagsMap.put("HIGH", "贷款额度高");
        tagsMap.put("LOW", "贷款利率低");
        tagsMap.put("FAST", "放款速度快");
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params) {

        String type = (String) params.get("type");
        String tags = (String) params.get("tags");
        String badge = (String) params.get("badge");
        String amount = (String) params.get("amount");
        String market = (String) params.get("market");

        String limit = (String) params.get("limit");

        params.put("sidx", "weights");
        params.put("order", "asc");


        String amountStart = "";
        String amountEnd = "";

        if ("1".equals(amount)) {
            amountStart = "-1";
            amountEnd = "2001";
        }
        if ("2".equals(amount)) {
            amountStart = "1999";
            amountEnd = "5001";
        }
        if ("3".equals(amount)) {
            amountStart = "4999";
        }
        IPage<LoanEntity> page = null;
        if (StringUtils.isNotBlank(market)) {
            page = this.page(
                    new Query<LoanEntity>().getPage(params),
                    new QueryWrapper<LoanEntity>()
                            .eq("state", 1)
                            .eq(Integer.valueOf(limit) >= 1000, "badge", "HOT")
                            .eq(tags != null && !"".equals(tags), "tags", tags)
                            .eq(badge != null && !"".equals(badge), "badge", badge)
                            .eq(type != null && !"".equals(type), "`type`", type)
                            .eq(StringUtils.isNotBlank(market), "market", market)
                            .ge(amountStart != null && !"".equals(amountStart), "minLoan", amountStart)
                            .lt(amountEnd != null && !"".equals(amountEnd), "maxLoan", amountEnd)
            );
        } else {
            page = this.page(
                    new Query<LoanEntity>().getPage(params),
                    new QueryWrapper<LoanEntity>()
                            .eq("state", 1)
                            .eq(Integer.valueOf(limit) >= 1000, "badge", "HOT")
                            .eq(tags != null && !"".equals(tags), "tags", tags)
                            .eq(badge != null && !"".equals(badge), "badge", badge)
                            .eq(type != null && !"".equals(type), "`type`", type)
                            .isNull("market")
                            .ge(amountStart != null && !"".equals(amountStart), "minLoan", amountStart)
                            .lt(amountEnd != null && !"".equals(amountEnd), "maxLoan", amountEnd)
            );
        }

        List<LoanEntity> list = page.getRecords();
        List<LoanEntity> newList = new ArrayList<LoanEntity>();
        for (LoanEntity entity : list) {
            entity.setTags((String) tagsMap.get(entity.getTags()));
            newList.add(entity);
        }
        page.setRecords(newList);
        return new PageUtils(page);
    }

    @Override
    public List<LoanEntity> getRandomLoan(Integer limit) {
        List<LoanEntity> list = this.list(new QueryWrapper<LoanEntity>().eq("state", 1));
        List<LoanEntity> randList = new ArrayList<LoanEntity>();
        for (int i = 0; i < limit; i++) {
            Integer index = (int) (Math.random() * list.size());
            LoanEntity entity = list.get(index);
            entity.setTags((String) tagsMap.get(entity.getTags()));
            if (!randList.contains(entity)) {
                randList.add(entity);
            } else {
                index = (int) (Math.random() * (list.size()));
                entity = list.get(index);
                entity.setTags((String) tagsMap.get(entity.getTags()));
                randList.add(entity);
            }
        }
        return randList;
    }

}
