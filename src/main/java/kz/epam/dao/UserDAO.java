package kz.epam.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import kz.epam.config.ConfigManager;
import kz.epam.constant.Constants;
import kz.epam.entities.User;
import org.apache.log4j.Logger;
import kz.epam.pool.ConnectionPool;

public class UserDAO extends AbstractDAO<User> {

    private static final String  SQL_FIND_PASSWORD_BY_LOGIN = "SELECT password FROM user WHERE login = ?";
    private static final String SQL_FIND_USERS_BY_ROLE = "SELECT * FROM user WHERE role_id = ?";
    private static final String SQL_SELECT_ALL_USERS = "SELECT user_id, first_name, last_name, login, " +
            "password, email, phone, role_name FROM user u JOIN user_role ur ON (ur.role_id = u.user_id)";
    private static final String SQL_CREATE_NEW_USER = "INSERT INTO user (first_name, last_name, " +
            "login, password, email, phone, role_id)" +
            "VALUES (?, ?, ?, ?, ?, ?, ?)";
    private static final String SQL_FIND_ROLE_ID_BY_NAME = "SELECT * FROM user_role " +
            "WHERE role_name = ?";
    private static final String SQL_FIND_ROLE_NAME_BY_ID = "SELECT * FROM user_role " +
            "WHERE role_id = ?";
    private static final String SQL_FIND_USER_BY_LOGIN_AND_PASSWORD = "SELECT * FROM user " +
            "WHERE login = ? AND password = ?";
    private static final String SQL_FIND_USER_BY_LOGIN = "SELECT * FROM user " +
            "WHERE login = ?";
    private static final String SQL_FIND_USER_BY_ID = "SELECT * FROM user " +
            "WHERE user_id = ?";
    private static final String SQL_FIND_USER_FIRST_NAME_BY_ID = "SELECT * FROM user " +
            "WHERE user_id = ?";
    private static final String SQL_FIND_USER_LAST_NAME_BY_ID = "SELECT * FROM user " +
            "WHERE user_id = ?";
    private static final String SQL_UPDATE_PASSWORD = "UPDATE user SET password = ? WHERE login = ?";
    private static final String SQL_UPDATE_USER_INFO = "UPDATE user SET first_name = ?, last_name = ?, " +
            "email = ?, phone = ? WHERE login = ?";

    private static final String USER_ID = "user_id";
    private static final String FIRST_NAME = "first_name";
    private static final String LAST_NAME = "last_name";
    private static final String ROLE_NAME = "role_name";
    private static final String ROLE_ID = "role_id";
    private static final String USER_INSERTED_MESSAGE = "User has been inserted ";

    private static String driverName = ConfigManager.getInstance().getProperty(ConfigManager.DATABASE_DRIVER_NAME);
    private static String url = ConfigManager.getInstance().getProperty(ConfigManager.DATABASE_URL);
    private static String db_user = ConfigManager.getInstance().getProperty(ConfigManager.DATABASE_USER);
    private static String db_password = ConfigManager.getInstance().getProperty(ConfigManager.DATABASE_PASSWORD);
    private static int maxConn = Integer.parseInt(ConfigManager.getInstance().getProperty(ConfigManager.MAX_CONN));
    private Logger log = Logger.getRootLogger();

    public String findPasswordByLogin (String login) {
        String password = null;
        ConnectionPool pool = ConnectionPool.getInstance(driverName, url, db_user, db_password, maxConn);
        Connection connection = pool.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_PASSWORD_BY_LOGIN)) {
            statement.setString(1, login);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    password = resultSet.getString("password");
                }
            }
            catch (SQLException e) {
                e.printStackTrace();
                log.error(Constants.SQL_ERROR + e.toString());
            }
            pool.freeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
            log.error(Constants.SQL_ERROR + e.toString());
        }
        return password;
    }

    public List<User> findAllUsersByRole (String roleName) {
        List<User> users = null;
        ConnectionPool pool = ConnectionPool.getInstance(driverName, url, db_user, db_password, maxConn);
        Connection connection = pool.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_USERS_BY_ROLE)) {
            statement.setInt(1, findRoleIDbyName(roleName));

            users = new ArrayList<>();

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    User user = new User();
                    user.setFirstName(resultSet.getString("first_name"));
                    user.setLastName(resultSet.getString("last_name"));
                    user.setLogin(resultSet.getString("login"));
                    user.setPhoneNumber(resultSet.getString("phone"));
                    users.add(user);
                }
            } catch (SQLException e) {
                    e.printStackTrace();
                    log.error(Constants.SQL_ERROR + e.toString());
                }
                pool.freeConnection(connection);
            } catch (SQLException e) {
                e.printStackTrace();
                log.error(Constants.SQL_ERROR + e.toString());
            }
        return users;
    }

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
                user.setId(resultSet.getInt(USER_ID));
                user.setFirstName(resultSet.getString(FIRST_NAME));
                user.setLastName(resultSet.getString(LAST_NAME));
                user.setLogin(resultSet.getString(Constants.LOGIN));
                user.setPassword(resultSet.getString(Constants.PASSWORD));
                user.setEmail(resultSet.getString(Constants.EMAIL));
                user.setPhoneNumber(resultSet.getString(Constants.PHONE));
                user.setRole(resultSet.getString(ROLE_NAME));
                users.add(user);
            }

            pool.freeConnection(connection);

        } catch (SQLException e) {
            e.printStackTrace();
            log.error(Constants.SQL_ERROR + e.toString());
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
                log.error(Constants.SQL_ERROR + e.toString());
            }
            pool.freeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
            log.error(Constants.SQL_ERROR + e.toString());
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
                    user.setId(resultSet.getInt(USER_ID));
                    user.setFirstName(resultSet.getString(FIRST_NAME));
                    user.setLastName(resultSet.getString(LAST_NAME));
                    user.setLogin(resultSet.getString(Constants.LOGIN));
