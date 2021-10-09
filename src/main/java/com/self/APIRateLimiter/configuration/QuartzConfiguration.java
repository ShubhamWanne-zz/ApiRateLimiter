package com.self.APIRateLimiter.configuration;

import com.self.APIRateLimiter.scheduler.RateLimiterScheduler;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QuartzConfiguration {

    @Bean
    public Trigger quartzTrigger(){
        SimpleScheduleBuilder simpleScheduleBuilder = SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(10).repeatForever();

        return TriggerBuilder.newTrigger()
                .forJob(jobDetail())
                .withSchedule(simpleScheduleBuilder)
                .withIdentity("rateLimiterJob")
                .build();
    }

    @Bean
    public JobDetail jobDetail() {
        return JobBuilder.newJob(RateLimiterScheduler.class).withIdentity("rateLimiterJob").storeDurably().build();
    }

}
