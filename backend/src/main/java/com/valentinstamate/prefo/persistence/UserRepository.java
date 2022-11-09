package com.valentinstamate.prefo.persistence;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jdk.jfr.Name;

@ApplicationScoped
@Transactional
@Name("userRepository")
public class UserRepository {

    @PersistenceContext(name = "default")
    private EntityManager em;

}
