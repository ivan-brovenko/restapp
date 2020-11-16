package com.softdev.testtask.service.impl;

import com.softdev.testtask.dto.UserDto;
import com.softdev.testtask.entity.Actor;
import com.softdev.testtask.entity.TVShow;
import com.softdev.testtask.entity.User;
import com.softdev.testtask.exception.UserAlreadyExistsException;
import com.softdev.testtask.exception.UserDoesNotExistException;
import com.softdev.testtask.repository.ActorRepository;
import com.softdev.testtask.repository.UserRepository;
import com.softdev.testtask.service.UserService;
import com.softdev.testtask.service.tmdb.TmdbApi;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    private ActorRepository actorRepository;

    private TmdbApi tmdbApi;


    private ModelMapper modelMapper;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Autowired
    public void setActorRepository(ActorRepository actorRepository) {
        this.actorRepository = actorRepository;
    }

    @Autowired
    public void setTmdbApi(TmdbApi tmdbApi) {
        this.tmdbApi = tmdbApi;
    }

    @Override
    public User registerUser(UserDto userDto) {
        boolean isUserExists = userRepository
                .findByEmail(userDto.getEmail())
                .isPresent();

        if (isUserExists) {
            throw new UserAlreadyExistsException("User with email: " + userDto.getEmail() + " already exists");
        }

        User user = modelMapper.map(userDto, User.class);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        return userRepository.save(user);
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User addActors(String userEmail, List<Long> actorsIds) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() ->
                        new UserDoesNotExistException("User with email: " + userEmail + " does not exist"));

//        List


//        user.getActors().addAll(actors);
        return user;
    }

//    @Override
//    public User addFavouriteActor(Long actorId, String email, String password) {
////        Actor actor = tmdbApi.getActor(actorId);
//
////        if (!actorRepository.findById(actorId).isPresent()) {
////            actorRepository.save(actor);
////        }
////        User user = findUser(email, password);
////        user.getFavouriteActors().add(actor);
////        return user;
//        return null;
//    }

//    @Override
//    public User deleteFavouriteActorByName(String actorName, String email, String password) {
//        User user = findUser(email, password);
//        Actor actorToDelete = user.getFavouriteActors()
//                .stream().filter(actor -> actor.getName().equals(actorName))
//                .findFirst().orElse(null);
//        user.getFavouriteActors().remove(actorToDelete);
//        return user;
//    }
//
//    @Override
//    public User markWatchedShowForUser(Long tvShowId, String email, String password) {
//        TVShow tvShow = tmdbApi.getTVShowById(tvShowId);
//        User user = findUser(email, password);
//        user.getWatchedTVShows().add(tvShow);
//        return user;
//    }
//
//    @Override
//    public User markUnWatchedShowForUser(Long tvShowId, String email, String password) {
//        TVShow tvShow = tmdbApi.getTVShowById(tvShowId);
//        User user = findUser(email, password);
//        user.getWatchedTVShows().remove(tvShow);
//        return user;
//    }
//
//    @Override
//    public List<TVShow> getUnwatchedTVShowsWithFavoriteActors(String email, String password) {
//        User user = findUser(email, password);
//        return user.getFavouriteActors()
//                .stream()
//                .map(actor -> tmdbApi.getMovieCredits(actor.getId()))
//                .flatMap(List::stream)
//                .filter(tvShow -> !user.getWatchedTVShows().contains(tvShow))
//                .collect(Collectors.toList());
//    }
}