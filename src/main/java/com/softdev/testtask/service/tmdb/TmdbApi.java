package com.softdev.testtask.service.tmdb;

import com.softdev.testtask.entity.Actor;
import com.softdev.testtask.entity.TVShow;

import java.util.List;

public interface TmdbApi {
    String popularTVShows();

    Actor getActor(Long actorId);

    TVShow getTVShowById(Long tvShowId);

    List<TVShow> getMovieCredits(Long actorId);
}
