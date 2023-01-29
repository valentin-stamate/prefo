package com.valentinstamate.prefobackend.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.valentinstamate.prefobackend.models.ResponseMessage;
import com.valentinstamate.prefobackend.persistence.models.ClassModel;
import com.valentinstamate.prefobackend.persistence.models.PackageModel;
import com.valentinstamate.prefobackend.persistence.models.PreferenceModel;
import com.valentinstamate.prefobackend.persistence.models.UserModel;
import com.valentinstamate.prefobackend.persistence.repository.ClassRepository;
import com.valentinstamate.prefobackend.persistence.repository.PackageRepository;
import com.valentinstamate.prefobackend.persistence.repository.PreferenceRepository;
import com.valentinstamate.prefobackend.persistence.repository.UserRepository;
import com.valentinstamate.prefobackend.service.excel.mapping.*;
import com.valentinstamate.prefobackend.service.excel.parser.ClassExcelParser;
import com.valentinstamate.prefobackend.service.excel.parser.PackageExcelParser;
import com.valentinstamate.prefobackend.service.excel.parser.StudentExcelParser;
import com.valentinstamate.prefobackend.service.excel.to_excel.PreferenceRowsToExcelService;
import com.valentinstamate.prefobackend.service.exception.ServiceException;
import com.valentinstamate.prefobackend.service.jwt.JwtService;
import com.valentinstamate.prefobackend.service.jwt.UserJwtPayloadService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

@ApplicationScoped
public class UserService {

    @Inject private UserRepository userRepository;
    @Inject private ClassRepository classRepository;
    @Inject private PackageRepository packageRepository;
    @Inject private PreferenceRepository preferenceRepository;
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
            var newUser = new UserModel(row.email, row.name, row.email, row.identifier, row.year, row.semester);
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

        preferenceRepository.removeAll();
        classRepository.removeAll();

        preferenceRepository.clearCache();
        classRepository.clearCache();
        userRepository.clearCache();

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

    public List<PackageModel> getPackages() throws ServiceException {
        return packageRepository.findAll();
    }

    public List<ClassModel> getClassesByPackage(String packageName) throws ServiceException {
        return classRepository.findClassesByPackage(packageName);
    }

    public List<Map<String, Object>> getUserPreferences(String username) throws ServiceException {
        var user = userRepository.findByUsername(username);

        var objectMapper = new ObjectMapper();

        return user.getPreferenceModels()
                .stream().map(item -> {
                    var classItem = item.get_class();
                    classItem.setPreferenceModels(new ArrayList<>());

                    Map<String, Object> map = objectMapper.convertValue(classItem, Map.class);
                    map.put("priority", item.getPriority());
                    map.put("completionDate", item.getCompletionDate());

                    return map;
                })
                .collect(Collectors.toList());
    }

    public void addUserPreferene(String username, int classId, int priority) throws ServiceException {
        var userModel = userRepository.findByUsername(username);
        var classModel = classRepository.findById((long) classId);

        if (classModel == null) {
            throw new ServiceException(ResponseMessage.NOT_FOUND, Response.Status.NOT_FOUND);
        }

        var existingPreference = preferenceRepository.findByUserAndClass(userModel, classModel);

        if (existingPreference != null) {
            throw new ServiceException(ResponseMessage.CLASS_ALREADY_ASSOCIATED, Response.Status.NOT_ACCEPTABLE);
        }

        var newPreference = new PreferenceModel(userModel, classModel, priority);

        userModel.addUserPreference(newPreference);
        classModel.addUserPreference(newPreference);

        preferenceRepository.persist(newPreference);
        userRepository.update(userModel);
        classRepository.update(classModel);
    }

    public void removeUserPreference(String username, int classId) throws ServiceException {
        var userModel = userRepository.findByUsername(username);
        var classModel = classRepository.findById((long) classId);

        if (classModel == null) {
            throw new ServiceException(ResponseMessage.NOT_FOUND, Response.Status.NOT_FOUND);
        }

        var existingPreference = preferenceRepository.findByUserAndClass(userModel, classModel);

        if (existingPreference == null) {
            throw new ServiceException(ResponseMessage.NOT_FOUND, Response.Status.NOT_FOUND);
        }

        userModel.removeUserPreference(existingPreference);
        classModel.removeUserPreference(existingPreference);

        preferenceRepository.remove(existingPreference);
        userRepository.update(userModel);
        classRepository.update(classModel);
    }

    public Map<String, List<ClassModel>> getAllClasses() throws ServiceException {
        var allClasses = classRepository.findAll();

        var map = new HashMap<String, List<ClassModel>>();

        for (var _class : allClasses) {
            var classPackage = _class.getClassPackage();

            if (!map.containsKey(classPackage)) {
                map.put(classPackage, new ArrayList<>());
            }

            map.get(classPackage).add(_class);
        }

        return map;
    }

    public InputStream exportStudentsPreferences() throws ServiceException {
        var objectMapper = new ObjectMapper();

        var packages = packageRepository.findAll();
        var packagesNumber = packages.size();

        var usersPreferenceRows = userRepository
                .findAllStudents()
                .stream().map(user -> {
                    var preferences = user.getPreferenceModels();

                    var year = user.getYear();
                    var semester = user.getSemester();

                    var row = new ArrayList<Object>();
                    row.add(user.getUsername());
                    row.add(user.getFullName());
                    row.add(year);
                    row.add(semester);
                    row.add(year * 2 - 1 + semester);

                    var sortedPreferencesByPackage = ServiceUtils.orderPreferences(preferences, packagesNumber);

                    for (int i = 1; i <= packagesNumber; i++) {
                        var packageQueue = sortedPreferencesByPackage.get(String.format("CO%d", i));

                        row.add(Objects.requireNonNullElse(packageQueue.poll(), ""));
                        row.add(Objects.requireNonNullElse(packageQueue.poll(), ""));
                        row.add(Objects.requireNonNullElse(packageQueue.poll(), ""));
                        row.add(Objects.requireNonNullElse(packageQueue.poll(), ""));
                    }

                    return (List<Object>) row;
                })
                .collect(Collectors.toList());

        var workbook = PreferenceRowsToExcelService.parse(usersPreferenceRows);

        var byteStream = new ByteArrayOutputStream();

        try {
            workbook.write(byteStream);
            var byteArray = byteStream.toByteArray();
            return new ByteArrayInputStream(byteArray);
        } catch (Exception e) {
            throw new ServiceException(ResponseMessage.CANNOT_WRITE_WORKBOOK, Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
}
