package com.hu.health.consult.config.dataSources;

public enum DataSourceTypeEnum{
    DATA_SOURCE_MASTER(1,"master"),
    DATA_SOURCE_SLAVE(2,"slave");

    DataSourceTypeEnum(int code, String name) {
        this.code=code;
        this.name=name;
    }
    private Integer code;
    private String name;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}