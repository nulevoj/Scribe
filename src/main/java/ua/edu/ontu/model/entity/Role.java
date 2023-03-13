package ua.edu.ontu.model.entity;

import jakarta.persistence.*;
import ua.edu.ontu.model.Roles;

@Entity
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private long roleId;

    @Column(name = "name", unique = true)
    @Enumerated(value = EnumType.ORDINAL)
    private Roles name;

    public Role() {
    }

    public Role(Roles name) {
        this.name = name;
    }

    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }

    public Roles getName() {
        return name;
    }

    public void setName(Roles name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return false;
        }
        Role role = (Role) object;
        return this.name == role.name;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

}
