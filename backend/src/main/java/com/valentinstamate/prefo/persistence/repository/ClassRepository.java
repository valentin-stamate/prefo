package com.valentinstamate.prefo.persistence.repository;

import com.valentinstamate.prefo.persistence.models.ClassModel;
import com.valentinstamate.prefo.persistence.repository.generic.DataRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@ApplicationScoped
@Transactional
public class ClassRepository extends DataRepository<ClassModel, Long> {

    @PersistenceContext(name = "default")
    private EntityManager em;

}
