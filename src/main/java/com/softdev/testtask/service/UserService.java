package com.softdev.testtask.service;

import com.softdev.testtask.entity.TVShow;
import com.softdev.testtask.entity.User;

import java.util.List;

public interface UserService {
    User registerUser(String email, String password);

    User findUser(String email, String password);

    User addFavouriteActor(Long actorId, String email, String password);

    User deleteFavouriteActorByName(String actorName, String email, String password);

    User markWatchedShowForUser(Long tvShowId, String email, String password);

    User markUnWatchedShowForUser(Long tvShowId, String email, String password);

    List<TVShow> getUnwatchedTVShowsWithFavoriteActors(String email, String password);
}

