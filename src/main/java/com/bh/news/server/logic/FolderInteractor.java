package com.bh.news.server.logic;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;

import com.bh.news.server.pojo.Article;
import com.bh.news.server.util.common.Response;
import com.bh.news.server.util.file.FileUtil;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class FolderInteractor extends BaseInteractor {

	public Response getArticle(Article article) {
		Response validationResponse = validateArticle(article);
		if (validationResponse != null) {
			return validationResponse;
		}

		Path path = Paths.get(getArticleFolderName(article));
		if (!Files.exists(path)) {
			return new Response(400, "unable to find file");
		}

		String articleJson = null;
		try {
			articleJson = FileUtil.readFileAsString(getArticleFolderName(article) + File.separator + ARTICLE_JSON);
			Gson gson = new Gson();
			Article parsedArticle = gson.fromJson(articleJson, Article.class);
			articleJson = gson.toJson(parsedArticle);
		} catch (IOException e) {
			return new Response(500, "unable to read article.json file: " + e.getMessage());
		} catch (JsonSyntaxException e) {
			return new Response(500, "article.json file is corrupted or invalid: " + e.getMessage());
		}

		if (articleJson == null) {
			return new Response(500, "Json is null");
		}
		return new Response(200, articleJson);
	}

	public Response createArticle(Article article) {
		if (article.getTitle() == null || article.getTitle().isEmpty()) {
			return new Response(400, "failure: article title is missing");
		}

		article.generateAttribute();

		Path path = Paths.get(getArticleFolderName(article));

		String articleJson = new Gson().toJson(article);
		try {
			FileUtil.createFolder(path);
			saveArticleToJsonFile(article);
		} catch (IOException e) {
			return new Response(500, "failure: " + e.getMessage());
		}

		return new Response(200, articleJson);
	}

	public Response deleteArticle(String articleFolde) {

		File file = new File(SAVE_PATH + File.separator + articleFolde);
		if (!file.exists()) {
			return new Response(400, "unable to find file");
		}

		boolean result = FileUtil.deleteFolderRecursively(file);
		if (result) {
			return new Response(200, "success");
		} else {
			return new Response(500, "failure");
		}
	}

	public Response updateArticle(Article article, String newTitle) {
		Response validationResponse = validateArticle(article);
		if (validationResponse != null) {
			return validationResponse;
		}

		File oldFile = new File(getArticleFolderName(article));
		if (!oldFile.exists()) {
			return new Response(400, "unable to find file");
		}

		if (newTitle != null && !newTitle.equals(article.getTitle())) {
			String newFolderName = SAVE_PATH + File.separator + newTitle + article.getId();
			File newFile = new File(newFolderName);
			try {
				FileSystemUtils.copyRecursively(oldFile, newFile);
				FileSystemUtils.deleteRecursively(oldFile);
				String jsonStr = FileUtil.readFileAsString(newFolderName + File.separator + ARTICLE_JSON);
				Article fullArticle = new Gson().fromJson(jsonStr, Article.class);
				fullArticle.setTitle(newTitle);
				saveArticleToJsonFile(fullArticle);
			} catch (IOException e) {
				return new Response(500, "failure: " + e.getMessage());
			}
		}

		return new Response(200, "success");
	}

	public Response getArticleList() {
		List<Article> articleList = new LinkedList<>();
		Gson gson = new Gson();
		Path rootPath = Paths.get(SAVE_PATH);

		if (!Files.exists(rootPath)) {
			return new Response(200, gson.toJson(articleList));
		}

		try (Stream<Path> walk = Files.walk(rootPath)) {

			List<String> result = walk.filter(Files::isDirectory).map(Path::toString).collect(Collectors.toList());

			for (String folderPath : result) {
				Path path = Paths.get(folderPath);
				if (path.equals(rootPath)) {
					continue;
				}
				String jsonString = FileUtil.readFileAsString(folderPath + File.separator + ARTICLE_JSON);
				Article article = gson.fromJson(jsonString, Article.class);
				articleList.add(article);
			}
		} catch (IOException e) {
			e.printStackTrace();
			return new Response(500, "failed to get article json list: " + e.getMessage());
		}

		return new Response(200, gson.toJson(articleList));
	}

	public Response getArticleFiles(String articleFolder) {

		Path rootPath = Paths.get(SAVE_PATH, articleFolder);
		if (!Files.exists(rootPath)) {
			return new Response(404, "unable to find" + articleFolder);
		}

		List<String> fileList = new LinkedList<>();
		try (Stream<Path> walk = Files.walk(rootPath)) {
			fileList = walk.filter(Files::isRegularFile).filter(p -> !p.equals(rootPath)).map(Path::getFileName)
					.map(Path::toString).collect(Collectors.toList());
		} catch (IOException e) {
			e.printStackTrace();
			return new Response(500, "failed to get file list from " + articleFolder + ": " + e.getMessage());
		}
		
		return new Response(200, new Gson().toJson(fileList));
	}
}
