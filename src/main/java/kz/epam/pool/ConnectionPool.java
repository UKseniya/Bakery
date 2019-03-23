package kz.epam.pool;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.log4j.Logger;

public class ConnectionPool {

    private static final String DRIVER_ERROR = "Driver loading error ";
    private static final String WAITING_ERROR = "Waiting error ";
    private static final String CONNECTION_CREATION_ERROR = "Connection creation error ";
    private static final String CONNECTION_RELEASE_ERROR = "Connection release error ";
    private static final int INCREMENT = 1;

    private Logger log = Logger.getRootLogger();
    private static ConnectionPool instance;
    private String driverName;
    private ArrayList<Connection> freeConnections = new ArrayList<>();
    private String url;
    private String user;
    private String password;
    private int maxConn;

    public ConnectionPool(String driverName, String url, String user, String password, int maxConn) {
        this.driverName = driverName;
        this.url = url;
        this.user = user;
        this.password = password;
        this.maxConn = maxConn;
        loadDrivers();
        for (int i = 0; i < maxConn; i++) {
            freeConnections.add(newConnection());
        }
    }

    public static synchronized ConnectionPool getInstance(String driverName, String url, String user, String password, int maxConn) {
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
            log.error(DRIVER_ERROR + e.toString());
        }
    }

    public synchronized Connection getConnection() {
        Connection connection = null;

        if (!freeConnections.isEmpty()) {
            connection = freeConnections.get(freeConnections.size() - INCREMENT);
            freeConnections.remove(connection);
            try {
                if (connection.isClosed()) {
                    connection = getConnection();
                }
            } catch (SQLException e) {
                connection = getConnection();
            }
        } else {
            try {
                synchronized (freeConnections) {
                    while (freeConnections.isEmpty()) {
                        freeConnections.wait();
                    }
                }
            } catch (InterruptedException e) {
                log.error(WAITING_ERROR + e.toString());
                Thread.currentThread().interrupt();
            }
        }
        return connection;
    }

    private Connection newConnection() {
        Connection connection = null;
        try {
            if (user == null) {
                connection = DriverManager.getConnection(url);
            } else {
                connection = DriverManager.getConnection(url, user, password);
            }
        } catch (SQLException ex) {
            log.error(CONNECTION_CREATION_ERROR + ex.toString());
            return null;
        }
        return connection;
    }

    public synchronized void freeConnection(Connection connection) {
        if ((connection != null) && (freeConnections.size() <= maxConn)) {
                synchronized (freeConnections) {
                    freeConnections.add(connection);
                    freeConnections.notifyAll();
                }
        }
    }

    public synchronized void release() {
        Iterator allConnections = freeConnections.iterator();
        while (allConnections.hasNext()) {
            Connection connection = (Connection) allConnections.next();
            try {
                connection.close();
            } catch (SQLException e) {
                log.error(CONNECTION_RELEASE_ERROR + e.toString());
            }
        }
        freeConnections.clear();
    }
}
