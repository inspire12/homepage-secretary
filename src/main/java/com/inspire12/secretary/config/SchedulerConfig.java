package com.inspire12.secretary.config;

import com.inspire12.secretary.scheduler.ApplicationContextHolder;
import org.quartz.spi.JobFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

@Configuration
public class SchedulerConfig {

    @Autowired
    private ApplicationContext applicationContext;

    /**
     * Create the job factory bean
     * @return Job factory bean
     */
    @Bean
    public JobFactory jobFactory() {

        ApplicationContextHolder jobFactory = new ApplicationContextHolder();
        jobFactory.setApplicationContext(applicationContext);
        return jobFactory;
    }

    /**
     * Create the Scheduler Factory bean
     * @return scheduler factory object
     */
    @Bean
    public SchedulerFactoryBean schedulerFactory() {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setAutoStartup(true);
        factory.setSchedulerName("My Scheduler");
        factory.setOverwriteExistingJobs(true);
        factory.setJobFactory(jobFactory());
        return factory;
    }

}
