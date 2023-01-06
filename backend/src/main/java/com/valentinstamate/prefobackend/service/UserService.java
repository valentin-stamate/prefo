package com.valentinstamate.prefobackend.service;

import com.valentinstamate.prefobackend.models.ResponseMessage;
import com.valentinstamate.prefobackend.persistence.models.ClassModel;
import com.valentinstamate.prefobackend.persistence.models.PackageModel;
import com.valentinstamate.prefobackend.persistence.models.UserModel;
import com.valentinstamate.prefobackend.persistence.repository.ClassRepository;
import com.valentinstamate.prefobackend.persistence.repository.PackageRepository;
import com.valentinstamate.prefobackend.persistence.repository.UserRepository;
import com.valentinstamate.prefobackend.service.excel.mapping.*;
import com.valentinstamate.prefobackend.service.excel.parser.ClassExcelParser;
import com.valentinstamate.prefobackend.service.excel.parser.PackageExcelParser;
import com.valentinstamate.prefobackend.service.excel.parser.StudentExcelParser;
import com.valentinstamate.prefobackend.service.exception.ServiceException;
import com.valentinstamate.prefobackend.service.jwt.JwtService;
import com.valentinstamate.prefobackend.service.jwt.UserJwtPayloadService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import java.io.InputStream;
import java.util.List;

@ApplicationScoped
public class UserService {

    @Inject private UserRepository userRepository;
    @Inject private ClassRepository classRepository;
    @Inject private PackageRepository packageRepository;
    @Inject private StudentExcelParser studentExcelParser;
    @Inject private ClassExcelParser classExcelService;
    @Inject private PackageExcelParser packageExcelParser;

    public String checkCredentialsForLogin(String username, String password) throws ServiceException {
        var existingUser = this.userRepository.findByUsername(username);

        if (existingUser == null) {
            throw new ServiceException(ResponseMessage.NOT_FOUND, Response.Status.NOT_FOUND);
        }

        if (!existingUser.getPassword().equals(password)) {
            throw new ServiceException(ResponseMessage.WRONG_PASSWORD, Response.Status.BAD_REQUEST);
        }

        var payload = UserJwtPayloadService.payloadFromUser(existingUser);
        var token = JwtService.sign(payload);

        if (token == null) {
            throw new ServiceException(ResponseMessage.ERROR_GENERATING_TOKEN, Response.Status.NOT_ACCEPTABLE);
        }

        return token;
    }

    public void importUsers(InputStream fileStream) throws ServiceException {
        byte[] buffer;

        try {
            buffer = fileStream.readAllBytes();
        } catch (Exception e) {
            throw new ServiceException(ResponseMessage.CANNOT_READ_FILE, Response.Status.BAD_REQUEST);
        }

        List<StudentExcelRowMapping> rows;

        try {
            rows = studentExcelParser.parse(buffer);
        } catch (Exception e) {
            System.out.println(e);
            throw new ServiceException(e.getMessage(), Response.Status.NOT_ACCEPTABLE);
        }

        userRepository.removeAllNonAdminUsers();

        for (var row : rows) {
            var newUser = new UserModel(row.email, row.name, row.email, row.identifier);
            userRepository.persist(newUser);
        }
    }

    public void importClasses(InputStream fileStream) throws ServiceException {
        byte[] buffer;

        try {
            buffer = fileStream.readAllBytes();
        } catch (Exception e) {
            throw new ServiceException(ResponseMessage.CANNOT_READ_FILE, Response.Status.BAD_REQUEST);
        }

        List<ClassExcelRowMapping> rows;

        try {
            rows = classExcelService.parse(buffer);
        } catch (Exception e) {
            System.out.println(e);
            throw new ServiceException(e.getMessage(), Response.Status.NOT_ACCEPTABLE);
        }

        classRepository.removeAll();

        for (var row : rows) {
            var newClass = new ClassModel(row.name, row.shortName, row.year, row.semester, row.owner, row.site, row.classPackage);
            classRepository.persist(newClass);
        }
    }

    public void importPackages(InputStream fileStream) throws ServiceException {
        byte[] buffer;

        try {
            buffer = fileStream.readAllBytes();
        } catch (Exception e) {
            throw new ServiceException(ResponseMessage.CANNOT_READ_FILE, Response.Status.BAD_REQUEST);
        }

        List<PackageExcelRowMapping> rows;

        try {
            rows = packageExcelParser.parse(buffer);
        } catch (Exception e) {
            System.out.println(e);
            throw new ServiceException(e.getMessage(), Response.Status.NOT_ACCEPTABLE);
        }

        packageRepository.removeAll();

        for (var row : rows) {
            var newPackage = new PackageModel(row.packageName, row.year, row.semester);
            packageRepository.persist(newPackage);
        }
    }

}
