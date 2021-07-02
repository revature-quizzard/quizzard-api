package com.revature.quizzard.repositories;

import com.revature.quizzard.models.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    UserEntity findByEmail(String username);

    //could potentially be used
    boolean existsByEmail(String email);

}
