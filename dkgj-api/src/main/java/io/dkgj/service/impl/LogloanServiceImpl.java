package io.dkgj.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.dkgj.common.utils.PageUtils;
import io.dkgj.common.utils.Query;

import io.dkgj.dao.LogloanDao;
import io.dkgj.entity.LogloanEntity;
import io.dkgj.service.LogloanService;


@Service("logloanService")
public class LogloanServiceImpl extends ServiceImpl<LogloanDao, LogloanEntity> implements LogloanService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<LogloanEntity> page = this.page(
                new Query<LogloanEntity>().getPage(params),
                new QueryWrapper<LogloanEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveUnique(LogloanEntity entity) {
        this.baseMapper.saveUnique(entity);
    }

    @Override
    public LogloanEntity getOneForUpdate(Integer relId, String nowDay) {
        return this.baseMapper.getOneForUpdate(relId,nowDay);
    }

    @Override
    public void updatePv(Integer id) {
        this.baseMapper.updatePv(id);
    }

    @Override
    public void updateTodayPv(Integer id) {
        this.baseMapper.updateTodayPv(id);
    }

    @Override
    public void updateUv(Integer id) {
        this.baseMapper.updateUv(id);
        this.baseMapper.downLoanIfUvMax(id);
    }

    @Override
    public void updateTodayUv(Integer id) {
        this.baseMapper.updateTodayUv(id);
    }

}
