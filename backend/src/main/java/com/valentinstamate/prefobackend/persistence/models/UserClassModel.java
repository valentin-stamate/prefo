package com.valentinstamate.prefobackend.persistence.models;

import jakarta.persistence.*;

@Entity
@Table(name = "user_classes", uniqueConstraints = @UniqueConstraint(columnNames = {"userid", "classid", "priority"}))
public class UserClassModel {

    @Id
    private Long id;

    @Column
    private Long userId;
    @Column
    private Long classId;
    @Column
    private Long priority;

    public UserClassModel() { }

    public UserClassModel(Long userId, Long classId, Long priority) {
        this.userId = userId;
        this.classId = classId;
        this.priority = priority;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public Long getPriority() {
        return priority;
    }

    public void setPriority(Long priority) {
        this.priority = priority;
    }
}
