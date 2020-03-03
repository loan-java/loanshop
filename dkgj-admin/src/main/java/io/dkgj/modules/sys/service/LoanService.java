package io.dkgj.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.dkgj.common.utils.PageUtils;
import io.dkgj.modules.sys.entity.LoanEntity;

import java.util.Map;

/**
 * 
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-03-25 15:45:22
 */
public interface LoanService extends IService<LoanEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void updateMarketNull(Integer id);
}

