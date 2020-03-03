package io.dkgj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.dkgj.common.utils.PageUtils;
import io.dkgj.dao.VersionConfigDao;
import io.dkgj.entity.VersionConfigEntity;
import io.dkgj.service.VersionConfigService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service("versionConfigService")
public class VersionConfigServiceImpl extends ServiceImpl<VersionConfigDao, VersionConfigEntity> implements VersionConfigService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        return null;
    }

    @Override
    public VersionConfigEntity findNewsVersion(Integer type,String chCode) {
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("type",type);
        params.put("chCode",chCode);
        return baseMapper.findNewsVersion(type,chCode);
    }

    @Override
    public VersionConfigEntity findNewsVersion(Integer type) {
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("type",type);
        return baseMapper.findGeneralNewsVersion(type);
    }

}
