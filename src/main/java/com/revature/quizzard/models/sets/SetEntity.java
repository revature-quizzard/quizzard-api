package com.revature.quizzard.models.sets;
import com.revature.quizzard.dtos.SetDTO;
import com.revature.quizzard.models.flashcards.CardEntity;
import com.revature.quizzard.models.user.AccountEntity;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "sets")
public @Data class SetEntity {

    @Id
    @Column(name = "set_id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinTable(
            name = "sets_cards",
            joinColumns = @JoinColumn(name = "set_id", referencedColumnName = "set_id"),
            inverseJoinColumns = @JoinColumn(name = "card_id", referencedColumnName = "card_id")
    )
    private Set<CardEntity> cards = new HashSet<>();

    // FOR OWNERSHIP OF A SET BY AN ACCOUNT
    @ManyToOne(targetEntity = AccountEntity.class)
    @JoinColumn(name = "account_id")
    private AccountEntity creator; // creator/owner

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "public")
    private Boolean isPublic;


    public SetEntity(SetDTO setDTO) {
        this.isPublic = setDTO.isPublic();
        this.name = setDTO.getSetName();
        this.creator = setDTO.getCreator();
    }

    @Override
    public String toString()
    {
        return "SetEntity{" +
                       "id=" + id +
                       ", name='" + name + '\'' +
                       ", isPublic=" + isPublic +
                       ", creator=" + creator +
                       ", cards=" + cards +
                       '}';
    }
}
