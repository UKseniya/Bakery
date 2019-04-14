package kz.epam.dao;

import kz.epam.entity.Entity;

import java.util.List;

abstract class AbstractDAO<T extends Entity> {

    public abstract List<T> findAll();

    public abstract T findEntityById(int id);

    public abstract boolean delete(int id);

    public abstract boolean create(T entity);

    public abstract T update(T entity);

}
