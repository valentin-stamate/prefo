package com.valentinstamate.prefobackend.filters;

import com.valentinstamate.prefobackend.filters.binding.UserAuthenticated;
import com.valentinstamate.prefobackend.models.ResponseMessage;
import com.valentinstamate.prefobackend.models.UserJwtPayload;
import com.valentinstamate.prefobackend.persistence.consts.UserType;
import com.valentinstamate.prefobackend.persistence.repository.UserRepository;
import com.valentinstamate.prefobackend.service.jwt.JwtService;
import com.valentinstamate.prefobackend.service.jwt.UserJwtPayloadService;
import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.Map;

@UserAuthenticated
@Provider
@Priority(Priorities.AUTHORIZATION)
public class UserFilter implements ContainerRequestFilter, ContainerResponseFilter {

    @Inject
    private UserRepository userRepository;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        String authorization = requestContext.getHeaderString("Authorization");

        if (authorization == null || authorization.equals("")) {
            requestContext.abortWith(Response.status(Response.Status.NOT_FOUND).entity(ResponseMessage.NO_AUTHORIZATION).build());
            return;
        }

        Map<String, Object> payload = JwtService.decode(authorization);

        if (payload == null) {
            requestContext.abortWith(Response.status(Response.Status.NOT_FOUND).entity(ResponseMessage.INVALID_AUTHORIZATION_TOKEN).build());
            return;
        }

        UserJwtPayload userPayload = UserJwtPayloadService.getUser(payload);

        if (userPayload.getUserType().equals(UserType.USER)) {
            requestContext.abortWith(Response.status(Response.Status.NOT_FOUND).entity(ResponseMessage.NOT_AUTHORIZED).build());
            return;
        }

        var exisingUser = this.userRepository.findByUsername(userPayload.getUsername());

        if (exisingUser == null) {
            requestContext.abortWith(Response.status(Response.Status.NOT_FOUND).entity(ResponseMessage.NOT_FOUND).build());
        }
    }

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException { }
}
