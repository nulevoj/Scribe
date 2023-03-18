package ua.edu.ontu.service.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ua.edu.ontu.model.RoleName;
import ua.edu.ontu.model.entity.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {

    boolean existsByName(RoleName name);

    Role findByName(RoleName name);

    @Override
    Role save(Role role);

}
