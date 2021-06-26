package com.revature.quizzard.repositories;

import com.revature.quizzard.models.user.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Integer> {

    AccountEntity findByUsername(String username);
    Optional<AccountEntity> findById(int id);
    AccountEntity findByUsernameAndPassword(String username, String password);

    boolean existsByUsername(String username);

    boolean existsUserByUsernameAndPassword(String name, String password);
    


}
