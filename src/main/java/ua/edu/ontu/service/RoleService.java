package ua.edu.ontu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.edu.ontu.model.Roles;
import ua.edu.ontu.model.entity.Role;
import ua.edu.ontu.model.repository.RoleRepository;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public Role saveIfNotExist(Roles roleName) {
        Role role = findByName(roleName);
        if (role == null) {
            role = new Role(roleName);
            save(role);
        }
        return role;
    }

    public Role findByName(Roles name) {
        return roleRepository.findByName(name);
    }

    public Role save(Role role) {
        return roleRepository.save(role);
    }

}
