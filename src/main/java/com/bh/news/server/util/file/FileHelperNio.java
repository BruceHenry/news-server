package com.bh.news.server.util.file;

public class FileHelperNio implements FileHelper{
    @Override
    public boolean saveFile(String path, String fileName) {
        return false;
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
    public boolean createFolder(String path) {
        return false;
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
