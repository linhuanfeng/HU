package com.hu.health.consult.config.dataSources;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 核心就是继承AbstractRoutingDataSource中数据源路由选择
 * 决定哪个数据源
 */
class DynamicDataSource extends AbstractRoutingDataSource {

    @Autowired
    private DataSourceProperties dataSourceProperties;

    /**
     * 随机的方式获取数据源
     * 以lookupKey为key,从resolveDataSources中获取数据源
     * 	protected DataSource determineTargetDataSource() {
     * 		Assert.notNull(this.resolvedDataSources, "DataSource router not initialized");
     * 		Object lookupKey = determineCurrentLookupKey();
     * 		DataSource dataSource = this.resolvedDataSources.get(lookupKey);
     * @return
     */
    @Override
    protected Object determineCurrentLookupKey() {
        DataSourceTypeEnum databaseType = DataSourceContextHolder.getDatabaseType();
        int i;
        List<Map<String, String>> mastersources = dataSourceProperties.getMastersources();
        List<Map<String, String>> slavesources = dataSourceProperties.getSlavesources();
        if(databaseType.equals(DataSourceTypeEnum.DATA_SOURCE_MASTER))
            // 随机获取主数据源
            i= ThreadLocalRandom.current().nextInt(mastersources.size())%mastersources.size();
        else
            i= ThreadLocalRandom.current().nextInt(slavesources.size())%slavesources.size();
        return databaseType.getName()+i; //
    }
}