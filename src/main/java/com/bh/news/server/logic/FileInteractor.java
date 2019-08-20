package com.bh.news.server.logic;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bh.news.server.util.common.Response;
import com.bh.news.server.util.file.FileUtil;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.MutableDataSet;

@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class FileInteractor extends BaseInteractor {

	private final static String INDEX_HTML = "index.html";
	private final static String MARKDOWN_EXTENSION = "md";

	public Response createFile(String articleFolder, MultipartFile multipartFile) {
		if (multipartFile.isEmpty()) {
			return new Response(400, "upload failure: file is empty");
		}
		String newFileName = multipartFile.getOriginalFilename();
		if (newFileName == null || newFileName.isEmpty()) {
			return new Response(400, "upload failure: file name is null/empty");
		}

		Path path = Paths.get(getArticleFolderName(articleFolder), newFileName);

		try {
			FileUtil.saveFile(path, multipartFile.getBytes());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return new Response(500, "upload meets FileNotFoundException: " + e.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
			return new Response(500, "upload meets IOException: " + e.getMessage());
		}
		return new Response(200, "success");
	}

	public Response deleteFile(String articleFolder, String fileName) {
		if (fileName == null || fileName.isEmpty()) {
			return new Response(400, "delete failure: file name is null/empty");
		}

		File file = new File(getArticleFolderName(articleFolder) + File.separator + fileName);
		if (!file.exists()) {
			return new Response(400, "delete failure: unable to find file");
		}

		boolean result = FileUtil.deleteFile(file);
		if (result) {
			return new Response(200, "success");
		} else {
			return new Response(500, "failure");
		}
	}

	public ByteArrayResource getFile(String articleFolder, String fileName) throws IOException {

		String filePath = getArticleFolderName(articleFolder) + File.separator + fileName;
		if (new File(filePath).exists() == false) {
			throw new FileNotFoundException();
		}
		ByteArrayResource resource = null;
		resource = new ByteArrayResource(FileUtil.readFileAsByteArray(filePath));
		return resource;
	}

	public Response saveMarkdownFile(String articleFolder, MultipartFile multipartFile) {
		if (multipartFile.isEmpty()) {
			return new Response(400, "upload failure: markdown file is empty");
		}
		if (!FileUtil.isFileExtensionMatch(multipartFile.getOriginalFilename(), MARKDOWN_EXTENSION)) {
			return new Response(400, "upload failure: must be a markdown file that ends with extension '.md'");
		}

		Path path = Paths.get(getArticleFolderName(articleFolder), INDEX_HTML);
		String content = null;
		try {
			content = new String(multipartFile.getBytes());
		} catch (IOException e) {
			return new Response(400, "unable to parse markdown file");
		}
		
		MutableDataSet options = new MutableDataSet();
		Parser parser = Parser.builder(options).build();
		HtmlRenderer renderer = HtmlRenderer.builder(options).build();
		
		Node document = parser.parse(content);
        String html = renderer.render(document);
        try {
			FileUtil.saveFile(path, html.getBytes());
		} catch (IOException e) {
			return new Response(500, "failed saving html file");
		}
        
		return new Response(200, "success");
	}
}
