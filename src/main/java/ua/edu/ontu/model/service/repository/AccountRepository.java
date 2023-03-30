package ua.edu.ontu.model.service.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ontu.model.entity.Account;

@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {

    boolean existsByEmail(String email);

    Account findByEmail(String email);

    @Override
    Account save(Account account);

    @Override
    void delete(Account account);

    @Transactional
    void deleteByEmail(String email);

}
