package com.bh.news.server.util.file;

import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.IOException;

public interface FileHelper {
    void saveFile(File file, byte[] fileContent) throws IOException;
    boolean deleteFile(String path, String fileName);
    boolean updateFile(String path, String fileName);
    boolean renameFile(String path, String oldFileName, String newFileName);

    boolean createFolder(File file);
    boolean deleteFolder(String path);
    boolean renameFolder(String path);
}
