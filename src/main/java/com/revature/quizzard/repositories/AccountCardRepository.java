package com.revature.quizzard.repositories;

import com.revature.quizzard.models.composites.AccountCardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountCardRepository extends JpaRepository<AccountCardEntity, Integer> {

}
