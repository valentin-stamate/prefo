package com.valentinstamate.prefobackend.persistence.models;

import com.valentinstamate.prefobackend.persistence.consts.UserType;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

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

    @OneToMany(cascade = CascadeType.REMOVE)
    private Set<PreferenceModel> preferenceModels;

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

    public UserModel(Long id, String username, String fullName, String email, String password, UserType userType) {
        this.id = id;
        this.username = username;
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.userType = userType;
    }

    public Set<PreferenceModel> getUserClasses() {
        return preferenceModels;
    }

    public void addUserClass(PreferenceModel preferenceModel) {
        if (this.preferenceModels == null) {
            this.preferenceModels = new HashSet<>();
        }

        this.preferenceModels.add(preferenceModel);
    }

    public void removeUserClass(PreferenceModel preferenceModel) {
        if (this.preferenceModels == null) {
            this.preferenceModels = new HashSet<>();
        }

        this.preferenceModels.remove(preferenceModel);
    }

    public void setUserClasses(Set<PreferenceModel> preferenceModels) {
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
}
