package io.dkgj.modules.job.task;

import io.dkgj.common.utils.DateUtils;
import io.dkgj.modules.sys.service.HistoryLogChannelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component("channelDataCopyTask")
public class ChannelDataCopyTask implements ITask {

    @Autowired
    private HistoryLogChannelService historyLogChannelService;
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void run(String params) {
        String beginDate = String.format("%s %s", DateUtils.format(DateUtils.addDateDays(new Date(), -1), "yyyy-MM-dd"), "00:00:00");
        String endDate = String.format("%s %s", DateUtils.format(DateUtils.addDateDays(new Date(), -1), "yyyy-MM-dd"), "23:59:59");
        logger.info("开始时间：", beginDate);
        logger.info("结束时间:", endDate);
        logger.info("开始执行渠道历史统计数据拷贝任务！");
        Map<String, Object> dataParams = new HashMap<String, Object>();
        dataParams.put("beginDate", beginDate);
        dataParams.put("endDate", endDate);
        historyLogChannelService.copyData(dataParams);
    }
}
