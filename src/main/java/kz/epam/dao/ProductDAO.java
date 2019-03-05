package kz.epam.dao;

import kz.epam.config.ConfigManager;
import kz.epam.constant.Constant;
import kz.epam.entity.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import kz.epam.pool.ConnectionPool;

public class ProductDAO extends AbstractDAO<Product> {

    private Logger log = Logger.getRootLogger();
    private static final String SQL_FIND_PRODUCT_NAME_BY_ID = "SELECT product_name FROM product_description pd " +
            "JOIN locale l ON (l.locale_id = pd.locale_id) " +
            "WHERE product_id = ? and locale_name = ?";
    private static final String SQL_FIND_PRODUCT_DESCRIPTION_BY_ID = "SELECT description FROM product_description pd " +
            "JOIN locale l ON (l.locale_id = pd.locale_id) " +
            "WHERE product_id = ? and locale_name = ?";
    private static final String SQL_FIND_ALL_PRODUCTS = "SELECT product_id, code, product_name, description, price, status_name FROM product p " +
            "JOIN product_description pd ON (p.product_id = pd.product_id) JOIN product_status ps ON (p.status = ps.status_id) " +
            "JOIN locale l ON (pd.locale_id = l.locale_id)" +
            "WHERE locale_name = ?";
    private static final String SQL_FIND_ALL_PRODUCTS_BY_STATUS = "SELECT * FROM product " +
            "WHERE status = ?";
    private static final String SQL_FIND_STATUS_ID_BY_NAME = "SELECT * FROM product_status " +
            "WHERE status_name = ?";
    private static final String SQL_FIND_STATUS_NAME_BY_ID = "SELECT * FROM product_status " +
            "WHERE status_id = ?";
    private static final String SQL_FIND_PRODUCT_ID_BY_PRODUCT_CODE = "SELECT product_id FROM product " +
            "WHERE code = ?";
    private static final String SQL_FIND_PRODUCT_BY_PRODUCT_CODE = "SELECT code, product_name, description, price FROM product p " +
            "JOIN product_description pd ON (p.product_id = pd.product_id) " +
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
    private static final String SQL_FIND_LOCALE_NAME_BY_ID = "SELECT locale_name FROM locale WHERE locale_id = ?";

    private static final String PRODUCT_NAME = "product_name";
    private static final String DESCRIPTION = "description";
    private static final String LOCALE_ID = "locale_id";
    private static final String LOCALE_NAME = "locale_name";
    private static final String PRODUCT_ID = "product_id";
    private static final String CODE = "code";
    private static final String PRICE = "price";
    private static final String STATUS_AVAILABLE = "available";
    private static final String STATUS_UNAVAILABLE = "n/a";
    private static final String STATUS_ID = "status_id";
    private static final String STATUS_NAME = "status_name";
    private static final String PRODUCT_INSERTION_MESSAGE = "Product has been inserted ";
    private static final String MONTH = "month";
    private static final String YEAR = "year";

    private static String driverName = ConfigManager.getInstance().getProperty(ConfigManager.DATABASE_DRIVER_NAME);
    private static String url = ConfigManager.getInstance().getProperty(ConfigManager.DATABASE_URL);
    private static String user_name = ConfigManager.getInstance().getProperty(ConfigManager.DATABASE_USER);
    private static String password = ConfigManager.getInstance().getProperty(ConfigManager.DATABASE_PASSWORD);
    private static int maxConn = Integer.parseInt(ConfigManager.getInstance().getProperty(ConfigManager.MAX_CONN));

    @Override
    public List findAll() {
        return null;
    }

