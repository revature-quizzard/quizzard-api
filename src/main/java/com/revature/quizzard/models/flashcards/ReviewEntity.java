package com.revature.quizzard.models.flashcards;

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
@Table(name = "reviews")
public @Data class ReviewEntity {

    @Id
    @Column(name = "review_id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "concern")
    private String concern;

    @Column(name = "approved")
    private boolean approved;

    @ManyToOne(targetEntity = AccountEntity.class)
    @JoinColumn(name = "account_id")
    private Set<AccountEntity> accounts = new HashSet<>();

}
