package ru.job4j.vacancy;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.io.FileInputStream;
import java.util.Properties;

/**
 * Vacancy parser main class.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class SqlRuParser {
    private static Logger logger = LogManager.getLogger("SqlRuParser");

    public static void main(String[] args) throws Exception {
        String appConfigPath = args[0];
        Properties appConfig = new Properties();
        appConfig.load(new FileInputStream(appConfigPath));
        String cronSchedule = appConfig.getProperty("cron.time");

        Scheduler scheduler = new StdSchedulerFactory().getScheduler();
        scheduler.start();

        JobDetail job = JobBuilder.newJob(ParserJob.class)
                .withIdentity("SqlRuParser")
                .usingJobData("appConfigPath", appConfigPath)
                .build();
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("SqlRuTrigger")
                .withSchedule(CronScheduleBuilder.cronSchedule(cronSchedule))
                .startNow()
                .build();
        scheduler.scheduleJob(job, trigger);
    }
}
