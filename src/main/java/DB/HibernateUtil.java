package DB;

// NOTE:
// Incorporated in the Database controller so we can change all the settings of it in one place

import Models.Event;
import Models.Timeline;
import Models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.sql.ordering.antlr.Factory;

import java.util.Properties;


public class HibernateUtil {

    private static final SessionFactory sf = getSessionFactory(); //buildSessionFactory();

    public static SessionFactory getSessionFactory() {
        SessionFactory sessionFactory = null;
            try {
                Configuration configuration = new Configuration();
                // Hibernate settings equivalent to hibernate.cfg.xml's properties
                Properties settings = new Properties();
                settings.put(Environment.DRIVER, "org.mariadb.jdbc.Driver");
                settings.put(Environment.URL, "jdbc:mysql://localhost:26439/testDB");
                //settings.put(Environment.USER, "root");
                //settings.put(Environment.PASS, "root");
                settings.put(Environment.DIALECT, "org.hibernate.dialect.MariaDBDialect");
                settings.put(Environment.SHOW_SQL, "true");
                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
                //settings.put(Environment.HBM2DDL_AUTO, "create-drop");
                settings.put(Environment.HBM2DDL_AUTO, "update");

                configuration.setProperties(settings);
                configuration.addAnnotatedClass(Event.class);
                configuration.addAnnotatedClass(Timeline.class);
                configuration.addAnnotatedClass(User.class);
                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();
                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                e.printStackTrace();
            }
        return sessionFactory;
    }

    //return new Configuration().configure().buildSessionFactory();


    public static SessionFactory getSf() {
        return sf;
    }

    public static void shutDown() {
        getSf().close();
    }
}
