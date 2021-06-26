package com.revature.quizzard.models.composites;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.revature.quizzard.models.user.AccountEntity;
import com.revature.quizzard.models.flashcards.CardEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @Author: Richard Taylor
 * @see: <code>AccountCardEntity</code>
 * This represents a composite key for <code>AccountCardEntity</code>.
 */
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public @Data class AccountCardId implements java.io.Serializable{

    @ManyToOne
    private AccountEntity accountEntity;

    @ManyToOne
    private CardEntity cardEntity; // Check link middle of link

}
