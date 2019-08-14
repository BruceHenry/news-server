package com.bh.news.server.logic;

import com.bh.news.server.pojo.Article;
import com.bh.news.server.util.common.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class ServerInteractor {

    @Autowired
    private Environment env;

    public String upload(MultipartFile multipartFile) {
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

    public Response createArticle(Article article) {
        return null;
    }
}
