package com.bh.news.server.util.file;

import java.io.*;

public class FileHelperIo implements FileHelper {

    @Override
    public void saveFile(File file, byte[] fileContent) throws IOException {
        BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file));
        out.write(fileContent);
        out.flush();
        out.close();
    }

    @Override
    public boolean deleteFile(String path, String fileName) {
        return false;
    }

    @Override
    public boolean updateFile(String path, String fileName) {
        return false;
    }

    @Override
    public boolean renameFile(String path, String oldFileName, String newFileName) {
        return false;
    }


    @Override
    public boolean createFolder(File file) {
        return file.mkdirs();
    }

    @Override
    public boolean deleteFolder(String path) {
        return false;
    }

    @Override
    public boolean renameFolder(String path) {
        return false;
    }
}
