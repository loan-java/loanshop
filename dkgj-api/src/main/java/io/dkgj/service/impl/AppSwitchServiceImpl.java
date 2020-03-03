package io.dkgj.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.dkgj.dao.AppSwitchDao;
import io.dkgj.entity.AppSwitchEntity;
import io.dkgj.service.AppSwitchService;
import org.springframework.stereotype.Service;


@Service("appSwitchService")
public class AppSwitchServiceImpl extends ServiceImpl<AppSwitchDao, AppSwitchEntity> implements AppSwitchService {



}
