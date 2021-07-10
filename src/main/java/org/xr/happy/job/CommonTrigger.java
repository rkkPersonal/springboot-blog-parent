package org.xr.happy.job;

import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.support.CronTrigger;

import java.util.Date;

/**
 * @author Steven
 */
public class CommonTrigger implements Trigger {

    private String cronExpression;

    public CommonTrigger(String cronExpression){
        this.cronExpression=cronExpression;
    }

    @Override
    public Date nextExecutionTime(TriggerContext triggerContext) {
        Date date = new CronTrigger(cronExpression).nextExecutionTime(triggerContext);
        return date;
    }
}
