package ua.edu.ontu.model.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.edu.ontu.model.entity.Account;

@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {

    @Override
    Account save(Account account);

    @Override
    void delete(Account account);

    @Transactional
    void deleteByEmail(String email);

    Account findByEmail(String email);

    boolean existsByEmail(String email);

}
