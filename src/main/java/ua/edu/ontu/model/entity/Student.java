package ua.edu.ontu.model.entity;

import jakarta.persistence.*;

@Entity
public class Student extends Person {

    @Id
    @Column(name = "account_id")
    private long accountId;

    @Column(name = "faculty")
    private String faculty;

    @Column(name = "degree")
    private String degree;

    @Column(name = "specialty")
    private String specialty;

    @Column(name = "year")
    private String year;

    public Student() {

    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

}
