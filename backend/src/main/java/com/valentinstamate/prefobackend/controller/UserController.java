package com.valentinstamate.prefobackend.controller;


import com.valentinstamate.prefobackend.filters.binding.UserAuthenticated;
import com.valentinstamate.prefobackend.service.UserService;
import com.valentinstamate.prefobackend.service.exception.ServiceException;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
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
            return Response
                    .status(e.getStatus())
                    .entity(e.getMessage())
                    .build();
        }
    }

    @GET
    @Path("/classes")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getClasses(@QueryParam("package") String packageName) {
        try {
            var result = userService.getClassesByPackage(packageName);

            return Response.ok(result).build();
        } catch (ServiceException e) {
            return Response
                    .status(e.getStatus())
                    .entity(e.getMessage())
                    .build();
        }
    }
}
