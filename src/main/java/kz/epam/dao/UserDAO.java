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
    private static final String SQL_SELECT_ALL_USERS = "SELECT user_id, first_name, last_name, login, " +
            "password, email, phone, role_name FROM user u JOIN user_role ur ON (ur.role_id = u.user_id)";
    private static final String SQL_CREATE_NEW_USER = "INSERT INTO user (first_name, last_name, " +
            "login, password, email, phone, role_id)" +
            "VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_FIND_ROLE_ID_BY_NAME = "SELECT * FROM user_role " +
            "WHERE role_name = ?";
    private static final String SQL_FIND_ROLE_NAME_BY_ID = "SELECT * FROM user_role " +
            "WHERE role_id = ?";
    private static final String SQL_FIND_USER_BY_LOGIN_AND_PASSWORD = "SELECT user_id, first_name, last_name, login, " +
            "password, email, phone, role_name FROM user AS u JOIN user_role AS ur ON ur.role_id = u.user_id " +
            "WHERE login = ? AND password = ?";
    private static final String SQL_FIND_USER_BY_LOGIN = "SELECT * FROM user " +
            "WHERE login = ?";
    private static final String SQL_FIND_USER_BY_ID = "SELECT * FROM user " +
            "WHERE user_id = ?";
    private static final String SQL_FIND_USER_FIRST_NAME_BY_ID = "SELECT * FROM user " +
            "WHERE user_id = ?";
    private static final String SQL_FIND_USER_LAST_NAME_BY_ID = "SELECT * FROM user " +
            "WHERE user_id = ?";

    private static String driverName = ConfigManager.getInstance().getProperty(ConfigManager.DATABASE_DRIVER_NAME);
    private static String url = ConfigManager.getInstance().getProperty(ConfigManager.DATABASE_URL);
    private static String db_user = ConfigManager.getInstance().getProperty(ConfigManager.DATABASE_USER);
    private static String db_password = ConfigManager.getInstance().getProperty(ConfigManager.DATABASE_PASSWORD);
    private static int maxConn = Integer.parseInt(ConfigManager.getInstance().getProperty(ConfigManager.MAX_CONN));
    private Logger log = Logger.getRootLogger();

    @Override

    public List<User> findAll() {
        List<User> users = null;
        ConnectionPool pool = ConnectionPool.getInstance(driverName, url, db_user, db_password, maxConn);
        Connection connection = pool.getConnection();

        try (Statement statement = connection.createStatement();
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
                user.setPhoneNumber(resultSet.getString("phone"));
                user.setRole(resultSet.getString("role_name"));
                users.add(user);
            }

            pool.freeConnection(connection);

        } catch (SQLException e) {
            e.printStackTrace();
            log.error("SQL error " + e.toString());
        }
        return users;
    }

    public boolean isUserRegistered (String login, String password) {
        boolean isRegistered = false;
        ConnectionPool pool = ConnectionPool.getInstance(driverName, url, db_user, db_password, maxConn);
        Connection connection = pool.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_USER_BY_LOGIN_AND_PASSWORD)) {
            statement.setString(1, login);
            statement.setString(2, password);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    isRegistered = true;
                }
            }
            catch (SQLException e) {
                e.printStackTrace();
                log.error("SQL error " + e.toString());
            }
            pool.freeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("SQL error " + e.toString());
        }
        return isRegistered;
    }

    public User findUserByLoginAndPassword (String login, String password) {
        User user = null;
        ConnectionPool pool = ConnectionPool.getInstance(driverName, url, db_user, db_password, maxConn);
        Connection connection = pool.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_USER_BY_LOGIN_AND_PASSWORD)) {
            statement.setString(1, login);
            statement.setString(2, password);

            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    user = new User();
                    user.setId(resultSet.getInt("user_id"));
                    user.setFirstName(resultSet.getString("first_name"));
                    user.setLastName(resultSet.getString("last_name"));
                    user.setLogin(resultSet.getString("login"));
                    user.setPassword(resultSet.getString("password"));
                    user.setEmail(resultSet.getString("email"));
                    user.setPhoneNumber(resultSet.getString("phone"));
                    user.setRole(resultSet.getString("role_name"));
                }

            } catch (SQLException e) {
                e.printStackTrace();
                log.error("SQL error " + e.toString());
            }
            pool.freeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("SQL error " + e.toString());
        }
        return user;
    }

    public boolean isLoginFree(String login) {
        boolean isFree = false;
        ConnectionPool pool = ConnectionPool.getInstance(driverName, url, db_user, db_password, maxConn);
        Connection connection = pool.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_USER_BY_LOGIN)) {
            statement.setString(1, login);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (!resultSet.next()) {
                    isFree = true;
                }
                pool.freeConnection(connection);
            } catch (SQLException e) {
                e.printStackTrace();
                log.error("SQL error " + e.toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("SQL error " + e.toString());
        }
        return isFree;
    }

    public int findUserId (User user) {
        int userId = 0;
        ConnectionPool pool = ConnectionPool.getInstance(driverName, url, db_user, db_password, maxConn);
        Connection connection = pool.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_USER_BY_LOGIN)) {
            statement.setString(1, user.getLogin());

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    userId = resultSet.getInt("user_id");
                }
                pool.freeConnection(connection);
            } catch (SQLException e) {
                e.printStackTrace();
                log.error("SQL error " + e.toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("SQL error " + e.toString());
        }
        return userId;
    }

    @Override
    public User findEntityById(int id) {
        User user = null;
        ConnectionPool pool = ConnectionPool.getInstance(driverName, url, db_user, db_password, maxConn);
        Connection connection = pool.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_USER_BY_ID)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    user = new User();
                    user.setFirstName(resultSet.getString("first_name"));
                    user.setLastName(resultSet.getString("last_name"));
                    user.setEmail(resultSet.getString("email"));
                    user.setPhoneNumber(resultSet.getString("phone"));
                }
                pool.freeConnection(connection);
            } catch (SQLException e) {
                e.printStackTrace();
                log.error("SQL error " + e.toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("SQL error " + e.toString());
        }
        return user;
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

        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_ROLE_ID_BY_NAME)) {
            statement.setString(1, role);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    roleId = resultSet.getInt("role_id");
                }
                pool.freeConnection(connection);
            } catch (SQLException e) {
                e.printStackTrace();
                log.error("SQL error " + e.toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("SQL error " + e.toString());
        }
        return roleId;
    }

    public String findRoleNameByID(int id) {
        String roleName = null;
        ConnectionPool pool = ConnectionPool.getInstance(driverName, url, db_user, db_password, maxConn);
        Connection connection = pool.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_ROLE_NAME_BY_ID)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    roleName = resultSet.getString("role_name");
                }
                pool.freeConnection(connection);
            } catch (SQLException e) {
                e.printStackTrace();
                log.error("SQL error " + e.toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("SQL error " + e.toString());
        }
        return roleName;
    }

    public String findFirstNameByID (int userID) {
        String firstName = null;
        ConnectionPool pool = ConnectionPool.getInstance(driverName, url, db_user, db_password, maxConn);
        Connection connection = pool.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_USER_FIRST_NAME_BY_ID)) {
            statement.setInt(1, userID);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    firstName = resultSet.getString("first_name");
                }
                pool.freeConnection(connection);
            } catch (SQLException e) {
                e.printStackTrace();
                log.error("SQL error " + e.toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("SQL error " + e.toString());
        }
        return firstName;
    }

    public String findLastNameByID (int userID) {
        String lastName = null;
        ConnectionPool pool = ConnectionPool.getInstance(driverName, url, db_user, db_password, maxConn);
        Connection connection = pool.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_USER_LAST_NAME_BY_ID)) {
            statement.setInt(1, userID);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    lastName = resultSet.getString("last_name");
                }
                pool.freeConnection(connection);
            } catch (SQLException e) {
                e.printStackTrace();
                log.error("SQL error " + e.toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("SQL error " + e.toString());
        }
        return lastName;
    }

    @Override
    public boolean create(User user) {
        ConnectionPool pool = ConnectionPool.getInstance(driverName, url, db_user, db_password, maxConn);
        Connection connection = pool.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_CREATE_NEW_USER)) {
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getLogin());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(5, user.getEmail());
            preparedStatement.setString(6,user.getPhoneNumber());
            preparedStatement.setInt(7, findRoleIDbyName("user"));
            preparedStatement.executeUpdate();
            log.info("User has been inserted " + user.getId());

            pool.freeConnection(connection);

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
