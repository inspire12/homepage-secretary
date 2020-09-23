package com.inspire12.secretary.scheduler;

import com.inspire12.secretary.service.community.ArticleService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

public class CronJob implements Job {

    @Autowired
    private ArticleService articleService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

    }
}
