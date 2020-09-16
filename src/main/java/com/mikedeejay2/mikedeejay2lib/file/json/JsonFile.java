package com.mikedeejay2.mikedeejay2lib.file.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mikedeejay2.mikedeejay2lib.PluginBase;
import com.mikedeejay2.mikedeejay2lib.file.DataFile;
import com.mikedeejay2.mikedeejay2lib.file.FileIO;

/**
 * Wrapper class for a DataFile of type Json
 *
 * @author Mikedeejay2
 */
public class JsonFile extends DataFile
{
    protected JsonObject jsonObject;
    private JsonFileIO jsonFileIO;

    public JsonFile(PluginBase plugin, String filePath)
    {
        super(plugin, filePath);
        jsonObject = null;
        this.jsonFileIO = new JsonFileIO(plugin);
    }

    /**
     * Get a JsonElement from the json file
     *
     * @param element Element to get
     * @return The JsonElement. If non-existent, will return null.
     */
    public JsonElement getElement(String element)
    {
        return jsonObject.get(element);
    }

    /**
     * Gets a string from the json file based off of the path.
     *
     * @param path Path that contains wanted String
     * @return The wanted string. If non-existent, will return null.
     */
    public String getString(String path)
    {
        JsonElement element = getElement(path);
        if(element == null) return null;
        return element.getAsString();
    }

    @Override
    public boolean loadFromDisk(boolean throwErrors)
    {
        jsonObject = jsonFileIO.loadJsonObjectFromDisk(file, throwErrors);
        return file.exists();
    }

    @Override
    public boolean loadFromJar(boolean throwErrors)
    {
        jsonObject = jsonFileIO.loadJsonObjectFromJar(filePath, throwErrors);
        return fileIO.getInputStreamFromJar(filePath) != null;
    }

    @Override
    public boolean saveToDisk(boolean throwErrors)
    {
        return false;
    }

    @Override
    public boolean delete(boolean throwErrors)
    {
        return false;
    }

    @Override
    public boolean updateFromJar(boolean throwErrors)
    {
        return false;
    }
}
