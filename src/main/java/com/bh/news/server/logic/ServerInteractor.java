package com.bh.news.server.logic;

import com.bh.news.server.pojo.Article;
import com.bh.news.server.util.common.Response;
import com.bh.news.server.util.file.FileHelper;
import com.bh.news.server.util.file.FileHelperIo;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;

@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class ServerInteractor {
    @Value("${storage.directory}")
    String SAVE_PATH = null;

    @Autowired
    private Environment env;

    private FileHelper fileHelper = new FileHelperIo();

    public Response uploadFile(MultipartFile multipartFile) {
        if (!multipartFile.isEmpty()) {
            return new Response(400, "upload failure: file is empty");
        }
        if (multipartFile.getOriginalFilename() == null) {
            return new Response(400, "upload failure: file name is null");
        }

        File file = new File(multipartFile.getOriginalFilename());

        try {
            fileHelper.saveFile(file, multipartFile.getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return new Response(500, "upload meets FileNotFoundException: " + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            return new Response(500, "upload meets IOException: " + e.getMessage());
        }
        return new Response(200, "success");
    }

    public Response createArticle(Article article) {
        article.generateAttribute();
        String folderName = article.getTitle() + article.getId();
        File file = new File(SAVE_PATH + File.separator + folderName);
        boolean result = fileHelper.createFolder(file);
        if (result) {
            return new Response(200, new Gson().toJson(article));
        } else {
            return new Response(500, "failure");
        }
    }

    public Response deleteArticle(Article article) {
        File file = new File(SAVE_PATH + File.separator + article.getTitle() + article.getId());
        boolean result = FileSystemUtils.deleteRecursively(file);
        if (result) {
            return new Response(200, "success");
        } else {
            return new Response(500, "failure");
        }
    }
}
