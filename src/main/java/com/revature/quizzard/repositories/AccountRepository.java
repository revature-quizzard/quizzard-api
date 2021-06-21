package com.revature.quizzard.repositories;

import com.revature.quizzard.models.user.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Integer> {

    AccountEntity findByUsername(String username);

    AccountEntity findByUsernameAndPassword(String username, String password);

    boolean existsByUsername(String username);

    boolean existsUserByUsernameAndPassword(String username, String password);

}
