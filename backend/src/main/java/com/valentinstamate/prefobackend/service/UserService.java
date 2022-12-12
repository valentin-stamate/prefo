package com.valentinstamate.prefobackend.service;

import com.valentinstamate.prefobackend.models.ResponseMessage;
import com.valentinstamate.prefobackend.persistence.repository.UserRepository;
import com.valentinstamate.prefobackend.service.exception.ServiceException;
import com.valentinstamate.prefobackend.service.jwt.JwtService;
import com.valentinstamate.prefobackend.service.jwt.UserJwtPayloadService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class UserService {

    @Inject
    private UserRepository userRepository;

    public String checkCredentialsForLogin(String username, String password) throws ServiceException {
        var existingUser = this.userRepository.findByUsername(username);

        if (existingUser == null) {
            throw new ServiceException(ResponseMessage.NOT_FOUND, Response.Status.NOT_FOUND);
        }

        if (!existingUser.getPassword().equals(password)) {
            throw new ServiceException(ResponseMessage.WRONG_PASSWORD, Response.Status.BAD_REQUEST);
        }

        var payload = UserJwtPayloadService.payloadFromUser(existingUser);
        var token = JwtService.sign(payload);

        if (token == null) {
            throw new ServiceException(ResponseMessage.ERROR_GENERATING_TOKEN, Response.Status.NOT_ACCEPTABLE);
        }

        return token;
    }

}
