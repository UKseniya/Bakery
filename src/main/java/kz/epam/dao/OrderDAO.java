package kz.epam.dao;

import kz.epam.config.ConfigManager;
import kz.epam.entities.*;
import kz.epam.pool.ConnectionPool;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO extends AbstractDAO<Order> {
    private static final String SQL_FIND_ALL_ORDER_NUMBERS = "SELECT order_number FROM ordering";
    private static final String SQL_FIND_ALL_ORDERS_BY_USER = "SELECT order_id, order_number, date " +
            "FROM ordering o JOIN order_status os ON (o.status = os.status_id) " +
            "WHERE user_id = ? AND status = ?";
    private static final String SQL_FIND_STATUS_ID_BY_NAME = "SELECT * FROM order_status " +
            "WHERE status_name = ?";
    private static final String SQL_FIND_STATUS_NAME_BY_ID = "SELECT * FROM order_status " +
            "WHERE status_id = ?";
    private static final String SQL_SELECT_ALL_ORDERS_BY_DATE_AND_STATUS = "SELECT order_id, order_number, user_id, " +
            "date, comment FROM ordering " +
            "WHERE date = ? and status = ?";
    private static final String SQL_CREATE_NEW_ORDER = "INSERT INTO ordering (order_number, user_id, date, comment, status)" +
            "VALUES (?, ?, ?, ?, ?)";
    private static final String SQL_CHANGE_STATUS = "UPDATE ordering SET status = + ? " +
            "WHERE order_number = ?";
    public static final String SQL_SELECT_LAST_ORDER_ID = "SELECT @@IDENTITY AS IDENTITY";

    private static String driverName = ConfigManager.getInstance().getProperty(ConfigManager.DATABASE_DRIVER_NAME);
    private static String url = ConfigManager.getInstance().getProperty(ConfigManager.DATABASE_URL);
    private static String user_name = ConfigManager.getInstance().getProperty(ConfigManager.DATABASE_USER);
    private static String password = ConfigManager.getInstance().getProperty(ConfigManager.DATABASE_PASSWORD);
    private static int maxConn = Integer.parseInt(ConfigManager.getInstance().getProperty(ConfigManager.MAX_CONN));
    private Logger log = Logger.getRootLogger();

    @Override
    public List findAll() {
        return null;
    }

    public List<Integer> findAllOrderNumbers() {
        List<Integer> orderNumbers = null;
        ConnectionPool pool = ConnectionPool.getInstance(driverName, url, user_name, password, maxConn);
        Connection connection = pool.getConnection();

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SQL_FIND_ALL_ORDER_NUMBERS);) {
            orderNumbers = new ArrayList<>();

            try {
                while (resultSet.next()) {
                    orderNumbers.add(Integer.parseInt(resultSet.getString("order_number")));
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }

            pool.freeConnection(connection);

        } catch (SQLException e) {
            e.printStackTrace();
            log.error("SQL error " + e.toString());
        }
        return orderNumbers;
    }

    public int findStatusID(String statusName) {
        int statusID = 0;
        ConnectionPool pool = ConnectionPool.getInstance(driverName, url, user_name, password, maxConn);
        Connection connection = pool.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_STATUS_ID_BY_NAME)) {
            preparedStatement.setString(1, statusName);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    statusID = resultSet.getInt("status_id");
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
        return statusID;
    }

    public String findStatusNameByID(int statusID) {
        String statusName = null;
        ConnectionPool pool = ConnectionPool.getInstance(driverName, url, user_name, password, maxConn);
        Connection connection = pool.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_STATUS_NAME_BY_ID)) {
            preparedStatement.setInt(1, statusID);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    statusName = resultSet.getString("status_name");
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
        return statusName;
    }

    public List<Order> findAllOrdersByUser(User user) {
        List<Order> orders = null;

        ConnectionPool pool = ConnectionPool.getInstance(driverName, url, user_name, password, maxConn);
        Connection connection = pool.getConnection();


        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_ALL_ORDERS_BY_USER)) {
            preparedStatement.setInt(1, user.getId());
            preparedStatement.setInt(2, findStatusID("closed"));

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                orders = new ArrayList<>();

                while (resultSet.next()) {

                    int orderID = resultSet.getInt("order_id");
                    LineItemDAO lineItemDAO = new LineItemDAO();
                    List<LineItem> items = lineItemDAO.findALL(orderID);

                    Order order = new Order();
                    order.setOrderNumber(resultSet.getString("order_number"));
                    order.setItems(items);
                    order.setRequestedDate(resultSet.getDate("date"));
                    orders.add(order);
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
        return orders;
    }

    public List<Order> findAllPendingOrdersByUser(User user) {
        List<Order> orders = null;

        ConnectionPool pool = ConnectionPool.getInstance(driverName, url, user_name, password, maxConn);
        Connection connection = pool.getConnection();


        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_ALL_ORDERS_BY_USER)) {
            preparedStatement.setInt(1, user.getId());
            preparedStatement.setInt(2, findStatusID("in progress"));

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                orders = new ArrayList<>();

                while (resultSet.next()) {
                    int orderID = resultSet.getInt("order_id");
                    LineItemDAO lineItemDAO = new LineItemDAO();
//                    List<LineItem> items = lineItemDAO.findALL(orderID);

                    Order order = new Order();
                    order.setOrderNumber(resultSet.getString("order_number"));
                    order.setItems(lineItemDAO.findALL(orderID));
                    order.setRequestedDate(resultSet.getDate("date"));
                    orders.add(order);
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
        return orders;
    }

    public List<Order> findAllPendingOrdersByDate(Date date) {
        List<Order> orders = null;
        ConnectionPool pool = ConnectionPool.getInstance(driverName, url, user_name, password, maxConn);
        Connection connection = pool.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ALL_ORDERS_BY_DATE_AND_STATUS)) {
            preparedStatement.setDate(1, date);
            preparedStatement.setInt(2, findStatusID("in progress"));

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                orders = new ArrayList<>();

                ProductDAO productDAO = new ProductDAO();
                UserDAO userDAO = new UserDAO();

                while (resultSet.next()) {
                    int orderID = resultSet.getInt("order_id");
                    LineItemDAO lineItemDAO = new LineItemDAO();
                    List<LineItem> items = lineItemDAO.findALL(orderID);

                    Order order = new Order();
                    order.setOrderNumber(resultSet.getString("order_number"));
                    order.setUser(userDAO.findEntityById(resultSet.getInt("user_id")));
                    order.setItems(items);
                    order.setRequestedDate(resultSet.getDate("date"));
                    order.setComment(resultSet.getString("comment"));
                    orders.add(order);
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

        return orders;
    }

    public List<Order> findAllCompletedOrdersByDate(Date date) {
        List<Order> orders = null;
        ConnectionPool pool = ConnectionPool.getInstance(driverName, url, user_name, password, maxConn);
        Connection connection = pool.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ALL_ORDERS_BY_DATE_AND_STATUS)) {
            preparedStatement.setDate(1, date);
            preparedStatement.setInt(2, findStatusID("completed"));

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                orders = new ArrayList<>();

                ProductDAO productDAO = new ProductDAO();
                UserDAO userDAO = new UserDAO();

                while (resultSet.next()) {
                    int orderID = resultSet.getInt("order_id");
                    LineItemDAO lineItemDAO = new LineItemDAO();
                    List<LineItem> items = lineItemDAO.findALL(orderID);

                    Order order = new Order();
                    order.setOrderNumber(resultSet.getString("order_number"));
                    order.setUser(userDAO.findEntityById(resultSet.getInt("user_id")));
                    order.setItems(items);
                    order.setRequestedDate(resultSet.getDate("date"));
                    orders.add(order);
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

        return orders;
    }

    public List<Order> findAllClosedOrdersByDate(Date date) {
        List<Order> orders = null;
        ConnectionPool pool = ConnectionPool.getInstance(driverName, url, user_name, password, maxConn);
        Connection connection = pool.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_ALL_ORDERS_BY_DATE_AND_STATUS)) {
            preparedStatement.setDate(1, date);
            preparedStatement.setInt(2, findStatusID("closed"));

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                orders = new ArrayList<>();

                ProductDAO productDAO = new ProductDAO();
                UserDAO userDAO = new UserDAO();

                while (resultSet.next()) {
                    int orderID = resultSet.getInt("order_id");
                    LineItemDAO lineItemDAO = new LineItemDAO();
                    List<LineItem> items = lineItemDAO.findALL(orderID);

                    Order order = new Order();
                    order.setOrderNumber(resultSet.getString("order_number"));
                    order.setUser(userDAO.findEntityById(resultSet.getInt("user_id")));
                    order.setItems(items);
                    order.setRequestedDate(resultSet.getDate("date"));
                    orders.add(order);
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
        ConnectionPool pool = ConnectionPool.getInstance(driverName, url, user_name, password, maxConn);
        Connection connection = pool.getConnection();
//        UserDAO userDAO = new UserDAO();
//        int userID = userDAO.findUserId(order.getUser());
//        ProductDAO productDAO = new ProductDAO();
//        int productID = productDAO.findProductbyCode(order.getItem().getProduct());

        java.util.Date utilDate = order.getRequestedDate();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_CREATE_NEW_ORDER)) {
            preparedStatement.setString(1, order.getOrderNumber());
            preparedStatement.setInt(2, order.getUser().getId());
            preparedStatement.setDate(3, sqlDate);
            preparedStatement.setString(4,order.getComment());
            preparedStatement.setInt(5, findStatusID(order.getStatus()));
            preparedStatement.executeUpdate();

            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = preparedStatement.executeQuery(SQL_SELECT_LAST_ORDER_ID)) {
                resultSet.next();
                int orderID = resultSet.getInt("IDENTITY");

                List<LineItem> items = order.getItems();
                LineItemDAO lineItemDAO = new LineItemDAO();
                for (LineItem item : items) {
                    lineItemDAO.create(orderID, item);
                }
            }

            pool.freeConnection(connection);

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("SQL error " + e.toString());
        }

        return false;
    }

    public  boolean updatePendingOrder(String orderNumber) {
        ConnectionPool pool = ConnectionPool.getInstance(driverName, url, user_name, password, maxConn);
        Connection connection = pool.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_CHANGE_STATUS)) {
            preparedStatement.setInt(1, findStatusID("completed"));
            preparedStatement.setString(2, orderNumber);
            preparedStatement.executeUpdate();

            pool.freeConnection(connection);
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            log.error("SQL error " + e.toString());
        }
        return false;
    }

    public  boolean updateCompletedOrder(String orderNumber) {
        ConnectionPool pool = ConnectionPool.getInstance(driverName, url, user_name, password, maxConn);
        Connection connection = pool.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_CHANGE_STATUS)) {
            preparedStatement.setInt(1, findStatusID("closed"));
            preparedStatement.setString(2, orderNumber);
            preparedStatement.executeUpdate();

            pool.freeConnection(connection);
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            log.error("SQL error " + e.toString());
        }
        return false;
    }
    @Override
    public Order update(Order order) {
        return null;
    }
}
