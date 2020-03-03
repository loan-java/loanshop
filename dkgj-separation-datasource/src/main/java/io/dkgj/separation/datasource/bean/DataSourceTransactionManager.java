package io.dkgj.separation.datasource.bean;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;


/***
 * 自定义事务
 *
 * @author Carlton
 *
 */
@Configuration
@EnableTransactionManagement
public class DataSourceTransactionManager extends DataSourceTransactionManagerAutoConfiguration {

    static Logger log = LoggerFactory.getLogger(DataSourceTransactionManager.class);

    @Resource(name = "writeDataSource")
    private DataSource dataSource;

    /**
     * 自定义事务
     * MyBatis自动参与到spring事务管理中，无需额外配置，只要org.mybatis.spring.SqlSessionFactoryBean引用的数据源与DataSourceTransactionManager引用的数据源一致即可，否则事务管理会不起作用。
     *
     * @return
     */
    @Bean(name = "transactionManager")
    public org.springframework.jdbc.datasource.DataSourceTransactionManager transactionManagers() {
        log.info("-------------------- transactionManager init ---------------------");
        return new org.springframework.jdbc.datasource.DataSourceTransactionManager(dataSource);
    }

}
