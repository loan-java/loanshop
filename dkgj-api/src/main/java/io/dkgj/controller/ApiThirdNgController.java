package io.dkgj.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.dkgj.common.utils.IPUtils;
import io.dkgj.common.utils.RedisUtils;
import io.dkgj.entity.LogAsoNgEntity;
import io.dkgj.service.LogAsoNgService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api")
@Api(tags = "熊猫")
@Slf4j
public class ApiThirdNgController {

    @Autowired
    private LogAsoNgService logAsoNgService;

    @Autowired
    private RedisUtils redisUtils;

    @GetMapping("ng/idfaRepeat")
    @ApiOperation("广告主去重接口")
    public Map<String, Object> idfaRepeat(@RequestParam String appid,
                                          @RequestParam String idfa,
                                          @RequestParam(required = false, defaultValue = "") String ip,
                                          @RequestParam String device,
                                          @RequestParam String os,
                                          @RequestParam(required = false, defaultValue = "") String keyword,
                                          @RequestParam(required = false, defaultValue = "") String channel) {
        Map<String, Object> result = new HashMap<String, Object>();
        String isExsit = (String) result.get("xm:" + idfa);
        if (StringUtils.isBlank(isExsit)) {
            LogAsoNgEntity entity = logAsoNgService.getOne(new QueryWrapper<LogAsoNgEntity>()
                    .eq("idfa", idfa)
                    .eq("status", 0));
            if (entity == null) {
                result.put(idfa, 0);
            } else {
                redisUtils.set("xm:" + idfa, 1, 60 * 60 * 24);
                result.put(idfa, 1);
            }
        } else {
            if ("1".equals(isExsit)) {
                result.put(idfa, 1);
            } else {
                result.put(idfa, 0);
            }
        }
        return result;
    }

    @GetMapping("ng/submit")
    @ApiOperation("激活接口")
    public Map<String, Object> submit(@RequestParam String appid,
                                      @RequestParam String idfa,
                                      @RequestParam(required = false, defaultValue = "") String ip,
                                      @RequestParam String device,
                                      @RequestParam String os,
                                      @RequestParam(required = false, defaultValue = "") String keyword,
                                      @RequestParam(required = false, defaultValue = "") String channel,
                                      HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        try {
            ip = IPUtils.getIpAddr(request);
            LogAsoNgEntity entity = logAsoNgService.getOne(new QueryWrapper<LogAsoNgEntity>()
                    .eq("idfa", idfa)
                    .eq("status", 0));
            if (entity == null) {
                entity = new LogAsoNgEntity();
                entity.setAppid(appid);
                entity.setIdfa(idfa);
                entity.setDevice(device);
                entity.setOs(os);
                entity.setKeyword(keyword);
                entity.setChannel(channel);
                entity.setIp(ip);
                entity.setCreateDate(new Date());
                entity.setStatus(0);
                logAsoNgService.save(entity);

                redisUtils.set("xm:" + idfa, 1, 60 * 60 * 24);
            }
            result.put("status", 1);
        } catch (Exception e) {
            result.put("status", 0);
        }
        return result;
    }

    @GetMapping("ng/click")
    @ApiOperation("点击接口")
    public Map<String, Object> click(@RequestParam String appid,
                                     @RequestParam String idfa,
                                     @RequestParam(required = false, defaultValue = "") String ip,
                                     @RequestParam String device,
                                     @RequestParam String os,
                                     @RequestParam(required = false, defaultValue = "") String keyword,
                                     @RequestParam(required = false, defaultValue = "") String channel,
                                     HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        try {
            ip = IPUtils.getIpAddr(request);
            LogAsoNgEntity entity = new LogAsoNgEntity();
            entity.setAppid(appid);
            entity.setIdfa(idfa);
            entity.setDevice(device);
            entity.setOs(os);
            entity.setKeyword(keyword);
            entity.setChannel(channel);
            entity.setIp(ip);
            entity.setCreateDate(new Date());
            entity.setStatus(1);
            logAsoNgService.save(entity);
            redisUtils.set(idfa, 0, 60 * 60 * 24);
            result.put("status", 1);
        } catch (Exception e) {
            result.put("status", 0);
        }

        return result;
    }
}
