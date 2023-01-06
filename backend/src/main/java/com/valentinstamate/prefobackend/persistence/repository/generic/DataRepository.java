package com.valentinstamate.prefobackend.persistence.repository.generic;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

public abstract class DataRepository<T, ID extends Serializable> implements Serializable {

    protected Class<T> entityClass;

    @PersistenceContext(name = "default")
    private EntityManager em;

    protected DataRepository(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @PostConstruct
    protected void init() { }

    public T newInstance() {
        try {
            return entityClass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            return null;
        }
    }

    public void persist(T entity) {
        em.persist(entity);
    }

    public void update(T entity) {
        em.merge(entity);
    }

    public void remove(T entity) {
        if (!em.contains(entity)) {
            entity = em.merge(entity);
        }

        em.remove(entity);
    }

    public void removeAll() {
        String qlString = "DELETE FROM " + entityClass.getSimpleName();
        em.createQuery(qlString, entityClass).executeUpdate();
    }

    public T refresh(T entity) {
        if (!em.contains(entity)) {
            entity = em.merge(entity);
        }

        em.refresh(entity);
        return entity;
    }

    public T findById(ID id) {
        if (id == null) {
            return null;
        }

        return em.find(entityClass, id);
    }

    public List<T> findAll() {
        String qlString = "SELECT e FROM " + entityClass.getSimpleName() + " e";
        return em.createQuery(qlString, entityClass).getResultList();
    }

    public void clearCache() {
        em.getEntityManagerFactory().getCache().evictAll();
    }

}
