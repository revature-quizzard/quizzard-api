package com.revature.quizzard.repositories;

import com.revature.quizzard.models.flashcards.CardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface CardRepository extends JpaRepository<CardEntity, Integer> {

//    Set<CardEntity> findByIsPublicIsTrue();
//    Set<CardEntity> findByReviewableTrue();


}

