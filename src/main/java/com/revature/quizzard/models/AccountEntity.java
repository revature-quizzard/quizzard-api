package com.revature.quizzard.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "accounts")
public @Data class AccountEntity {


    @Id
    @Column(name = "account_id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private UserEntity user;

    @ManyToOne(targetEntity = ReviewEntity.class)
    @JoinColumn(name = "review_id")
    private ReviewEntity review;
    //private Set<ReviewEntity> reviews = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.accountEntity", cascade = CascadeType.ALL)
    private Set<AccountCardEntity> accountCardEntities = new HashSet<>();


    private String username;

    private String password;

    private int points;
}
