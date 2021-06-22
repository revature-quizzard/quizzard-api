package com.revature.quizzard.models.sets;

import com.revature.quizzard.models.flashcards.CardEntity;
import com.revature.quizzard.models.user.AccountEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sets")
public @Data class SetEntity {

    @Id
    @Column(name = "set_id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "cards_sets",
            joinColumns = { @JoinColumn(name = "set_id")},
            inverseJoinColumns = { @JoinColumn(name = "card_id") }
    )
    private Set<CardEntity> cards = new HashSet<>();

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "account_id")
    private AccountEntity account;

    @Column(name = "name", nullable = false)
    String name;

    @Column(name = "public")
    Boolean isPublic;
}
