package com.bh.news.server.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.bh.news.server.logic.FileInteractor;
import com.bh.news.server.logic.FolderInteractor;
import com.bh.news.server.pojo.Article;
import com.bh.news.server.util.common.Response;
import com.bh.news.server.util.http.HttpResponseHelper;

@RestController
public class ServerController {

	@Autowired
	FileInteractor fileInteractor;

	@Autowired
	FolderInteractor folderInteractor;


	@ExceptionHandler(value=Exception.class)
	public ResponseEntity<?> handleException(HttpServletRequest req, Exception e) {
		//todo
		return HttpResponseHelper.internalServerError(e.getMessage());
	}

	@RequestMapping(value = "/articles", method = RequestMethod.GET)
	public ResponseEntity<?> getArticleList() {
		Response response = folderInteractor.getArticleList();
		return HttpResponseHelper.ok(response.getMessage());
	}

	@RequestMapping(value = "/article", method = RequestMethod.GET)
	public ResponseEntity<?> getArticle(@RequestBody Article article) {
		Response response = folderInteractor.getArticle(article);
		return HttpResponseHelper.ok(response.getMessage());
	}

	@RequestMapping(value = "/article", method = RequestMethod.POST)
	public ResponseEntity<?> updateArticle(@RequestBody Article article,
			@RequestHeader(value = "Title", required = false) String newTitle) {
		Response response = folderInteractor.updateArticle(article, newTitle);
		return HttpResponseHelper.respondRest(response);
	}

	@RequestMapping(value = "/article", method = RequestMethod.PUT)
	public ResponseEntity<?> createArticle(@RequestBody Article article) {
		Response response = folderInteractor.createArticle(article);
		return HttpResponseHelper.respondRest(response);
	}

	@RequestMapping(value = "/article/{article-folder}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteArticle(@PathVariable("article-folder") String articleFolde) {
		Response response = folderInteractor.deleteArticle(articleFolde);
		return HttpResponseHelper.respondRest(response);
	}

	@RequestMapping(value = "/file/{article-folder}/{file-name}", method = RequestMethod.GET)
	public ResponseEntity<?> getFile(@PathVariable("article-folder") String articleFolder,
			@PathVariable("file-name") String fileName) {
		ByteArrayResource byteArrayResource = null;
		try {
			byteArrayResource = fileInteractor.getFile(articleFolder, fileName);
		} catch (FileNotFoundException e) {
			return HttpResponseHelper.respondRest(new Response(400, "file not found"));

		} catch (IOException e) {
			return HttpResponseHelper.respondRest(new Response(500, "unable to get file"));
		}

		HttpHeaders header = new HttpHeaders();
		header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
		header.add("Cache-Control", "no-cache, no-store, must-revalidate");
		header.add("Pragma", "no-cache");
		header.add("Expires", "0");

		ResponseEntity<?> resp = ResponseEntity.ok()
				.headers(header)
				.contentLength(byteArrayResource.contentLength())
				.contentType(MediaType.parseMediaType("application/octet-stream"))
				.body(byteArrayResource);

		return resp;
	}
	
	@RequestMapping(value = "/file/{article-folder}", method = RequestMethod.GET)
	public ResponseEntity<?> getArticleFiles(@PathVariable("article-folder") String articleFolder) {
		Response response = folderInteractor.getArticleFiles(articleFolder);
		return HttpResponseHelper.ok(response.getMessage());
	}

	@RequestMapping(value = "/file/{article-folder}", method = RequestMethod.POST)
	public ResponseEntity<?> createFile(@PathVariable("article-folder") String articleFolder,
			@RequestParam("file") MultipartFile multipartFile) {
		Response response = fileInteractor.createFile(articleFolder, multipartFile);
		return HttpResponseHelper.respondRest(response);
	}

	@RequestMapping(value = "/file/{article-folder}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteFile(@PathVariable("article-folder") String articleFolder,
			@RequestHeader("File-Name") String fileName) {
		Response response = fileInteractor.deleteFile(articleFolder, fileName);
		return HttpResponseHelper.respondRest(response);
	}
	
	@RequestMapping(value = "/file/{article-folder}/markdown", method = RequestMethod.POST)
	public ResponseEntity<?> uploadMarkdown(@PathVariable("article-folder") String articleFolder,
			@RequestParam("file") MultipartFile multipartFile) {
		Response response = fileInteractor.saveMarkdownFile(articleFolder, multipartFile);
		return HttpResponseHelper.respondRest(response);
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
