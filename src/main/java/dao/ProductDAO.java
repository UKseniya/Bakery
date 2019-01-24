package dao;

import entities.Product;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import pool.ConnectionPool;

public class ProductDAO extends AbstractDAO<Product> {
    private Logger log = Logger.getRootLogger();
    private static final String SQL_SELECT_ALL_PRODUCTS = "SELECT * FROM product";


    @Override
    public List<Product> findAll() {
        List<Product> products = null;
        ConnectionPool pool = ConnectionPool.getInstance();

        try (Connection connection = pool.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_PRODUCTS);){
            products = new ArrayList<>();

            while (resultSet.next()) {
                Product product = new Product();
                product.setId(resultSet.getInt("product_id"));
                product.setCode(resultSet.getString("code"));
                product.setName(resultSet.getString("name"));
                product.setPrice(resultSet.getDouble("price"));
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            log.error("SQL error " + e.toString());
        }
        return products;
    }

    @Override
    public Product findEntityById(int id) {
        return null;
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
