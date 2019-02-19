package kz.epam.dao;

import kz.epam.config.ConfigManager;
import kz.epam.constants.Constants;
import kz.epam.entities.LineItem;
import kz.epam.entities.Product;
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

    private static final String ITEM_ID = "item_id";
    private static final String PRODUCT_ID = "product_id";
    private static final String PRODUCT_PRICE = "product_price";
    private static final String QUANTITY = "quantity";

    private static String driverName = ConfigManager.getInstance().getProperty(ConfigManager.DATABASE_DRIVER_NAME);
    private static String url = ConfigManager.getInstance().getProperty(ConfigManager.DATABASE_URL);
    private static String user_name = ConfigManager.getInstance().getProperty(ConfigManager.DATABASE_USER);
    private static String password = ConfigManager.getInstance().getProperty(ConfigManager.DATABASE_PASSWORD);
    private static int maxConn = Integer.parseInt(ConfigManager.getInstance().getProperty(ConfigManager.MAX_CONN));
    private Logger log = Logger.getRootLogger();

    public List<LineItem> findALL (int orderID, String locale) {
        List<LineItem> items = null;
        ConnectionPool pool = ConnectionPool.getInstance(driverName, url, user_name, password, maxConn);
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
                    Product product = productDAO.findEntityById(productID, locale);
                    product.setPrice(resultSet.getDouble(PRODUCT_PRICE));
                    lineItem.setProduct(product);
                    lineItem.setQuantity(resultSet.getInt(QUANTITY));
                    items.add(lineItem);
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
            return items;
    }


    public boolean create (int orderID, LineItem item) {
        ConnectionPool pool = ConnectionPool.getInstance(driverName, url, user_name, password, maxConn);
        Connection connection = pool.getConnection();

        ProductDAO productDAO = new ProductDAO();
        int productID = productDAO.findProductIdByCode(item.getProduct());
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_CREATE_NEW_LINE_ITEM)) {
            preparedStatement.setInt(1, orderID);
            preparedStatement.setInt(2, productID);
            preparedStatement.setDouble(3, item.getProduct().getPrice());
            preparedStatement.setInt(4, item.getQuantity());;
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
    public List<LineItem> findAll() {
        throw new UnsupportedOperationException(Constants.NOT_SUPPORTED_EXCEPTION_MESSAGE);
    }

    @Override
    public LineItem findEntityById(int id) {
        throw new UnsupportedOperationException(Constants.NOT_SUPPORTED_EXCEPTION_MESSAGE);
    }

    @Override
    public int findEntityByID(LineItem entity) {
        throw new UnsupportedOperationException(Constants.NOT_SUPPORTED_EXCEPTION_MESSAGE);
    }

    @Override
    public boolean delete(int id) {
        throw new UnsupportedOperationException(Constants.NOT_SUPPORTED_EXCEPTION_MESSAGE);
    }

    @Override
    public boolean delete(LineItem entity) {
        throw new UnsupportedOperationException(Constants.NOT_SUPPORTED_EXCEPTION_MESSAGE);
    }

    @Override
    public boolean create(LineItem entity) {
        throw new UnsupportedOperationException(Constants.NOT_SUPPORTED_EXCEPTION_MESSAGE);
    }

    @Override
    public LineItem update(LineItem entity) {
        throw new UnsupportedOperationException(Constants.NOT_SUPPORTED_EXCEPTION_MESSAGE);
    }
}
