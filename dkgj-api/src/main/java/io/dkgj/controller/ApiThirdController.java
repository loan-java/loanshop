package io.dkgj.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.dkgj.common.utils.IPUtils;
import io.dkgj.common.utils.R;
import io.dkgj.common.utils.RedisUtils;
import io.dkgj.entity.LogAsoEntity;
import io.dkgj.service.LogAsoService;
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
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api")
@Api(tags = "第三方")
@Slf4j
public class ApiThirdController {

    @Autowired
    private LogAsoService logAsoService;

    @Autowired
    private RedisUtils redisUtils;

    @GetMapping("traceASO")
    @ApiOperation("ASO埋点")
    public R traceASO(@RequestParam String appid,
                      @RequestParam(required = false, defaultValue = "") String idfa,
                      @RequestParam(required = false, defaultValue = "") String ip,
                      @RequestParam(required = false, defaultValue = "") String device,
                      @RequestParam(required = false, defaultValue = "") String os,
                      @RequestParam(required = false, defaultValue = "") String keyword,
                      @RequestParam(required = false, defaultValue = "") String channel,
                      HttpServletRequest request) {
        ip = IPUtils.getIpAddr(request);
        LogAsoEntity entity = getSubmitLogAsoEntity(idfa);
        if (entity == null) {
            entity = new LogAsoEntity();
            entity.setAppid(appid);
            entity.setIdfa(idfa);
            entity.setDevice(device);
            entity.setOs(os);
            entity.setKeyword(keyword);
            entity.setChannel(channel);
            entity.setIp(ip);
            entity.setCreateDate(new Date());
            entity.setStatus(0);
            logAsoService.save(entity);
        }
        return R.ok();
    }

    @GetMapping("idfaRepeat")
    @ApiOperation("广告主去重接口")
    public Map<String, Object> idfaRepeat(@RequestParam String appid,
                                          @RequestParam(required = false, defaultValue = "") String idfa,
                                          @RequestParam(required = false, defaultValue = "") String ip,
                                          @RequestParam(required = false, defaultValue = "") String device,
                                          @RequestParam(required = false, defaultValue = "") String os,
                                          @RequestParam(required = false, defaultValue = "") String keyword,
                                          @RequestParam(required = false, defaultValue = "") String channel) {
        Map<String, Object> result = new HashMap<String, Object>();
        String isExsit = (String) result.get(idfa);
        if (StringUtils.isBlank(isExsit)) {
            LogAsoEntity entity = getSubmitLogAsoEntity(idfa);
            if (entity == null) {
                result.put(idfa, 0);
            } else {
                redisUtils.set(idfa, 1, 60 * 60 * 24);
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

    @GetMapping("submit")
    @ApiOperation("激活接口")
    public Map<String, Object> submit(@RequestParam String appid,
                                      @RequestParam(required = false, defaultValue = "") String idfa,
                                      @RequestParam(required = false, defaultValue = "") String ip,
                                      @RequestParam(required = false, defaultValue = "") String device,
                                      @RequestParam(required = false, defaultValue = "") String os,
                                      @RequestParam(required = false, defaultValue = "") String keyword,
                                      @RequestParam(required = false, defaultValue = "") String channel,
                                      HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        try {
            ip = IPUtils.getIpAddr(request);
            LogAsoEntity entity = getSubmitLogAsoEntity(idfa);
            if (entity == null) {
                entity = new LogAsoEntity();
                entity.setAppid(appid);
                entity.setIdfa(idfa);
                entity.setDevice(device);
                entity.setOs(os);
                entity.setKeyword(keyword);
                entity.setChannel(channel);
                entity.setIp(ip);
                entity.setCreateDate(new Date());
                entity.setStatus(0);
                logAsoService.save(entity);

                redisUtils.set(idfa, 1, 60 * 60 * 24);
            }
            result.put("code", 0);
            result.put("result", "ok");
        } catch (Exception e) {
            result.put("code", 103);
            result.put("result", e.getMessage());
        }
        return result;
    }

    private LogAsoEntity getSubmitLogAsoEntity(@RequestParam(required = false, defaultValue = "") String idfa) {
        List<LogAsoEntity> list = logAsoService.list(new QueryWrapper<LogAsoEntity>()
                .eq("idfa", idfa)
                .eq("status", 0));
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    @GetMapping("click")
    @ApiOperation("点击接口")
    public Map<String, Object> click(@RequestParam String appid,
                                     @RequestParam(required = false, defaultValue = "") String idfa,
                                     @RequestParam(required = false, defaultValue = "") String ip,
                                     @RequestParam(required = false, defaultValue = "") String device,
                                     @RequestParam(required = false, defaultValue = "") String os,
                                     @RequestParam(required = false, defaultValue = "") String keyword,
                                     @RequestParam(required = false, defaultValue = "") String channel,
                                     HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        try {
            ip = IPUtils.getIpAddr(request);
            LogAsoEntity entity = new LogAsoEntity();
            entity.setAppid(appid);
            entity.setIdfa(idfa);
            entity.setDevice(device);
            entity.setOs(os);
            entity.setKeyword(keyword);
            entity.setChannel(channel);
            entity.setIp(ip);
            entity.setCreateDate(new Date());
            entity.setStatus(1);
            logAsoService.save(entity);
            redisUtils.set(idfa, 0, 60 * 60 * 24);
            result.put("code", 0);
            result.put("result", "ok");
        } catch (Exception e) {
            result.put("code", 103);
            result.put("result", e.getMessage());
        }

        return result;
    }
}
