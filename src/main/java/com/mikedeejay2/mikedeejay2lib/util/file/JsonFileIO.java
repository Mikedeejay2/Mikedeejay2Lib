package com.mikedeejay2.mikedeejay2lib.util.file;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.mikedeejay2.mikedeejay2lib.BukkitPlugin;
import com.mikedeejay2.mikedeejay2lib.data.json.JsonFile;

import java.io.*;
import java.util.Map;
import java.util.Set;

/**
 * Util class for saving and loading Json files from the disk
 *
 * @author Mikedeejay2
 */
public final class JsonFileIO
{
    /**
     * Load a JsonObject from the disk.
     *
     * @param folder      The root folder of the file path
     * @param filePath    Path to the file. This should NOT include plugin.getDataFolder()
     * @param throwErrors Whether this method should throw errors if something goes wrong or not
     * @param json        The <tt>JsonObject</tt> to load the JSON into
     * @return The requested JsonObject
     */
    public static JsonObject loadJsonObjectFromDisk(File folder, String filePath, JsonObject json, boolean throwErrors)
    {
        return loadJsonObjectFromDisk(new File(folder, filePath), json, throwErrors);
    }

    /**
     * Load a JsonObject from the disk.
     *
     * @param file        The file to be loaded
     * @param throwErrors Whether this method should throw errors if something goes wrong or not
     * @param json        The <tt>JsonObject</tt> to load the JSON into
     * @return The requested JsonObject
     */
    public static JsonObject loadJsonObjectFromDisk(File file, JsonObject json, boolean throwErrors)
    {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        Reader reader = FileIO.getReaderFromDisk(file, throwErrors);
        try
        {
            JsonObject newJson = gson.fromJson(reader, JsonObject.class);
            newJson.entrySet().forEach(entry -> json.add(entry.getKey(), entry.getValue()));
            reader.close();
        }
        catch(Exception e)
        {
            if(throwErrors) FileIO.logFileCouldNotBeLoaded(file.getPath(), e);
        }
        return json;
    }

    /**
     * Load a JsonObject from this plugin's Jar.
     *
     * @param filePath    The path to the json file in the jar
     * @param json        The <tt>JsonObject</tt> to load the JSON into
     * @param classLoader The <tt>ClassLoader</tt> to get the resource from
     * @param throwErrors Whether this method should throw errors if something goes wrong or not
     * @return The requested JsonObject
     */
    public static JsonObject loadJsonObjectFromJar(String filePath, JsonObject json, ClassLoader classLoader, boolean throwErrors)
    {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        Reader reader = FileIO.getReaderFromJar(filePath, classLoader, throwErrors);
        try
        {
            JsonObject newJson = gson.fromJson(reader, JsonObject.class);
            newJson.entrySet().forEach(entry -> json.add(entry.getKey(), entry.getValue()));
            reader.close();
        }
        catch(Exception e)
        {
            if(throwErrors) FileIO.logFileCouldNotBeLoaded(filePath, e);
        }
        return json;
    }

    /**
     * Get a JsonWriter from a file
     *
     * @param file        File to get JsonWriter from
     * @param throwErrors Whether this method should throw errors if something goes wrong or not
     * @return The requested JsonWriter
     */
    public static JsonWriter getJsonWriter(File file, boolean throwErrors)
    {
        JsonWriter writer = null;
        try
        {
            writer = new JsonWriter(new FileWriter(file));
        }
        catch(IOException e)
        {
            if(throwErrors) FileIO.logFileCouldNotBeSaved(file.getPath(), e);
        }
        return writer;
    }

    /**
     * Save a json file to disk
     *
     * @param file        The file to save to
     * @param json        The json file that will be saved to the file
     * @param throwErrors Whether this method should throw errors if something goes wrong or not
     * @return Whether the save was successful or not
     */
    public static boolean saveJsonFile(File file, JsonObject json, boolean throwErrors)
    {
        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        String str = gson.toJson(json);
        InputStream inputStream = new ByteArrayInputStream(str.getBytes());
        return FileIO.saveFile(file, inputStream, true, throwErrors);
    }

    /**
     * Update a json file on the disk with new values from a jar file of the corresponding name
     *
     * @param plugin      Reference to the plugin instance
     * @param filePath    The path of the file that should be updated, WITHOUT plugin.getDataFolder()
     * @param original    The JsonObject that is currently loaded
     * @param throwErrors Whether this method should throw errors if something goes wrong or not
     * @return Whether the update was successful or not
     */
    public static boolean updateFromJar(BukkitPlugin plugin, String filePath, JsonObject original, boolean throwErrors)
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
     * @param original    The original JsonObject from the currently loaded file
     * @param set         The root set of the json file
     * @param throwErrors Whether this method should throw errors if something goes wrong or not
     * @return Whether the update was successful or not
     */
    private static boolean updateFromJarIterate(JsonObject original, Set<Map.Entry<String, JsonElement>> set, boolean throwErrors)
    {
        for(Map.Entry<String, JsonElement> element : set)
        {
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
