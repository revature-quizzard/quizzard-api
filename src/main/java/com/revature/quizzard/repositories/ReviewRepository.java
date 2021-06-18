package com.revature.quizzard.repositories;

import com.revature.quizzard.models.flashcards.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface ReviewRepository extends JpaRepository<ReviewEntity, Integer> {

}
