package com.bh.news.server;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@RestController
public class ServerController {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> getStatus() {
		System.out.println("It is working");
		return ok("It is working");
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	@ResponseBody
	public String upload(@RequestParam("file") MultipartFile multipartFile) {
		if (!multipartFile.isEmpty()) {
			try {
				File file = new File(multipartFile.getOriginalFilename());
				BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file));
				out.write(multipartFile.getBytes());
				out.flush();
				out.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				return "upload meets FileNotFoundException: " + e.getMessage();
			} catch (IOException e) {
				e.printStackTrace();
				return "upload meets IOException: " + e.getMessage();
			}
			return "upload success";
		} else {
			return "upload failure: file is empty";
		}
	}
	
	@RequestMapping(value = "/upload/batch", method = RequestMethod.POST)
    public @ResponseBody String batchUpload(HttpServletRequest request) {
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
        MultipartFile file = null;
        BufferedOutputStream stream = null;
        for (int i = 0; i < files.size(); ++i) {
            file = files.get(i);
            if (!file.isEmpty()) {
                try {
                    byte[] bytes = file.getBytes();
                    stream = new BufferedOutputStream(new FileOutputStream(new File(file.getOriginalFilename())));
                    stream.write(bytes);
                    stream.close();
                } catch (Exception e) {
                    stream = null;
                    return "You failed to upload " + i + " => " + e.getMessage();
                }
            } else {
                return "You failed to upload " + i + " because the file was empty.";
            }
        }
        return "upload successful";
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
