package com.mikedeejay2.mikedeejay2lib.file.json;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mikedeejay2.mikedeejay2lib.PluginBase;
import com.mikedeejay2.mikedeejay2lib.file.FileIO;

import java.io.File;

/**
 * Util class for saving and loading Json files from the disk
 */
public final class JsonFileIO
{
    private static final PluginBase plugin = PluginBase.getInstance();

    /**
     * Load a JsonObject from the disk.
     *
     * @param filePath Path to the file. This should NOT include plugin.getDataFolder()
     * @return The requested JsonObject
     */
    public static JsonObject loadJsonObjectFromDisk(String filePath)
    {
        return loadJsonObjectFromDisk(new File(plugin.getDataFolder(), filePath));
    }

    /**
     * Load a JsonObject from the disk.
     *
     * @param file The file to be loaded
     * @return The requested JsonObject
     */
    public static JsonObject loadJsonObjectFromDisk(File file)
    {
        JsonObject json = null;
        JsonParser parser = new JsonParser();
        try
        {
            json = (JsonObject)parser.parse(FileIO.getReaderFromDisk(file));
        }
        catch(Exception e)
        {
            FileIO.logFileCouldNotBeLoaded(file.getPath(), e);
        }
        return json;
    }

    /**
     * Load a JsonObject from this plugin's Jar.
     *
     * @param filePath The path to the json file in the jar=
     * @return THe requested JsonObject
     */
    public static JsonObject loadJsonObjectFromJar(String filePath)
    {
        JsonObject json = null;
        JsonParser parser = new JsonParser();
        try
        {
            json = (JsonObject)parser.parse(FileIO.getReaderFromJar(filePath));
        }
        catch(Exception e)
        {
            FileIO.logFileCouldNotBeLoaded(filePath, e);
        }
        return json;
    }
}
