package com.valentinstamate.prefobackend.persistence.repository;

import com.valentinstamate.prefobackend.persistence.models.PackageModel;
import com.valentinstamate.prefobackend.persistence.repository.generic.DataRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@ApplicationScoped
@Transactional
public class PackageRepository extends DataRepository<PackageModel, Long> {
    @PersistenceContext(name = "default")
    private EntityManager em;

    public PackageRepository() {
        super(PackageModel.class);
    }

    public PackageModel findByPackageName(String packageName) {
        try {
            return em.createQuery("SELECT p FROM PackageModel p WHERE p.packageName = :packageName", PackageModel.class)
                    .setParameter("packageName", packageName)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
}
