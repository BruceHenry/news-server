package com.bh.news.server.util;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public class HttpResponseHelper {

    public static ResponseEntity<?> response(Response response) {
        if (response.getCode() == 200) {
            return ok(response.getMessage());
        }
        else if (response.getCode() == 400) {
            return badRequest(response.getMessage());
        }
        else if (response.getCode() == 401) {
            return unauthorized(response.getMessage());
        }
        else if (response.getCode() == 404) {
            return notFound(response.getMessage());
        }
        else {
            return internalServerError(response.getMessage());
        }
    }

    public static ResponseEntity<?> ok(String obj) {
        HttpHeaders headers = new HttpHeaders();
        if (headers.getContentType() != MediaType.APPLICATION_JSON) {
            headers.setContentType(MediaType.APPLICATION_JSON);
        }
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(obj);
    }

    public static ResponseEntity<?> internalServerError(String obj) {
        HttpHeaders headers = new HttpHeaders();
        if (headers.getContentType() != MediaType.APPLICATION_JSON) {
            headers.setContentType(MediaType.APPLICATION_JSON);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).headers(headers).body(obj);
    }

    public static ResponseEntity<?> notFound(String obj) {
        HttpHeaders headers = new HttpHeaders();
        if (headers.getContentType() != MediaType.APPLICATION_JSON) {
            headers.setContentType(MediaType.APPLICATION_JSON);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).headers(headers).body(obj);
    }

    public static ResponseEntity<?> badRequest(String obj) {
        HttpHeaders headers = new HttpHeaders();
        if (headers.getContentType() != MediaType.APPLICATION_JSON) {
            headers.setContentType(MediaType.APPLICATION_JSON);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(headers).body(obj);
    }

    public static ResponseEntity<?> unauthorized(String obj) {
        HttpHeaders headers = new HttpHeaders();
        if (headers.getContentType() != MediaType.APPLICATION_JSON) {
            headers.setContentType(MediaType.APPLICATION_JSON);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).headers(headers).body(obj);
    }
}
