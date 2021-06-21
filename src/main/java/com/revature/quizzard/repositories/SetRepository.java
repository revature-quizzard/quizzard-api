package com.revature.quizzard.repositories;

import com.revature.quizzard.models.flashcards.CardEntity;
import com.revature.quizzard.models.sets.SetEntity;
import com.revature.quizzard.models.user.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SetRepository extends JpaRepository<SetEntity, Integer> {

    @Query(nativeQuery = true, value = "SELECT * FROM sets WHERE account_id = ?1")
    List<SetEntity> findAllCreatedByAccount(AccountEntity creator);
}
