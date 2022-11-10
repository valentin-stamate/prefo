package com.valentinstamate.prefo.persistence.repository;

import com.valentinstamate.prefo.persistence.ClassModel;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@ApplicationScoped
@Transactional
public class ClassRepository {

    @PersistenceContext(name = "default")
    private EntityManager em;

    public ClassModel findById(Long id) {
        return em.find(ClassModel.class, id);
    }

}
