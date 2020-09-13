package com.mikedeejay2.mikedeejay2lib.file;

import java.util.HashMap;

public class FileManager
{
    private HashMap<String, DataFile> files;

    public FileManager()
    {
        this.files = new HashMap<>();
    }

    public boolean containsFile(String filePath)
    {
        return files.containsKey(filePath);
    }

    public DataFile getDataFile(String filePath)
    {
        return files.get(filePath);
    }

    public void addDataFile(DataFile file)
    {
        files.put(file.getFilePath(), file);
    }

    public void removeDataFile(String filePath)
    {
        files.remove(filePath);
    }

    public void removeDataFile(DataFile file)
    {
        removeDataFile(file.getFilePath());
    }
}
