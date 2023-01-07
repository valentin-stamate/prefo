package com.valentinstamate.prefobackend.persistence.repository;

import com.valentinstamate.prefobackend.persistence.models.ClassModel;
import com.valentinstamate.prefobackend.persistence.repository.generic.DataRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.util.List;

@ApplicationScoped
@Transactional
public class ClassRepository extends DataRepository<ClassModel, Long> {

    @PersistenceContext(name = "default")
    private EntityManager em;

    public ClassRepository() {
        super(ClassModel.class);
    }

    public List<ClassModel> findClassesByPackage(String packageName) {
        try {
            return em.createQuery("SELECT c FROM ClassModel c WHERE c.classPackage = :package", ClassModel.class)
                    .setParameter("package", packageName)
                    .getResultList();
        } catch (Exception e) {
            throw e;
        }
    }

}
