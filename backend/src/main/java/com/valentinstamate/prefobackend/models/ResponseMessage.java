package com.valentinstamate.prefobackend.models;

public interface ResponseMessage {
    String NOT_FOUND = "Not found.";
    String WRONG_PASSWORD = "Wrong password.";
    String ERROR_GENERATING_TOKEN = "Error generating auth token.";
    String NO_AUTHORIZATION = "Authorization header is not present.";
    String INVALID_AUTHORIZATION_TOKEN = "Invalid authorization token.";
    String NOT_AUTHORIZED = "Not authorized.";
    String CANNOT_READ_FILE = "Cannot read file stream.";
    String INVALID_EXCEL_HEADER = "Invalid excel header.";
    String ERROR_PARSING_EXCEL = "Error parsing excel.";
    String CLASS_ALREADY_ASSOCIATED = "The class is already associated with the user.";
    String SOMETHING_WENT_WRONG = "Something went wrong.";
    String CANNOT_WRITE_WORKBOOK = "Cannot write workbook";
    String PREFERENCES_LIST_NOT_EMPTY = "Preference list should not be empty";
    String CLASS_NOT_FOUND = "Class not found";
    String PACKAGE_NOT_FOUND = "Package not found";
    String INVALID_PRIORITY = "Invalid priority";
    String CLASSES_SAME_TYPE = "Classes must be in the same package";
}
