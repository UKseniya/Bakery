package kz.epam.dao;

import kz.epam.constant.Constant;
import kz.epam.entity.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import kz.epam.pool.ConnectionPool;

public class ProductDAO extends AbstractDAO<Product> {

    private static final String SQL_FIND_PRODUCT_NAME_BY_ID = "SELECT product_name FROM product_description pd " +
            "JOIN locale l ON (l.locale_id = pd.locale_id) " +
            "WHERE product_id = ? and locale_name = ?";
    private static final String SQL_FIND_PRODUCT_DESCRIPTION_BY_ID = "SELECT description FROM product_description pd " +
            "JOIN locale l ON (l.locale_id = pd.locale_id) " +
            "WHERE product_id = ? and locale_name = ?";
    private static final String SQL_FIND_ALL_PRODUCTS_BY_STATUS = "SELECT * FROM product " +
            "WHERE status = ?";
    private static final String SQL_FIND_STATUS_ID_BY_NAME = "SELECT * FROM product_status " +
            "WHERE status_name = ?";
    private static final String SQL_FIND_PRODUCT_ID_BY_PRODUCT_CODE = "SELECT product_id FROM product " +
            "WHERE code = ?";
    private static final String SQL_FIND_PRODUCT_BY_PRODUCT_CODE = "SELECT code, product_name, description, price " +
            "FROM product p JOIN product_description pd ON (p.product_id = pd.product_id) " +
            "JOIN locale l ON (pd.locale_id = l.locale_id) WHERE code = ? AND locale_name = ?";
    private static final String SQL_FIND_PRODUCT_BY_PRODUCT_ID = "SELECT * FROM product " +
            "WHERE product_id = ?";
    private static final String SQL_CHANGE_STATUS = "UPDATE product SET status = ? " +
            "WHERE code = ?";
    private static final String SQL_CHANGE_PRODUCT_PRICE = "UPDATE product SET price = ? WHERE code = ?";
    private static final String SQL_CHANGE_PRODUCT_DESCRIPTION = "UPDATE product_description SET " +
            "product_name = ?, description = ? WHERE product_id = ? and locale_id = ?";
    private static final String SQL_ADD_NEW_PRODUCT = "INSERT INTO product (code, price, status) VALUES (?, ?, ?)";
    private static final String SQL_ADD_PRODUCT_DESCRIPTION = "INSERT INTO product_description (product_id, locale_id, " +
            "product_name, description) VALUES (?, ?, ?, ?)";
    private static final String SQL_FIND_LOCALE_ID_BY_NAME = "SELECT locale_id FROM locale WHERE locale_name = ?";

    private static final Logger LOG = Logger.getRootLogger();
    private static final String PRODUCT_NAME = "product_name";
    private static final String DESCRIPTION = "description";
    private static final String LOCALE_ID = "locale_id";
    private static final String PRODUCT_ID = "product_id";
    private static final String CODE = "code";
    private static final String STATUS_AVAILABLE = "available";
    private static final String STATUS_UNAVAILABLE = "n/a";
    private static final String STATUS_ID = "status_id";
    private static final String PRODUCT_INSERTION_MESSAGE = "The following product has been inserted:";

    @Override
    public List findAll() {
        throw new UnsupportedOperationException(Constant.NOT_SUPPORTED_EXCEPTION_MESSAGE);
    }

