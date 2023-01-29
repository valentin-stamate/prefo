package com.valentinstamate.prefobackend.persistence.repository;

import com.valentinstamate.prefobackend.persistence.models.ClassModel;
import com.valentinstamate.prefobackend.persistence.models.PreferenceModel;
import com.valentinstamate.prefobackend.persistence.models.UserModel;
import com.valentinstamate.prefobackend.persistence.repository.generic.DataRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
@Transactional
public class PreferenceRepository extends DataRepository<PreferenceModel, Long> {

    @PersistenceContext(name = "default")
    private EntityManager em;

    public PreferenceRepository() {
        super(PreferenceModel.class);
    }

    public PreferenceModel findByUserAndClass(UserModel userModel, ClassModel classModel) {
        try {
            return em.createQuery("SELECT pr FROM PreferenceModel pr WHERE pr.user = :user AND pr._class = :class", PreferenceModel.class)
                    .setParameter("user", userModel)
                    .setParameter("class", classModel)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
}
