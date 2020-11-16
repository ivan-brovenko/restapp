package com.softdev.testtask.service.tmdb;

import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

public class TmdbErrorHandler implements ResponseErrorHandler {
    @Override
    public boolean hasError(ClientHttpResponse clientHttpResponse) throws IOException {
        return new DefaultResponseErrorHandler().hasError(clientHttpResponse);
    }

    @Override
    public void handleError(ClientHttpResponse clientHttpResponse) throws IOException {
        
    }
}
