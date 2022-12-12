package com.valentinstamate.prefobackend.persistence.models;

import com.valentinstamate.prefobackend.persistence.consts.ClassPackage;
import jakarta.persistence.*;

@Entity
@Table(name = "classes")
public class ClassModel {

    @Id private Long id;
    @Column
    private String className;
    @Column
    private String shortName;
    @Column
    private int year;
    @Column
    private int semester;
    @Column
    private String holder;
    @Column
    private String courseLink;
    @Column
    @Enumerated(EnumType.STRING)
    private ClassPackage classPackage;

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

    public ClassPackage getClassPackage() {
        return classPackage;
    }

    public void setClassPackage(ClassPackage classPackage) {
        this.classPackage = classPackage;
    }
}
