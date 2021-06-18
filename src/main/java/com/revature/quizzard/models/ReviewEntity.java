package com.revature.quizzard.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Table(name = "reviews")
@NoArgsConstructor
@AllArgsConstructor
@Entity
public @Data class ReviewEntity {

    @Id
    @Column(name = "review_id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "concern")
    private String concern;

    @Column(name = "approved")
    private boolean approved;

}
