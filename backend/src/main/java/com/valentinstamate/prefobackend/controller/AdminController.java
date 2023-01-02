package com.valentinstamate.prefobackend.controller;

import com.valentinstamate.prefobackend.filters.binding.AdminAuthenticated;
import com.valentinstamate.prefobackend.models.ResponseMessage;
import com.valentinstamate.prefobackend.service.UserService;
import com.valentinstamate.prefobackend.service.exception.ServiceException;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.media.multipart.FormDataParam;

import java.io.InputStream;

@Path("/admins")
@AdminAuthenticated
public class AdminController {

    @Inject
    private UserService userService;

    @POST
    @Path("/import-users")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response hello(@FormDataParam("file") InputStream fileStream) {

        try {
            userService.importUsers(fileStream);

            return Response.ok().build();
        } catch (ServiceException e) {
            return Response
                    .status(e.getStatus())
                    .entity(e.getMessage())
                    .build();
        }


    }

}
