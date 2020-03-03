package io.dkgj.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.dkgj.common.utils.PageUtils;
import io.dkgj.entity.LoanEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-04-08 10:59:05
 */
public interface LoanService extends IService<LoanEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<LoanEntity> getRandomLoan(Integer limit);
}

