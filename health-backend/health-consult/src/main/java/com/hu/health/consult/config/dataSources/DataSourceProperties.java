package com.hu.health.consult.config.dataSources;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

@Data
@ConfigurationProperties(prefix = "spring")
public class DataSourceProperties {
    private List<Map<String,String>> mastersources;
    private List<Map<String,String>> slavesources;
}