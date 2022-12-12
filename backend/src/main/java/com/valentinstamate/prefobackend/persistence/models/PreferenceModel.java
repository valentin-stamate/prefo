package com.valentinstamate.prefobackend.persistence.models;

import jakarta.persistence.*;

@Entity
@Table(name = "preferences")
public class PreferenceModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "class_id")
    private ClassModel _class;

    public PreferenceModel() { }

    public PreferenceModel(Long id, ClassModel _class) {
        this.id = id;
        this._class = _class;
    }

    public PreferenceModel(ClassModel _class) {
        this._class = _class;
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
}
