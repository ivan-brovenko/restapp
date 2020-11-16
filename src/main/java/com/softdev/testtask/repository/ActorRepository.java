package com.softdev.testtask.repository;

import com.softdev.testtask.entity.Actor;
import org.springframework.data.repository.CrudRepository;

public interface ActorRepository extends CrudRepository<Actor, Long> {
    Actor save(Actor actor);
}
