package kz.epam.dao;

import kz.epam.constant.Constant;
import kz.epam.entity.LineItem;
import kz.epam.entity.Product;
import kz.epam.pool.ConnectionPool;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LineItemDAO extends AbstractDAO<LineItem> {

    private static final String SQL_FIND_LINE_ITEMS_BY_ORDER_ID = "SELECT * FROM line_item WHERE order_id = ?";
    private static final String SQL_CREATE_NEW_LINE_ITEM = "INSERT INTO line_item (order_id, product_id, product_price, quantity) " +
            "VALUES (?, ?, ?, ?)";
    private static final String SQL_FIND_TOP_PRODUCTS_FOR_LAST_MONTH = "SELECT * FROM top_products " +
            "WHERE month = ? AND year = ?";
    private static final String SQL_CANCEL_LINE_ITEM = "DELETE FROM line_item WHERE order_id = ?";

    private static final Logger LOG = Logger.getRootLogger();
    private static final String ITEM_ID = "item_id";
    private static final String PRODUCT_ID = "product_id";
    private static final String PRODUCT_PRICE = "product_price";
    private static final String QUANTITY = "quantity";

    public List<LineItem> findAllByOrderID(int orderID, String locale) {
        List<LineItem> items = null;
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_LINE_ITEMS_BY_ORDER_ID)) {
            preparedStatement.setInt(1, orderID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                items = new ArrayList<>();
                while (resultSet.next()) {
                    LineItem lineItem = new LineItem();
                    lineItem.setId(resultSet.getInt(ITEM_ID));
                    int productID = resultSet.getInt(PRODUCT_ID);
                    ProductDAO productDAO = new ProductDAO();
                    Product product = productDAO.findEntityByIdAndLocale(productID, locale);
                    product.setPrice(resultSet.getDouble(PRODUCT_PRICE));
                    lineItem.setProduct(product);
                    lineItem.setQuantity(resultSet.getInt(QUANTITY));
                    items.add(lineItem);
                }
            }
        } catch (SQLException e) {
            LOG.error(String.format(Constant.STRING_FORMAT, Constant.SQL_ERROR, e.toString()));
        } finally {
            pool.releaseConnection(connection);
        }
        return items;
    }

    public boolean create(int orderID, LineItem item) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        ProductDAO productDAO = new ProductDAO();
        int productID = productDAO.findProductIdByCode(item.getProduct());
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_CREATE_NEW_LINE_ITEM)) {
            preparedStatement.setInt(1, orderID);
            preparedStatement.setInt(2, productID);
            preparedStatement.setDouble(3, item.getProduct().getPrice());
            preparedStatement.setInt(4, item.getQuantity());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            LOG.error(String.format(Constant.STRING_FORMAT, Constant.SQL_ERROR, e.toString()));
        } finally {
            pool.releaseConnection(connection);
        }
        return false;
    }

    public List<LineItem> findTopProducts(int month, int year, String locale) {
        List<LineItem> items = null;
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_TOP_PRODUCTS_FOR_LAST_MONTH)) {
            statement.setInt(1, month);
            statement.setInt(2, year);

            items = new ArrayList<>();

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int productID = resultSet.getInt(PRODUCT_ID);
                    LineItem item = new LineItem();
                    ProductDAO productDAO = new ProductDAO();
                    item.setProduct(productDAO.findEntityByIdAndLocale(productID, locale));
                    item.setQuantity(resultSet.getInt(QUANTITY));
                    items.add(item);
                }
            }
        } catch (SQLException e) {
            LOG.error(String.format(Constant.STRING_FORMAT, Constant.SQL_ERROR, e.toString()));
        } finally {
            pool.releaseConnection(connection);
        }
        return items;
    }

    @Override
    public List<LineItem> findAll() {
        throw new UnsupportedOperationException(Constant.NOT_SUPPORTED_EXCEPTION_MESSAGE);
    }

    @Override
    public LineItem findEntityById(int id) {
        throw new UnsupportedOperationException(Constant.NOT_SUPPORTED_EXCEPTION_MESSAGE);
    }

    @Override
    public boolean delete(int id) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_CANCEL_LINE_ITEM)) {
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
    public boolean create(LineItem entity) {
        throw new UnsupportedOperationException(Constant.NOT_SUPPORTED_EXCEPTION_MESSAGE);
    }

    @Override
    public LineItem update(LineItem entity) {
        throw new UnsupportedOperationException(Constant.NOT_SUPPORTED_EXCEPTION_MESSAGE);
    }
}
