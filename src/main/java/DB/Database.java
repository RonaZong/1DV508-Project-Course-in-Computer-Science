package DB;

import Models.Event;
import Models.Timeline;
import Models.User;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;

public class Database {

    private static SessionFactory factory;

    public static void startDB() {
        try {
//            factory = new Configuration().configure().addAnnotatedClass(User.class).buildSessionFactory();
            Configuration configuration = new Configuration();
//            configuration.configure().addAnnotatedClass(hibernate.xml);
            configuration.configure().addAnnotatedClass(User.class);
            configuration.configure().addAnnotatedClass(Timeline.class);
            configuration.configure().addAnnotatedClass(Event.class);

            factory = configuration.buildSessionFactory();

            Database database = new Database();

        }
        catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }

        DatabaseController dc = new DatabaseController();

        /* Add few user records in database */

    }

    /* Method to CREATE an user in the database */
    public Integer addUser(String username, String fullName, String password, String passwordHint, String image, boolean isAdmin) {
        Session session = factory.openSession();
        Transaction tx = null;
        Integer userID = null;

        try {
            // begin a unit of work and return the associated transaction object
            tx = session.beginTransaction();
            User user = new User(username, fullName, password, passwordHint, image, isAdmin);
            userID = (Integer) session.save(user);
            tx.commit();
        }
        catch (HibernateException e) {
            // If the Session throws an exception,
            // the transaction must be rolled back and the session must be discarded.
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        }
        finally {
            session.close();
        }

        return userID;
    }

    /* Method to READ all the users */
    public void listUsers() {
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            // begin a unit of work and return the associated transaction object
            tx = session.beginTransaction();
            List users = session.createQuery("FROM User").list();
            for (Iterator iterator = users.iterator(); iterator.hasNext();) {
                User user = (User) iterator.next();
                System.out.println("User Name: " + user.getUsername());
                System.out.println("Password Hint: " + user.getPasswordHint());
                System.out.println("User is admin: " + user.isAdmin());
            }
            tx.commit();
        }
        catch (HibernateException e) {
            // If the Session throws an exception,
            // the transaction must be rolled back and the session must be discarded.
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        }
        finally {
            session.close();
        }
    }

    /* Method to UPDATE info for a user */
    public void updateEmployee(Integer UserID, String username, String fullName, String password, String passwordHint, String image, boolean isAdmin){
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            User user = (User) session.get(User.class, UserID);
            user.setUsername(username);
            user.setFullname(fullName);
            user.setPassword(password);
            user.setPasswordHint(passwordHint);
            user.setImage(image);
            user.setAdmin(isAdmin);
            session.update(user);
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    /* Method to DELETE an employee from the records */
    public void deleteEmployee(Integer EmployeeID){
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            User employee = (User) session.get(User.class, EmployeeID);
            session.delete(employee);
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }


    public static List<Event> getEventsBetween(int timelineID, LocalDateTime startDate, LocalDateTime endDate) {
        Session session = factory.openSession();
        String hql = "FROM Timeline where timelineID = ";
        Query query = session.createQuery(hql);
//        query.setParameter("beginDate", startDate);
//        query.setParameter("endDate", endDate);
//        query.setParameter("timelineID", timelineID);
        System.out.println("From: "+startDate.toString());
        System.out.println("To: "+endDate.toString());
        System.out.println("SQL QUERY: "+query.toString());
        List<Event> listEvents = query.list();
        return listEvents;
    }


    /** Cancel the execution of the current query. */
    void cancelQuery() {

    }

    /** Completely clear the session. */
    void clear() {

    }
}