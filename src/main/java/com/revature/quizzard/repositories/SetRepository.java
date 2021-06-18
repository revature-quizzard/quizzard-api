package com.revature.quizzard.repositories;

import com.revature.quizzard.models.flashcards.CardEntity;
import com.revature.quizzard.models.sets.SetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface SetRepository extends JpaRepository<SetEntity, Integer> {

}
