package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {
        UserService userService = new UserServiceImpl();
        List<User> beginList = new ArrayList<>();
        List<User> resultList;
        for (int i = 0; i < 4; i++) {
            beginList.add(new User("UserN" + i, "UserL" + i, Byte.valueOf((byte) (Math.random() * 127))));
        }

        userService.createUsersTable();
        for (User o : beginList) {
            userService.saveUser(o.getName(), o.getLastName(), o.getAge());
        }
        resultList = userService.getAllUsers();
        for (User o : resultList) {
            System.out.println(o);
        }
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
