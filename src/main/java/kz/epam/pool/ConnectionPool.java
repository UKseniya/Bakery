package kz.epam.pool;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

public class ConnectionPool {

    private static ConnectionPool instance;
    private final String DRIVER_NAME;
    private ArrayList<Connection> freeConnections = new ArrayList<>();
    private String URL;
    private String user;
    private String password;
    private int maxConn;

    public ConnectionPool(String DRIVER_NAME, String URL, String user, String password, int maxConn) {
        this.DRIVER_NAME = DRIVER_NAME;
        this.URL = URL;
        this.user = user;
        this.password = password;
        this.maxConn = maxConn;
        loadDrivers();
    }

    private void loadDrivers() {
        try {
            Driver driver = (Driver) Class.forName(DRIVER_NAME).newInstance();
            DriverManager.registerDriver(driver);
        } catch (Exception e) {

        }
    }

    static synchronized public ConnectionPool getInstance(String DRIVER_NAME, String URL, String user, String password, int maxConn) {
        if (instance == null) {
            instance = new ConnectionPool(DRIVER_NAME, URL, user, password, maxConn);
        }
        return instance;
    }

    public synchronized Connection getConnection() {
        Connection connection = null;

        /*if (!freeConnections.isEmpty()) {
            connection = (Connection) freeConnections.get(freeConnections.size() - 1);
            freeConnections.remove(connection);
            try {
                if (connection.isClosed()) {
                    connection = getConnection();
                }
            } catch (SQLException e) {
                connection = getConnection();
            } catch (Exception e) {
                connection = getConnection();
            }
        } else {
        */
            connection = newConnection();
        //}
        return connection;
    }

    private Connection newConnection() {
        Connection connection = null;
        try {
            if (user == null) {
                connection = DriverManager.getConnection(URL);
            } else {
                connection = DriverManager.getConnection(URL, user, password);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
        return connection;
    }

    public synchronized void setFreeConnection(Connection connection) {
        if ((connection != null) && (freeConnections.size() <= maxConn)) {
            freeConnections.add(connection);
        }
    }

    public synchronized void release() {
        Iterator allConnections = freeConnections.iterator();
        while (allConnections.hasNext()) {
            Connection connection = (Connection) allConnections.next();
            try {
                connection.close();
            }
            catch (SQLException e) {

            }
        }
        freeConnections.clear();
    }

    //    private ConnectionPool() {
//
//    }
//    private static ConnectionPool instance = null;
//
//    public static ConnectionPool getInstance(){
//        if (instance == null) {
//            instance = new ConnectionPool();
//        }
//        return instance;
//    }
//
//    public static Connection getConnection(){
//        Context context;
//        Connection connection = null;
//        try {
//            context = new InitialContext();
//            DataSource dataSource = (DataSource)context.lookup("java:comp/env/jdbc/bakery");
//            connection = dataSource.getConnection();
//        } catch (NamingException e) {
//            e.printStackTrace();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return connection;
//    }
}
