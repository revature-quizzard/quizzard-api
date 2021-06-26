package com.revature.quizzard.repositories;

import com.revature.quizzard.models.sets.QuizEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface QuizRepository extends JpaRepository<QuizEntity, Integer> {

}
