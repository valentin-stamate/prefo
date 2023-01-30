package com.valentinstamate.prefobackend.controller;

import com.valentinstamate.prefobackend.controller.requests.PreferencesBody;
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

@Path("/students")
@UserAuthenticated
public class StudentController {

    @Inject
    private UserService userService;

    @GET
    @Path("/info")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStudentInfo(@Context HttpHeaders headers) {
        var jwtPayload = UserJwtPayloadService.getUserPayloadFromHeaders(headers);

        try {
            var result = userService.getUserInfo(jwtPayload.getUsername());

            return Response.ok(result).build();
        } catch (ServiceException e) {
            return Response.status(e.getStatus()).entity(e.getMessage()).build();
        }
    }

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
    public Response getClasses(@Context HttpHeaders headers) {
        var jwtPayload = UserJwtPayloadService.getUserPayloadFromHeaders(headers);

        try {
            var result = userService.getAllCLassesWithRestriction(jwtPayload.getUsername());

            return Response.ok(result).build();
        } catch (ServiceException e) {
            return Response.status(e.getStatus()).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/preference")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStudentClasses(@Context HttpHeaders headers) {
        var jwtPayload = UserJwtPayloadService.getUserPayloadFromHeaders(headers);

        try {
            var result = userService.getStudentPreferences(jwtPayload.getUsername());

            return Response.ok(result).build();
        } catch (ServiceException e) {
            return Response.status(e.getStatus()).entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("/preference")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateStudentPreferences(@Context HttpHeaders headers, PreferencesBody body) {
        var jwtPayload = UserJwtPayloadService.getUserPayloadFromHeaders(headers);

        try {
            userService.updateStudentPreferences(jwtPayload.getUsername(), body);

            return Response.ok().build();
        } catch (ServiceException e) {
            return Response.status(e.getStatus()).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/preference")
    public Response removeStudentClass(@Context HttpHeaders headers, @QueryParam("packageName") String packageName) {
        var jwtPayload = UserJwtPayloadService.getUserPayloadFromHeaders(headers);

        try {
            userService.removeStudentPreferences(jwtPayload.getUsername(), packageName);

            return Response.ok().build();
        } catch (ServiceException e) {
            return Response.status(e.getStatus()).entity(e.getMessage()).build();
        }
    }
}
