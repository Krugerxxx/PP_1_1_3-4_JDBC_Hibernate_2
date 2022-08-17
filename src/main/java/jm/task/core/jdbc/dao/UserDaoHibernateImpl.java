package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import javax.persistence.PersistenceException;
import javax.persistence.QueryTimeoutException;
import javax.persistence.RollbackException;
import javax.persistence.TransactionRequiredException;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private final String TABLE_USERS = "users";
    private Util util = Util.getInstance();

    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        try (Session session = util.getSession()) {
            String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_USERS +
                    "(id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                    "name VARCHAR (30) NOT NULL, " +
                    "lastName VARCHAR (30) NOT NULL, " +
                    "age SMALLINT NOT NULL)";
            session.beginTransaction();
            session.createSQLQuery(sql).executeUpdate();
            session.getTransaction().commit();
        } catch (IllegalStateException | PersistenceException e) {
            System.out.println("Ошибка при создании таблицы");
            StackTraceElement[] element = e.getStackTrace();
            for (StackTraceElement o : element) {
                System.out.println(o);
            }
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = util.getSession()) {
            String sql = "DROP TABLE " + TABLE_USERS;
            session.beginTransaction();
            session.createSQLQuery(sql).executeUpdate();
            session.getTransaction().commit();
        } catch (IllegalStateException | PersistenceException e) {
            System.out.println("Ошибка при удалении таблицы");
            StackTraceElement[] element = e.getStackTrace();
            for (StackTraceElement o : element) {
                System.out.println(o);
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Session session = util.getSession()) {
            session.beginTransaction();
            session.save(new User(name, lastName, age));
            session.getTransaction().commit();
        } catch (IllegalStateException | PersistenceException e) {
            System.out.println("Ошибка при добалении данных в таблицу");
            StackTraceElement[] element = e.getStackTrace();
            for (StackTraceElement o : element) {
                System.out.println(o);
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = util.getSession()) {
            session.beginTransaction();
            User user = session.load(User.class, id);
            session.delete(user);
            session.getTransaction().commit();
        } catch (IllegalStateException | PersistenceException e) {
            System.out.println("Ошибка при удалении данных из таблицы");
            StackTraceElement[] element = e.getStackTrace();
            for (StackTraceElement o : element) {
                System.out.println(o);
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = util.getSession()) {
            return session.createQuery("FROM User").getResultList();
        } catch (HibernateException e) {
            System.out.println("Ошибка при чтении данных");
            StackTraceElement[] element = e.getStackTrace();
            for (StackTraceElement o : element) {
                System.out.println(o);
            }
        }
        return null;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = util.getSession()) {
            session.beginTransaction();
            session.createQuery("DELETE FROM User").executeUpdate();
            session.getTransaction().commit();
        } catch (IllegalStateException | PersistenceException e) {
            System.out.println("Ошибка при очистке таблицы");
            StackTraceElement[] element = e.getStackTrace();
            for (StackTraceElement o : element) {
                System.out.println(o);
            }
        }
    }
}
