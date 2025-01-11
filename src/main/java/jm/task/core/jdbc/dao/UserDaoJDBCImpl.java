package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    @Override
    public void createUsersTable() {
        try (Connection connection = Util.connectJDBC();
             Statement stmt = connection.createStatement()) {

            DatabaseMetaData dbm = connection.getMetaData();
            ResultSet tables = dbm.getTables(null, null, "USERS", null);

            if (tables.next()) {
                stmt.executeUpdate(
                        "CREATE TABLE users (\n" +
                                "id INT AUTO_INCREMENT PRIMARY KEY,\n" +
                                "name VARCHAR(100) NOT NULL,\n" +
                                "lastName VARCHAR(100) NOT NULL,\n" +
                                "age INT NOT NULL)");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dropUsersTable() {
        try (Connection connection = Util.connectJDBC();
             Statement stmt = connection.createStatement()) {

            DatabaseMetaData dbm = connection.getMetaData();
            ResultSet tables = dbm.getTables(null, null, "USERS", null);

            if (tables.next()) {
                stmt.executeUpdate("DROP TABLE users");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = Util.connectJDBC();
             PreparedStatement statement = connection.prepareStatement(
                     "INSERT INTO users (name, lastName, age) VALUES (?, ?, ?)")) {

            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setByte(3, age);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("User с именем — " + name + " добавлен в базу данных\n");
    }

    @Override
    public void removeUserById(long id) {
        try (Connection connection = Util.connectJDBC();
             PreparedStatement statement = connection.prepareStatement(
                     "DELETE FROM users WHERE id = ?")) {
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        try (Connection connection = Util.connectJDBC();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM users;");) {
            while (rs.next()) {
                String name = rs.getString("name");
                String lastName = rs.getString("lastName");
                byte age = (byte) rs.getInt("age");
                list.add(new User(name, lastName, age));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void cleanUsersTable() {
        try (Connection connection = Util.connectJDBC();
             Statement stmt = connection.createStatement();) {
            stmt.executeUpdate("TRUNCATE users");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
