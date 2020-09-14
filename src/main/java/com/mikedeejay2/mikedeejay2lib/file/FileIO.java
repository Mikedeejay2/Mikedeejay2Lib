package com.mikedeejay2.mikedeejay2lib.file;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mikedeejay2.mikedeejay2lib.PluginBase;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;

/**
 * Simple util class for saving and loading a file
 */
public class FileIO
{
    private static final PluginBase plugin = PluginBase.getInstance();

    /**
     * Get an input stream to an internal file inside of the plugin's jar
     *
     * @param filePath Path to file
     * @return The requested InputStream
     */
    public static InputStream getInputStreamFromJar(String filePath)
    {
        return plugin.getResource(filePath);
    }

    /**
     * Get an input stream from a file path on the disk
     *
     * @param filePath Path to get InputStream from
     * @return The requested InputStream
     */
    public static InputStream getInputStreamFromDisk(String filePath)
    {
        return getInputStreamFromDisk(new File(plugin.getDataFolder(), filePath));
    }

    /**
     * Get a Reader from a file path on the disk
     *
     * @param filePath The path to get the reader from
     * @return The requested Reader
     */
    public static Reader getReaderFromDisk(String filePath)
    {
        return getReaderFromDisk(new File(plugin.getDataFolder(), filePath));
    }

    /**
     * Get a Reader from this plugin's jar from a path
     *
     * @param filePath Path to get the Reader from
     * @return The requested Reader
     */
    public static Reader getReaderFromJar(String filePath)
    {
        InputStreamReader reader = null;
        try
        {
            reader = new InputStreamReader(getInputStreamFromJar(filePath), StandardCharsets.UTF_8);
        }
        catch(Exception e)
        {
            logFileCouldNotBeLoaded(filePath, e, true);
        }
        return reader;
    }

    /**
     * Get a Reader from a file path on the disk
     *
     * @param file The file to get the reader from
     * @return The requested reader
     */
    public static Reader getReaderFromDisk(File file)
    {
        InputStreamReader reader = null;
        try
        {
            reader = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8);
        }
        catch(Exception e)
        {
            logFileCouldNotBeLoaded(file.getPath(), e, false);
        }
        return reader;
    }

    /**
     * Get an InputStream from a File on the disk
     *
     * @param file File to get the InputStream from
     * @return The requested InputStream
     */
    public static InputStream getInputStreamFromDisk(File file)
    {
        FileInputStream inputStream = null;
        try
        {
            if(!file.exists()) file.createNewFile();
            inputStream = new FileInputStream(file);
        }
        catch(Exception e)
        {
            logFileCouldNotBeLoaded(file.getPath(), e, false);
        }
        return inputStream;
    }

    /**
     * Gets an InputStream from the jar and saves it to the file path
     *
     * @param filePath Path to save the file to
     * @param replace Replace existing file or not
     * @return If save was successful or not
     */
    public static boolean saveFileFromJar(String filePath, boolean replace)
    {
        InputStream inputStream = getInputStreamFromJar(filePath);
        File file = new File(plugin.getDataFolder(), filePath);
        return saveFile(file, inputStream, replace);
    }

    /**
     * Save the file to the requested file path
     *
     * @param filePath Path to the file. This should NOT include plugin.getDataFolder()
     * @param replace Replace the existing file or not
     * @return If save was successful or not
     */
    public static boolean saveFile(String filePath, boolean replace)
    {
        return saveFile(new File(plugin.getDataFolder(), filePath), replace);
    }

    /**
     * Save the file to the requested file path
     *
     * @param file The file to save to
     * @param replace Replace the existing file or not
     * @return If save was successful or not
     */
    public static boolean saveFile(File file, boolean replace)
    {
        try
        {
            if(!file.exists()) file.createNewFile();
            FileInputStream inputStream = new FileInputStream(file);
            return saveFile(file, inputStream, replace);
        }
        catch(Exception e)
        {
            logFileCouldNotBeLoaded(file.getPath(), e, false);
        }
        return false;
    }

    /**
     * Save the file to the requested file path using the requested InputStream
     *
     * @param filePath Path to the file. This should NOT include plugin.getDataFolder()
     * @param input The InputStream to use when saving the file
     * @param replace Replace the existing file or not
     * @return If save was successful or not
     */
    public static boolean saveFile(String filePath, InputStream input, boolean replace)
    {
        return saveFile(new File(plugin.getDataFolder(), filePath), input, replace);
    }

    /**
     * Save the file to the requested file using the requested InputStream
     *
     * @param file The File to save the contents to
     * @param input The InputStream to use when saving the file
     * @param replace Replace the existing file or not
     * @return If the save was successful or not
     */
    public static boolean saveFile(File file, InputStream input, boolean replace)
    {
        if (!file.exists()) file.mkdirs();

        try
        {
            if (!file.exists() || replace)
            {
                if(!file.exists()) file.createNewFile();
                OutputStream output = new FileOutputStream(file);
                byte[] buffer = new byte[1024];
                int length;
                while ((length = input.read(buffer)) > 0)
                {
                    output.write(buffer, 0, length);
                }
                output.close();
                input.close();
            }
        }
        catch (IOException ex)
        {
            plugin.getLogger().log(Level.SEVERE, "Could not save " + file.getName() + " to " + file.getPath(), ex);
            return false;
        }
        return true;
    }

    /**
     * Helper method for printing "The file {filePath} could not be loaded!"
     *
     * @param filePath Path to print
     * @param exception The exception that was thrown
     */
    public static void logFileCouldNotBeLoaded(String filePath, Exception exception, boolean silence)
    {
        if(silence)
        {
            plugin.getLogger().log(Level.SEVERE, "The file \"" + filePath + "\" could not be loaded!");
        }
        else
        {
            plugin.getLogger().log(Level.SEVERE, "The file \"" + filePath + "\" could not be loaded!", exception);
        }
    }

    /**
     * Delete a file based off of a file path.
     *
     * @param filePath Path to the file. This should NOT include plugin.getDataFolder()
     * @return Whether deletion was successful or not
     */
    public static boolean deleteFile(String filePath)
    {
        return deleteFile(new File(plugin.getDataFolder(), filePath));
    }

    /**
     * Delete a file
     *
     * @param file File to delete
     * @return Whether deletion was successful or not
     */
    public static boolean deleteFile(File file)
    {
        return file.delete();
    }
}
