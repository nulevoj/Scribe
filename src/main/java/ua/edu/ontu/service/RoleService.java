package ua.edu.ontu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.edu.ontu.model.RoleName;
import ua.edu.ontu.model.entity.Role;
import ua.edu.ontu.service.repository.RoleRepository;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public boolean isExist(RoleName roleName) {
        return roleRepository.existsByName(roleName);
    }

    public Role findByName(RoleName roleName) {
        return roleRepository.findByName(roleName);
    }

    public Role save(Role role) {
        return roleRepository.save(role);
    }

}
