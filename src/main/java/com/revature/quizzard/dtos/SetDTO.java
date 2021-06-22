package com.revature.quizzard.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.security.config.web.servlet.SecurityMarker;

import java.util.HashSet;
import java.util.Set;

@Accessors
@Getter @Setter
public class SetDTO {

    @NotNull
    @JsonProperty("set_id")
    private int setId;

    @NotNull
    @JsonProperty("user_id")
    private int userId;

    @NotNull
    @JsonProperty("set_name")
    private String name;

    @JsonProperty("cards")
    @Getter @Setter
    private Set<CardDTO> cards = new HashSet<>();

    public void addCardToSet (CardDTO card){
        cards.add(card);
    }

}
