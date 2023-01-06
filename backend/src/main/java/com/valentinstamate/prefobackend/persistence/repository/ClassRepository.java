package com.valentinstamate.prefobackend.persistence.repository;

import com.valentinstamate.prefobackend.persistence.models.ClassModel;
import com.valentinstamate.prefobackend.persistence.repository.generic.DataRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@ApplicationScoped
@Transactional
public class ClassRepository extends DataRepository<ClassModel, Long> {

    @PersistenceContext(name = "default")
    private EntityManager em;

    public ClassRepository() {
        super(ClassModel.class);
    }

}
