package com.valentinstamate.prefobackend.controller;

import com.valentinstamate.prefobackend.filters.binding.AdminAuthenticated;
import com.valentinstamate.prefobackend.service.UserService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/admin")
@AdminAuthenticated
public class AdminController {

    @Inject
    private UserService userService;

    @GET
    public Response hello() {
        return Response.ok().build();
    }

}
