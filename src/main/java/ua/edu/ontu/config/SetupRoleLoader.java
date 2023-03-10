package ua.edu.ontu.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import ua.edu.ontu.model.Roles;
import ua.edu.ontu.service.RoleService;

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

        for (Roles role : Roles.values()) {
            roleService.saveIfNotExist(role);
        }

        alreadySetup = true;
    }

}
