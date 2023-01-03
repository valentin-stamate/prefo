package com.valentinstamate.prefobackend.persistence.models;

import jakarta.persistence.*;

@Entity
@Table(name = "packages")
public class PackageModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String packageName;

    public PackageModel() { }

    public PackageModel(Long id, String packageName) {
        this.id = id;
        this.packageName = packageName;
    }

    public PackageModel(String packageName) {
        this.packageName = packageName;
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
}