    public String findProductNameById(int id, String locale) {
        String productName = null;
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_PRODUCT_NAME_BY_ID)) {
            statement.setInt(1, id);
            statement.setString(2, locale);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    productName = resultSet.getString(PRODUCT_NAME);
                }
            }
        } catch (SQLException e) {
            LOG.error(String.format(Constant.STRING_FORMAT, Constant.SQL_ERROR, e.toString()));
        } finally {
            pool.releaseConnection(connection);
        }
        return productName;
    }

    public String findProductDescriptionById(int id, String locale) {
        String description = null;
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_PRODUCT_DESCRIPTION_BY_ID)) {
            statement.setInt(1, id);
            statement.setString(2, locale);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    description = resultSet.getString(DESCRIPTION);
                }
            }
        } catch (SQLException e) {
            LOG.error(String.format(Constant.STRING_FORMAT, Constant.SQL_ERROR, e.toString()));
        } finally {
            pool.releaseConnection(connection);
        }
        return description;
    }

    public int findLocaleIDbyName(String locale) {
        int localeID = 0;
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_LOCALE_ID_BY_NAME)) {
            statement.setString(1, locale);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    localeID = resultSet.getInt(LOCALE_ID);
                }
            }
        } catch (SQLException e) {
            LOG.error(String.format(Constant.STRING_FORMAT, Constant.SQL_ERROR, e.toString()));
        } finally {
            pool.releaseConnection(connection);
        }
        return localeID;
    }

    public List<Product> findAllAvailableProducts(String locale) {
        List<Product> products = null;

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_ALL_PRODUCTS_BY_STATUS)) {
            preparedStatement.setInt(1, findStatusIDbyName(STATUS_AVAILABLE));

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                products = new ArrayList<>();

                while (resultSet.next()) {
                    Product product = new Product();
                    product.setId(resultSet.getInt(PRODUCT_ID));
                    int productID = resultSet.getInt(PRODUCT_ID);
                    product.setName(findProductNameById(productID, locale));
                    product.setDescription(findProductDescriptionById(productID, locale));
                    product.setCode(resultSet.getString(CODE));
                    product.setPrice(resultSet.getDouble(Constant.PRODUCT_PRICE));
                    products.add(product);
                }
            }
        } catch (SQLException e) {
            LOG.error(String.format(Constant.STRING_FORMAT, Constant.SQL_ERROR, e.toString()));
        } finally {
            pool.releaseConnection(connection);
        }
        return products;
    }

    public List<Product> findAllCancelledProducts(String locale) {
        List<Product> products = null;

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_ALL_PRODUCTS_BY_STATUS)) {
            preparedStatement.setInt(1, findStatusIDbyName(STATUS_UNAVAILABLE));

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                products = new ArrayList<>();

                while (resultSet.next()) {
                    Product product = new Product();
                    product.setId(resultSet.getInt(PRODUCT_ID));
                    int productID = resultSet.getInt(PRODUCT_ID);
                    product.setName(findProductNameById(productID, locale));
                    product.setDescription(findProductDescriptionById(productID, locale));
                    product.setCode(resultSet.getString(CODE));
                    product.setPrice(resultSet.getDouble(Constant.PRODUCT_PRICE));
                    products.add(product);
                }
            }
        } catch (SQLException e) {
            LOG.error(String.format(Constant.STRING_FORMAT, Constant.SQL_ERROR, e.toString()));
        } finally {
            pool.releaseConnection(connection);
        }
        return products;
    }

    public Product findProductbyCode(String code, String locale) {
        Product product = null;

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_PRODUCT_BY_PRODUCT_CODE)) {
            preparedStatement.setString(1, code);
            preparedStatement.setString(2, locale);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    product = new Product();
                    product.setName(resultSet.getString(PRODUCT_NAME));
                    product.setDescription(resultSet.getString(DESCRIPTION));
                    product.setCode(resultSet.getString(CODE));
                    product.setPrice(resultSet.getDouble(Constant.PRODUCT_PRICE));
                }
            }
        } catch (SQLException e) {
            LOG.error(String.format(Constant.STRING_FORMAT, Constant.SQL_ERROR, e.toString()));
        } finally {
            pool.releaseConnection(connection);
        }
        return product;
    }

    public int findStatusIDbyName(String status) {
        int statusID = 0;
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_STATUS_ID_BY_NAME)) {
            statement.setString(1, status);

            try (ResultSet resultSet = statement.executeQuery()) {
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

    public int findProductIdByCode(Product product) {
        int productID = 0;

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_PRODUCT_ID_BY_PRODUCT_CODE)) {
            preparedStatement.setString(1, product.getCode());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    productID = resultSet.getInt(PRODUCT_ID);
                }
            }
        } catch (SQLException e) {
            LOG.error(String.format(Constant.STRING_FORMAT, Constant.SQL_ERROR, e.toString()));
        } finally {
            pool.releaseConnection(connection);
        }
        return productID;
    }

    public Product findEntityByIdAndLocale(int id, String locale) {
        Product product = null;

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_PRODUCT_BY_PRODUCT_ID)) {
            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    product = new Product();
                    product.setCode(resultSet.getString(CODE));
                    product.setName(findProductNameById(id, locale));
                    product.setDescription(findProductDescriptionById(id, locale));

                }
            }
        } catch (SQLException e) {
            LOG.error(String.format(Constant.STRING_FORMAT, Constant.SQL_ERROR, e.toString()));
        } finally {
            pool.releaseConnection(connection);
        }
        return product;
    }

    @Override
    public Product findEntityById(int id) {
        throw new UnsupportedOperationException(Constant.NOT_SUPPORTED_EXCEPTION_MESSAGE);
    }

    public boolean updateProductStatus(Product product, String status) {

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_CHANGE_STATUS)) {
            preparedStatement.setInt(1, findStatusIDbyName(status));
            preparedStatement.setString(2, product.getCode());
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
    public boolean delete(int id) {
        throw new UnsupportedOperationException(Constant.NOT_SUPPORTED_EXCEPTION_MESSAGE);
    }

    public boolean addNewProductDescription(Product product, String locale) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_ADD_PRODUCT_DESCRIPTION)) {
            preparedStatement.setInt(1, findProductIdByCode(product));
            preparedStatement.setInt(2, findLocaleIDbyName(locale));
            preparedStatement.setString(3, product.getName());
            preparedStatement.setString(4, product.getDescription());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            LOG.error(String.format(Constant.STRING_FORMAT, Constant.SQL_ERROR, e.toString()));
        } finally {
            pool.releaseConnection(connection);
        }
        return false;
    }

    public boolean updatePrice(Product product) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_CHANGE_PRODUCT_PRICE)) {
            preparedStatement.setDouble(1, product.getPrice());
            preparedStatement.setString(2, product.getCode());
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            LOG.error(String.format(Constant.STRING_FORMAT, Constant.SQL_ERROR, e.toString()));
        } finally {
            pool.releaseConnection(connection);
        }
        return false;
    }

    public boolean update(Product product, String locale) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_CHANGE_PRODUCT_DESCRIPTION)) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setString(2, product.getDescription());
            preparedStatement.setInt(3, findProductIdByCode(product));
            preparedStatement.setInt(4, findLocaleIDbyName(locale));
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
    public boolean create(Product product) {

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_ADD_NEW_PRODUCT)) {
            preparedStatement.setString(1, product.getCode());
            preparedStatement.setDouble(2, product.getPrice());
            preparedStatement.setInt(3, findStatusIDbyName(STATUS_AVAILABLE));
            preparedStatement.executeUpdate();

            LOG.info(String.format(Constant.STRING_FORMAT, PRODUCT_INSERTION_MESSAGE, product.getId()));
            return true;
        } catch (SQLException e) {
            LOG.error(String.format(Constant.STRING_FORMAT, Constant.SQL_ERROR, e.toString()));
        } finally {
            pool.releaseConnection(connection);
        }
        return false;
    }

    @Override
    public Product update(Product entity) {
        throw new UnsupportedOperationException(Constant.NOT_SUPPORTED_EXCEPTION_MESSAGE);
    }
}
