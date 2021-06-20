package com.revature.quizzard.repositories;

import com.revature.quizzard.models.flashcards.CardEntity;
import com.revature.quizzard.models.sets.SetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface SetRepository extends JpaRepository<SetEntity, Integer> {

    //@Query("Select s from SetEntity s where s.isPublic=:isPublic")
    //Set<SetEntity>findIsPublic(@Param("isPublic") Boolean isPublic);
    Set<SetEntity>findIsPublicTrue(Boolean isPublic);
}
