package com.revature.quizzard.controllers;

import com.revature.quizzard.dtos.SetDTO;
import com.revature.quizzard.services.SetService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import java.util.Set;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * SetController Class -- Allows to reach the API functionalities for the Set component
 */
@RestController
@RequestMapping("/set")
@AllArgsConstructor(onConstructor_ = @Autowired)
public class SetController {

    private SetService setService;

    /**
     * getPublicSets -- Endpoint to find all the public sets persisted in the database
     * @param req -- Empty request that triggers the process to find all the public sets
     * @return -- JSON structure with the public set data and their public cards
     */
    @GetMapping(value = "public", produces = APPLICATION_JSON_VALUE)
    public Set<SetDTO> getPubicSets(HttpServletRequest  req){
        return setService.findIsPublic(true);
    }

}
