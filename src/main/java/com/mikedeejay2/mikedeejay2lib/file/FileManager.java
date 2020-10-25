package com.mikedeejay2.mikedeejay2lib.file;

import com.mikedeejay2.mikedeejay2lib.PluginBase;

import java.util.HashMap;
import java.util.Map;

/**
 * A file manager that is stored in PluginBase to manage all of the plugin's files.
 *
 * @author Mikedeejay2
 */
public class FileManager
{
    // The hashmap of file paths to DataFiles
    private Map<String, DataFile> files;

    public FileManager()
    {
        this.files = new HashMap<>();
    }

    /**
     * Method that returns whether this manager contains a file of a file path or not
     *
     * @param filePath Path to test existence for
     * @return Whether the file path exists or not in this manager
     */
    public boolean containsFile(String filePath)
    {
        return files.containsKey(filePath);
    }

    /**
     * Get a DataFile based off of a file's path
     *
     * @param filePath The file path to get the data file from
     * @return The requested data file
     */
    public DataFile getDataFile(String filePath)
    {
        return files.get(filePath);
    }

    /**
     * Add a new data file to the file manager
     *
     * @param file File to add
     */
    public void addDataFile(DataFile file)
    {
        files.put(file.getFilePath(), file);
    }

    /**
     * Remove a data file from the file manager
     *
     * @param filePath The path of the file to remove
     */
    public void removeDataFile(String filePath)
    {
        files.remove(filePath);
    }

    /**
     * Remove a data file from the file manager
     *
     * @param file The file to remove
     */
    public void removeDataFile(DataFile file)
    {
        removeDataFile(file.getFilePath());
    }
}
