package com.self.APIRateLimiter.scheduler;

import com.self.APIRateLimiter.service.RateLimiterService;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class RateLimiterScheduler extends QuartzJobBean {

    @Autowired
    RateLimiterService rateLimiterService;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        System.out.println("Clearing request cache: "+ rateLimiterService.clearCache());
    }
}
