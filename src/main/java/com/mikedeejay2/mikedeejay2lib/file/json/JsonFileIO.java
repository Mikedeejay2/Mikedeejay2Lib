package com.mikedeejay2.mikedeejay2lib.file.json;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mikedeejay2.mikedeejay2lib.PluginBase;
import com.mikedeejay2.mikedeejay2lib.file.FileIO;
import com.mikedeejay2.mikedeejay2lib.util.PluginInstancer;

import java.io.File;

/**
 * Util class for saving and loading Json files from the disk
 *
 * @author Mikedeejay2
 */
public final class JsonFileIO extends PluginInstancer<PluginBase>
{
    private final FileIO fileIO;

    public JsonFileIO(PluginBase plugin)
    {
        super(plugin);
        this.fileIO = new FileIO(plugin);
    }

    /**
     * Load a JsonObject from the disk.
     *
     * @param filePath Path to the file. This should NOT include plugin.getDataFolder()
     * @return The requested JsonObject
     */
    public JsonObject loadJsonObjectFromDisk(String filePath, boolean throwErrors)
    {
        return loadJsonObjectFromDisk(new File(plugin.getDataFolder(), filePath), throwErrors);
    }

    /**
     * Load a JsonObject from the disk.
     *
     * @param file The file to be loaded
     * @return The requested JsonObject
     */
    public JsonObject loadJsonObjectFromDisk(File file, boolean throwErrors)
    {
        JsonObject json = null;
        JsonParser parser = new JsonParser();
        try
        {
            json = (JsonObject)parser.parse(fileIO.getReaderFromDisk(file, throwErrors));
        }
        catch(Exception e)
        {
            if(throwErrors) fileIO.logFileCouldNotBeLoaded(file.getPath(), e);
        }
        return json;
    }

    /**
     * Load a JsonObject from this plugin's Jar.
     *
     * @param filePath The path to the json file in the jar=
     * @return THe requested JsonObject
     */
    public JsonObject loadJsonObjectFromJar(String filePath, boolean throwErrors)
    {
        JsonObject json = null;
        JsonParser parser = new JsonParser();
        try
        {
            json = (JsonObject)parser.parse(fileIO.getReaderFromJar(filePath, throwErrors));
        }
        catch(Exception e)
        {
            if(throwErrors) fileIO.logFileCouldNotBeLoaded(filePath, e);
        }
        return json;
    }
}
