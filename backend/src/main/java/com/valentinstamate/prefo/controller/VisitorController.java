package com.valentinstamate.prefo.controller;

import com.valentinstamate.prefo.service.UserService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/visitor")
public class VisitorController {

    @Inject
    private UserService userService;

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response login() {
        return Response.ok().build();
    }

//    @POST
//    @Path("/login")
//    @Consumes(MediaType.APPLICATION_JSON)
//    @Produces(MediaType.TEXT_PLAIN)
//    public Response login(LoginUserBody requestBody) {
//
//        try {
//            Object result = userService.loginUser(
//                    requestBody.getUsername(),
//                    requestBody.getPassword()
//            );
//
//            return Response.ok(result).build();
//        } catch (ServiceException e) {
//            return Response.status(e.getStatus()).entity(e.getMessage()).build();
//        }
//    }

}