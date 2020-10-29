package com.softdev.testtask.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.softdev.testtask.dto.UserDto;
import com.softdev.testtask.entity.TVShow;
import com.softdev.testtask.entity.User;
import com.softdev.testtask.service.UserService;
import com.softdev.testtask.service.tmdb.TmdbApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserControllerV1 {

    @Autowired
    private UserService userService;

    @Autowired
    private TmdbApi tmdbApi;

    @Autowired
    private ObjectMapper objectMapper;

    @RequestMapping(value = "/register", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity registerUser(@RequestBody @Valid UserDto userDto) {
        User registeredUser = userService.registerUser(userDto.getEmail(),userDto.getPassword());
        return ResponseEntity.status(HttpStatus.OK).body(registeredUser);
    }

    @RequestMapping(value = "/actors", method = POST)
    public ResponseEntity addFavouriteActorByName(@RequestParam String email,
                                                  @RequestParam String password,
                                                  @RequestParam Long actorId) {

        if (userService.addFavouriteActor(actorId, email, password) != null) {
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @RequestMapping(value = "/actors/delete", method = POST)
    public ResponseEntity deleteFavouriteActorByName(@RequestParam String email,
                                                     @RequestParam String password,
                                                     @RequestParam String actorName) {

        if (userService.deleteFavouriteActorByName(actorName, email, password) != null) {
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @RequestMapping(value = "/tvs/watched", method = POST)
    public ResponseEntity markWatchedShowForUser(@RequestParam String email,
                                                     @RequestParam String password,
                                                     @RequestParam Long tvShowId) {

        if (userService.markWatchedShowForUser(tvShowId, email, password) != null) {
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @RequestMapping(value = "/tvs/unwatched", method = POST)
    public ResponseEntity markUnWatchedShowForUser(@RequestParam String email,
                                                 @RequestParam String password,
                                                 @RequestParam Long tvShowId) {

        if (userService.markUnWatchedShowForUser(tvShowId, email, password) != null) {
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @RequestMapping(value = "tvs/unwatched/actors", method = GET)
    public ResponseEntity markUnWatchedShowForUser(@RequestParam String email,
                                                   @RequestParam String password) {

        if (userService.findUser(email, password) == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        List<TVShow> tvShows = userService.
                getUnwatchedTVShowsWithFavoriteActors(email, password);

        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(tvShows);
    }
}
