package com.revature.quizzard.repositories;

import com.revature.quizzard.models.sets.SetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SetRepository extends JpaRepository<SetEntity, Integer> {
    Optional<SetEntity> findById(int id);
    List<SetEntity> findAllByIsPublic(boolean condition);
}
