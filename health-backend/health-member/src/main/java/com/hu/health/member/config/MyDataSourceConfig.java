package com.hu.health.member.config;

import com.zaxxer.hikari.HikariDataSource;
//import io.seata.rm.datasource.DataSourceProxy;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
@Deprecated
//@Configuration
public class MyDataSourceConfig {

    /**
     * 	protected static <T> T createDataSource(DataSourceProperties properties, Class<? extends DataSource> type) {
     * 		return (T) properties.initializeDataSourceBuilder().type(type).build();
     *        }
     */
//    @Bean
//    public DataSource dataSource(DataSourceProperties dataSourceProperties){
//        HikariDataSource hikariDataSource = dataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
//        return new DataSourceProxy(hikariDataSource); // 使用seata的代理数据源，否则事务无法回滚
//    }

}
