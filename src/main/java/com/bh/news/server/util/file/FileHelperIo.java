package com.bh.news.server.util.file;

import java.io.*;

public class FileHelperIo {

	public static void saveFile(File file, byte[] fileContent) throws IOException {
		BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file));
		out.write(fileContent);
		out.flush();
		out.close();
	}

	public static boolean createFolder(File file) {
		return file.mkdirs();
	}
}
