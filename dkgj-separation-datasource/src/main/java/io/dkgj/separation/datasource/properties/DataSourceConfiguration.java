package io.dkgj.separation.datasource.properties;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class DataSourceConfiguration {
    @Value("${spring.datasource.type}")
    private Class<? extends DataSource> dataSourceType;

    @Bean(name = "writeDataSource", destroyMethod = "close", initMethod = "init")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.druid.master")
    public DataSource masterDataSource() {
        return DataSourceBuilder.create().type(dataSourceType).build();
    }

    @Bean(name = "readDataSource", destroyMethod = "close", initMethod = "init")
    @ConfigurationProperties(prefix = "spring.datasource.druid.slave")
    public DataSource slaveDataSource() {
        return DataSourceBuilder.create().type(dataSourceType).build();
    }

    @Bean("readDataSources")
    public List<DataSource> readDataSources() throws SQLException {
        List<DataSource> dataSources = new ArrayList<>();
        //dataSources.add(masterDataSource());
        dataSources.add(slaveDataSource());
        return dataSources;
    }

}
