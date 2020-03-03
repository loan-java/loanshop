package io.dkgj.modules.sys.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.dkgj.common.utils.PageUtils;
import io.dkgj.common.utils.Query;

import io.dkgj.modules.sys.dao.PopupDao;
import io.dkgj.modules.sys.entity.PopupEntity;
import io.dkgj.modules.sys.service.PopupService;


@Service("popupService")
public class PopupServiceImpl extends ServiceImpl<PopupDao, PopupEntity> implements PopupService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<PopupEntity> page = this.page(
                new Query<PopupEntity>().getPage(params),
                new QueryWrapper<PopupEntity>()
        );

        return new PageUtils(page);
    }

}
