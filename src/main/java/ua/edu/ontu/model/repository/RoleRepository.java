package ua.edu.ontu.model.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.edu.ontu.model.Roles;
import ua.edu.ontu.model.entity.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {

    @Override
    Role save(Role role);

    Role findByName(Roles name);

}
