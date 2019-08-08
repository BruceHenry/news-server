package com.bh.news.server;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ServerController {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> getStatus() {
		System.out.println("It is working");
		return ok("It is working");
	}

	private ResponseEntity<?> ok(String obj) {
		HttpHeaders headers = new HttpHeaders();
		if (headers.getContentType() != MediaType.APPLICATION_JSON) {
			headers.setContentType(MediaType.APPLICATION_JSON);
		}
		return ResponseEntity.status(HttpStatus.OK).headers(headers).body(obj);
	}

	private ResponseEntity<?> internalServerError(String obj) {
		HttpHeaders headers = new HttpHeaders();
		if (headers.getContentType() != MediaType.APPLICATION_JSON) {
			headers.setContentType(MediaType.APPLICATION_JSON);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).headers(headers).body(obj);
	}

	private ResponseEntity<?> notFound(String obj) {
		HttpHeaders headers = new HttpHeaders();
		if (headers.getContentType() != MediaType.APPLICATION_JSON) {
			headers.setContentType(MediaType.APPLICATION_JSON);
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND).headers(headers).body(obj);
	}

	private ResponseEntity<?> badRequest(String obj) {
		HttpHeaders headers = new HttpHeaders();
		if (headers.getContentType() != MediaType.APPLICATION_JSON) {
			headers.setContentType(MediaType.APPLICATION_JSON);
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).headers(headers).body(obj);
	}
	
	private ResponseEntity<?> unauthorized(String obj) {
		HttpHeaders headers = new HttpHeaders();
		if (headers.getContentType() != MediaType.APPLICATION_JSON) {
			headers.setContentType(MediaType.APPLICATION_JSON);
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).headers(headers).body(obj);
	}

}
