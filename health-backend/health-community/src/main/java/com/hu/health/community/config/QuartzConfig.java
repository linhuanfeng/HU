package com.hu.health.community.config;

import com.hu.health.community.task.DeleteQuestionCacheTask;
import com.hu.health.community.task.ThumbTask;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.util.Date;

//@Configuration
public class QuartzConfig {

    private static final String Thumb_TASK_IDENTITY="ThumbTaskQuartz";

//    @Bean
//    public JobDetail jobDetail(){
//        return JobBuilder.newJob(ThumbTask.class).storeDurably().build();
//    }
//
//    @Bean
//    public Trigger trigger(){
//        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
//                .withIntervalInSeconds(30)
//                .repeatForever();
//        return TriggerBuilder.newTrigger().forJob(jobDetail())
//                .withIdentity(Thumb_TASK_IDENTITY)
//                .withSchedule(scheduleBuilder)
//                .build();
//    }

    // 下面是利用工厂包装factoryBean
    // jobDetail->绑定到trigger->加入到schedule调度

    @Bean
    public MethodInvokingJobDetailFactoryBean thumbTaskJobDetail(ThumbTask thumbTask){
        MethodInvokingJobDetailFactoryBean jobDetailFactoryBean = new MethodInvokingJobDetailFactoryBean();
        jobDetailFactoryBean.setConcurrent(true);
        jobDetailFactoryBean.setTargetObject(thumbTask);
        jobDetailFactoryBean.setTargetMethod("thumbToDB");
        return jobDetailFactoryBean;
    }

    @Bean
    public CronTriggerFactoryBean thumbTaskCronTrigger(JobDetail thumbTaskJobDetail){
        CronTriggerFactoryBean triggerFactoryBean = new CronTriggerFactoryBean();
        triggerFactoryBean.setJobDetail(thumbTaskJobDetail);
        triggerFactoryBean.setStartTime(new Date());
        // [秒] [分] [小时] [日] [月] [周] [年]
        // 从第一秒开始，每20秒一次
        triggerFactoryBean.setCronExpression("1/20 * * * * ?");
        return triggerFactoryBean;
    }

    @Bean
    public MethodInvokingJobDetailFactoryBean deleteQuestionCacheJobDetail(DeleteQuestionCacheTask DeleteQuestionCacheTask){
        MethodInvokingJobDetailFactoryBean jobDetailFactoryBean = new MethodInvokingJobDetailFactoryBean();
        jobDetailFactoryBean.setConcurrent(true);
        jobDetailFactoryBean.setTargetObject(DeleteQuestionCacheTask);
        jobDetailFactoryBean.setTargetMethod("deleteQuestionCache");
        return jobDetailFactoryBean;
    }

    @Bean
    public CronTriggerFactoryBean deleteQuestionCacheCronTrigger(JobDetail deleteQuestionCacheJobDetail){
        CronTriggerFactoryBean triggerFactoryBean = new CronTriggerFactoryBean();
        triggerFactoryBean.setJobDetail(deleteQuestionCacheJobDetail);
        triggerFactoryBean.setStartTime(new Date());
        // [秒] [分] [小时] [日] [月] [周] [年]
        // 从第一秒开始，每20秒一次
        triggerFactoryBean.setCronExpression("16/20 * * * * ?");
        return triggerFactoryBean;
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(Trigger thumbTaskCronTrigger,Trigger deleteQuestionCacheCronTrigger){
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        // 1秒后启动
        schedulerFactoryBean.setStartupDelay(1);
        schedulerFactoryBean.setTriggers(thumbTaskCronTrigger,deleteQuestionCacheCronTrigger);
        return schedulerFactoryBean;
    }





}
