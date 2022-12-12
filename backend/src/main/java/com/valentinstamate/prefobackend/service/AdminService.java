package com.valentinstamate.prefobackend.service;

import com.valentinstamate.prefobackend.persistence.repository.UserRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class AdminService {

    @Inject
    private UserRepository userRepository;

}
