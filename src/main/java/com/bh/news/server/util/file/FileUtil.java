package com.bh.news.server.util.file;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.util.FileSystemUtils;

public class FileUtil {
	public static void saveFile(File file, byte[] fileContent) throws IOException {
		BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file));
		out.write(fileContent);
		out.flush();
		out.close();
	}
	
	public static void saveFile(Path path, byte[] fileContent) throws IOException {
		Files.write(path, fileContent);
	}

	public static void createFolder(Path path) throws IOException {
		Files.createDirectories(path);
	}
	
	public static String readFileAsString(String filePath) throws IOException {
		return new String(readFileAsByteArray(filePath));
	}
	
	public static byte[] readFileAsByteArray(String filePath) throws IOException {
		return Files.readAllBytes(Paths.get(filePath));
	}
	
	public static boolean deleteFile(File file) {
		return file.delete();
	}
	
	public static boolean deleteFolderRecursively(File file) {
		return FileSystemUtils.deleteRecursively(file);
	}
	
	public static String getFileExtension(String fileName) {
		int i = fileName.lastIndexOf('.');
		if (i > 0) {
		    return fileName.substring(i+1);
		}
		return "";
	}
	
	public static boolean isFileExtensionMatch(String fileName, String extensionName) {
		return getFileExtension(fileName).equals(extensionName);
	}
	
	
}
