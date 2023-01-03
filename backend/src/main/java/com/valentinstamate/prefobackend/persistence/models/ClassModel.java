package com.valentinstamate.prefobackend.persistence.models;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "classes")
public class ClassModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String className;
    @Column(nullable = false)
    private String shortName;
    @Column(nullable = false)
    private int year;
    @Column(nullable = false)
    private int semester;
    @Column(nullable = false)
    private String holder;
    @Column(nullable = false)
    private String courseLink;
    @Column(nullable = false)
    private String classPackage;

    @OneToMany(cascade = CascadeType.REMOVE, mappedBy = "_class")
    private Set<PreferenceModel> preferenceModels;

    public ClassModel() {}

    public ClassModel(Long id, String className, String shortName, int year, int semester, String holder,
                      String courseLink, String classPackage, Set<PreferenceModel> preferenceModels) {
        this.id = id;
        this.className = className;
        this.shortName = shortName;
        this.year = year;
        this.semester = semester;
        this.holder = holder;
        this.courseLink = courseLink;
        this.classPackage = classPackage;
        this.preferenceModels = preferenceModels;
    }

    public ClassModel(Long id, String className, String shortName, int year, int semester, String holder,
                      String courseLink, String classPackage) {
        this.id = id;
        this.className = className;
        this.shortName = shortName;
        this.year = year;
        this.semester = semester;
        this.holder = holder;
        this.courseLink = courseLink;
        this.classPackage = classPackage;
    }

    public ClassModel(String className, String shortName, int year, int semester, String holder, String courseLink, String classPackage) {
        this.className = className;
        this.shortName = shortName;
        this.year = year;
        this.semester = semester;
        this.holder = holder;
        this.courseLink = courseLink;
        this.classPackage = classPackage;
    }

    public Set<PreferenceModel> getUserClasses() {
        return preferenceModels;
    }

    public void setUserClasses(Set<PreferenceModel> preferenceModels) {
        this.preferenceModels = preferenceModels;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public String getHolder() {
        return holder;
    }

    public void setHolder(String holder) {
        this.holder = holder;
    }

    public String getCourseLink() {
        return courseLink;
    }

    public void setCourseLink(String courseLink) {
        this.courseLink = courseLink;
    }

    public String getClassPackage() {
        return classPackage;
    }

    public void setClassPackage(String classPackage) {
        this.classPackage = classPackage;
    }
}
