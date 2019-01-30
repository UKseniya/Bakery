package kz.epam.dao;

import kz.epam.config.ConfigManager;
import kz.epam.entities.*;
import kz.epam.pool.ConnectionPool;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO extends AbstractDAO<Order> {
    private Logger log = Logger.getRootLogger();
    private static final String SQL_SELECT_ALL_ORDERS_BY_USER = "SELECT * FROM ordering " +
            "WHERE user_id = ?";
    private static final String SQL_SELECT_ALL_ORDERS_BY_DATE = "SELECT * FROM ordering " +
            "WHERE date = ?";
    private static final String SQL_CREATE_NEW_ORDER = "INSERT INTO ordering (user_id, product_id, " +
            "quantity, date)" +
            "VALUES (?, ?, ?, ?)";

    private static String driverName = ConfigManager.getInstance().getProperty(ConfigManager.DATABASE_DRIVER_NAME);
    private static String url = ConfigManager.getInstance().getProperty(ConfigManager.DATABASE_URL);
    private static String user_name = ConfigManager.getInstance().getProperty(ConfigManager.DATABASE_USER);
    private static String password = ConfigManager.getInstance().getProperty(ConfigManager.DATABASE_PASSWORD);
    private static int maxConn = Integer.parseInt(ConfigManager.getInstance().getProperty(ConfigManager.MAX_CONN));

    @Override
    public List findAll() {
        return null;
    }

    public List<Order> findAllByUser (User user) {
            List<Order> orders = null;

        ConnectionPool pool = ConnectionPool.getInstance(driverName, url, user_name, password, maxConn);
        Connection connection = pool.getConnection();

            try (
                 PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ALL_ORDERS_BY_USER)) {
                preparedStatement.setInt(1, user.getId());
                ResultSet resultSet = preparedStatement.executeQuery();
                orders = new ArrayList<>();

                Order order = null;
                ProductDAO productDAO = new ProductDAO();
                Product product = productDAO.findProductbyCode(order.getItem().getProduct().getCode());

                while (resultSet.next()) {
                    order = new Order();
                    order.setId(resultSet.getInt("order_id"));
                    order.setUser(user);
                    order.getItem().setProduct(product);
                    order.getItem().setQuantity(resultSet.getInt("quantity"));
                    order.setRequestedDate(resultSet.getDate("date"));
                    orders.add(order);
                }

            } catch (SQLException e) {
                e.printStackTrace();
                log.error("SQL error " + e.toString());
            } finally {
                pool.release();
                pool.setFreeConnection(connection);
            }

            return orders;
    }

    public List<Order> findAllByDate (Date date) {
        List<Order> orders = null;
        ConfigManager config = ConfigManager.getInstance();
        ConnectionPool pool = ConnectionPool.getInstance(driverName, url, user_name, password, maxConn);
        Connection connection = pool.getConnection();
        try (
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ALL_ORDERS_BY_DATE)) {
            preparedStatement.setDate(1, date);
            ResultSet resultSet = preparedStatement.executeQuery();
            orders = new ArrayList<>();

            Order order = null;
            ProductDAO productDAO = new ProductDAO();
            Product product = productDAO.findProductbyCode(order.getItem().getProduct().getCode());

            while (resultSet.next()) {
                order = new Order();
                order.setId(resultSet.getInt("order_id"));
                order.getUser().setId(resultSet.getInt("user_id"));
                order.getItem().setProduct(product);
                order.getItem().setQuantity(resultSet.getInt("quantity"));
                order.setRequestedDate(resultSet.getDate("date"));
                orders.add(order);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            log.error("SQL error " + e.toString());
        }
        finally {
            pool.release();
            pool.setFreeConnection(connection);
        }

        return orders;
    }

    @Override
    public Order findEntityById(int id) {
        return null;
    }

    @Override
    public int findEntityByID(Order order) {
        return 0;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public boolean delete(Order order) {
        return false;
    }

    @Override
    public boolean create(Order order) {
        ConfigManager config = ConfigManager.getInstance();
        ConnectionPool pool = ConnectionPool.getInstance(driverName, url, user_name, password, maxConn);
        Connection connection = pool.getConnection();
//        UserDAO userDAO = new UserDAO();
//        int userID = userDAO.findUserId(order.getUser());
//        ProductDAO productDAO = new ProductDAO();
//        int productID = productDAO.findProductbyCode(order.getItem().getProduct());

        java.util.Date utilDate = order.getRequestedDate();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

        try (
             PreparedStatement preparedStatement = connection.prepareStatement(SQL_CREATE_NEW_ORDER);
        ) {
            preparedStatement.setInt(1, order.getUser().getId());
            preparedStatement.setInt(2, order.getItem().getProduct().getId());
            preparedStatement.setInt(3, order.getItem().getQuantity());
            preparedStatement.setDate(4, sqlDate);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("SQL error " + e.toString());
        } finally {
            pool.release();
            pool.setFreeConnection(connection);
        }

        return false;
    }

    @Override
    public Order update(Order order) {
        return null;
    }
}
