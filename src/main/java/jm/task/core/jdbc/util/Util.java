package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.*;
import java.util.Properties;

public class Util {
    private static Util util;
    private final String NAME_USER = "root";
    private final String PASSWORD = "root";
    private final String URL = "jdbc:mysql://localhost:3306/mysql";
    private Connection connection;

    private SessionFactory sessionFactory;

    private Util() {
    }

    public static synchronized Util getInstance() {
        if (util == null) {
            util = new Util();
        }
        return util;
    }

    public Connection getConnection() throws SQLException {
        connection = DriverManager.getConnection(URL, NAME_USER, PASSWORD);
        connection.setAutoCommit(false);
        return connection;
    }

    public Session getSession() throws HibernateException{
        if (sessionFactory == null) {
            Configuration configuration = new Configuration();
            Properties properties = new Properties();
            properties.setProperty(Environment.URL, URL);
            properties.setProperty(Environment.USER, NAME_USER);
            properties.setProperty(Environment.PASS, PASSWORD);
            properties.setProperty(Environment.POOL_SIZE, "1");
            properties.setProperty(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
            properties.setProperty(Environment.SHOW_SQL, "true");
            configuration.setProperties(properties);
            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties())
                    .build();
            sessionFactory = new MetadataSources(serviceRegistry)
                    .addAnnotatedClass(User.class)
                    .buildMetadata()
                    .buildSessionFactory();
        }
        return sessionFactory.openSession();
    }

}
