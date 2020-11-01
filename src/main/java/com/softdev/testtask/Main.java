package com.softdev.testtask;

import org.springframework.web.util.UriComponentsBuilder;

public class Main {
    public static void main(String[] args) {
       Runner runner = new Runner();
       runner.run();
    }
}

class Runner {
    private static final String BASE_URL = "https://api.baseurl.org/";
    private static final String API_KEY = "someapikey666";
    private static final String LANGUAGE = "en";

    public void run(){
        System.out.println(getUsersUrl());
        System.out.println(getUserByIdUrl("666"));
        System.out.println(getUserOrdersUrl("777"));
    }

    /**
     * https://api.baseurl.org/users?api_key=someapikey666&language=en
     */
    public String getUsersUrl(){
        return getUrl("users");
    }

    /**
     * https://api.baseurl.org/users/{userId}?api_key=someapikey666&language=en
     */
    public String getUserByIdUrl(String userId){
        return getUrl("users",userId);
    }

    /**
     * https://api.baseurl.org/user/{userId}/orders?api_key=someapikey666&language=en
     */
    public String getUserOrdersUrl(String userId){
        return getUrl("users",userId, "orders");
    }

    public String getUrl(String ... path){
        return UriComponentsBuilder.fromHttpUrl(BASE_URL)
                .pathSegment(path)
                .queryParam("api_key", API_KEY)
                .queryParam("language", LANGUAGE)
                .build()
                .toUriString();
    }
}
