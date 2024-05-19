package com.zhi.seckill.starter.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author ZhiJH
 * @description Spring事务配置类
 * @date 2024/5/11
 */
@Configuration
@MapperScan(value = {"com.zhi.seckill.infrastructure.mapper"})
@ComponentScan(value = {"com.zhi.seckill"})
@PropertySource(value = {"classpath:properties/jdbc.properties", "classpath:properties/mybatis.properties"})
@Import({JdbcConfig.class, RedisConfig.class, MybatisConfig.class})
@ServletComponentScan(basePackages = {"com.zhi.seckill"})
@EnableTransactionManagement(proxyTargetClass = true)
public class TransactionConfig {

    @Bean
    public TransactionManager transactionManager(DruidDataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
