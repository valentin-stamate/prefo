package com.valentinstamate.prefobackend.persistence.repository;

import com.valentinstamate.prefobackend.persistence.consts.UserType;
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

    public UserRepository() {
        super(UserModel.class);
    }

    public List<UserModel> allUsersExcept(Long id) {
        try {
            return em.createQuery("SELECT u FROM UserModel u WHERE u.id <> :id", UserModel.class)
                    .setParameter("id", id)
                    .getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    public UserModel findByUsername(String username) {
        try {
            return em.createQuery("SELECT U FROM UserModel u WHERE u.username = :username", UserModel.class)
                    .setParameter("username", username)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public void removeAllNonAdminUsers() {
        try {
            em.createQuery("DELETE FROM UserModel WHERE userType = :user")
                    .setParameter("user", UserType.USER)
                    .executeUpdate();
        } catch (Exception e) {
            throw e;
        }
    }

}
