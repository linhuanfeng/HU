package com.hu.health.sport.aop;


import com.hu.health.sport.config.dataSources.DataSourceContextHolder;
import com.hu.health.sport.config.dataSources.DataSourceTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class DataSourceAspect {
    private static final String[] queryStrs={"query","select","get"};

    @Pointcut("execution(* com.hu.health.sport.dao.*.*(..))")
    public void executeSql(){}

    @Before("executeSql()")
    public void doBefore(JoinPoint joinPoint){
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        String mName = methodSignature.getMethod().getName();
        log.info("拦截的sql方法：{}",mName);
        DataSourceContextHolder.setDatabaseType(DataSourceTypeEnum.DATA_SOURCE_MASTER); // 默认master
        for (String queryStr : queryStrs) {
            if(mName.startsWith(queryStr)){
                log.info("查询语句，设置数据源为slave");
                DataSourceContextHolder.setDatabaseType(DataSourceTypeEnum.DATA_SOURCE_SLAVE);
                break;
            }
        }
        log.info("当前数据源是：{}",DataSourceContextHolder.getDatabaseType().getName());
    }
}
