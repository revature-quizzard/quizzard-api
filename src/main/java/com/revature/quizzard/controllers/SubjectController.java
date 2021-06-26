package com.revature.quizzard.controllers;

import com.revature.quizzard.models.flashcards.SubjectEntity;
import com.revature.quizzard.services.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/subject")
public class SubjectController {

    private SubjectService subjectService;

    /**
     * Constructor that creates SubjectController from an autowired SubjectService
     * @param subjectService
     * @author Giancarlo Tomasello
     * @author Kevin Chang
     */
    @Autowired
    public SubjectController(SubjectService subjectService){
        this.subjectService = subjectService;
    }

    /**
     * Retuns a list of all the subjects in the database
     * @param req A HttpServletRequest
     * @return List<SubjectEntity>
     * @author Giancarlo Tomasello
     * @author Kevin Chang
     */
    @GetMapping(value = "/all", produces = APPLICATION_JSON_VALUE)
    public List<SubjectEntity> getSubjects(HttpServletRequest req){
        return subjectService.getAllSubjects();
    }

}
