package com.bh.news.server.util.http;

import com.bh.news.server.util.common.Response;
import com.google.gson.Gson;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

public class HttpResponseHelper {

    public static ResponseEntity<?> respond(Response response) {
        if (response.getCode() == 200) {
            return ok(new Gson().toJson(response));
        }
        else if (response.getCode() == 400) {
            return badRequest(new Gson().toJson(response));
        }
        else if (response.getCode() == 401) {
            return unauthorized(new Gson().toJson(response));
        }
        else if (response.getCode() == 404) {
            return notFound(new Gson().toJson(response));
        }
        else {
            return internalServerError(new Gson().toJson(response));
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
