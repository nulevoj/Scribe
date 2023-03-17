package ua.edu.ontu.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ua.edu.ontu.dto.RegistrationDto;
import ua.edu.ontu.model.Roles;
import ua.edu.ontu.model.entity.Account;
import ua.edu.ontu.model.entity.Role;
import ua.edu.ontu.model.repository.AccountRepository;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class AccountService implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = findByEmail(username);
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
                .map(role -> new SimpleGrantedAuthority(Roles.USER.toString()))
                .collect(Collectors.toList());
    }

    public Account findByEmail(String email) {
        return accountRepository.findByEmail(email);
    }

    public Account saveDto(RegistrationDto accountDto) {
        if (isExist(accountDto.getEmail())) {
            throw new RuntimeException("Email already taken. ");
        }
        Account account = new Account();
        account.setEmail(accountDto.getEmail());
        account.setPassword(passwordEncoder.encode(accountDto.getPassword()));
        account.addRole(roleService.findByName(Roles.USER));
        return accountRepository.save(account);
    }

    public Account save(Account account) {
        return accountRepository.save(account);
    }

    public void delete(Account account) {
        accountRepository.delete(account);
    }

    public void delete(String email) {
        accountRepository.deleteByEmail(email);
    }

    public boolean isExist(String email) {
        return accountRepository.existsByEmail(email);
    }

}
