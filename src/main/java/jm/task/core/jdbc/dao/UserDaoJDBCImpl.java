package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final String TABLE_USERS = "users";
    private Util util = Util.getInstance();

    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String messageExists = "CREATE TABLE " + TABLE_USERS +
                "(id bigint auto_increment primary key, " +
                "name varchar (30) not null, " +
                "lastName varchar (30) not null, " +
                "age smallint not null)";
        try (Connection connection = util.getConnection()) {
            Statement statement = connection.createStatement();
            statement.executeUpdate(messageExists);
        } catch (SQLException e) {
            System.out.println("Была попытка создания существующей таблицы.");
        }
    }

    public void dropUsersTable() {
        String messageDrop = "drop table " + TABLE_USERS;
        try (Connection connection = util.getConnection()) {
            Statement statement = connection.createStatement();
            statement.executeUpdate(messageDrop);
        } catch (SQLException e) {
            System.out.println("Попытка удаления несуществующей таблицы.");
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String messageInsert = "INSERT INTO " + TABLE_USERS +
                "(name, lastname, age) value ('" +
                name + "', '" + lastName + "', " + age + ")";
        try (Connection connection = util.getConnection()) {
            Statement statement = connection.createStatement();
            statement.executeUpdate(messageInsert);
            connection.commit();
            System.out.println("User с именем " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            System.out.println("Ошибка при добавлении новой записи в таблицу.");
        }
    }

    public void removeUserById(long id) {
        String messageRemove = "DELETE  FROM " + TABLE_USERS + " WHERE id = " + id;
        try (Connection connection = util.getConnection()) {
            Statement statement = connection.createStatement();
            statement.executeUpdate(messageRemove);
            connection.commit();
        } catch (SQLException e) {
            System.out.println("Неудачная попытка удаления пользователя.");
        }
    }

    public List<User> getAllUsers() {
        List<User> resultList = new ArrayList<>();
        String messageGetAll = "SELECT * FROM " + TABLE_USERS;
        try (Connection connection = util.getConnection()) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(messageGetAll);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastname"));
                user.setAge(resultSet.getByte("age"));
                resultList.add(user);
            }
            resultSet.close();
        } catch (SQLException e) {
            System.out.println("Ошибка при чтении данных из таблицы.");
        }
        return resultList;
    }

    public void cleanUsersTable() {
        String messageClean = "TRUNCATE " + TABLE_USERS;
        try (Connection connection = util.getConnection()) {
            Statement statement = connection.createStatement();
            statement.executeUpdate(messageClean);
        } catch (SQLException e) {
            System.out.println("Ошибка при очистке таблицы.");
        }
    }

}
