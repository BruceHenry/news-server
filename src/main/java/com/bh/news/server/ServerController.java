package com.bh.news.server;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.bh.news.server.logic.ServerInteractor;
import com.bh.news.server.pojo.Article;
import com.bh.news.server.util.http.HttpResponseHelper;
import com.bh.news.server.util.common.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

@RestController
public class ServerController {

    @Autowired
    ServerInteractor serverInteractor;

    @RequestMapping(value = "/**", method = RequestMethod.GET)
    public ResponseEntity<?> getStatus(HttpServletRequest request) {
        System.out.println("It is working from:" + request.getRequestURI());
        return HttpResponseHelper.ok("It is working");
    }

    @RequestMapping(value = "/article", method = RequestMethod.GET)
    public ResponseEntity<?> getArticle(@RequestBody Article article) {
        //todo
        //update folder
        return HttpResponseHelper.ok("It is working");
    }

    @RequestMapping(value = "/article", method = RequestMethod.POST)
    public ResponseEntity<?> updateArticle(@RequestBody Article article) {
        //todo
        //update folder
        return HttpResponseHelper.ok("It is working");
    }

    @RequestMapping(value = "/article", method = RequestMethod.PUT)
    public ResponseEntity<?> createArticle(@RequestBody Article article) {
        //todo
        Response response = serverInteractor.createArticle(article);
        return HttpResponseHelper.ok("It is working");
    }

    @RequestMapping(value = "/article", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteArticle(@RequestBody Article article) {
        //todo
        //delete folder
        return HttpResponseHelper.ok("It is working");
    }

    @RequestMapping(value = "/file/{article-id}/{file-name}", method = RequestMethod.GET)
    public ResponseEntity<?> getFile(@PathVariable("article-id") String articleId, @PathVariable("file-name") String fileName) {
        //todo
        //get file
        return HttpResponseHelper.ok("It is working");
    }

    @RequestMapping(value = "/file/{article-id}/{file-name}", method = RequestMethod.POST)
    public ResponseEntity<?> updateFile(@PathVariable("article-id") String articleId, @PathVariable("file-name") String fileName) {
        //todo
        //update file
        return HttpResponseHelper.ok("It is working");
    }

    @RequestMapping(value = "/file/{article-id}/{file-name}", method = RequestMethod.PUT)
    public ResponseEntity<?> createFile(@PathVariable("article-id") String articleId, @PathVariable("file-name") String fileName) {
        //todo
        //create file
        return HttpResponseHelper.ok("It is working");
    }

    @RequestMapping(value = "/file/{article-id}/{file-name}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteFile(@PathVariable("article-id") String articleId, @PathVariable("file-name") String fileName) {
        //todo
        //create file
        return HttpResponseHelper.ok("It is working");
    }


    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public String upload(@RequestParam("file") MultipartFile multipartFile) {
        return serverInteractor.upload(multipartFile);
    }

    @RequestMapping(value = "/upload/batch", method = RequestMethod.POST)
    public String batchUpload(HttpServletRequest request) {
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
