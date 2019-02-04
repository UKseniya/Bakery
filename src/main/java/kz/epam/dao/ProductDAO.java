package kz.epam.dao;

import kz.epam.config.ConfigManager;
import kz.epam.entities.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import kz.epam.pool.ConnectionPool;

public class ProductDAO extends AbstractDAO<Product> {
    private Logger log = Logger.getRootLogger();
    private static final String SQL_FIND_ALL_PRODUCTS = "SELECT * FROM product";
    private static final String SQL_FIND_PRODUCT_BY_PRODUCT_CODE = "SELECT * FROM product " +
            "WHERE code = ?";
    private static final String SQL_FIND_PRODUCT_BY_PRODUCT_ID = "SELECT * FROM product " +
            "WHERE product_id = ?";

    private static String driverName = ConfigManager.getInstance().getProperty(ConfigManager.DATABASE_DRIVER_NAME);
    private static String url = ConfigManager.getInstance().getProperty(ConfigManager.DATABASE_URL);
    private static String user_name = ConfigManager.getInstance().getProperty(ConfigManager.DATABASE_USER);
    private static String password = ConfigManager.getInstance().getProperty(ConfigManager.DATABASE_PASSWORD);
    private static int maxConn = Integer.parseInt(ConfigManager.getInstance().getProperty(ConfigManager.MAX_CONN));

    @Override
    public List<Product> findAll() {
        List<Product> products = null;

        ConnectionPool pool = ConnectionPool.getInstance(driverName, url, user_name, password, maxConn);
        Connection connection = pool.getConnection();

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SQL_FIND_ALL_PRODUCTS);){
            products = new ArrayList<>();

            while (resultSet.next()) {
                Product product = new Product();
                product.setId(resultSet.getInt("product_id"));
                product.setCode(resultSet.getString("code"));
                product.setName(resultSet.getString("name"));
                product.setPrice(resultSet.getDouble("price"));
                products.add(product);
            }

            pool.freeConnection(connection);

        } catch (SQLException e) {
            e.printStackTrace();
            log.error("SQL error " + e.toString());
        }
        return products;
    }

    public Product findProductbyCode(String code) {
        Product product = null;

        ConnectionPool pool = ConnectionPool.getInstance(driverName, url, user_name, password, maxConn);
        Connection connection = pool.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_PRODUCT_BY_PRODUCT_CODE)) {
            preparedStatement.setString(1, code);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if(resultSet.next()) {
                    product = new Product();
                    product.setId(resultSet.getInt("product_id"));
                    product.setName(resultSet.getString("name"));
                    product.setCode(resultSet.getString("code"));
                    product.setPrice(resultSet.getDouble("price"));
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
        return product;
    }

    public int findProductbyCode(Product product) {
        int productID = 0;

        ConnectionPool pool = ConnectionPool.getInstance(driverName, url, user_name, password, maxConn);
        Connection connection = pool.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_PRODUCT_BY_PRODUCT_CODE)) {
            preparedStatement.setString(1, product.getCode());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if(resultSet.next()) {
                    productID = resultSet.getInt("product_id");
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
        return productID;
    }

    public String findProductNamebyID(int productID) {
        String productName = null;

        ConnectionPool pool = ConnectionPool.getInstance(driverName, url, user_name, password, maxConn);
        Connection connection = pool.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_PRODUCT_BY_PRODUCT_CODE)) {
            preparedStatement.setInt(1, productID);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if(resultSet.next()) {
                    productName = resultSet.getString("product_name");
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
        return productName;
    }

    @Override
    public Product findEntityById(int id) {
        Product product = null;

        ConnectionPool pool = ConnectionPool.getInstance(driverName, url, user_name, password, maxConn);
        Connection connection = pool.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_PRODUCT_BY_PRODUCT_CODE)) {
            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if(resultSet.next()) {
                    product = new Product();
                    product.setId(resultSet.getInt("product_id"));
                    product.setName(resultSet.getString("name"));
                    product.setCode(resultSet.getString("code"));
                    product.setPrice(resultSet.getDouble("price"));
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
        return product;
    }

    @Override
    public int findEntityByID(Product entity) {
        return 0;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public boolean delete(Product entity) {
        return false;
    }

    @Override
    public boolean create(Product entity) {
        return false;
    }

    @Override
    public Product update(Product entity) {
        return null;
    }
}
