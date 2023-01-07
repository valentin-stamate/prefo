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
    @Enumerated(EnumType.STRING)
    private UserType userType = UserType.USER;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "user")
    private List<PreferenceModel> preferenceModels = new ArrayList<>();

    public UserModel() { }

    public UserModel(String username, String fullName, String email, String password, UserType userType) {
        this.username = username;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.userType = userType;
    }

    public UserModel(String username, String fullName, String email, String password) {
        this.username = username;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
    }

    public UserModel(Long id, String username, String fullName, String email, String password, UserType userType, List<PreferenceModel> preferenceModels) {
        this.id = id;
        this.username = username;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.userType = userType;
        this.preferenceModels = preferenceModels;
    }

    public List<PreferenceModel> getUserClasses() {
        return preferenceModels;
    }

    public void addUserPreference(PreferenceModel preferenceModel) {
        this.preferenceModels.add(preferenceModel);
    }

    public void removeUserPreference(PreferenceModel preferenceModel) {
        this.preferenceModels.remove(preferenceModel);
    }

    public void setUserClasses(List<PreferenceModel> preferenceModels) {
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

    public List<PreferenceModel> getPreferenceModels() {
        return preferenceModels;
    }

    public void setPreferenceModels(List<PreferenceModel> preferenceModels) {
        this.preferenceModels = preferenceModels;
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
