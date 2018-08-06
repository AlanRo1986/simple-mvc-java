package com.lanxinbase.config;



import com.lanxinbase.constant.ConstantMysql;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.apache.ibatis.session.LocalCacheScope;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;




/**
 * Created by alan.luo on 2017/10/18.
 */
@Configuration
@EnableTransactionManagement
public class MybatisConfiguration implements TransactionManagementConfigurer {

    public MybatisConfiguration(){
    }


    @Bean(destroyMethod = "close")
    public BasicDataSource dataSource(){
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(ConstantMysql.driver);
        dataSource.setUrl(ConstantMysql.url);
        dataSource.setUsername(ConstantMysql.username);
        dataSource.setPassword(ConstantMysql.password);

        dataSource.setInitialSize(ConstantMysql.initialSize);
        dataSource.setMaxIdle(ConstantMysql.maxIdle);
        dataSource.setMaxActive(ConstantMysql.maxActive);
        dataSource.setMaxOpenPreparedStatements(ConstantMysql.maxOpen);

        dataSource.setTestWhileIdle(ConstantMysql.testWhileIdle);
        dataSource.setValidationQuery(ConstantMysql.validationQuery);
        dataSource.setTimeBetweenEvictionRunsMillis(ConstantMysql.timeBetweenEvictionRunsMillis);
        dataSource.setMinEvictableIdleTimeMillis(ConstantMysql.minEvictableIdleTimeMillis);
        dataSource.setTestOnBorrow(ConstantMysql.testOnBorrow);

        return dataSource;
    }

    @Bean
    public SqlSessionFactoryBean sessionFactoryBean() throws Exception{
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(this.dataSource());
        sqlSessionFactoryBean.setConfiguration(this.getConfiguration());
        String locationPattern = ResourcePatternResolver.CLASSPATH_URL_PREFIX + "mapper/**.xml";
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean.setMapperLocations(resolver.getResources(locationPattern));
        return sqlSessionFactoryBean;
    }

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer(){
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setBasePackage("com.lanxinbase.repository.mybatis.mapper");
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sessionFactoryBean");
        return mapperScannerConfigurer;
    }

    @Bean
    public PlatformTransactionManager transactionManager(){
        DataSourceTransactionManager manager = new DataSourceTransactionManager(this.dataSource());
        manager.setDataSource(this.dataSource());
        manager.setDefaultTimeout(40);
        manager.setRollbackOnCommitFailure(true);
        return manager;
    }

    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return this.transactionManager();
    }

    public org.apache.ibatis.session.Configuration getConfiguration(){
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setLogImpl(StdOutImpl.class);
        configuration.setLocalCacheScope(LocalCacheScope.SESSION);
        configuration.setCacheEnabled(true);
        return configuration;
    }


}
