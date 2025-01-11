package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.sql.*;

public class Util {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/jdbc";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root1111";
    public static Connection connectJDBC()  {
        Connection con = null;
        try {
            con = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            System.err.println("Не удалось установить соединение с базой данных.");
            e.printStackTrace();

            throw new RuntimeException("Ошибка при установлении соединения с базой данных.", e);
        }
        System.out.println(con);
        return con;
    }

    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        SessionFactory sessionFactory = new Configuration()
                .setProperty("hibernate.connection.driver_class", "com.mysql.jdbc.Driver")
                .setProperty("hibernate.connection.url", DB_URL)
                .setProperty("hibernate.connection.username", USERNAME)
                .setProperty("hibernate.connection.password", PASSWORD)

                .setProperty("hibernate.current_session_context_class", "thread")
                .setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect")
                .setProperty("hibernate.show_sql", "true")
                .addAnnotatedClass(User.class)
                .buildSessionFactory();

        return sessionFactory;
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