    public String findProductNameById(int id, String locale) {
        String product_name = null;
        ConnectionPool pool = ConnectionPool.getInstance(driverName, url, user_name, password, maxConn);
        Connection connection = pool.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_PRODUCT_NAME_BY_ID)) {
            statement.setInt(1, id);
            statement.setString(2, locale);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    product_name = resultSet.getString(PRODUCT_NAME);
                }
                pool.freeConnection(connection);
            } catch (SQLException e) {
                e.printStackTrace();
                log.error(Constant.SQL_ERROR + e.toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            log.error(Constant.SQL_ERROR + e.toString());
        }
        return product_name;
    }

    public String findProductDescriptionById(int id, String locale) {
        String description = null;
        ConnectionPool pool = ConnectionPool.getInstance(driverName, url, user_name, password, maxConn);
        Connection connection = pool.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_PRODUCT_DESCRIPTION_BY_ID)) {
            statement.setInt(1, id);
            statement.setString(2, locale);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    description = resultSet.getString(DESCRIPTION);
                }
                pool.freeConnection(connection);
            } catch (SQLException e) {
                e.printStackTrace();
                log.error(Constant.SQL_ERROR + e.toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            log.error(Constant.SQL_ERROR + e.toString());
        }
        return description;
    }

    public int findLocaleIDbyName(String locale) {
        int localeID = 0;
        ConnectionPool pool = ConnectionPool.getInstance(driverName, url, user_name, password, maxConn);
        Connection connection = pool.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_LOCALE_ID_BY_NAME)) {
            statement.setString(1, locale);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    localeID = resultSet.getInt(LOCALE_ID);
                }
                pool.freeConnection(connection);
            } catch (SQLException e) {
                e.printStackTrace();
                log.error(Constant.SQL_ERROR + e.toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            log.error(Constant.SQL_ERROR + e.toString());
        }
        return localeID;
    }

    public String findLocaleNameByID(int id) {
        String localeName = null;
        ConnectionPool pool = ConnectionPool.getInstance(driverName, url, user_name, password, maxConn);
        Connection connection = pool.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_LOCALE_NAME_BY_ID)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    localeName = resultSet.getString(LOCALE_NAME);
                }
                pool.freeConnection(connection);
            } catch (SQLException e) {
                e.printStackTrace();
                log.error(Constant.SQL_ERROR + e.toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            log.error(Constant.SQL_ERROR + e.toString());
        }
        return localeName;
    }

    public List<Product> findAll(String locale) {
        List<Product> products = null;

        ConnectionPool pool = ConnectionPool.getInstance(driverName, url, user_name, password, maxConn);
        Connection connection = pool.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_ALL_PRODUCTS)) {
            preparedStatement.setString(1, locale);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                products = new ArrayList<>();

                while (resultSet.next()) {
                    Product product = new Product();
                    product.setId(resultSet.getInt(PRODUCT_ID));
                    product.setName(resultSet.getString(PRODUCT_NAME));
                    product.setDescription(resultSet.getString(DESCRIPTION));
                    product.setCode(resultSet.getString(CODE));
                    product.setPrice(resultSet.getDouble(PRICE));
                    products.add(product);
                }
                pool.freeConnection(connection);
            } catch (SQLException e) {
                e.printStackTrace();
                log.error(Constant.SQL_ERROR + e.toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            log.error(Constant.SQL_ERROR + e.toString());
        }
        return products;
    }

    public List<Product> findAllAvailableProducts(String locale) {
        List<Product> products = null;

        ConnectionPool pool = ConnectionPool.getInstance(driverName, url, user_name, password, maxConn);
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
                    product.setPrice(resultSet.getDouble(PRICE));
                    products.add(product);
                }
                pool.freeConnection(connection);
            } catch (SQLException e) {
                e.printStackTrace();
                log.error(Constant.SQL_ERROR + e.toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            log.error(Constant.SQL_ERROR + e.toString());
        }
        return products;
    }

    public List<Product> findAllCancelledProducts(String locale) {
        List<Product> products = null;

        ConnectionPool pool = ConnectionPool.getInstance(driverName, url, user_name, password, maxConn);
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
                    product.setPrice(resultSet.getDouble(PRICE));
                    products.add(product);
                }
                pool.freeConnection(connection);
            } catch (SQLException e) {
                e.printStackTrace();
                log.error(Constant.SQL_ERROR + e.toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            log.error(Constant.SQL_ERROR + e.toString());
        }
        return products;
    }

    public Product findProductbyCode(String code, String locale) {
        Product product = null;

        ConnectionPool pool = ConnectionPool.getInstance(driverName, url, user_name, password, maxConn);
        Connection connection = pool.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_PRODUCT_BY_PRODUCT_CODE)) {
            preparedStatement.setString(1, code);
            preparedStatement.setString(2, locale);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if(resultSet.next()) {
                    product = new Product();
                    product.setName(resultSet.getString(PRODUCT_NAME));
                    product.setDescription(resultSet.getString(DESCRIPTION));
                    product.setCode(resultSet.getString(CODE));
                    product.setPrice(resultSet.getDouble(PRICE));
                }
                pool.freeConnection(connection);
            } catch (SQLException e) {
                e.printStackTrace();
                log.error(Constant.SQL_ERROR + e.toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            log.error(Constant.SQL_ERROR + e.toString());
        }
        return product;
    }

    public int findStatusIDbyName(String status) {
        int statusID = 0;
        ConnectionPool pool = ConnectionPool.getInstance(driverName, url, user_name, password, maxConn);
        Connection connection = pool.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_STATUS_ID_BY_NAME)) {
            statement.setString(1, status);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    statusID = resultSet.getInt(STATUS_ID);
                }
                pool.freeConnection(connection);
            } catch (SQLException e) {
                e.printStackTrace();
                log.error(Constant.SQL_ERROR + e.toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            log.error(Constant.SQL_ERROR + e.toString());
        }
        return statusID;
    }

    public String findStatusNameByID(int id) {
        String statusName = null;
        ConnectionPool pool = ConnectionPool.getInstance(driverName, url, user_name, password, maxConn);
        Connection connection = pool.getConnection();

        try (PreparedStatement statement = connection.prepareStatement(SQL_FIND_STATUS_NAME_BY_ID)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    statusName = resultSet.getString(STATUS_NAME);
                }
                pool.freeConnection(connection);
            } catch (SQLException e) {
                e.printStackTrace();
                log.error(Constant.SQL_ERROR + e.toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            log.error(Constant.SQL_ERROR + e.toString());
        }
        return statusName;
    }

    public int findProductIdByCode(Product product) {
        int productID = 0;

        ConnectionPool pool = ConnectionPool.getInstance(driverName, url, user_name, password, maxConn);
        Connection connection = pool.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_PRODUCT_ID_BY_PRODUCT_CODE)) {
            preparedStatement.setString(1, product.getCode());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if(resultSet.next()) {
                    productID = resultSet.getInt(PRODUCT_ID);
                }
                pool.freeConnection(connection);
            } catch (SQLException e) {
                e.printStackTrace();
                log.error(Constant.SQL_ERROR + e.toString());
            }

        } catch (SQLException e) {
            e.printStackTrace();
            log.error(Constant.SQL_ERROR + e.toString());
        }
        return productID;
    }

    public Product findEntityById(int id, String locale) {
        Product product = null;

        ConnectionPool pool = ConnectionPool.getInstance(driverName, url, user_name, password, maxConn);
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
                pool.freeConnection(connection);
            } catch (SQLException e) {
                e.printStackTrace();
                log.error(Constant.SQL_ERROR + e.toString());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            log.error(Constant.SQL_ERROR + e.toString());
        }
        return product;
    }

    @Override
    public Product findEntityById(int id) {
        throw new UnsupportedOperationException(Constant.NOT_SUPPORTED_EXCEPTION_MESSAGE);
    }

    public boolean updateProductStatus(Product product, String status) {

        ConnectionPool pool = ConnectionPool.getInstance(driverName, url, user_name, password, maxConn);
        Connection connection = pool.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_CHANGE_STATUS)) {
            preparedStatement.setInt(1, findStatusIDbyName(status));
            preparedStatement.setString(2, product.getCode());
            preparedStatement.executeUpdate();

                pool.freeConnection(connection);

                return true;
        } catch (SQLException e) {
            e.printStackTrace();
            log.error(Constant.SQL_ERROR + e.toString());
        }
        return false;
    }

    @Override
    public int findEntityByID(Product entity) {
        throw new UnsupportedOperationException(Constant.NOT_SUPPORTED_EXCEPTION_MESSAGE);
    }

    @Override
    public boolean delete(int id) {
        throw new UnsupportedOperationException(Constant.NOT_SUPPORTED_EXCEPTION_MESSAGE);
    }

    @Override
    public boolean delete(Product entity) {
        throw new UnsupportedOperationException(Constant.NOT_SUPPORTED_EXCEPTION_MESSAGE);
    }

    public boolean addNewProductDescription(Product product, String locale) {
        ConnectionPool pool = ConnectionPool.getInstance(driverName, url, user_name, password, maxConn);
        Connection connection = pool.getConnection();

            try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_ADD_PRODUCT_DESCRIPTION)) {
                preparedStatement.setInt(1, findProductIdByCode(product));
                preparedStatement.setInt(2, findLocaleIDbyName(locale));
                preparedStatement.setString(3, product.getName());
                preparedStatement.setString(4, product.getDescription());
                preparedStatement.executeUpdate();

                pool.freeConnection(connection);
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
                log.error(Constant.SQL_ERROR + e.toString());
            }

        return false;
    }

    public boolean updatePrice(Product product) {
        ConnectionPool pool = ConnectionPool.getInstance(driverName, url, user_name, password, maxConn);
        Connection connection = pool.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_CHANGE_PRODUCT_PRICE)) {
            preparedStatement.setDouble(1, product.getPrice());
            preparedStatement.setString(2, product.getCode());
            preparedStatement.executeUpdate();

            pool.freeConnection(connection);

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            log.error(Constant.SQL_ERROR + e.toString());
        }
        return false;
    }

    public boolean update(Product product, String locale) {
        ConnectionPool pool = ConnectionPool.getInstance(driverName, url, user_name, password, maxConn);
        Connection connection = pool.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_CHANGE_PRODUCT_DESCRIPTION)) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setString(2, product.getDescription());
            preparedStatement.setInt(3, findProductIdByCode(product));
            preparedStatement.setInt(4, findLocaleIDbyName(locale));
            preparedStatement.executeUpdate();

            pool.freeConnection(connection);

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            log.error(Constant.SQL_ERROR + e.toString());
        }
        return false;
    }

    @Override
    public boolean create(Product product) {

        ConnectionPool pool = ConnectionPool.getInstance(driverName, url, user_name, password, maxConn);
        Connection connection = pool.getConnection();

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL_ADD_NEW_PRODUCT)) {
            preparedStatement.setString(1, product.getCode());
            preparedStatement.setDouble(2, product.getPrice());
            preparedStatement.setInt(3, findStatusIDbyName(STATUS_AVAILABLE));
            preparedStatement.executeUpdate();

            log.info(PRODUCT_INSERTION_MESSAGE + product.getId());

            pool.freeConnection(connection);

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            log.error(Constant.SQL_ERROR + e.toString());
        }

        return false;
    }

    @Override
    public Product update(Product entity) {
        throw new UnsupportedOperationException(Constant.NOT_SUPPORTED_EXCEPTION_MESSAGE);
    }
}
