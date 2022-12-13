package com.hu.health.member.config.dataSources;
@Deprecated
public class DataSourceContextHolder {
    private static final ThreadLocal<DataSourceTypeEnum> contextHolder=new ThreadLocal<>();
    public static void setDatabaseType(DataSourceTypeEnum databaseType){
        contextHolder.set(databaseType);
    }
    public static DataSourceTypeEnum getDatabaseType(){
        DataSourceTypeEnum typeEnum = contextHolder.get();
        if(typeEnum==null)
            typeEnum=DataSourceTypeEnum.DATA_SOURCE_MASTER;
        return typeEnum;
    }
}
