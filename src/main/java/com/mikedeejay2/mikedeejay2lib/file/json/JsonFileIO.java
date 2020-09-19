package com.mikedeejay2.mikedeejay2lib.file.json;

import com.google.gson.*;
import com.google.gson.stream.JsonWriter;
import com.mikedeejay2.mikedeejay2lib.PluginBase;
import com.mikedeejay2.mikedeejay2lib.file.FileIO;
import com.mikedeejay2.mikedeejay2lib.util.PluginInstancer;

import java.io.*;
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
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        Reader reader = fileIO.getReaderFromDisk(file, throwErrors);
        try
        {
            json = gson.fromJson(reader, JsonObject.class);
            reader.close();
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
     * @return The requested JsonObject
     */
    public JsonObject loadJsonObjectFromJar(String filePath, boolean throwErrors)
    {
        JsonObject json = null;
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        Reader reader = fileIO.getReaderFromJar(filePath, throwErrors);
        try
        {
            json = gson.fromJson(reader, JsonObject.class);
            reader.close();
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

    /**
     * Save a json file to disk
     *
     * @param file The file to save to
     * @param json The json file that will be saved to the file
     * @param throwErrors Whether this method should throw errors if something goes wrong or not
     * @return Whether the save was successful or not
     */
    public boolean saveJsonFile(File file, JsonObject json, boolean throwErrors)
    {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        String str = gson.toJson(json);
        InputStream inputStream = new ByteArrayInputStream(str.getBytes());
        return fileIO.saveFile(file, inputStream, true, throwErrors);
    }

    /**
     * Update a json file on the disk with new values from a jar file of the corresponding name
     *
     * @param filePath The path of the file that should be updated, WITHOUT plugin.getDataFolder()
     * @param original The JsonObject that is currently loaded
     * @param throwErrors Whether this method should throw errors if something goes wrong or not
     * @return Whether the update was successful or not
     */
    public boolean updateFromJar(String filePath, JsonObject original, boolean throwErrors)
    {
        JsonFile jarFile = new JsonFile(plugin, filePath);
        boolean success = jarFile.loadFromJar(true);
        if(!success) return false;
        JsonObject jarJsonObject = jarFile.getJsonObject();
        Set<Map.Entry<String, JsonElement>> set = jarJsonObject.entrySet();
        return updateFromJarIterate(original, set, throwErrors);
    }

    /**
     * Update the json object in a recursive iterative format
     *
     * @param original The original JsonObject from the currently loaded file
     * @param set The root set of the json file
     * @param throwErrors Whether this method should throw errors if something goes wrong or not
     * @return Whether the update was successful or not
     */
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
                updateFromJarIterate(original.getAsJsonObject(memberName), newSet, throwErrors);
            }
        }
        return true;
    }
}
