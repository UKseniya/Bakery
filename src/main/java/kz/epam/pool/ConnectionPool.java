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
    private static final String ILLEGAL_MONITOR_EXCEPTION = "Illegal Monitor State Exception ";
    private static final String CONNECTION_RELEASE_ERROR = "Connection release error ";

    private Logger log = Logger.getRootLogger();
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
        for (int i = 0; i < maxConn; i++) {
            freeConnections.add(newConnection());
        }
    }

    static synchronized public ConnectionPool getInstance(String DRIVER_NAME, String URL, String user, String password, int maxConn) {
        if (instance == null) {
            instance = new ConnectionPool(DRIVER_NAME, URL, user, password, maxConn);
        }
        return instance;
    }

    private void loadDrivers() {
        try {
            Driver driver = (Driver) Class.forName(DRIVER_NAME).newInstance();
            DriverManager.registerDriver(driver);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(DRIVER_ERROR + e.toString());
        }
    }

    public synchronized Connection getConnection() {
        Connection connection = null;

        if (!freeConnections.isEmpty()) {
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
        }
        else {
            try {
                synchronized (freeConnections) {
                    while (freeConnections.isEmpty()) {
                        freeConnections.wait();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                log.error(WAITING_ERROR + e.toString());
            }
        }
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
            log.error(CONNECTION_CREATION_ERROR + ex.toString());
            return null;
        }
        return connection;
    }

    public synchronized void freeConnection(Connection connection) {
        if ((connection != null) && (freeConnections.size() <= maxConn)) {
            try {
                synchronized (freeConnections) {
                    freeConnections.add(connection);
                    freeConnections.notifyAll();
                }
            } catch (IllegalMonitorStateException ex) {
                ex.printStackTrace();
                log.error(ILLEGAL_MONITOR_EXCEPTION + ex.toString());
            }
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
                e.printStackTrace();
                log.error(CONNECTION_RELEASE_ERROR + e.toString());
            }
        }
        freeConnections.clear();
    }
}
