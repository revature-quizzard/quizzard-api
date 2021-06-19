package com.revature.quizzard.models.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class UserEntity {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(targetEntity = AccountEntity.class)
    private AccountEntity account;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    public int getId() {return this.id;}

    @JsonIgnore
    public AccountEntity getAccount() {return this.account;}

    public String getFirstName() {return this.firstName;}

    public String getLastName() {return this.lastName;}

    public String getEmail() {return this.email;}

    public void setId(int id) {this.id = id; }

    @JsonIgnore
    public void setAccount(AccountEntity account) {this.account = account; }

    public void setFirstName(String firstName) {this.firstName = firstName; }

    public void setLastName(String lastName) {this.lastName = lastName; }

    public void setEmail(String email) {this.email = email; }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof UserEntity)) return false;
        final UserEntity other = (UserEntity) o;
        if (!other.canEqual((Object) this)) return false;
        if (this.getId() != other.getId()) return false;
        final Object this$account = this.getAccount();
        final Object other$account = other.getAccount();
        if (this$account == null ? other$account != null : !this$account.equals(other$account)) return false;
        final Object this$firstName = this.getFirstName();
        final Object other$firstName = other.getFirstName();
        if (this$firstName == null ? other$firstName != null : !this$firstName.equals(other$firstName)) return false;
        final Object this$lastName = this.getLastName();
        final Object other$lastName = other.getLastName();
        if (this$lastName == null ? other$lastName != null : !this$lastName.equals(other$lastName)) return false;
        final Object this$email = this.getEmail();
        final Object other$email = other.getEmail();
        if (this$email == null ? other$email != null : !this$email.equals(other$email)) return false;
        return true;
    }

    protected boolean canEqual(final Object other) {return other instanceof UserEntity;}

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        result = result * PRIME + this.getId();
        final Object $account = this.getAccount();
        result = result * PRIME + ($account == null ? 43 : $account.hashCode());
        final Object $firstName = this.getFirstName();
        result = result * PRIME + ($firstName == null ? 43 : $firstName.hashCode());
        final Object $lastName = this.getLastName();
        result = result * PRIME + ($lastName == null ? 43 : $lastName.hashCode());
        final Object $email = this.getEmail();
        result = result * PRIME + ($email == null ? 43 : $email.hashCode());
        return result;
    }

    public String toString() {return "UserEntity(id=" + this.getId() + ", account=" + this.getAccount() + ", firstName=" + this.getFirstName() + ", lastName=" + this.getLastName() + ", email=" + this.getEmail() + ")";}
}
