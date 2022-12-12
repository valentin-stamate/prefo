package com.valentinstamate.prefobackend.persistence.models;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "users")
public class UserModel implements Serializable {

    @Id
    private Long id;
    @Column
    private String fullName;
    @Column
    private String identifier;
    @Column
    private String email;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return fullName;
    }

    public void setUsername(String username) {
        this.fullName = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
