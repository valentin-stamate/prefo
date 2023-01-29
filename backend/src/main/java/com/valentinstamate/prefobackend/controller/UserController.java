package com.valentinstamate.prefobackend.controller;

import com.valentinstamate.prefobackend.filters.binding.UserAuthenticated;
import com.valentinstamate.prefobackend.service.UserService;
import com.valentinstamate.prefobackend.service.exception.ServiceException;
import com.valentinstamate.prefobackend.service.jwt.UserJwtPayloadService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/users")
@UserAuthenticated
public class UserController {

    @Inject
    private UserService userService;

    @GET
    @Path("/packages")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPackages() {
        try {
            var result = userService.getPackages();

            return Response.ok(result).build();
        } catch (ServiceException e) {
            return Response.status(e.getStatus()).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/classes")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getClasses() {
        try {
            var result = userService.getAllClasses();

            return Response.ok(result).build();
        } catch (ServiceException e) {
            return Response.status(e.getStatus()).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/user-preference")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserClasses(@Context HttpHeaders headers) {
        var jwtPayload = UserJwtPayloadService.getUserPayloadFromHeaders(headers);

        try {
            var result = userService.getUserPreferences(jwtPayload.getUsername());

            return Response.ok(result).build();
        } catch (ServiceException e) {
            return Response.status(e.getStatus()).entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("/user-preference")
    public Response associateUserClass(@Context HttpHeaders headers, @QueryParam("id") int classId, @QueryParam("priority") int priority) {
        var jwtPayload = UserJwtPayloadService.getUserPayloadFromHeaders(headers);

        try {
            userService.addUserPreferene(jwtPayload.getUsername(), classId, priority);

            return Response.ok().build();
        } catch (ServiceException e) {
            return Response.status(e.getStatus()).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/user-preference")
    public Response removeUserClass(@Context HttpHeaders headers, @QueryParam("id") int classId) {
        var jwtPayload = UserJwtPayloadService.getUserPayloadFromHeaders(headers);

        try {
            userService.removeUserPreference(jwtPayload.getUsername(), classId);

            return Response.ok().build();
        } catch (ServiceException e) {
            return Response.status(e.getStatus()).entity(e.getMessage()).build();
        }
    }
}
