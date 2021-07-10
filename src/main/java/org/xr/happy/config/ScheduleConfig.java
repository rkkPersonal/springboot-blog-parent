package org.xr.happy.config;


import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.stereotype.Component;
import org.xr.happy.job.CommonTrigger;
import org.xr.happy.job.GlobalCleanJob;

/**
 * @author Steven
 */
@Component
public class ScheduleConfig implements SchedulingConfigurer {

    private String cronExpression="0 */1 * * * ?";

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.addTriggerTask(new GlobalCleanJob(),new CommonTrigger(cronExpression));
    }
}
