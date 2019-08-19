package com.bh.news.server.util.file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileHelperNio {

	public static String readFile(String filePath) throws IOException {
		String content = "";
		content = new String(Files.readAllBytes(Paths.get(filePath)));
		return content;
	}
}
