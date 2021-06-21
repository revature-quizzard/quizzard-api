package com.revature.quizzard.models.composites;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.revature.quizzard.models.flashcards.CardEntity;
import com.revature.quizzard.models.user.AccountEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

/**
 * @Author: Richard Taylor
 *
 * AccountCardEntity
 *
 * Used to link an <code>AccountEntity</code> to a <code>CardEntity</code>.
 * If, say, a user wants to bookmark a card, the process would go as follows:
 * 1) Create a new <code>AccountCardEntity</code>
 * 2) Set the Card and Account to it via the setters.
 * 3) Optionally, Set confident and/or favorite to true/false.
 * 4) Save the <code>AccountEntity</code> or <code>CardEntity</code> to the database.
 *
 * NOTE: This allows the card/row to be edited rather than a new row created.
 *
 * Usage:
 * <code>
 * AccountCardEntity accountCardEntity = new AccountCardEntity();
 *
 * accountCardEntity.setCardEntity(cardEntity); //link the userEntity
 * accountCardEntity.setAccountEntity(accountEntity); //link the accountEntity
 *
 * //Save the account or card, NOT the AccountCardEntity
 *
 * //setFavorite and/or setConfident here
 *
 * //To edit, find the AccountCardEntity by the user or account, update the fields that need to be updated, and save either the user or the account
 * </code>
 */
@Entity
@Table(name = "accounts_cards")
@AssociationOverride(name = "pk.accountEntity", joinColumns = @JoinColumn(name = "account_id"))
@AssociationOverride(name = "pk.cardEntity", joinColumns = @JoinColumn(name = "card_id"))
@NoArgsConstructor
@AllArgsConstructor
public class AccountCardEntity {

    @EmbeddedId
    private AccountCardId pk = new AccountCardId();

    @Column(name = "confident", columnDefinition = "boolean default false")

    private Boolean confident;

    @Column(name = "favorite")
    private Boolean favorite;


    public AccountCardEntity(AccountEntity accountEntity, CardEntity cardEntity, Boolean favorite, Boolean confident) {
        this.setAccountEntity(accountEntity);
        this.setCardEntity(cardEntity);
        if(favorite != null) this.setFavorite(favorite);
        if(confident != null) this.setConfident(confident);
    }

    public void setAccountEntity(AccountEntity accountEntity) {
        this.pk.setAccountEntity(accountEntity);
    }

    @JsonIgnore
    public AccountEntity getAccountEntity() {
        return this.pk.getAccountEntity();
    }

    public void setCardEntity(CardEntity cardEntity) {
        this.pk.setCardEntity(cardEntity);
    }
    @JsonIgnore
    public CardEntity getCardEntity() {
        return this.pk.getCardEntity();
    }

    public void setFavorite(Boolean favorite) {
        this.favorite = favorite;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setConfident(Boolean confident) {
        this.confident = confident;
    }

    public boolean isConfident() {
        return confident;
    }

}
