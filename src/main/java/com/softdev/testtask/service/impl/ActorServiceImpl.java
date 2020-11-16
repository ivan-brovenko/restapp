package com.softdev.testtask.service.impl;

import com.softdev.testtask.entity.Actor;
import com.softdev.testtask.repository.ActorRepository;
import com.softdev.testtask.service.ActorService;
import com.softdev.testtask.service.tmdb.TmdbApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActorServiceImpl implements ActorService {
    private ActorRepository actorRepository;
    private TmdbApi tmdbApi;

    @Autowired
    public void setActorRepository(ActorRepository actorRepository) {
        this.actorRepository = actorRepository;
    }

    @Override
    public Actor addActor(Actor actor) {
        return null;
    }
}
