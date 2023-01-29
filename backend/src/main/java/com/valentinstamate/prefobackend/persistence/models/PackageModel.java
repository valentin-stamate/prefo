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

    @Column(nullable = false)
    private Integer classNumber;

    public PackageModel() { }

    public PackageModel(Long id, String packageName, Integer year, Integer semester, Integer classNumber) {
        this.id = id;
        this.packageName = packageName;
        this.year = year;
        this.semester = semester;
        this.classNumber = classNumber;
    }

    public PackageModel(String packageName, Integer year, Integer semester, Integer classNumber) {
        this.packageName = packageName;
        this.year = year;
        this.semester = semester;
        this.classNumber = classNumber;
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

    public Integer getClassNumber() {
        return classNumber;
    }

    public void setClassNumber(Integer classes) {
        this.classNumber = classes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PackageModel that = (PackageModel) o;

        if (!packageName.equals(that.packageName)) return false;
        if (!year.equals(that.year)) return false;
        return semester.equals(that.semester);
    }

    @Override
    public int hashCode() {
        int result = packageName.hashCode();
        result = 31 * result + year.hashCode();
        result = 31 * result + semester.hashCode();
        return result;
    }
}
