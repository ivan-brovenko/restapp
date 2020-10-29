package com.softdev.testtask.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique=true)
    private String email;

    private String password;

    @ManyToMany(mappedBy = "users")
    private Set<Actor> favouriteActors;

    @ManyToMany(mappedBy = "users")
    private Set<TVShow> watchedTVShows;
}