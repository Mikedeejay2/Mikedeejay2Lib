package com.mikedeejay2.mikedeejay2lib.file.json;

import com.google.gson.*;
import com.google.gson.stream.JsonWriter;
import com.mikedeejay2.mikedeejay2lib.PluginBase;
import com.mikedeejay2.mikedeejay2lib.file.FileIO;
import com.mikedeejay2.mikedeejay2lib.util.PluginInstancer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

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
     * @param throwErrors Whether this method should throw errors if something goes wrong or not
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
     * @param throwErrors Whether this method should throw errors if something goes wrong or not
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
     * @param filePath The path to the json file in the jar
     * @param throwErrors Whether this method should throw errors if something goes wrong or not
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

    /**
     * Get a JsonWriter from a file
     *
     * @param file File to get JsonWriter from
     * @param throwErrors Whether this method should throw errors if something goes wrong or not
     * @return The requested JsonWriter
     */
    public JsonWriter getJsonWriter(File file, boolean throwErrors)
    {
        JsonWriter writer = null;
        try
        {
            writer = new JsonWriter(new FileWriter(file));
        }
        catch(IOException e)
        {
            if(throwErrors) fileIO.logFileCouldNotBeSaved(file.getPath(), e);
        }
        return writer;
    }

    public boolean saveJsonFile(File file, JsonObject json, boolean throwErrors)
    {
        JsonWriter writer = getJsonWriter(file, throwErrors);
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        if(!file.exists())
        {
            try
            {
                file.createNewFile();
            }
            catch(IOException e)
            {
                if(throwErrors) fileIO.logFileCouldNotBeSaved(file.getPath(), e);
                return false;
            }
        }
        gson.toJson(json, writer);
        return true;
    }

    public boolean updateFromJar(String filePath, JsonObject original, boolean throwErrors)
    {
        JsonFile jarFile = new JsonFile(plugin, filePath);
        jarFile.loadFromJar(true);
        JsonObject jarJsonObject = jarFile.getJsonObject();
        Set<Map.Entry<String, JsonElement>> set = jarJsonObject.entrySet();
        return updateFromJarIterate(original, set, throwErrors);
    }

    private boolean updateFromJarIterate(JsonObject original, Set<Map.Entry<String, JsonElement>> set, boolean throwErrors)
    {
        for(Iterator<Map.Entry<String, JsonElement>> i = set.iterator(); i.hasNext(); )
        {
            Map.Entry<String, JsonElement> element = i.next();
            String memberName = element.getKey();
            JsonElement jsonElement = element.getValue();
            if(!original.has(memberName))
            {
                original.add(memberName, jsonElement);
            }
            else if(jsonElement.isJsonObject())
            {
                Set<Map.Entry<String, JsonElement>> newSet = jsonElement.getAsJsonObject().entrySet();
                return updateFromJarIterate(original, newSet, throwErrors);
            }
        }
        return true;
    }
}
