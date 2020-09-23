package com.inspire12.secretary.scheduler;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@Service
public class CloudJobExecutor extends QuartzJobBean {
    private int MAX_SLEEP_IN_SECONDS = 5;
    private volatile Thread currThread;
    Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    @Autowired
    private SchedulerFactoryBean schedulerFactoryBean;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        // TODO Auto-generated method stub
        // 실제 수행할 로직..
        JobKey jobKey = context.getJobDetail().getKey();
        currThread = Thread.currentThread();

        IntStream.range(0, 5).forEach(i -> {
            logger.info("SimpleJob Counting - {}", i);
            try {
                TimeUnit.SECONDS.sleep(MAX_SLEEP_IN_SECONDS);
            } catch (InterruptedException e) {
                logger.error(e.getMessage(), e);
            }
        });
    }

}