package ua.edu.ontu.model.entity;

import jakarta.persistence.*;

@Entity
public class Employee extends Person {

    @Column(name = "position")
    private String position;

    @Column(name = "degree")
    private String degree;

    public Employee() {

    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

}
