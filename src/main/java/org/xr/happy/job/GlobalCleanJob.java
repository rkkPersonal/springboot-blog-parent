package org.xr.happy.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Steven
 */
public class GlobalCleanJob implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(GlobalCleanJob.class);

    public GlobalCleanJob() {

    }

    @Override
    public void run() {
        logger.info("GlobalCleanJob Start Running!!!");
    }
}
