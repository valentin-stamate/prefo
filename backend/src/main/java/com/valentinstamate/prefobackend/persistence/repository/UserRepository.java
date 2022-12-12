package com.valentinstamate.prefobackend.persistence.repository;

import com.valentinstamate.prefobackend.persistence.models.ClassModel;
import com.valentinstamate.prefobackend.persistence.models.UserClassModel;
import com.valentinstamate.prefobackend.persistence.models.UserModel;
import com.valentinstamate.prefobackend.persistence.repository.generic.DataRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
@Transactional
public class UserRepository extends DataRepository<UserModel, Long> {

    @PersistenceContext(name = "default")
    private EntityManager em;

    public List<UserModel> allUsersExcept(Long id) {
        return em.createQuery("SELECT u FROM UserModel u WHERE u.id <> :id", UserModel.class)
                .setParameter("id", id)
                .getResultList();
    }

    public void addClass(UserModel userModel, ClassModel classModel, Long priority) {
        UserClassModel userClassModel = new UserClassModel(userModel.getId(), classModel.getId(), priority);
        em.persist(userClassModel);
    }

    public boolean removeClass(UserModel userModel, ClassModel classModel) {
        UserClassModel userClassModel = em.createQuery("SELECT ucm FROM UserClassModel ucm WHERE ucm.userId = :uid AND ucm.classId = :ucid", UserClassModel.class)
                .setParameter("uid", userModel.getId())
                .setParameter("ucid", classModel.getId())
                .getSingleResult();

        if (userClassModel == null) {
            return false;
        }

        em.remove(userClassModel);

        return true;
    }

}
