package com.valentinstamate.prefobackend.persistence.models;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "preferences")
public class PreferenceModel implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "user_id", nullable = false)
    private UserModel user;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "class_id", nullable = false)
    private ClassModel _class;

    @Column
    private Integer priority;

    public PreferenceModel() { }

    public PreferenceModel(Long id, UserModel user, ClassModel _class, Integer priority) {
        this.id = id;
        this.user = user;
        this._class = _class;
        this.priority = priority;
    }

    public PreferenceModel(UserModel user, ClassModel _class, Integer priority) {
        this.user = user;
        this._class = _class;
        this.priority = priority;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ClassModel get_class() {
        return _class;
    }

    public void set_class(ClassModel _class) {
        this._class = _class;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PreferenceModel that = (PreferenceModel) o;

        if (!user.equals(that.user)) return false;
        if (!_class.equals(that._class)) return false;
        return priority.equals(that.priority);
    }

    @Override
    public int hashCode() {
        int result = user.hashCode();
        result = 31 * result + _class.hashCode();
        result = 31 * result + priority.hashCode();
        return result;
    }
}
