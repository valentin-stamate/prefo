package com.valentinstamate.prefobackend.persistence.models;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "packages")
public class PackageModel implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String packageName;

    @Column(nullable = false)
    private Integer year;

    @Column(nullable = false)
    private Integer semester;

    public PackageModel() { }

    public PackageModel(Long id, String packageName, Integer year, Integer semester) {
        this.id = id;
        this.packageName = packageName;
        this.year = year;
        this.semester = semester;
    }

    public PackageModel(String packageName, Integer year, Integer semester) {
        this.packageName = packageName;
        this.year = year;
        this.semester = semester;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
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
}
