package com.bh.news.server.util.file;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.util.FileSystemUtils;

public class FileUtil {
	public static void saveFile(File file, byte[] fileContent) throws IOException {
		BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file));
		out.write(fileContent);
		out.flush();
		out.close();
	}

	public static boolean createFolder(File file) {
		return file.mkdirs();
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
	
}
