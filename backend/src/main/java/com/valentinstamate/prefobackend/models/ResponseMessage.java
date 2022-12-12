package com.valentinstamate.prefobackend.models;

public interface ResponseMessage {
    String NOT_FOUND = "Not found.";
    String WRONG_PASSWORD = "Wrong password.";
    String ERROR_GENERATING_TOKEN = "Error generating auth token.";
    String NO_AUTHORIZATION = "Authorization header is not present.";
    String INVALID_AUTHORIZATION_TOKEN = "Invalid authorization token.";
    String NOT_AUTHORIZED = "Not authorized.";
}
