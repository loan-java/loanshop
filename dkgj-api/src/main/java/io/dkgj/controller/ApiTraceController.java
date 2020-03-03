package io.dkgj.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.dkgj.annotation.Login;
import io.dkgj.common.utils.IPUtils;
import io.dkgj.common.utils.R;
import io.dkgj.common.validator.ValidatorUtils;
import io.dkgj.entity.*;
import io.dkgj.form.*;
import io.dkgj.entity.MLogDeviceEntity;
import io.dkgj.entity.UserEntity;
import io.dkgj.service.MLogDeviceService;
import io.dkgj.service.UserService;
import io.dkgj.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;

@RestController
@RequestMapping("/api")
@Api(tags = "跟踪接口")
@Slf4j
public class ApiTraceController {

    @Autowired
    private LogloanService logloanService;

    @Autowired
    private UserService userService;

    @Autowired
    private MLogDeviceService mLogDeviceService;

    @Autowired
    private LogChannelVisitService logChannelVisitService;

    @Autowired
    private LogVisitService logVisitService;

    @Autowired
    private LogChannelService logChannelService;

    @Autowired
    private LogVisitSourceService logVisitSourceService;

    @Autowired
    private ChannelManageService channelManageService;

    @Autowired
    private LogAppLoginService logAppLoginService;

    @Autowired
    private LogChannelTodayVistService logChannelTodayVistService;

    @Autowired
    private LogLoanVistService logLoanVistService;

    @Autowired
    private Executor myExecutor;


