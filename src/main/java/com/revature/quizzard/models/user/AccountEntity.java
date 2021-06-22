package com.revature.quizzard.models.user;


import com.revature.quizzard.dtos.responsemodel.lists.AccountCardDTO;
import com.revature.quizzard.models.composites.AccountCardEntity;
import com.revature.quizzard.models.flashcards.ReviewEntity;
import com.revature.quizzard.models.sets.SetEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


@NoArgsConstructor
@AllArgsConstructor
@Table(name = "accounts")
@Entity
public @Data class AccountEntity {

    @Id
    @Column(name = "account_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private UserEntity user;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pk.accountEntity", cascade = CascadeType.ALL)
    private Set<AccountCardEntity> accountCardEntities = new HashSet<>();

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "accounts_roles",
            joinColumns = { @JoinColumn(name = "account_id")},
            inverseJoinColumns = { @JoinColumn(name = "role_id") }
    )
    private Set<RoleEntity> roles = new HashSet<>();

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "points", columnDefinition = "Integer default 0", nullable = true)
    private int points;

    //scuffed implementation, I know -Richard
    public Set<AccountCardDTO> getAccountCards() {
        return this.accountCardEntities.stream()
                .map(AccountCardDTO::new)
                .collect(Collectors.toSet());
    }
}
