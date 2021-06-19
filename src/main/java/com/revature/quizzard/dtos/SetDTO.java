package com.revature.quizzard.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.config.web.servlet.SecurityMarker;

import java.util.HashSet;
import java.util.Set;

public class SetDTO {

    @NotNull
    @JsonProperty("user_id")
    @Getter @Setter
    private int userId;

    @NotNull
    @JsonProperty("set_name")
    @Getter @Setter
    private String name;

    @JsonProperty("sets")
    @Getter @Setter
    private Set<CardDTO> cards = new HashSet<>();


}
