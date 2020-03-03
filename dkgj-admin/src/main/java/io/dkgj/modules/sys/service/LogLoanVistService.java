package io.dkgj.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.dkgj.common.utils.PageUtils;
import io.dkgj.modules.sys.entity.LogLoanVistEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-07-08 15:21:07
 */
public interface LogLoanVistService extends IService<LogLoanVistEntity> {

    PageUtils queryPage(Map<String, Object> params);

    List<LogLoanVistEntity> listExport(Map<String, Object> params);
}

