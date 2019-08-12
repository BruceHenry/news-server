package com.bh.news.server;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.bh.news.server.logic.ServerInteractor;
import com.bh.news.server.pojo.Article;
import com.bh.news.server.util.HttpResponseHelper;
import com.bh.news.server.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@RestController
public class ServerController {

    @Autowired
    ServerInteractor serverInteractor;

    @RequestMapping(value = "/**", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> getStatus(HttpServletRequest request) {
        System.out.println("It is working from:" + request.getRequestURI());
        return HttpResponseHelper.ok("It is working");
    }

    @RequestMapping(value = "/article", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> getArticle(@RequestBody Article article) {
        //todo
        //update folder
        return HttpResponseHelper.ok("It is working");
    }

    @RequestMapping(value = "/article", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> updateArticle(@RequestBody Article article) {
        //todo
        //update folder
        return HttpResponseHelper.ok("It is working");
    }

    @RequestMapping(value = "/article", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<?> createArticle(@RequestBody Article article) {
        //todo
        Response response = serverInteractor.createArticle(article);
        return HttpResponseHelper.ok("It is working");
    }

    @RequestMapping(value = "/article", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<?> deleteArticle(@RequestBody Article article) {
        //todo
        //delete folder
        return HttpResponseHelper.ok("It is working");
    }

    @RequestMapping(value = "/file/{article-id}/{file-name}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> getFile(@PathVariable("article-id") String articleId, @PathVariable("file-name") String fileName) {
        //todo
        //get file
        return HttpResponseHelper.ok("It is working");
    }

    @RequestMapping(value = "/file/{article-id}/{file-name}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> updateFile(@PathVariable("article-id") String articleId, @PathVariable("file-name") String fileName) {
        //todo
        //update file
        return HttpResponseHelper.ok("It is working");
    }

    @RequestMapping(value = "/file/{article-id}/{file-name}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<?> createFile(@PathVariable("article-id") String articleId, @PathVariable("file-name") String fileName) {
        //todo
        //create file
        return HttpResponseHelper.ok("It is working");
    }

    @RequestMapping(value = "/file/{article-id}/{file-name}", method = RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<?> deleteFile(@PathVariable("article-id") String articleId, @PathVariable("file-name") String fileName) {
        //todo
        //create file
        return HttpResponseHelper.ok("It is working");
    }


    @RequestMapping(value = "/upload/", method = RequestMethod.POST)
    @ResponseBody
    public String upload(@RequestParam("file") MultipartFile multipartFile) {
        return serverInteractor.upload(multipartFile);
    }

    @RequestMapping(value = "/upload/batch", method = RequestMethod.POST)
    public @ResponseBody
    String batchUpload(HttpServletRequest request) {
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



}
