package com.bh.news.server.logic;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bh.news.server.util.common.Response;
import com.bh.news.server.util.file.FileUtil;

@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class FileInteractor extends BaseInteractor {

	public Response createFile(String articleFolder, MultipartFile multipartFile) {
		if (articleFolder == null || articleFolder.isEmpty()) {
			return new Response(400, "delete failure: article name is null/empty");
		}
		if (multipartFile.isEmpty()) {
			return new Response(400, "upload failure: file is empty");
		}
		String newFileName = multipartFile.getOriginalFilename();
		if (newFileName == null || newFileName.isEmpty()) {
			return new Response(400, "upload failure: file name is null/empty");
		}

		File file = new File(getArticleFolderName(articleFolder) + File.separator + newFileName);

		try {
			FileUtil.saveFile(file, multipartFile.getBytes());
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
		if (articleFolder == null || articleFolder.isEmpty()) {
			return new Response(400, "delete failure: article name is null/empty");
		}
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
		if (resource == null) {
			throw new IOException();
		}
		return resource;
	}

}
