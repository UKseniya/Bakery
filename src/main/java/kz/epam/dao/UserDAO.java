package kz.epam.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import kz.epam.config.ConfigManager;
import kz.epam.entities.User;
import org.apache.log4j.Logger;
import kz.epam.pool.ConnectionPool;

public class UserDAO extends AbstractDAO<User> {
    private Logger log = Logger.getRootLogger();
    private static final String SQL_SELECT_ALL_USERS = "SELECT user_id, first_name, last_name, login, " +
            "password, email, role_name FROM user u JOIN user_role ur ON (ur.role_id = user_id)";
    private static final String SQL_CREATE_NEW_USER = "INSERT INTO user (first_name, last_name, " +
            "login, password, email, role_id)" +
            "VALUES (?, ?, ?, ?, ?, ?)";
    private static final String SQL_FIND_ROLE_ID_BY_NAME = "SELECT * FROM user_role " +
            "WHERE role_name = ?";
    private static final String SQL_FIND_USER_BY_LOGIN_AND_PASSWORD = "SELECT user_id, first_name, last_name, login, " +
            "password, email, role_name FROM user u JOIN user_role ur ON (ur.role_id = user_id) " +
            "WHERE login = ? AND password = ?";
    private static final String SQL_FIND_USER_BY_LOGIN = "SELECT * FROM user " +
            "WHERE login = ?";

    private static String driverName = ConfigManager.getInstance().getProperty(ConfigManager.DATABASE_DRIVER_NAME);
    private static String url = ConfigManager.getInstance().getProperty(ConfigManager.DATABASE_URL);
    private static String db_user = ConfigManager.getInstance().getProperty(ConfigManager.DATABASE_USER);
    private static String db_password = ConfigManager.getInstance().getProperty(ConfigManager.DATABASE_PASSWORD);
    private static String maximumConnection = ConfigManager.getInstance().getProperty(ConfigManager.MAX_CONN);
    private static int maxConn = Integer.parseInt(maximumConnection);

    @Override

    public List<User> findAll() {
        List<User> users = null;
        ConnectionPool pool = ConnectionPool.getInstance(driverName, url, db_user, db_password, maxConn);
        Connection connection = pool.getConnection();
        try (
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
                user.setRole(resultSet.getString("role_name"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("SQL error " + e.toString());
        } finally {
            pool.release();
            pool.setFreeConnection(connection);
        }
        return users;
    }

    public boolean isUserRegistered (String login, String password) {
        boolean isRegistered = false;
        ConnectionPool pool = ConnectionPool.getInstance(driverName, url, db_user, db_password, maxConn);
        Connection connection = pool.getConnection();
        try (
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
        } finally {
            try {
                connection.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
            pool.setFreeConnection(connection);
            pool.release();

        }
        return isRegistered;
    }

    public User findUserByLoginAndPassword (String login, String password) {
        User user = null;
        ConnectionPool pool = ConnectionPool.getInstance(driverName, url, db_user, db_password, maxConn);
        Connection connection = pool.getConnection();
        try (
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
                user.setRole(resultSet.getString("role_name"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            log.error("SQL error " + e.toString());
        } finally {
            try {
                connection.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
            pool.setFreeConnection(connection);
            pool.release();
        }
        return user;
    }

    public boolean isLoginFree(String login) {
        boolean isFree = false;
        ConnectionPool pool = ConnectionPool.getInstance(driverName, url, db_user, db_password, maxConn);
        Connection connection = pool.getConnection();
        try (
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_USER_BY_LOGIN)) {
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();

            if (!resultSet.next()) {
                isFree = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("SQL error " + e.toString());
        } finally {
            try {
                connection.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
            pool.setFreeConnection(connection);
            pool.release();
        }
        return isFree;
    }

    public int findUserId (User user) {
        int userId = 0;
        ConnectionPool pool = ConnectionPool.getInstance(driverName, url, db_user, db_password, maxConn);
        Connection connection = pool.getConnection();
        try (
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_USER_BY_LOGIN)) {
            statement.setString(1, user.getLogin());
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                userId = resultSet.getInt("user_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("SQL error " + e.toString());
        } finally {
            try {
                connection.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
            pool.setFreeConnection(connection);
            pool.release();
        }
        return userId;
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

    public int findRoleIDbyName(String role) {
        int roleId = 0;
        ConnectionPool pool = ConnectionPool.getInstance(driverName, url, db_user, db_password, maxConn);
        Connection connection = pool.getConnection();
        try (
             PreparedStatement statement = connection.prepareStatement(SQL_FIND_ROLE_ID_BY_NAME)) {
            statement.setString(1, role);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                roleId = resultSet.getInt("role_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("SQL error " + e.toString());
        } finally {
            try {
                connection.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
            pool.setFreeConnection(connection);
            pool.release();
        }
        return roleId;
    }

    @Override
    public boolean create(User user) {
        ConnectionPool pool = ConnectionPool.getInstance(driverName, url, db_user, db_password, maxConn);
        Connection connection = pool.getConnection();
        try (
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_CREATE_NEW_USER);
             ) {
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getLogin());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(5, user.getEmail());
            preparedStatement.setInt(6, findRoleIDbyName("user"));
            preparedStatement.executeUpdate();
            log.info("User has been inserted " + user.getId());
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("SQL error " + e.toString());
        } finally {
            try {
                connection.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }
            pool.setFreeConnection(connection);
            pool.release();
        }

        return false;
    }

    @Override
    public User update(User entity) {
        return null;
    }

}
