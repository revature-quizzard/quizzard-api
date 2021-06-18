package com.revature.quizzard.models;

import com.revature.quizzard.models.ids.AccountCardId;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "accounts_cards")
@AssociationOverrides({
    @AssociationOverride(name = "pk.accountEntity",
    joinColumns = @JoinColumn(name = "account_id")),
    @AssociationOverride(name = "pk.cardEntity",
    joinColumns = @JoinColumn(name = "card_id"))
})
@NoArgsConstructor
public class AccountCardEntity {

    @EmbeddedId
    private AccountCardId pk = new AccountCardId();

    @Column(name = "confident")
    private boolean confident;

    @Column(name = "favorite")
    private boolean favorite;

    public void setAccountEntity(AccountEntity accountEntity) {
        this.pk.setAccountEntity(accountEntity);
    }

    public AccountEntity getAccountEntity() {
        return this.pk.getAccountEntity();
    }

    public void setCardEntity(CardEntity cardEntity) {
        this.pk.setCardEntity(cardEntity);
    }

    public CardEntity getCardEntity() {
        return this.pk.getCardEntity();
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setConfident(boolean confident) {
        this.confident = confident;
    }

    public boolean isConfident() {
        return confident;
    }

}
