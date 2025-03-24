package com.example.config;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import org.flywaydb.core.Flyway;

public class FlywayConfig implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver Loaded");
            Flyway flyway = Flyway.configure()
                    .dataSource("jdbc:mysql://localhost:3306/hotel_db",
                            "root", "password123#")
                    .locations("classpath:db/migration")
                    .baselineOnMigrate(true)
                    .baselineVersion("1")
                    .load();
            flyway.baseline();
            flyway.migrate();
            System.out.println("success");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("class not found "+e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("flyway exception "+e.getMessage());
        }
    }
}
