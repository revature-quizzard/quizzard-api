package com.revature.quizzard.models.user;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.revature.quizzard.dtos.responsemodel.lists.AccountCardDTO;
import com.revature.quizzard.models.composites.AccountCardEntity;
import com.revature.quizzard.models.flashcards.ReviewEntity;
import com.revature.quizzard.models.sets.SetEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;


@NoArgsConstructor
@AllArgsConstructor
@Table(name = "accounts")
@Entity
public class AccountEntity {

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

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public int getId() {return this.id;}

    public Set<AccountCardEntity> getAccountCardEntities() {return this.accountCardEntities;}

    public Set<RoleEntity> getRoles() {return this.roles;}

    public String getUsername() {return this.username;}

    public String getPassword() {return this.password;}

    public int getPoints() {return this.points;}

    public void setId(int id) {this.id = id; }

    public void setAccountCardEntities(Set<AccountCardEntity> accountCardEntities) {this.accountCardEntities = accountCardEntities; }

    public void setRoles(Set<RoleEntity> roles) {this.roles = roles; }

    public void setUsername(String username) {this.username = username; }

    public void setPassword(String password) {this.password = password; }

    public void setPoints(int points) {this.points = points; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountEntity that = (AccountEntity) o;
        return getId() == that.getId() && getPoints() == that.getPoints() && Objects.equals(getAccountCardEntities(), that.getAccountCardEntities()) && Objects.equals(getRoles(), that.getRoles()) && getUsername().equals(that.getUsername()) && getPassword().equals(that.getPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getAccountCardEntities(), getRoles(), getUsername(), getPassword(), getPoints());
    }

    @Override
    public String toString() {
        return "AccountEntity{" +
                "id=" + id +
                ", accountCardEntities=" + accountCardEntities +
                ", roles=" + roles +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", points=" + points +
                '}';
    }

    //scuffed implementation, I know
    public Set<AccountCardDTO> getAccountCards() {
        return this.accountCardEntities.stream()
                .map(AccountCardDTO::new)
                .collect(Collectors.toSet());
    }
}
