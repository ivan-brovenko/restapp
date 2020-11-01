package com.softdev.testtask.service.tmdb.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.softdev.testtask.entity.Actor;
import com.softdev.testtask.entity.TVShow;
import com.softdev.testtask.exception.TmdbException;
import com.softdev.testtask.service.tmdb.TmdbApi;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
public class TmdbApiImpl implements TmdbApi {
    @Value("${tmdb.apikey}")
    private String tmdbApiKey;
    @Value("${tmdb.language}")
    private String tmdbLanguage;
    @Value("${tmdb.api.base.url}")
    private String tmdbApiBaseUrl;

    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String popularTVShows() throws IllegalArgumentException {
        String url = getTmdbUrl("/tv/popular");

        ResponseEntity<String> response
                = restTemplate.getForEntity(url, String.class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            return null;
        }

        return response.getBody();
    }

    public Actor findActorById(Long actorId) {
        String url = getTmdbUrl("person", actorId.toString());
        ResponseEntity<Actor> responseEntity = restTemplate
                .exchange(url, HttpMethod.GET, null, new ParameterizedTypeReference<Actor>() {});
        return responseEntity.getBody();
    }

//    @Override
//    public Actor getActor(Long actorId) {
//        String url = getTmdbUrl("/person/" + actorId);
//        ResponseEntity<Actor> response = restTemplate.getForEntity(url, Actor.class);
//
//        if (!response.getStatusCode().is2xxSuccessful()) {
//            return null;
//        }
//
//        return response.getBody();
//    }

    @Override
    public TVShow getTVShowById(Long tvShowId) {
        String url = getTmdbUrl("/tv/" + tvShowId);
        ResponseEntity<TVShow> response = restTemplate.getForEntity(url, TVShow.class);

        if (!response.getStatusCode().is2xxSuccessful()) {
            return null;
        }

        return response.getBody();
    }

    @Override
    public List<TVShow> getMovieCredits(Long actorId) {
        try {
            String url = getTmdbUrl("/person/" + actorId + "/movie_credits");
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            JsonNode jsonNode = objectMapper.readTree(response.getBody());
            String resultsList = jsonNode.get("cast").asText();
            return new ArrayList<>(objectMapper
                    .readValue(resultsList, new TypeReference<List<TVShow>>() {
                    }));
        } catch (JsonProcessingException e) {
            log.error("Can't parse movie credits", e);
            throw new TmdbException("Can't parse movie credits", e);
        }

    }

    private String getTmdbUrl(String tmdbItem) {
        try {
            StringBuilder builder = new StringBuilder(tmdbApiBaseUrl);
            builder.append(tmdbItem);
            URIBuilder uriBuilder = new URIBuilder(builder.toString());
            uriBuilder.addParameter("language", tmdbLanguage);
            uriBuilder.addParameter("api_key", tmdbApiKey);
            return uriBuilder.build().toString();
        } catch (URISyntaxException e) {
            log.error("can't build tmdb url {}", tmdbItem, e);
            throw new TmdbException("Can't get tmdb url", e);
        }

    }

    private String getTmdbUrl(String... path) {
        return UriComponentsBuilder
                .fromUriString(tmdbApiBaseUrl)
                .pathSegment(path)
                .queryParam("language", tmdbLanguage)
                .queryParam("api_key", tmdbApiKey)
                .build().toUriString();
    }
}
