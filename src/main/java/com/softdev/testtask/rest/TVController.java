package com.softdev.testtask.rest;

import com.softdev.testtask.service.tmdb.TmdbApi;
import com.softdev.testtask.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@RequestMapping("/tv")
public class TVController {
    @Autowired
    private UserService userService;

    @Autowired
    private TmdbApi tmdbApi;

//    @RequestMapping(value = "/popular", method = POST)
//    public ResponseEntity popular(@RequestParam String email,
//                                  @RequestParam String password) {
//        if (userService.findUser(email, password) == null) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
//        }
//
//        String popularMovies = tmdbApi.popularTVShows();
//
//        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(popularMovies);
//    }
}
