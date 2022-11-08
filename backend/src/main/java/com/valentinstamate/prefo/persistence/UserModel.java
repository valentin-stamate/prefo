package com.valentinstamate.prefo.persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "users")
public class UserModel implements Serializable {
    @Id
    private Long id;

    public Long getId() {
        return id;
    }
}
