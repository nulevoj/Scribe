package ua.edu.ontu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.edu.ontu.model.RoleName;
import ua.edu.ontu.model.entity.Account;
import ua.edu.ontu.model.entity.Role;
import ua.edu.ontu.model.service.AccountService;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AccountService accountService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountService.findByEmail(username);
        if (account == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }

        User user = new User(
                account.getEmail(),
                account.getPassword(),
                mapRolesToAuthorities(account.getRoles())
        );
        return user;
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(RoleName.USER.toString()))
                .collect(Collectors.toList());
    }

    public String getEmailFromPrincipal() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!(principal instanceof UserDetails)) {
            throw new RuntimeException("!(principal instanceof UserDetails)");
        }
        return ((UserDetails) principal).getUsername();
    }

}
