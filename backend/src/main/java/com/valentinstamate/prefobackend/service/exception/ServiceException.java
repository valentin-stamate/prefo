package com.valentinstamate.prefobackend.service.exception;

import jakarta.ws.rs.core.Response;
public class ServiceException extends Exception {

    private final Response.Status statusCode;

    public ServiceException(String message, Response.Status statusCode) {
        super(message.toString());

        this.statusCode = statusCode;
    }

    public Response.Status getStatus() {
        return statusCode;
    }
}
