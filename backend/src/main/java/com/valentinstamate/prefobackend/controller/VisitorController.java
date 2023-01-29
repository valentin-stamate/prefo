package com.valentinstamate.prefobackend.controller;

import com.valentinstamate.prefobackend.controller.requests.LoginBody;
import com.valentinstamate.prefobackend.service.UserService;
import com.valentinstamate.prefobackend.service.exception.ServiceException;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/visitors")
public class VisitorController {

    @Inject
    private UserService userService;

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response login(LoginBody loginBody) {
        try {
            var result = userService.checkCredentialsForLogin(loginBody.username, loginBody.password);

            return Response.ok().entity(result).build();
        } catch (ServiceException e) {
            return Response.status(e.getStatus()).entity(e.getMessage()).build();
        }
    }
}