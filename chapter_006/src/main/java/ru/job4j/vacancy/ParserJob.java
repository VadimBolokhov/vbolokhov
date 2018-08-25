package ru.job4j.vacancy;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.Year;
import java.util.List;
import java.util.Properties;

/**
 * Parser job to executed.
 * @author Vadim Bolokhov
 * @version $Id$
 * @since 0.1
 */
public class ParserJob implements Job {
    private static Logger logger = LogManager.getLogger("ParserJob");
    /** Application properties */
    private Properties appConfig = new Properties();

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            String appConfigPath = loadAppConfig(context);
            List<Vacancy> vacancies = this.getVacancies();
            logger.info(String.format("%d vacancies received", vacancies.size()));
            this.saveVacancies(vacancies);
            updateAppConfig(appConfigPath);
            logger.info("Update success.");
        } catch (Exception e) {
            throw new JobExecutionException(e.getMessage());
        }
    }

    private String loadAppConfig(JobExecutionContext context) throws IOException {
        JobDataMap dataMap = context.getJobDetail().getJobDataMap();
        String appConfigPath = dataMap.getString("appConfigPath");
        this.appConfig.load(new FileInputStream(appConfigPath));
        return appConfigPath;
    }

    private List<Vacancy> getVacancies() {
        LocalDateTime thisYear = LocalDateTime.of(Year.now().getValue(), 1, 1, 0, 0);
        String dateString = this.appConfig.getProperty("lastUpdate", thisYear.toString());
        LocalDateTime lastUpdate = LocalDateTime.parse(dateString);
        return new VacancyParser(lastUpdate).getVacancies();
    }

    private void saveVacancies(List<Vacancy> vacancies)
            throws ClassNotFoundException, SQLException {
        String user = appConfig.getProperty("jdbc.username");
        String password = appConfig.getProperty("jdbc.password");
        String driver = appConfig.getProperty("jdbc.driver");
        String url = appConfig.getProperty("jdbc.url");
        Class.forName(driver);
        try (Connection con = DriverManager.getConnection(url, user, password)) {
            new SQLVacancyDepositor(con).saveVacancies(vacancies);
        }

    }

    private void updateAppConfig(String appConfigPath) throws IOException {
        this.appConfig.setProperty("lastUpdate", LocalDateTime.now().toString());
        this.appConfig.store(new FileWriter(appConfigPath), "store to properties file");
    }
}
