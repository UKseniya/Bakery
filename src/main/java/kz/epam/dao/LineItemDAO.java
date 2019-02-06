package kz.epam.dao;

import kz.epam.config.ConfigManager;
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

    private static String driverName = ConfigManager.getInstance().getProperty(ConfigManager.DATABASE_DRIVER_NAME);
    private static String url = ConfigManager.getInstance().getProperty(ConfigManager.DATABASE_URL);
    private static String user_name = ConfigManager.getInstance().getProperty(ConfigManager.DATABASE_USER);
    private static String password = ConfigManager.getInstance().getProperty(ConfigManager.DATABASE_PASSWORD);
    private static int maxConn = Integer.parseInt(ConfigManager.getInstance().getProperty(ConfigManager.MAX_CONN));
    private Logger log = Logger.getRootLogger();

    public List<LineItem> findALL (int orderID) {
        List<LineItem> items = null;
        ConnectionPool pool = ConnectionPool.getInstance(driverName, url, user_name, password, maxConn);
        Connection connection = pool.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_LINE_ITEMS_BY_ORDER_ID)) {
            preparedStatement.setInt(1, orderID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                items = new ArrayList<>();
                while (resultSet.next()) {
                    LineItem lineItem = new LineItem();
                    lineItem.setId(resultSet.getInt("item_id"));
                    int productID = resultSet.getInt("product_id");
                    ProductDAO productDAO = new ProductDAO();
                    Product product = productDAO.findEntityById(productID);
                    lineItem.setProduct(product);
                    product.setPrice(resultSet.getDouble("product_price"));
                    lineItem.setQuantity(resultSet.getInt("quantity"));
                    items.add(lineItem);
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
            return items;
    }


    @Override
    public List<LineItem> findAll() {
        return null;
    }

    @Override
    public LineItem findEntityById(int id) {
        return null;
    }

    @Override
    public int findEntityByID(LineItem entity) {
        return 0;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public boolean delete(LineItem entity) {
        return false;
    }

    public boolean create (int orderID, LineItem item) {
        ConnectionPool pool = ConnectionPool.getInstance(driverName, url, user_name, password, maxConn);
        Connection connection = pool.getConnection();

//        ProductDAO productDAO = new ProductDAO();
//        int productID = productDAO.findProductbyCode(item.getProduct());
        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_CREATE_NEW_LINE_ITEM)) {
            preparedStatement.setInt(1, orderID);
            preparedStatement.setInt(2, item.getProduct().getId());
            preparedStatement.setDouble(3, item.getProduct().getPrice());
            preparedStatement.setInt(4, item.getQuantity());;
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
    public boolean create(LineItem entity) {
        return false;
    }

    @Override
    public LineItem update(LineItem entity) {
        return null;
    }
}
