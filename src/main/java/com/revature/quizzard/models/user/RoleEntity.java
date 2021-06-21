package com.revature.quizzard.models.user;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
    @Column(name = "role_id", unique = true, nullable = false)
    private int Id;

    @Column(name = "name")
    private String name;

//    @ManyToMany(mappedBy = "roles")
//    private Set<AccountEntity> accountEntities = new HashSet<>();


    // TODO need to determine if we can make use of this class without an enum , for a simplistic approach to Spring Security
}
