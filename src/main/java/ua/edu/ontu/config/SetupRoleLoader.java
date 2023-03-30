package ua.edu.ontu.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import ua.edu.ontu.model.RoleName;
import ua.edu.ontu.model.entity.Role;
import ua.edu.ontu.model.service.RoleService;

@Component
public class SetupRoleLoader implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private RoleService roleService;

    private boolean alreadySetup = false;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (alreadySetup) {
            return;
        }
        for (RoleName roleName : RoleName.values()) {
            if (!roleService.isExist(roleName)) {
                roleService.save(new Role(roleName));
            }
        }
        alreadySetup = true;
    }

}
