package io.dkgj.separation.datasource.bean;

import io.dkgj.separation.datasource.enums.DataSourceType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataSourceContextHolder {
    private static final ThreadLocal<String> local = new ThreadLocal<String>();

    public static ThreadLocal<String> getLocal() {
        return local;
    }

    private final static Logger logger = LoggerFactory.getLogger(DataSourceContextHolder.class);

    public static void read() {
        local.set(DataSourceType.read.getType());
        logger.info("切换到读库...");
    }

    public static void write() {
        local.set(DataSourceType.write.getType());
        logger.info("切换到写库...");
    }

    //清除local中的值，用于数据源切换失败的问题
    public static void clear() {
        local.remove();
    }

    public static String getJdbcType() {
        return local.get();
    }

}