package com.valentinstamate.prefobackend.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.valentinstamate.prefobackend.controller.requests.PreferencesBody;
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
    @Inject private PreferenceService preferenceService;

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
            var newPackage = new PackageModel(row.packageName, row.year, row.semester, row.classes);
            packageRepository.persist(newPackage);
        }
    }

    public List<PackageModel> getPackages() throws ServiceException {
        return packageRepository.findAll();
    }

    public List<ClassModel> getClassesByPackage(String packageName) throws ServiceException {
        return classRepository.findClassesByPackage(packageName);
    }

    public Map<String, List<Map<String, Object>>> getStudentPreferences(String username) throws ServiceException {
        var userModel = userRepository.findByUsername(username);

        var objectMapper = new ObjectMapper();

        var hashMap = new TreeMap<String, List<Map<String, Object>>>();
        var preferences = preferenceService.getStudentSorterPreferences(userModel);

        for (var preferenceModel : preferences) {
            var classItem = preferenceModel.get_class();
            var packageName = classItem.getClassPackage();

            classItem.setPreferenceModels(new ArrayList<>());

            Map<String, Object> map = objectMapper.convertValue(classItem, Map.class);
            map.put("priority", preferenceModel.getPriority());
            map.put("completionDate", preferenceModel.getCompletionDate());

            if (!hashMap.containsKey(packageName)) {
                hashMap.put(packageName, new ArrayList<>());
            }

            hashMap.get(packageName).add(map);
        }

        return hashMap;
    }

    public void addStudentPreferences(String username, PreferencesBody preferencesBody) throws ServiceException {
        var userModel = userRepository.findByUsername(username);

        var packageName = preferencesBody.packageName;
        var size = preferencesBody.preferences.length;
        var preferences = preferencesBody.preferences;

        var packageModel = packageRepository.findByPackageName(packageName);

        if (packageModel == null) {
            throw new ServiceException(ResponseMessage.PACKAGE_NOT_FOUND, Response.Status.NOT_FOUND);
        }

        var classNumber = packageModel.getClassNumber();

        if (size == 0) {
            throw new ServiceException(ResponseMessage.PREFERENCES_LIST_NOT_EMPTY, Response.Status.NOT_ACCEPTABLE);
        }

        var newPreferences = new ArrayList<PreferenceModel>();

        for (var preference : preferences) {
            var classModel = classRepository.findById((long) preference.id);
            var priority = preference.priority;

            if (classModel == null) {
                throw new ServiceException(ResponseMessage.CLASS_NOT_FOUND, Response.Status.NOT_FOUND);
            }

            if (!classModel.getClassPackage().equals(packageName)) {
                throw new ServiceException(ResponseMessage.CLASSES_SAME_TYPE, Response.Status.NOT_ACCEPTABLE);
            }

            if (priority < 1 || priority > classNumber) {
                throw new ServiceException(ResponseMessage.INVALID_PRIORITY, Response.Status.NOT_ACCEPTABLE);
            }

            newPreferences.add(new PreferenceModel(userModel, classModel, priority));
        }

        userModel.removeUserPreferencesByPackageName(packageName);
        userModel.addUserPreferences(newPreferences);
        preferenceRepository.removeByUserAndPackageName(userModel, packageName);

        userRepository.update(userModel);

        userRepository.clearCache();
        classRepository.clearCache();
        preferenceRepository.clearCache();
    }

    public void removeStudentPreferences(String username, String packageName) throws ServiceException {
        var userModel = userRepository.findByUsername(username);
        var packageModel = packageRepository.findByPackageName(packageName);

        if (packageModel == null) {
            throw new ServiceException(ResponseMessage.PACKAGE_NOT_FOUND, Response.Status.NOT_FOUND);
        }

        preferenceRepository.removeByUserAndPackageName(userModel, packageName);
        userRepository.clearCache();
        classRepository.clearCache();
    }

    public Map<String, List<ClassModel>> getAllClasses() throws ServiceException {
        var allClasses = classRepository.findAll();

        var map = new TreeMap<String, List<ClassModel>>();

        for (var _class : allClasses) {
            var classPackage = _class.getClassPackage();
            _class.setPreferenceModels(new ArrayList<>());

            if (!map.containsKey(classPackage)) {
                map.put(classPackage, new ArrayList<>());
            }

            map.get(classPackage).add(_class);
        }

        return map;
    }

    public InputStream exportStudentsPreferences() throws ServiceException {
        var packages = packageRepository.findAll();
        var packagesNumber = packages.size();

        var classesPerPackageMap = new TreeMap<String, Integer>();
        for (var packageModel : packages) {
            classesPerPackageMap.put(packageModel.getPackageName(), packageModel.getClassNumber());
        }

        var usersPreferenceRows = userRepository
                .findAllStudents()
                .stream().map(user -> {
                    var preferences = preferenceService.getStudentSorterPreferences(user);

                    var year = user.getYear();
                    var semester = user.getSemester();

                    var row = new ArrayList<>();
                    row.add(user.getUsername());
                    row.add(user.getFullName());
                    row.add(year);
                    row.add(semester);
                    row.add(year * 2 - 1 + semester);

                    var sortedPreferencesByPackage = ServiceUtils.orderPreferences(preferences, packagesNumber);

                    for (int i = 1; i <= packagesNumber; i++) {
                        var packageName = String.format("CO%d", i);
                        var packageQueue = sortedPreferencesByPackage.get(packageName);
                        var classesPerPackage = classesPerPackageMap.get(packageName);

                        for (int j = 1; j <= classesPerPackage; j++) {
                            row.add(Objects.requireNonNullElse(packageQueue.poll(), ""));
                        }
                    }

                    return (List<Object>) row;
                })
                .collect(Collectors.toList());

        var workbook = PreferenceRowsToExcelService.parse(usersPreferenceRows, classesPerPackageMap);

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
