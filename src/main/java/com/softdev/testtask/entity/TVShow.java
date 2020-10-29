package com.softdev.testtask.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TVShow {

    @Id
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(name = "tvshow_user",
            joinColumns = {@JoinColumn(name = "tvshow_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")})
    private Set<User> users;
}
