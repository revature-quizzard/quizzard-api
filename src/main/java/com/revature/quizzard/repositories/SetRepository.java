package com.revature.quizzard.repositories;

import com.revature.quizzard.models.sets.SetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SetRepository extends JpaRepository<SetEntity, Integer> {

    List<SetEntity> findAll();
}
