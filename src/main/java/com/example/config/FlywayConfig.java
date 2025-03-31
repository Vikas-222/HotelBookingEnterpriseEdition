package com.example.config;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.flywaydb.core.Flyway;

@WebListener
public class FlywayConfig implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Flyway flyway = Flyway.configure()
                    .dataSource("jdbc:mysql://localhost:3306/hotel_db",
                            "root", "password123#")
                    .locations("classpath:db/migration")
                    .baselineOnMigrate(true)
                    .baselineVersion("1")
                    .baselineDescription("flyway baseline")
                    .load();
            flyway.repair();
            flyway.baseline();
            flyway.migrate();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
