package com.mikedeejay2.mikedeejay2lib.util.file;

import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;

/**
 * Simple util class for saving and loading a file
 *
 * @author Mikedeejay2
 */
public final class FileIO
{
    /**
     * Get an input stream to an internal file inside of the plugin's jar
     *
     * @param filePath Path to file
     * @param classLoader The <tt>ClassLoader</tt> to get the resource from
     * @return The requested InputStream
     */
    public static InputStream getInputStreamFromJar(String filePath, ClassLoader classLoader)
    {
        Validate.notNull(filePath, "Filename cannot be null");

        try
        {
            URL url = classLoader.getResource(filePath);

            if (url == null) return null;

            URLConnection connection = url.openConnection();
            connection.setUseCaches(false);
            return connection.getInputStream();
        }
        catch (IOException exception)
        {
            // Ignored
        }
        return null;
    }

    /**
     * Get an input stream from a file path on the disk
     *
     * @param folder      The root folder of the file path
     * @param filePath    Path to get InputStream from
     * @param throwErrors Whether this method should throw errors if something goes wrong or not
     * @return The requested InputStream
     */
    public static InputStream getInputStreamFromDisk(File folder, String filePath, boolean throwErrors)
    {
        return getInputStreamFromDisk(new File(folder, filePath), throwErrors);
    }

    /**
     * Get a Reader from a file path on the disk
     *
     * @param folder      The root folder of the file path
     * @param filePath    The path to get the reader from
     * @param throwErrors Whether this method should throw errors if something goes wrong or not
     * @return The requested Reader
     */
    public static Reader getReaderFromDisk(File folder, String filePath, boolean throwErrors)
    {
        return getReaderFromDisk(new File(folder, filePath), throwErrors);
    }

    /**
     * Get a Reader from this plugin's jar from a path
     *
     * @param filePath    Path to get the Reader from
     * @param classLoader The <tt>ClassLoader</tt> to get the resource from
     * @param throwErrors Whether this method should throw errors if something goes wrong or not
     * @return The requested Reader
     */
    public static Reader getReaderFromJar(String filePath, ClassLoader classLoader, boolean throwErrors)
    {
        InputStreamReader reader = null;
        try
        {
            reader = new InputStreamReader(getInputStreamFromJar(filePath, classLoader), StandardCharsets.UTF_8);
        }
        catch(Exception e)
        {
            if(throwErrors) logFileCouldNotBeLoaded(filePath, e);
        }
        return reader;
    }

    /**
     * Get a Reader from a file path on the disk
     *
     * @param file        The file to get the reader from
     * @param throwErrors Whether this method should throw errors if something goes wrong or not
     * @return The requested reader
     */
    public static Reader getReaderFromDisk(File file, boolean throwErrors)
    {
        InputStreamReader reader = null;
        try
        {
            reader = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8);
        }
        catch(Exception e)
        {
            if(throwErrors) logFileCouldNotBeLoaded(file.getPath(), e);
        }
        return reader;
    }

    /**
     * Get an InputStream from a File on the disk
     *
     * @param file        File to get the InputStream from
     * @param throwErrors Whether this method should throw errors if something goes wrong or not
     * @return The requested InputStream
     */
    public static InputStream getInputStreamFromDisk(File file, boolean throwErrors)
    {
        FileInputStream inputStream = null;
        try
        {
            if(!file.exists()) file.createNewFile();
            inputStream = new FileInputStream(file);
        }
        catch(Exception e)
        {
            if(throwErrors) logFileCouldNotBeLoaded(file.getPath(), e);
        }
        return inputStream;
    }

    /**
     * Gets an InputStream from the jar and saves it to the file path
     *
     * @param folder      The root folder of the file path
     * @param filePath    Path to save the file to
     * @param replace     Replace existing file or not
     * @param classLoader The <tt>ClassLoader</tt> to get the resource from
     * @param throwErrors Whether this method should throw errors if something goes wrong or not
     * @return If save was successful or not
     */
    public static boolean saveFileFromJar(File folder, String filePath, ClassLoader classLoader, boolean replace, boolean throwErrors)
    {
        InputStream inputStream = getInputStreamFromJar(filePath, classLoader);
        File file = new File(folder, filePath);
        return saveFile(file, inputStream, replace, throwErrors);
    }

    /**
     * Save the file to the requested file path using the requested InputStream
     *
     * @param folder      The root folder of the file path
     * @param filePath    Path to the file. This should NOT include plugin.getDataFolder()
     * @param input       The InputStream to use when saving the file
     * @param replace     Replace the existing file or not
     * @param throwErrors Whether this method should throw errors if something goes wrong or not
     * @return If save was successful or not
     */
    public static boolean saveFile(File folder, String filePath, InputStream input, boolean replace, boolean throwErrors)
    {
        return saveFile(new File(folder, filePath), input, replace, throwErrors);
    }

    /**
     * Save the file to the requested file using the requested InputStream
     *
     * @param file        The File to save the contents to
     * @param input       The InputStream to use when saving the file
     * @param replace     Replace the existing file or not
     * @param throwErrors Whether this method should throw errors if something goes wrong or not
     * @return If the save was successful or not
     */
    public static boolean saveFile(File file, InputStream input, boolean replace, boolean throwErrors)
    {
        if (!file.exists()) file.getParentFile().mkdirs();

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
            if(throwErrors) logFileCouldNotBeSaved(file.getPath(), ex);
            return false;
        }
        return true;
    }

    /**
     * Helper method for printing "The file {filePath} could not be loaded!"
     *
     * @param filePath  Path to print
     * @param exception The exception that was thrown
     */
    public static void logFileCouldNotBeLoaded(String filePath, Exception exception)
    {
        Bukkit.getLogger().log(Level.SEVERE, "The file \"" + filePath + "\" could not be loaded!", exception);
    }

    public static void logFileCouldNotBeSaved(String filePath, Exception exception)
    {
        Bukkit.getLogger().log(Level.SEVERE, "The file \"" + filePath + "\" could not be saved!", exception);
    }

    /**
     * Delete a file based off of a file path.
     *
     * @param folder      The root folder of the file path
     * @param filePath Path to the file. This should NOT include plugin.getDataFolder()
     * @return Whether deletion was successful or not
     */
    public static boolean deleteFile(File folder, String filePath)
    {
        return deleteFile(new File(folder, filePath));
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
