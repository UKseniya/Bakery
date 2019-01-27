package kz.epam.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import kz.epam.entities.User;
import org.apache.log4j.Logger;
import kz.epam.pool.ConnectionPool;

public class UserDAO extends AbstractDAO<User> {
    private Logger log = Logger.getRootLogger();
    private static final String SQL_SELECT_ALL_USERS = "SELECT * FROM user";
    private static final String SQL_CREATE_NEW_USER = "INSERT INTO user (first_name, last_name, " +
            "login, password, email, role)" +
            "VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SQL_FIND_USER_BY_LOGIN_AND_PASSWORD = "SELECT * FROM user " +
            "WHERE login = ? AND password = ?";
    private static final String SQL_FIND_USER_BY_LOGIN = "SELECT * FROM user " +
            "WHERE login = ?";

    @Override
    public  List<User> findAll() {
        List<User> users = null;
        ConnectionPool pool = ConnectionPool.getInstance();

        try (Connection connection = pool.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_USERS);){
            users = new ArrayList<>();

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt("user_id"));
                user.setFirstName(resultSet.getString("first_name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setLogin(resultSet.getString("login"));
                user.setPassword(resultSet.getString("password"));
                user.setEmail(resultSet.getString("email"));
                user.setRole(resultSet.getString("role"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("SQL error " + e.toString());
        }
        return users;
    }

    public boolean isUserRegistered (String login, String password) {
        boolean isRegistered = false;
        ConnectionPool pool = ConnectionPool.getInstance();

        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_USER_BY_LOGIN_AND_PASSWORD)) {
            statement.setString(1, login);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                isRegistered = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("SQL error " + e.toString());
        }
        return isRegistered;
    }

    public User findUserByLoginAndPassword (String login, String password) {
        User user = null;
        ConnectionPool pool = ConnectionPool.getInstance();

        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_USER_BY_LOGIN_AND_PASSWORD)) {
            statement.setString(1, login);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                user = new User();
                user.setId(resultSet.getInt("user_id"));
                user.setFirstName(resultSet.getString("first_name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setLogin(resultSet.getString("login"));
                user.setPassword(resultSet.getString("password"));
                user.setEmail(resultSet.getString("email"));
                user.setRole(resultSet.getString("role"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            log.error("SQL error " + e.toString());
        }
        return user;
    }

    public boolean isLoginFree(String login) {
        boolean isFree = false;
        ConnectionPool pool = ConnectionPool.getInstance();

        try (Connection connection = pool.getConnection();
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_USER_BY_LOGIN)) {
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();

            if (!resultSet.next()) {
                isFree = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("SQL error " + e.toString());
        }
        return isFree;
    }

    @Override
    public User findEntityById(int id) {
        return null;
    }


    @Override
    public int findEntityByID(User entity) {
        return 0;
    }


    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public boolean delete(User entity) {
        return false;
    }


    @Override
    public boolean create(User user) {
        ConnectionPool pool = ConnectionPool.getInstance();
        try (Connection connection = pool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_CREATE_NEW_USER);
             ) {
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getLogin());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(5, user.getEmail());
            preparedStatement.setString(6, "user");
            preparedStatement.executeUpdate();
            log.info("User has been inserted " + user.getId());
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("SQL error " + e.toString());
        }

        return false;
    }

    @Override
    public User update(User entity) {
        return null;
    }

}
