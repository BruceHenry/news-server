package com.bh.news.server.logic;

import java.io.File;
import java.io.IOException;

import com.bh.news.server.pojo.Article;
import com.bh.news.server.util.common.Response;
import com.bh.news.server.util.file.FileUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class BaseInteractor {

	protected final static String SAVE_PATH = "articles";
	protected final static String ARTICLE_JSON = "article.json";

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
		File jsonFile = new File(getArticleFolderName(article) + File.separator + ARTICLE_JSON);
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String articleJson = gson.toJson(article);
		FileUtil.saveFile(jsonFile, articleJson.getBytes());
	}

}
