package com.revature.quizzard.models.ids;

import com.revature.quizzard.models.AccountEntity;
import com.revature.quizzard.models.CardEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public @Data class AccountCardId implements java.io.Serializable{

    @ManyToOne
    private AccountEntity accountEntity;

    @ManyToOne
    private CardEntity cardEntity; // Check link middle of link

}
