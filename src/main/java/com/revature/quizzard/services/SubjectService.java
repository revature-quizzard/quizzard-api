package com.revature.quizzard.services;

import com.revature.quizzard.models.flashcards.SubjectEntity;
import com.revature.quizzard.repositories.SubjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubjectService {

    private SubjectRepository subjectRepository;

    @Autowired
    public SubjectService(SubjectRepository subjectRepository){
        this.subjectRepository = subjectRepository;
    }

    /**
     * Returns a list of all the subjects stored in the database
     * @return A list of SubjectEntities
     */
    public List<SubjectEntity> getAllSubjects(){
        List<SubjectEntity> subjectEntities = subjectRepository.findAll();

        return subjectEntities;
    }
}
