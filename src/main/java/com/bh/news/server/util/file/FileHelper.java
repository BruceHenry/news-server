package com.bh.news.server.util.file;

import org.springframework.beans.factory.annotation.Value;

public interface FileHelper {
    @Value("${storage.directory}")
    String SAVE_PATH = null;

    boolean saveFile(String path, String fileName);
    boolean deleteFile(String path, String fileName);
    boolean updateFile(String path, String fileName);
    boolean renameFile(String path, String oldFileName, String newFileName);

    boolean createFolder(String path);
    boolean deleteFolder(String path);
    boolean renameFolder(String path);
}