    @PostMapping("traceProduct")
    @ApiOperation("产品点击事件埋点")
    public R traceProduct(@RequestBody LogLoanForm form, HttpServletRequest request) {
        log.info("产品点击事件埋点:{}", JSON.toJSONString(form));
        LogloanEntity tmp = logloanService.getOne(new QueryWrapper<LogloanEntity>()
                .eq("relId", form.getRelId())
                .eq("DATE_FORMAT(createdat,'%Y-%m-%d')", new SimpleDateFormat("yyyy-MM-dd").format(new Date())));
        LogChannelTodayVistEntity channelTmp = logChannelTodayVistService.getOne(new QueryWrapper<LogChannelTodayVistEntity>()
                .eq("channel", form.getChannel())
                .eq("DATE_FORMAT(create_date,'%Y-%m-%d')", new SimpleDateFormat("yyyy-MM-dd").format(new Date())));

        LogloanEntity entity = new LogloanEntity();
        entity.setCreatedat(new Date());
        entity.setUpdatedat(new Date());
        entity.setRefer(form.getRefer());
        entity.setRelid(form.getRelId());
        UserEntity userEntity = userService.queryByMobile(form.getMobile());
        MLogDeviceEntity mLogDeviceEntity = queryByUUID(form.getUuid());
        if (userEntity == null || mLogDeviceEntity == null) {
            if (tmp == null) {
                entity.setTodayUv(0);
                entity.setUv(1);
                if (userService.isTodayUser(form.getMobile())) {
                    entity.setTodayUv(1);
                    channelTmp = setChannelTodayVisitUV(form, channelTmp);
                }
            } else {
                this.logloanService.updateUv(tmp.getId());
                if (userService.isTodayUser(form.getMobile())) {
                    this.logloanService.updateTodayUv(tmp.getId());
                    channelTmp = setChannelTodayVisitUV(form, channelTmp);
                }
            }

            mLogDeviceEntity = new MLogDeviceEntity();
            mLogDeviceEntity.setAppid(1);
            mLogDeviceEntity.setUuid(form.getUuid());
            mLogDeviceEntity.setCreatedat(new Date());
            mLogDeviceService.saveUnique(mLogDeviceEntity);

            LogVisitEntity logVisitEntity = new LogVisitEntity();
            logVisitEntity.setChannel(form.getChannel());
            logVisitEntity.setIp(IPUtils.getIpAddr(request));
            logVisitEntity.setDeviceid(mLogDeviceEntity.getId());
            logVisitEntity.setMobile(form.getMobile());
            logVisitEntity.setLoanid(form.getRelId());
            logVisitEntity.setCreatedat(new Date());
            logVisitEntity.setUpdatedat(new Date());

            logVisitService.saveUnique(logVisitEntity);
        } else {
            if (mLogDeviceEntity != null) {
                LogVisitEntity logVisitEntity = logVisitService.getOne(
                        new QueryWrapper<LogVisitEntity>()
                                //.eq("deviceId", mLogDeviceEntity.getId())
                                .eq("loanId", form.getRelId())
                                .eq("mobile", form.getMobile())
                                //.eq("ip", IPUtils.getIpAddr(request))
                                .eq("DATE_FORMAT(createdAt,'%Y-%m-%d')", new SimpleDateFormat("yyyy-MM-dd").format(new Date())));

                if (logVisitEntity == null) {
                    if (tmp == null) {
                        entity.setUv(1);
                        entity.setTodayUv(0);
                        if (userService.isTodayUser(form.getMobile())) {
                            entity.setTodayUv(1);
                            channelTmp = setChannelTodayVisitUV(form, channelTmp);
                        }
                    } else {
                        logloanService.updateUv(tmp.getId());
                        if (userService.isTodayUser(form.getMobile())) {
                            logloanService.updateTodayUv(tmp.getId());
                            channelTmp = setChannelTodayVisitUV(form, channelTmp);
                        }
                    }
                    logVisitEntity = new LogVisitEntity();
                    logVisitEntity.setChannel(form.getChannel());
                    logVisitEntity.setIp(IPUtils.getIpAddr(request));
                    logVisitEntity.setDeviceid(mLogDeviceEntity.getId());
                    logVisitEntity.setMobile(form.getMobile());
                    logVisitEntity.setLoanid(form.getRelId());
                    logVisitEntity.setCreatedat(new Date());
                    logVisitEntity.setUpdatedat(new Date());
                    logVisitService.saveUnique(logVisitEntity);
                }
            }
        }

        if (tmp == null) {
            entity.setPv(1);
            entity.setTodayPv(0);
            if (userService.isTodayUser(form.getMobile())) {
                entity.setTodayPv(1);
                if (null == channelTmp.getId()) {
                    channelTmp.setPv(1);
                    channelTmp.setChannel(form.getChannel());
                    channelTmp.setCreateDate(new Date());
                    log.info(JSONObject.toJSONString(channelTmp));
                    logChannelTodayVistService.saveUnique(channelTmp);
                } else {
                    this.logChannelTodayVistService.updatePv(channelTmp.getId());
                }
            }
            log.info(JSONObject.toJSONString(entity));
            logloanService.saveUnique(entity);
        } else {
            logloanService.updatePv(tmp.getId());
            if (userService.isTodayUser(form.getMobile())) {
                logloanService.updateTodayPv(tmp.getId());
                if (null != channelTmp.getId()) {
                    this.logChannelTodayVistService.updatePv(channelTmp.getId());
                } else {
                    channelTmp.setPv(1);
                    channelTmp.setChannel(form.getChannel());
                    channelTmp.setCreateDate(new Date());
                    log.info(JSONObject.toJSONString(channelTmp));
                    logChannelTodayVistService.saveUnique(channelTmp);
                }
            }
        }

        String ip = IPUtils.getIpAddr(request);
        myExecutor.execute(new Runnable() {
            @Override
            public void run() {
                log.info("用户[" + form.getMobile() + "],点击产品数据入库线程执行");
                UserEntity tmpUser = userService.queryByMobile(form.getMobile());
                LogLoanVistEntity logLoanVistEntity = new LogLoanVistEntity();
                logLoanVistEntity.setChannel(form.getChannel());
                logLoanVistEntity.setLoanId(Long.valueOf(form.getRelId()));
                logLoanVistEntity.setUserId(Long.valueOf(tmpUser.getId()));
                logLoanVistEntity.setIp(ip);
                logLoanVistEntity.setCreateTime(new Date());
                logLoanVistService.save(logLoanVistEntity);
            }
        });

        return R.ok();
    }

