package io.dkgj.service.impl;

import org.springframework.stereotype.Service;

import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.dkgj.common.utils.PageUtils;
import io.dkgj.common.utils.Query;

import io.dkgj.dao.MLogDeviceDao;
import io.dkgj.entity.MLogDeviceEntity;
import io.dkgj.service.MLogDeviceService;


@Service("mLogDeviceService")
public class MLogDeviceServiceImpl extends ServiceImpl<MLogDeviceDao, MLogDeviceEntity> implements MLogDeviceService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MLogDeviceEntity> page = this.page(
                new Query<MLogDeviceEntity>().getPage(params),
                new QueryWrapper<MLogDeviceEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void saveUnique(MLogDeviceEntity mLogDeviceEntity) {
        this.baseMapper.saveUnique(mLogDeviceEntity);
    }

    @Override
    public MLogDeviceEntity getOneForUpdate(String uuid) {
        return this.baseMapper.getOneForUpdate(uuid);
    }

}
