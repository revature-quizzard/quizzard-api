package com.revature.quizzard.repositories;


import com.revature.quizzard.models.sets.SetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Set;

/**
 * Interface to handle all the calls to the database for data of the sets
 */
@Repository
public interface SetRepository extends JpaRepository<SetEntity, Integer> {

    @Query("Select s from SetEntity s where s.isPublic=:isPublic")
    Set<SetEntity>findIsPublic(@Param("isPublic") Boolean isPublic);


}
