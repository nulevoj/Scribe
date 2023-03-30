package ua.edu.ontu.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ua.edu.ontu.dto.RegistrationDto;
import ua.edu.ontu.model.RoleName;
import ua.edu.ontu.model.entity.Account;
import ua.edu.ontu.model.entity.Person;
import ua.edu.ontu.model.service.repository.AccountRepository;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public boolean isExist(String email) {
        return accountRepository.existsByEmail(email);
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
        account.addRole(roleService.findByName(RoleName.USER));
        return accountRepository.save(account);
    }

    public Account save(Account account) {
        return accountRepository.save(account);
    }

    public void setPerson(Account account, Person person) {
        account.setPerson(person);
        person.setAccount(account);

    }

    public void delete(Account account) {
        accountRepository.delete(account);
    }

    public void delete(String email) {
        accountRepository.deleteByEmail(email);
    }

}
