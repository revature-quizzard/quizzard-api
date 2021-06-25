package com.revature.quizzard.models.sets;

import com.revature.quizzard.dtos.SetDTO;
import com.revature.quizzard.models.flashcards.CardEntity;
import com.revature.quizzard.models.user.AccountEntity;
import lombok.*;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;

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
    private int setId;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "sets_cards",
            joinColumns = { @JoinColumn(name = "set_id")},
            inverseJoinColumns = { @JoinColumn(name = "card_id") }
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
//        this.creator = setDTO.getCreator();
//        this.cards = new HashSet<>(setDTO.getLocalFlashcards().stream().map(CardEntity::new).collect(Collectors.toSet()));
    }
}
