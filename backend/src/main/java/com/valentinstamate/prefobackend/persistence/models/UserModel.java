package com.valentinstamate.prefobackend.persistence.models;

import com.valentinstamate.prefobackend.persistence.consts.UserType;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class UserModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String username;
    @Column(unique = true, nullable = false)
    private String fullName;
    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private Integer year = 0;
    @Column(nullable = false)
    private Integer semester = 0;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserType userType = UserType.USER;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "user")
    private List<PreferenceModel> preferenceModels = new ArrayList<>();

    public UserModel() { }

    public UserModel(String username, String fullName, String email, String password, Integer year, Integer semester, UserType userType) {
        this.username = username;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.year = year;
        this.semester = semester;
        this.userType = userType;
    }

    public UserModel(String username, String fullName, String email, String password, Integer year, Integer semester) {
        this.username = username;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.year = year;
        this.semester = semester;
    }

    public UserModel(Long id, String username, String fullName, String email, String password, Integer year, Integer semester, UserType userType, List<PreferenceModel> preferenceModels) {
        this.id = id;
        this.username = username;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.year = year;
        this.semester = semester;
        this.userType = userType;
        this.preferenceModels = preferenceModels;
    }


    public void addUserPreferenceModels(ArrayList<PreferenceModel> preferences) {
        this.preferenceModels.addAll(preferences);
    }

    public void removeUserPreference(PreferenceModel preferenceModel) {
        this.preferenceModels.remove(preferenceModel);
    }

    public List<PreferenceModel> getPreferenceModels() {
        return preferenceModels;
    }

    public void setPreferenceModels(List<PreferenceModel> preferenceModels) {
        this.preferenceModels = preferenceModels;
    }

    public void removeUserPreferencesByPackageName(String packageName) {
        preferenceModels.removeIf(item -> item.get_class().getClassPackage().equals(packageName));
    }

    public void setUserPreferences(List<PreferenceModel> preferenceModels) {
        this.preferenceModels = preferenceModels;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getSemester() {
        return semester;
    }

    public void setSemester(Integer semester) {
        this.semester = semester;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserModel userModel = (UserModel) o;

        if (!username.equals(userModel.username)) return false;
        return email.equals(userModel.email);
    }

    @Override
    public int hashCode() {
        int result = username.hashCode();
        result = 31 * result + email.hashCode();
        return result;
    }
}
