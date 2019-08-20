package com.bh.news.server.logic;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;

import com.bh.news.server.EnvironmentMap;
import com.bh.news.server.pojo.Article;
import com.bh.news.server.util.common.Response;
import com.bh.news.server.util.file.FileUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public abstract class BaseInteractor {
	private final static String FILE_PATH = "file.path";
	protected final static String ARTICLE_JSON = "article.json";

	protected final static String SAVE_PATH = EnvironmentMap.getInstance().getEnvMap().get(FILE_PATH);

	protected static Response validateArticle(Article article) {
		if (article.getTitle() == null) {
			return new Response(400, "failure: article title is missing");
		}
		if (article.getId() == null) {
			return new Response(400, "failure: article id is missing");
		}
		return null;
	}

	protected static String getArticleFolderName(Article article) {
		return SAVE_PATH + File.separator + article.getTitle() + article.getId();
	}

	protected static String getArticleFolderName(String articleFolder) {
		return SAVE_PATH + File.separator + articleFolder;
	}

	protected static void saveArticleToJsonFile(Article article) throws IOException {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String articleJson = gson.toJson(article);
		Path path = Paths.get(getArticleFolderName(article), ARTICLE_JSON);
		FileUtil.saveFile(path, articleJson.getBytes());
	}

}
