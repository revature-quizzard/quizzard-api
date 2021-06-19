package com.revature.quizzard.models.user;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.revature.quizzard.models.composites.AccountCardEntity;
import com.revature.quizzard.models.flashcards.ReviewEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


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

    @ManyToOne(targetEntity = ReviewEntity.class)
    @JoinColumn(name = "review_id")
    private ReviewEntity review;
    //private Set<ReviewEntity> reviews = new HashSet<>();

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

    public int getId() {return this.id;}

    @JsonIgnore
    public UserEntity getUser() {return this.user;}

    public ReviewEntity getReview() {return this.review;}

    public Set<AccountCardEntity> getAccountCardEntities() {return this.accountCardEntities;}

    public Set<RoleEntity> getRoles() {return this.roles;}

    public String getUsername() {return this.username;}

    public String getPassword() {return this.password;}

    public int getPoints() {return this.points;}

    public void setId(int id) {this.id = id; }

    @JsonIgnore
    public void setUser(UserEntity user) {this.user = user; }

    public void setReview(ReviewEntity review) {this.review = review; }

    public void setAccountCardEntities(Set<AccountCardEntity> accountCardEntities) {this.accountCardEntities = accountCardEntities; }

    public void setRoles(Set<RoleEntity> roles) {this.roles = roles; }

    public void setUsername(String username) {this.username = username; }

    public void setPassword(String password) {this.password = password; }

    public void setPoints(int points) {this.points = points; }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof AccountEntity)) return false;
        final AccountEntity other = (AccountEntity) o;
        if (!other.canEqual((Object) this)) return false;
        if (this.getId() != other.getId()) return false;
        final Object this$user = this.getUser();
        final Object other$user = other.getUser();
        if (this$user == null ? other$user != null : !this$user.equals(other$user)) return false;
        final Object this$review = this.getReview();
        final Object other$review = other.getReview();
        if (this$review == null ? other$review != null : !this$review.equals(other$review)) return false;
        final Object this$accountCardEntities = this.getAccountCardEntities();
        final Object other$accountCardEntities = other.getAccountCardEntities();
        if (this$accountCardEntities == null ? other$accountCardEntities != null : !this$accountCardEntities.equals(other$accountCardEntities))
            return false;
        final Object this$roles = this.getRoles();
        final Object other$roles = other.getRoles();
        if (this$roles == null ? other$roles != null : !this$roles.equals(other$roles)) return false;
        final Object this$username = this.getUsername();
        final Object other$username = other.getUsername();
        if (this$username == null ? other$username != null : !this$username.equals(other$username)) return false;
        final Object this$password = this.getPassword();
        final Object other$password = other.getPassword();
        if (this$password == null ? other$password != null : !this$password.equals(other$password)) return false;
        if (this.getPoints() != other.getPoints()) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {return other instanceof AccountEntity;}

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        result = result * PRIME + this.getId();
        final Object $user = this.getUser();
        result = result * PRIME + ($user == null ? 43 : $user.hashCode());
        final Object $review = this.getReview();
        result = result * PRIME + ($review == null ? 43 : $review.hashCode());
        final Object $accountCardEntities = this.getAccountCardEntities();
        result = result * PRIME + ($accountCardEntities == null ? 43 : $accountCardEntities.hashCode());
        final Object $roles = this.getRoles();
        result = result * PRIME + ($roles == null ? 43 : $roles.hashCode());
        final Object $username = this.getUsername();
        result = result * PRIME + ($username == null ? 43 : $username.hashCode());
        final Object $password = this.getPassword();
        result = result * PRIME + ($password == null ? 43 : $password.hashCode());
        result = result * PRIME + this.getPoints();
        return result;
    }

    public String toString() {return "AccountEntity(id=" + this.getId() + ", user=" + this.getUser() + ", review=" + this.getReview() + ", accountCardEntities=" + this.getAccountCardEntities() + ", roles=" + this.getRoles() + ", username=" + this.getUsername() + ", password=" + this.getPassword() + ", points=" + this.getPoints() + ")";}
}
