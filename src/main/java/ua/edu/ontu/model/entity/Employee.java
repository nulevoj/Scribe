package ua.edu.ontu.model.entity;

import jakarta.persistence.*;

@Entity
public class Employee extends Person {

    @Id
    @Column(name = "account_id")
    private Long accountId;

    @Column(name = "position")
    private String position;

    @Column(name = "degree")
    private String degree;

    public Employee() {

    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
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
