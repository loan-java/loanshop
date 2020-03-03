package io.dkgj.controller;/**
 * Created by qtz-zl on 2019/2/25.
 */

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.dkgj.common.utils.R;
import io.dkgj.common.utils.VersionCompareUtils;
import io.dkgj.entity.AppSwitchEntity;
import io.dkgj.entity.VersionConfigEntity;
import io.dkgj.service.AppSwitchService;
import io.dkgj.service.VersionConfigService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author zhanl
 * @create 2019-02-25 11:23
 **/
@RestController
@RequestMapping("/api")
public class ApiVersionConfigController {

    @Autowired
    private VersionConfigService versionConfigService;

    @Autowired
    private AppSwitchService appSwitchService;

    @GetMapping("get_new_version")
    @ApiOperation("获取是否有新版本升级")
    public R getNewVersion(@RequestParam("type") Integer type, @RequestParam("currentVersion") String currentVersion, @RequestParam("chCode") String chCode) {

        String upChCode = chCode != null ? chCode.toUpperCase() : null;
        VersionConfigEntity entity = null;
        if ("XJDK".equals(upChCode)) {
            entity = versionConfigService.findNewsVersion(type, chCode);
        } else {
            entity = versionConfigService.findNewsVersion(type);
        }
        if (entity == null) {
            return R.error("没有找到新版本！");
        }
        Integer flag = null;
        try {
            flag = VersionCompareUtils.compareVersion(currentVersion, entity.getVersion());
        } catch (Exception e) {
            R.error("传入Version异常");
            e.printStackTrace();
        }
        if (flag < 0) {
            return R.ok().put("data", entity);
        }
        return R.error("没有找到新版本！");
    }

    @GetMapping("get_index_app")
    public R getIndexApp(@RequestParam("type") Integer type, @RequestParam("packageName") String packageName) {
        AppSwitchEntity entity = appSwitchService.getOne(new QueryWrapper<AppSwitchEntity>()
                .eq("type", type)
                .eq("app_package_name", packageName));
        return R.ok().put("data", entity);
    }
}
