package com.revature.quizzard.repositories;

import com.revature.quizzard.models.flashcards.CardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface CardRepository extends JpaRepository<CardEntity, Integer> {

    Set<CardEntity> findByisPublicIsTrue();
    Set<CardEntity> findByReviewableTrue();


    @Query(nativeQuery = true, value = "select c.* from accounts_cards ac inner join cards c on (ac.card_id = c.card_id) where ac.account_id = ?1")
    List<CardEntity> findAllCardsByAccountId(int accountId);


}