//                    user.setPassword(resultSet.getString(Constants.PASSWORD));
                    user.setEmail(resultSet.getString(Constants.EMAIL));
                    user.setPhoneNumber(resultSet.getString(Constants.PHONE));
                    user.setRole(findRoleNameByID(resultSet.getInt(ROLE_ID)));
                }

            } catch (SQLException e) {
                e.printStackTrace();
                log.error(Constants.SQL_ERROR + e.toString());
            }
            pool.freeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
            log.error(Constants.SQL_ERROR + e.toString());
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
                log.error(Constants.SQL_ERROR + e.toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            log.error(Constants.SQL_ERROR + e.toString());
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
                    userId = resultSet.getInt(USER_ID);
                }
                pool.freeConnection(connection);
            } catch (SQLException e) {
                e.printStackTrace();
                log.error(Constants.SQL_ERROR + e.toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            log.error(Constants.SQL_ERROR + e.toString());
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
                    user.setFirstName(resultSet.getString(FIRST_NAME));
                    user.setLastName(resultSet.getString(LAST_NAME));
                    user.setEmail(resultSet.getString(Constants.EMAIL));
                    user.setPhoneNumber(resultSet.getString(Constants.PHONE));
                }
                pool.freeConnection(connection);
            } catch (SQLException e) {
                e.printStackTrace();
                log.error(Constants.SQL_ERROR + e.toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            log.error(Constants.SQL_ERROR + e.toString());
        }
        return user;
    }


    @Override
    public int findEntityByID(User entity) {
        throw new UnsupportedOperationException(Constants.NOT_SUPPORTED_EXCEPTION_MESSAGE);
    }

    @Override
    public boolean delete(int id) {
        throw new UnsupportedOperationException(Constants.NOT_SUPPORTED_EXCEPTION_MESSAGE);
    }

    @Override
    public boolean delete(User entity) {
        throw new UnsupportedOperationException(Constants.NOT_SUPPORTED_EXCEPTION_MESSAGE);
    }

    public int findRoleIDbyName(String role) {
        int roleId = 0;
        ConnectionPool pool = ConnectionPool.getInstance(driverName, url, db_user, db_password, maxConn);
        Connection connection = pool.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_ROLE_ID_BY_NAME)) {
            statement.setString(1, role);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    roleId = resultSet.getInt(ROLE_ID);
                }
                pool.freeConnection(connection);
            } catch (SQLException e) {
                e.printStackTrace();
                log.error(Constants.SQL_ERROR + e.toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            log.error(Constants.SQL_ERROR + e.toString());
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
                    roleName = resultSet.getString(ROLE_NAME);
                }
                pool.freeConnection(connection);
            } catch (SQLException e) {
                e.printStackTrace();
                log.error(Constants.SQL_ERROR + e.toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            log.error(Constants.SQL_ERROR + e.toString());
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
                    firstName = resultSet.getString(FIRST_NAME);
                }
                pool.freeConnection(connection);
            } catch (SQLException e) {
                e.printStackTrace();
                log.error(Constants.SQL_ERROR + e.toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            log.error(Constants.SQL_ERROR + e.toString());
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
                    lastName = resultSet.getString(LAST_NAME);
                }
                pool.freeConnection(connection);
            } catch (SQLException e) {
                e.printStackTrace();
                log.error(Constants.SQL_ERROR + e.toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            log.error(Constants.SQL_ERROR + e.toString());
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
            preparedStatement.setInt(7, findRoleIDbyName(Constants.USER));
            preparedStatement.executeUpdate();
            log.info(USER_INSERTED_MESSAGE + user.getId());

            pool.freeConnection(connection);

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            log.error(Constants.SQL_ERROR + e.toString());
        }
        return false;
    }

    public boolean updateUserInfo (User user) {
        ConnectionPool pool = ConnectionPool.getInstance(driverName, url, db_user, db_password, maxConn);
        Connection connection = pool.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_USER_INFO)) {
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getPhoneNumber());
            preparedStatement.setString(5, user.getLogin());
            preparedStatement.executeUpdate();

            pool.freeConnection(connection);

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            log.error(Constants.SQL_ERROR + e.toString());
        }
        return false;
    }

    public boolean updateUserPassword (User user) {
        ConnectionPool pool = ConnectionPool.getInstance(driverName, url, db_user, db_password, maxConn);
        Connection connection = pool.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_PASSWORD)) {
            preparedStatement.setString(1, user.getPassword());
            preparedStatement.setString(2, user.getLogin());
            preparedStatement.executeUpdate();

            pool.freeConnection(connection);

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            log.error(Constants.SQL_ERROR + e.toString());
        }
        return false;
    }

    @Override
    public User update(User entity) {
        throw new UnsupportedOperationException(Constants.NOT_SUPPORTED_EXCEPTION_MESSAGE);
    }
}
