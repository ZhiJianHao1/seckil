package com.zhi.seckill.starter.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

/**
 * @author ZhiJH
 * @description Mybatis配置类
 * @date 2024/5/11
 */
public class MybatisConfig {
    @Value("${mybatis.scanpackages}")
    private String scanPackages;

    @Bean
    public SqlSessionFactoryBean sqlSessionFactoryBean(DruidDataSource dataSource) {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setTypeAliasesPackage(scanPackages);
        return sqlSessionFactoryBean;
    }
}
