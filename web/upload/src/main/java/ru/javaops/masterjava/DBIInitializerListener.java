package ru.javaops.masterjava;

import ru.javaops.masterjava.persist.DBIProvider;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.DriverManager;

@WebListener
public class DBIInitializerListener implements ServletContextListener {

    private static final String URL = "jdbc:postgresql://localhost:5432/masterjava";
    private static final String USER = "user";
    private static final String PASSWORD = "password";

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        DBIProvider.init(() -> {
            try {
                Class.forName("org.postgresql.Driver");
            } catch (ClassNotFoundException e) {
                throw new IllegalStateException("PostgreSQL driver not found", e);
            }
            return DriverManager.getConnection(URL, USER, PASSWORD);
        });
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }

}
