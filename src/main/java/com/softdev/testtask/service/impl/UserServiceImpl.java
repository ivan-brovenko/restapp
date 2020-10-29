package com.softdev.testtask.service.impl;

import com.softdev.testtask.entity.Actor;
import com.softdev.testtask.entity.TVShow;
import com.softdev.testtask.entity.User;
import com.softdev.testtask.repository.ActorRepository;
import com.softdev.testtask.repository.UserRepository;
import com.softdev.testtask.service.UserService;
import com.softdev.testtask.service.tmdb.TmdbApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    private ActorRepository actorRepository;

    private TmdbApi tmdbApi;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public void setActorRepository(ActorRepository actorRepository) {
        this.actorRepository = actorRepository;
    }

    @Autowired
    public void setTmdbApi(TmdbApi tmdbApi) {
        this.tmdbApi = tmdbApi;
    }

    @Override
    public User registerUser(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (Objects.nonNull(user)) {
            return null;
        }
        user = User.builder().email(email).password(passwordEncoder.encode(password)).build();
        return userRepository.save(user);
    }

    @Override
    public User findUser(String email, String password) {
        User foundUser = userRepository.findByEmail(email);
        if (Objects.nonNull(foundUser)) {
            if (passwordEncoder.matches(password, foundUser.getPassword())) {
                return foundUser;
            }
        }
        return null;
    }

    @Override
    public User addFavouriteActor(Long actorId, String email, String password) {
        Actor actor = tmdbApi.getActor(actorId);

        if (!actorRepository.findById(actorId).isPresent()) {
            actorRepository.save(actor);
        }
        User user = findUser(email, password);
        user.getFavouriteActors().add(actor);
        return user;
    }

    @Override
    public User deleteFavouriteActorByName(String actorName, String email, String password) {
        User user = findUser(email, password);
        Actor actorToDelete = user.getFavouriteActors()
                .stream().filter(actor -> actor.getName().equals(actorName))
                .findFirst().orElse(null);
        user.getFavouriteActors().remove(actorToDelete);
        return user;
    }

    @Override
    public User markWatchedShowForUser(Long tvShowId, String email, String password) {
        TVShow tvShow = tmdbApi.getTVShowById(tvShowId);
        User user = findUser(email, password);
        user.getWatchedTVShows().add(tvShow);
        return user;
    }

    @Override
    public User markUnWatchedShowForUser(Long tvShowId, String email, String password) {
        TVShow tvShow = tmdbApi.getTVShowById(tvShowId);
        User user = findUser(email, password);
        user.getWatchedTVShows().remove(tvShow);
        return user;
    }

    @Override
    public List<TVShow> getUnwatchedTVShowsWithFavoriteActors(String email, String password) {
        User user = findUser(email, password);
        return user.getFavouriteActors()
                .stream()
                .map(actor -> tmdbApi.getMovieCredits(actor.getId()))
                .flatMap(List::stream)
                .filter(tvShow -> !user.getWatchedTVShows().contains(tvShow))
                .collect(Collectors.toList());
    }
}