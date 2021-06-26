package com.revature.quizzard.repositories;

import com.revature.quizzard.models.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    boolean existsByEmail(UserEntity entity); // Testing to see if this works later on if so This is Vince McMahon Levels of Ascension
    boolean existsByEmail(String email);

}
