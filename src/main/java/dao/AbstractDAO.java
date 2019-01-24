package dao;

import entities.Entity;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public abstract class AbstractDAO<T extends Entity> {


    public abstract List<T> findAll();

    public abstract T findEntityById(int id);

    public abstract int findEntityByID(T entity);

    public abstract boolean delete(int id);

    public abstract boolean delete(T entity);

    public abstract boolean create(T entity);

    public abstract T update(T entity); //

    public void close(Statement st) {
        try {
            if (st != null) {
                st.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    public void close(PreparedStatement ps) {
        try {
            if (ps != null) {
                ps.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    public void close(Connection cn) {
        try {
            if (cn != null) {
                cn.close();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
