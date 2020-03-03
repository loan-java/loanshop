package io.dkgj.separation.datasource.bean;

import io.dkgj.separation.datasource.enums.DataSourceType;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.util.concurrent.atomic.AtomicInteger;

public class MyAbstractRoutingDataSource extends AbstractRoutingDataSource {
    private final int dataSourceNumber;
    private AtomicInteger count = new AtomicInteger(0);

    public MyAbstractRoutingDataSource(int dataSourceNumber) {
        this.dataSourceNumber = dataSourceNumber;
    }

    @Override
    protected Object determineCurrentLookupKey() {
        String typeKey = DataSourceContextHolder.getJdbcType();
        if (DataSourceType.write.getType().equals(typeKey) || typeKey == null) {
            return DataSourceType.write.getType();
        }
        // 读 简单负载（因为从库数量为1，实际上目前没有负载效果）
        int number = count.getAndAdd(1);
        int lookupKey = number % dataSourceNumber;
        return new Integer(lookupKey);
    }
}