    private MLogDeviceEntity queryByUUID(String uuid) {
        List<MLogDeviceEntity> list = mLogDeviceService.list(new QueryWrapper<MLogDeviceEntity>().eq("uuid", uuid));
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    private LogChannelTodayVistEntity setChannelTodayVisitUV(LogLoanForm form, LogChannelTodayVistEntity channelTmp) {
        if (channelTmp == null) {
            channelTmp = new LogChannelTodayVistEntity();
            channelTmp.setChannel(form.getChannel());
            channelTmp.setUv(1);
        } else {
            this.logChannelTodayVistService.updateUv(channelTmp.getId());
        }
        return channelTmp;
    }

    @PostMapping("traceChannel")
    @ApiOperation("渠道埋点")
    public R traceChannel(@RequestBody LogChannelForm form, HttpServletRequest request) {
        if (StringUtils.isNotBlank(form.getChannel())) {
            String ip = IPUtils.getIpAddr(request);
            log.error("渠道点击埋点查询到的ip为：" + ip);
            LogChannelVisitEntity logChannelVisitEntity = queryTodayRecord(form, ip);
            Boolean uvFlag = false;
            if (logChannelVisitEntity == null) {
                logChannelVisitEntity = new LogChannelVisitEntity();
                logChannelVisitEntity.setChannel(form.getChannel());
                logChannelVisitEntity.setIp(ip);
                logChannelVisitEntity.setCreatedat(new Date());
                logChannelVisitEntity.setUpdatedat(new Date());

                logChannelVisitService.saveUnique(logChannelVisitEntity);
                uvFlag = true;
            }

            ChannelManageEntity channelManageEntity = channelManageService.getOne(new QueryWrapper<ChannelManageEntity>()
                    .eq("channelCode", form.getChannel()));
            LogChannelEntity entity = new LogChannelEntity();

            if (channelManageEntity != null) {
                entity.setChannel(String.valueOf(channelManageEntity.getId()));
            } else {
                //entity.setChannel(form.getChannel());
                return R.error("渠道不存在");
            }

            LogChannelEntity tmp = queryTodayLogChannel(channelManageEntity);

            if (tmp == null) {
                entity.setCreatedat(new Date());
                entity.setUpdatedat(new Date());
                entity.setRegnum(0);
                entity.setClicknum(1);
                entity.setClickdownnum(0);
                entity.setAppOpenNum(0);
                if (uvFlag) {
                    entity.setUvNum(1);
                } else {
                    entity.setUvNum(0);
                }
                logChannelService.saveUnique(entity);
            } else {
                this.logChannelService.updateClickNum(tmp.getId());
                if (uvFlag) {
                    this.logChannelService.updateUv(tmp.getId());
                }
            }
        }
        return R.ok();
    }

    private LogChannelEntity queryTodayLogChannel(ChannelManageEntity channelManageEntity) {
        List<LogChannelEntity> list = logChannelService.list(new QueryWrapper<LogChannelEntity>()
                .eq("channel", channelManageEntity.getId())
                .like("DATE_FORMAT(createdAt,'%Y-%m-%d')", new SimpleDateFormat("yyyy-MM-dd").format(new Date())));
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    private LogChannelVisitEntity queryTodayRecord(@RequestBody LogChannelForm form, String ip) {
        List<LogChannelVisitEntity> list = logChannelVisitService.list(new QueryWrapper<LogChannelVisitEntity>()
                .eq("channel", form.getChannel())
                .eq("ip", ip)
                .eq("DATE_FORMAT(createdAt,'%Y-%m-%d')", new SimpleDateFormat("yyyy-MM-dd").format(new Date())));

        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }


    @PostMapping("traceMdevice")
    @ApiOperation("设备埋点")
    public R traceMdevice(@RequestBody MLogDeviceForm form) {
        MLogDeviceEntity mLogDeviceEntity = queryByUUID(form.getUuid());
        if (mLogDeviceEntity == null) {
            mLogDeviceEntity = new MLogDeviceEntity();
            mLogDeviceEntity.setAppid(1);
            mLogDeviceEntity.setUuid(form.getUuid());
            mLogDeviceEntity.setCreatedat(new Date());

            mLogDeviceService.saveUnique(mLogDeviceEntity);
        }

        return R.ok();
    }


    @PostMapping("traceChannelAppOpenNum")
    @ApiOperation("渠道APP打开数据埋点")
    public R traceChannelAppOpenNum(@RequestBody LogChannelForm form) {
        ChannelManageEntity channelManageEntity = channelManageService.getOne(new QueryWrapper<ChannelManageEntity>()
                .eq("channelCode", form.getChannel()));
        LogChannelEntity entity = new LogChannelEntity();

        if (channelManageEntity != null) {
            entity.setChannel(String.valueOf(channelManageEntity.getId()));
        } else {
            //entity.setChannel(form.getChannel());
            return R.error("渠道不存在");
        }

        LogChannelEntity tmp = queryTodayLogChannel(channelManageEntity);


        if (tmp == null) {
            entity.setCreatedat(new Date());
            entity.setUpdatedat(new Date());
            entity.setRegnum(0);
            entity.setClicknum(0);
            entity.setClickdownnum(0);
            entity.setAppOpenNum(1);
            entity.setUvNum(0);
            logChannelService.saveUnique(entity);
        } else {

            logChannelService.updateAppOpenNum(tmp.getId());
        }
        return R.ok();
    }

    @PostMapping("traceChannelDownNum")
    @ApiOperation("渠道下载数据埋点")
    public R traceChannelDownNum(@RequestBody LogChannelForm form) {
        ChannelManageEntity channelManageEntity = channelManageService.getOne(new QueryWrapper<ChannelManageEntity>()
                .eq("channelCode", form.getChannel()));
        LogChannelEntity entity = new LogChannelEntity();

        if (channelManageEntity != null) {
            entity.setChannel(String.valueOf(channelManageEntity.getId()));
        } else {
            //entity.setChannel(form.getChannel());
            return R.error("渠道不存在");
        }

        LogChannelEntity tmp = queryTodayLogChannel(channelManageEntity);


        if (tmp == null) {
            entity.setCreatedat(new Date());
            entity.setUpdatedat(new Date());
            entity.setRegnum(0);
            entity.setClicknum(0);
            entity.setClickdownnum(1);
            entity.setAppOpenNum(0);
            entity.setUvNum(0);
            logChannelService.saveUnique(entity);
        } else {
            logChannelService.updateClickDownNum(tmp.getId());
        }
        return R.ok();
    }

    @PostMapping("traceReferer")
    @ApiOperation("注册来源检测接口")
    public R traceReferer(@RequestBody LogVisitSourceForm form) {
        ValidatorUtils.validateEntity(form);
        LogVisitSourceEntity entity = new LogVisitSourceEntity();
        BeanUtils.copyProperties(form, entity);
        entity.setCreateDate(new Date());
        logVisitSourceService.save(entity);
        return R.ok();
    }

    @PostMapping("tal")
    @ApiOperation("app登录埋点")
    @Login
    public R traceAppLogin(@ApiIgnore @RequestAttribute("userId") Integer userId, @RequestBody LogAppLoginForm form) {
        ValidatorUtils.validateEntity(form);
        LogAppLoginEntity entity = new LogAppLoginEntity();
        BeanUtils.copyProperties(form, entity);
        entity.setUserId(Long.valueOf(userId));
        entity.setCreatedat(new Date());
        logAppLoginService.save(entity);
        return R.ok();
    }


}
