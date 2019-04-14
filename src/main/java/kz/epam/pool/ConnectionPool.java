package kz.epam.pool;

import kz.epam.config.ConfigManager;
import kz.epam.constant.Constant;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConnectionPool {

    private static final String DRIVER_ERROR = "Driver loading error.";
    private static final String CONNECTION_CREATION_ERROR = "Connection creation error.";
    private static final String CONNECTION_RELEASE_ERROR = "Connection release error.";

    private Logger log = Logger.getRootLogger();
    private static ConnectionPool instance;
    private BlockingQueue<Connection> freeConnections;

    private static String driverName = ConfigManager.getInstance().getProperty(ConfigManager.DATABASE_DRIVER_NAME);
    private static String url = ConfigManager.getInstance().getProperty(ConfigManager.DATABASE_URL);
    private static String user = ConfigManager.getInstance().getProperty(ConfigManager.DATABASE_USER);
    private static String password = ConfigManager.getInstance().getProperty(ConfigManager.DATABASE_PASSWORD);
    private static int maxConn = Integer.parseInt(ConfigManager.getInstance().getProperty(ConfigManager.MAX_CONN));

    private ConnectionPool(String driverName, String url, String user, String password, int maximumConnectionNumber) {
        this.driverName = driverName;
        this.url = url;
        this.user = user;
        this.password = password;
        this.maxConn = maximumConnectionNumber;
        loadDrivers();
        freeConnections = new ArrayBlockingQueue<>(maximumConnectionNumber);
        for (int i = 0; i < maximumConnectionNumber; i++) {
            freeConnections.offer(newConnection());
        }
    }

    public static ConnectionPool getInstance() {
        if (instance == null) {
            instance = new ConnectionPool(driverName, url, user, password, maxConn);
        }
        return instance;
    }

    private void loadDrivers() {
        try {
            Driver driver = (Driver) Class.forName(driverName).newInstance();
            DriverManager.registerDriver(driver);
        } catch (Exception e) {
            log.error(String.format(Constant.STRING_FORMAT, DRIVER_ERROR, e.toString()));
        }
    }

    public Connection getConnection() {
        Connection connection = null;

        if (!freeConnections.isEmpty()) {
            try {
                connection = freeConnections.take();
            } catch (InterruptedException e) {
                log.error(e.toString());
            }
        }
        return connection;
    }

    private Connection newConnection() {
        Connection connection;
        try {
            if (user == null) {
                connection = DriverManager.getConnection(url);
            } else {
                connection = DriverManager.getConnection(url, user, password);
            }
        } catch (SQLException ex) {
            log.error(String.format(Constant.STRING_FORMAT, CONNECTION_CREATION_ERROR, ex.toString()));
            return null;
        }
        return connection;
    }

    public void releaseConnection(Connection connection) {
        if (connection != null) {
            try {
                freeConnections.put(connection);
            } catch (InterruptedException e) {
                log.error(String.format(Constant.STRING_FORMAT, CONNECTION_RELEASE_ERROR, e.toString()));
            }
        }
    }

}
