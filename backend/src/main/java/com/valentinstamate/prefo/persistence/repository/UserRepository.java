package com.valentinstamate.prefo.persistence.repository;

import com.valentinstamate.prefo.persistence.ClassModel;
import com.valentinstamate.prefo.persistence.UserClassModel;
import com.valentinstamate.prefo.persistence.UserModel;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
@Transactional
public class UserRepository {

    @PersistenceContext(name = "default")
    private EntityManager em;

    public UserModel findUserById(Long id) {
        return em.find(UserModel.class, id);
    }

    public List<UserModel> allUsers() {
        return em.createQuery("SELECT u FROM UserModel u", UserModel.class).getResultList();
    }

    public List<UserModel> allUsersExcept(Long id) {
        return em.createQuery("SELECT u FROM UserModel u WHERE u.id <> :id", UserModel.class)
                .setParameter("id", id)
                .getResultList();
    }

    public boolean deleteUser(Long id) {
        UserModel userModel = em.find(UserModel.class, id);

        if (userModel == null) {
            return false;
        }

        em.remove(userModel);
        return true;
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
