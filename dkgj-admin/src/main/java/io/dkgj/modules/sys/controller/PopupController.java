package io.dkgj.modules.sys.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.dkgj.common.validator.ValidatorUtils;
import io.dkgj.modules.sys.entity.BannerEntity;
import io.dkgj.modules.sys.entity.ChannelManageEntity;
import io.dkgj.modules.sys.service.ChannelManageService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.dkgj.modules.sys.entity.PopupEntity;
import io.dkgj.modules.sys.service.PopupService;
import io.dkgj.common.utils.PageUtils;
import io.dkgj.common.utils.R;


/**
 * @author Mark
 * @email sunlightcs@gmail.com
 * @date 2019-06-05 19:04:34
 */
@RestController
@RequestMapping("sys/popup")
public class PopupController {
    @Autowired
    private PopupService popupService;

    @Autowired
    private ChannelManageService channelManageService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("sys:popup:list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = popupService.queryPage(params);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:popup:info")
    public R info(@PathVariable("id") Long id) {
        PopupEntity popup = popupService.getById(id);
        return R.ok().put("popup", popup);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("sys:popup:save")
    public R save(@RequestBody PopupEntity popup) {
        popup.setCreateDate(new Date());
        fillChannel(popup);
        popupService.save(popup);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("sys:popup:update")
    public R update(@RequestBody PopupEntity popup) {
        ValidatorUtils.validateEntity(popup);
        popup.setUpdateDate(new Date());
        fillChannel(popup);
        popupService.updateById(popup);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("sys:popup:delete")
    public R delete(@RequestBody Long[] ids) {
        popupService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

    private void fillChannel(@RequestBody PopupEntity popup) {
        if (popup.getChannel().contains("all")) {
            popup.setChannel("");
            List<ChannelManageEntity> list = channelManageService.list(new QueryWrapper<ChannelManageEntity>()
                    .eq("state", 0));
            String channelStr = "";
            for (ChannelManageEntity entity : list) {
                channelStr += entity.getChannelcode() + ",";
            }
            popup.setChannel(channelStr);
        }
    }

}
