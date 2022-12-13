package com.hu.health.community.config.dataSources;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.util.CollectionUtils;
import javax.sql.DataSource;
import java.util.*;

//@Configuration
//@EnableConfigurationProperties(DataSourceProperties.class)
@Deprecated
public class MybatisConfig {

    @Autowired
    private DataSourceProperties dataSourceProperties;

    /**
     * 配置多主多从
     * @return
     * @throws Exception
     */
    @Bean
    public List<DataSource> masterDataSources() throws Exception {
        List<Map<String, String>> mastersources = dataSourceProperties.getMastersources();
        if(CollectionUtils.isEmpty(mastersources))
            throw new IllegalArgumentException("至少需要一个主数据源");
        List<DataSource> dataSources=new ArrayList<>();
        for (Map<String, String> props : mastersources) {
            dataSources.add(DruidDataSourceFactory.createDataSource(props));
        }
        return dataSources;
    }

    @Bean
    public List<DataSource> slaveDataSources() throws Exception {
        List<Map<String, String>> slavesources = dataSourceProperties.getSlavesources();
        if(CollectionUtils.isEmpty(slavesources))
            throw new IllegalArgumentException("至少需要一个主数据源");
        List<DataSource> dataSources=new ArrayList<>();
        for (Map<String, String> props : slavesources) {
            dataSources.add(DruidDataSourceFactory.createDataSource(props));
        }
        return dataSources;
    }

    @Bean
    @Primary // 同个接口多个实现类指定哪一个
    @DependsOn({"masterDataSources","slaveDataSources"})
    public DynamicDataSource dataSource(List<DataSource> masterDataSources,List<DataSource> slaveDataSources){
        Map<Object,Object> targetDataSources=new HashMap<>();
        for (int i = 0; i < masterDataSources.size(); i++) {
            targetDataSources.put(DataSourceTypeEnum.DATA_SOURCE_MASTER.getName()+i,masterDataSources.get(i));
        }
        for (int i = 0; i < slaveDataSources.size(); i++) {
            targetDataSources.put(DataSourceTypeEnum.DATA_SOURCE_SLAVE.getName()+i,slaveDataSources.get(i));
        }

        DynamicDataSource dataSource = new DynamicDataSource();
        dataSource.setTargetDataSources(targetDataSources); // 可选数据源
        dataSource.setDefaultTargetDataSource(slaveDataSources.get(0)); // 默认数据源，不能为空
        return dataSource;
    }
}
