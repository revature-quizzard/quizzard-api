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

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    public int getId() {return this.id;}


    public String getFirstName() {return this.firstName;}

    public String getLastName() {return this.lastName;}

    public String getEmail() {return this.email;}

    public void setId(int id) {this.id = id; }

    public void setFirstName(String firstName) {this.firstName = firstName; }

    public void setLastName(String lastName) {this.lastName = lastName; }

    public void setEmail(String email) {this.email = email; }

}
