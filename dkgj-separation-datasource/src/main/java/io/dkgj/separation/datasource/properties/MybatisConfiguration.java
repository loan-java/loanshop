package io.dkgj.separation.datasource.properties;

import io.dkgj.separation.datasource.bean.MyAbstractRoutingDataSource;
import io.dkgj.separation.datasource.utils.SpringContextUtil;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;


import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Configuration
@AutoConfigureAfter(DataSourceConfiguration.class)
@ConditionalOnClass({EnableTransactionManagement.class})
@Import({DataSourceConfiguration.class})
@MapperScan(basePackages = {"com.wocloud.arch.ssm.mapper"})
public class MybatisConfiguration {
    @Value("${spring.datasource.type}")
    private Class<? extends DataSource> dataSourceType;
    @Value("${spring.datasource.readSize}")
    private String dataSourceSize;

    @Bean
    @ConditionalOnMissingBean
    public SqlSessionFactory sqlSessionFactory(ApplicationContext ac) throws Exception {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(roundRobinDataSouceProxy(ac));
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:mapper/*.xml"));
        sqlSessionFactoryBean.setTypeAliasesPackage("io.dkgj.entity");
        sqlSessionFactoryBean.getObject().getConfiguration().setMapUnderscoreToCamelCase(true);
        return sqlSessionFactoryBean.getObject();
    }

    /**
     * 有多少个数据源就要配置多少个bean
     */
    @Bean
    public AbstractRoutingDataSource roundRobinDataSouceProxy(ApplicationContext ac) {
        int size = Integer.parseInt(dataSourceSize);
        MyAbstractRoutingDataSource proxy = new MyAbstractRoutingDataSource(size);
        Map<Object, Object> targetDataSources = new HashMap<Object, Object>();

        DataSource writeDataSource = (DataSource) ac.getBean("writeDataSource");
        List<DataSource> readDataSources = (List<DataSource>) ac.getBean("readDataSources");
        for (int i = 0; i < size; i++) {
            targetDataSources.put(i, readDataSources.get(i));
        }
        proxy.setDefaultTargetDataSource(writeDataSource);
        proxy.setTargetDataSources(targetDataSources);
        return proxy;
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    //事务管理
    @Bean
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return new DataSourceTransactionManager((DataSource) SpringContextUtil.getBean("roundRobinDataSouceProxy"));
    }

}