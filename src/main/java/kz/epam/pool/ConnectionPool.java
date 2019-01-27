package kz.epam.pool;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionPool {

    private ConnectionPool() {

    }
    private static ConnectionPool instance = null;

    public static ConnectionPool getInstance(){
        if (instance == null) {
            instance = new ConnectionPool();
        }
        return instance;
    }

    public static Connection getConnection(){
        Context context;
        Connection connection = null;
        try {
            context = new InitialContext();
            DataSource dataSource = (DataSource)context.lookup("java:comp/env/jdbc/bakery");
            connection = dataSource.getConnection();
        } catch (NamingException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
