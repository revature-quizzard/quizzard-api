package com.revature.quizzard.models.user;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "roles")
@ToString
public @Data class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private int Id;

    @Column(name = "name")
    private String name;

//    @ManyToMany(mappedBy = "roles")
//    @JsonIgnoreProperties(value = "roles")
//    @JsonBackReference
//    private Set<AccountEntity> accountEntities = new HashSet<>();


    // TODO need to determine if we can make use of this class without an enum , for a simplistic approach to Spring Security
}
