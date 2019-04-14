package kz.epam.dao;

import kz.epam.constant.Constant;
import kz.epam.entity.LineItem;
import kz.epam.entity.Order;
import kz.epam.entity.User;
import kz.epam.pool.ConnectionPool;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO extends AbstractDAO<Order> {

    private static final String SQL_FIND_ALL_ORDER_NUMBERS = "SELECT order_number FROM ordering";
    private static final String SQL_FIND_ALL_ORDERS_BY_USER = "SELECT order_id, order_number, date " +
            "FROM ordering " +
            "WHERE user_id = ? AND status = ?";
    private static final String SQL_FIND_STATUS_ID_BY_NAME = "SELECT status_id FROM order_status " +
            "WHERE status_name = ?";
    private static final String SQL_FIND_STATUS_NAME_BY_ID = "SELECT status_name FROM order_status os " +
            "JOIN locale l ON (os.locale_id = l.locale_id) " +
            "WHERE status_id = ? and locale_name = ?";
    private static final String SQL_FIND_ALL_ORDERS_BY_DATE_AND_STATUS = "SELECT order_id, order_number, user_id, " +
            "date, comment FROM ordering " +
            "WHERE date = ? and status = ?";
    private static final String SQL_FIND_ALL_ORDERS = "SELECT order_id, order_number, user_id, " +
            "date, comment, status FROM ordering ORDER BY date DESC";
    private static final String SQL_FIND_ORDER_BY_ORDER_NUMBER = "SELECT * FROM ordering WHERE order_number = ?";
    private static final String SQL_CREATE_NEW_ORDER = "INSERT INTO ordering (order_number, user_id, date, comment, status)" +
            "VALUES (?, ?, ?, ?, ?)";
    private static final String SQL_CHANGE_STATUS = "UPDATE ordering SET status = + ? " +
            "WHERE order_number = ?";
    private static final String SQL_SELECT_LAST_ORDER_ID = "SELECT @@IDENTITY AS IDENTITY";
    private static final String SQL_CANCEL_ORDER = "DELETE FROM ordering WHERE order_id = ?";

    private static final Logger LOG = Logger.getRootLogger();
    private static final String ORDER_ID = "order_id";
    private static final String ORDER_NUMBER = "order_number";
    private static final String USER_ID = "user_id";
    private static final String COMMENT = "comment";
    private static final String STATUS_NAME = "status_name";
    private static final String STATUS_ID = "status_id";
    private static final String STATUS_CLOSED = "closed";
    private static final String STATUS_COMPLETED = "completed";
    private static final String IDENTITY = "IDENTITY";

    public List findAll(String locale) {
        List<Order> orders = null;
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SQL_FIND_ALL_ORDERS)) {
            orders = new ArrayList<>();

            UserDAO userDAO = new UserDAO();

            while (resultSet.next()) {
                int orderID = resultSet.getInt(ORDER_ID);
                LineItemDAO lineItemDAO = new LineItemDAO();

                Order order = new Order();
                order.setOrderNumber(resultSet.getString(ORDER_NUMBER));
                order.setUser(userDAO.findEntityById(resultSet.getInt(USER_ID)));
                order.setItems(lineItemDAO.findAllByOrderID(orderID, locale));
                order.setRequestedDate(resultSet.getDate(Constant.DATE));
                order.setComment(resultSet.getString(COMMENT));
                order.setStatus(findStatusNameByID(resultSet.getInt(Constant.STATUS), locale));
                orders.add(order);
            }
        } catch (SQLException e) {
            LOG.error(String.format(Constant.STRING_FORMAT, Constant.SQL_ERROR, e.toString()));
        } finally {
            pool.releaseConnection(connection);
        }
        return orders;
    }

    public List<Integer> findAllOrderNumbers() {
        List<Integer> orderNumbers = null;
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SQL_FIND_ALL_ORDER_NUMBERS);) {
            orderNumbers = new ArrayList<>();

            while (resultSet.next()) {
                orderNumbers.add(Integer.parseInt(resultSet.getString(ORDER_NUMBER)));
            }

        } catch (SQLException e) {
            LOG.error(String.format(Constant.STRING_FORMAT, Constant.SQL_ERROR, e.toString()));
        } finally {
            pool.releaseConnection(connection);
        }
        return orderNumbers;
    }

    public int findStatusID(String statusName) {
        int statusID = 0;
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_STATUS_ID_BY_NAME)) {
            preparedStatement.setString(1, statusName);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    statusID = resultSet.getInt(STATUS_ID);
                }
            }
        } catch (SQLException e) {
            LOG.error(String.format(Constant.STRING_FORMAT, Constant.SQL_ERROR, e.toString()));
        } finally {
            pool.releaseConnection(connection);
        }
        return statusID;
    }

    public String findStatusNameByID(int statusID, String locale) {
        String statusName = null;
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_STATUS_NAME_BY_ID)) {
            preparedStatement.setInt(1, statusID);
            preparedStatement.setString(2, locale);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    statusName = resultSet.getString(STATUS_NAME);
                }
            }
        } catch (SQLException e) {
            LOG.error(String.format(Constant.STRING_FORMAT, Constant.SQL_ERROR, e.toString()));
        } finally {
            pool.releaseConnection(connection);
        }
        return statusName;
    }

    public List<Order> findAllOrdersByUser(User user, String locale) {
        List<Order> orders = null;

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_ALL_ORDERS_BY_USER)) {
            preparedStatement.setInt(1, user.getId());
            preparedStatement.setInt(2, findStatusID(STATUS_CLOSED));

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                orders = new ArrayList<>();

                while (resultSet.next()) {
                    int orderID = resultSet.getInt(ORDER_ID);
                    LineItemDAO lineItemDAO = new LineItemDAO();
                    List<LineItem> items = lineItemDAO.findAllByOrderID(orderID, locale);

                    Order order = new Order();
                    order.setOrderNumber(resultSet.getString(ORDER_NUMBER));
                    order.setItems(items);
                    order.setRequestedDate(resultSet.getDate(Constant.DATE));
                    orders.add(order);
                }
            }
        } catch (SQLException e) {
            LOG.error(String.format(Constant.STRING_FORMAT, Constant.SQL_ERROR, e.toString()));
        } finally {
            pool.releaseConnection(connection);
        }
        return orders;
    }

    public List<Order> findAllPendingOrdersByUser(User user, String locale) {
        List<Order> orders = null;

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_ALL_ORDERS_BY_USER)) {
            preparedStatement.setInt(1, user.getId());
            preparedStatement.setInt(2, findStatusID(Constant.IN_PROGRESS_STATUS));

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                orders = new ArrayList<>();

                while (resultSet.next()) {
                    int orderID = resultSet.getInt(ORDER_ID);
                    LineItemDAO lineItemDAO = new LineItemDAO();

                    Order order = new Order();
                    order.setOrderNumber(resultSet.getString(ORDER_NUMBER));
                    order.setItems(lineItemDAO.findAllByOrderID(orderID, locale));
                    order.setRequestedDate(resultSet.getDate(Constant.DATE));
                    orders.add(order);
                }
            }
        } catch (SQLException e) {
            LOG.error(String.format(Constant.STRING_FORMAT, Constant.SQL_ERROR, e.toString()));
        } finally {
            pool.releaseConnection(connection);
        }
        return orders;
    }

    public List<Order> findAllPendingOrdersByDate(Date date, String locale) {
        List<Order> orders = null;
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_ALL_ORDERS_BY_DATE_AND_STATUS)) {
            preparedStatement.setDate(1, date);
            preparedStatement.setInt(2, findStatusID(Constant.IN_PROGRESS_STATUS));

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                orders = new ArrayList<>();

                UserDAO userDAO = new UserDAO();

                while (resultSet.next()) {
                    int orderID = resultSet.getInt(ORDER_ID);
                    LineItemDAO lineItemDAO = new LineItemDAO();
                    List<LineItem> items = lineItemDAO.findAllByOrderID(orderID, locale);

                    Order order = new Order();
                    order.setOrderNumber(resultSet.getString(ORDER_NUMBER));
                    order.setUser(userDAO.findEntityById(resultSet.getInt(USER_ID)));
                    order.setItems(items);
                    order.setRequestedDate(resultSet.getDate(Constant.DATE));
                    order.setComment(resultSet.getString(COMMENT));
                    orders.add(order);
                }
            }
        } catch (SQLException e) {
            LOG.error(String.format(Constant.STRING_FORMAT, Constant.SQL_ERROR, e.toString()));
        } finally {
            pool.releaseConnection(connection);
        }

        return orders;
    }

    public List<Order> findAllCompletedOrdersByDate(Date date, String locale) {
        List<Order> orders = null;
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_ALL_ORDERS_BY_DATE_AND_STATUS)) {
            preparedStatement.setDate(1, date);
            preparedStatement.setInt(2, findStatusID(STATUS_COMPLETED));

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                orders = new ArrayList<>();

                UserDAO userDAO = new UserDAO();

                while (resultSet.next()) {
                    int orderID = resultSet.getInt(ORDER_ID);
                    LineItemDAO lineItemDAO = new LineItemDAO();
                    List<LineItem> items = lineItemDAO.findAllByOrderID(orderID, locale);

                    Order order = new Order();
                    order.setOrderNumber(resultSet.getString(ORDER_NUMBER));
                    order.setUser(userDAO.findEntityById(resultSet.getInt(USER_ID)));
                    order.setItems(items);
                    order.setRequestedDate(resultSet.getDate(Constant.DATE));
                    orders.add(order);
                }
            }
        } catch (SQLException e) {
            LOG.error(String.format(Constant.STRING_FORMAT, Constant.SQL_ERROR, e.toString()));
        } finally {
            pool.releaseConnection(connection);
        }
        return orders;
    }

    @Override
    public List<Order> findAll() {
        throw new UnsupportedOperationException(Constant.NOT_SUPPORTED_EXCEPTION_MESSAGE);
    }

    public Order findEntityByOrderNumber(String number) {
        Order order = null;
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_ORDER_BY_ORDER_NUMBER)) {
            preparedStatement.setString(1, number);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                if (resultSet.next()) {
                    order = new Order();
                    order.setId(resultSet.getInt(ORDER_ID));
                    order.setOrderNumber(resultSet.getString(ORDER_NUMBER));
                    order.setRequestedDate(resultSet.getDate(Constant.DATE));
                }
            }
        } catch (SQLException e) {
            LOG.error(String.format(Constant.STRING_FORMAT, Constant.SQL_ERROR, e.toString()));
        } finally {
            pool.releaseConnection(connection);
        }
        return order;
    }

    @Override
    public Order findEntityById(int id) {
        throw new UnsupportedOperationException(Constant.NOT_SUPPORTED_EXCEPTION_MESSAGE);
    }

    @Override
    public boolean delete(int id) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        LineItemDAO lineItemDAO = new LineItemDAO();
        lineItemDAO.delete(id);

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_CANCEL_ORDER)) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            LOG.error(String.format(Constant.STRING_FORMAT, Constant.SQL_ERROR, e.toString()));
        } finally {
            pool.releaseConnection(connection);
        }
        return false;
    }

    @Override
    public boolean create(Order order) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        java.util.Date utilDate = order.getRequestedDate();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_CREATE_NEW_ORDER)) {
            preparedStatement.setString(1, order.getOrderNumber());
            preparedStatement.setInt(2, order.getUser().getId());
            preparedStatement.setDate(3, sqlDate);
            preparedStatement.setString(4, order.getComment());
            preparedStatement.setInt(5, findStatusID(order.getStatus()));
            preparedStatement.executeUpdate();

            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(SQL_SELECT_LAST_ORDER_ID)) {
                resultSet.next();
                int orderID = resultSet.getInt(IDENTITY);

                List<LineItem> items = order.getItems();
                LineItemDAO lineItemDAO = new LineItemDAO();
                for (LineItem item : items) {
                    lineItemDAO.create(orderID, item);
                }
            }
            return true;
        } catch (SQLException e) {
            LOG.error(String.format(Constant.STRING_FORMAT, Constant.SQL_ERROR, e.toString()));
        } finally {
            pool.releaseConnection(connection);
        }
        return false;
    }

    @Override
    public Order update(Order entity) {
        throw new UnsupportedOperationException(Constant.NOT_SUPPORTED_EXCEPTION_MESSAGE);
    }

    public boolean updatePendingOrder(String orderNumber) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_CHANGE_STATUS)) {
            preparedStatement.setInt(1, findStatusID(STATUS_COMPLETED));
            preparedStatement.setString(2, orderNumber);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            LOG.error(String.format(Constant.STRING_FORMAT, Constant.SQL_ERROR, e.toString()));
        } finally {
            pool.releaseConnection(connection);
        }
        return false;
    }

    public boolean updateCompletedOrder(String orderNumber) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_CHANGE_STATUS)) {
            preparedStatement.setInt(1, findStatusID(STATUS_CLOSED));
            preparedStatement.setString(2, orderNumber);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            LOG.error(String.format(Constant.STRING_FORMAT, Constant.SQL_ERROR, e.toString()));
        } finally {
            pool.releaseConnection(connection);
        }
        return false;
    }
}
