package com.valentinstamate.prefobackend.controller;

import com.valentinstamate.prefobackend.filters.binding.AdminAuthenticated;
import com.valentinstamate.prefobackend.service.UserService;
import com.valentinstamate.prefobackend.service.exception.ServiceException;
import com.valentinstamate.prefobackend.service.jwt.UserJwtPayloadService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.media.multipart.FormDataParam;
import java.io.InputStream;

@Path("/admins")
@AdminAuthenticated
public class AdminController {

    @Inject
    private UserService userService;

    @GET
    @Path("/info")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAdminInfo(@Context HttpHeaders headers) {
        var jwtPayload = UserJwtPayloadService.getUserPayloadFromHeaders(headers);

        try {
            var result = userService.getUserInfo(jwtPayload.getUsername());

            return Response.ok(result).build();
        } catch (ServiceException e) {
            return Response.status(e.getStatus()).entity(e.getMessage()).build();
        }
    }

    @GET
    @Path("/students")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStudents() {
        try {
            var result = userService.getStudents();

            return Response.ok(result).build();
        } catch (ServiceException e) {
            return Response.status(e.getStatus()).entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("/students")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response importStudents(@FormDataParam("file") InputStream fileStream) {
        try {
            userService.importStudents(fileStream);

            return Response.ok().build();
        } catch (ServiceException e) {
            return Response.status(e.getStatus()).entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("/import-classes")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response importClasses(@FormDataParam("discipline") InputStream classesFileStream, @FormDataParam("packete") InputStream packagesFileStream) {
        try {
            userService.importPackages(packagesFileStream);
            userService.importClasses(classesFileStream);

            return Response.ok().build();
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
    @Path("/export-preferences")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response exportPreferences() {
        try {
            var result = userService.exportStudentsPreferences();

            return Response.ok(result).build();
        } catch (ServiceException e) {
            return Response.status(e.getStatus()).entity(e.getMessage()).build();
        }
    }
}
