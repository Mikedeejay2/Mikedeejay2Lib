package com.mikedeejay2.mikedeejay2lib.file.json;

import com.google.gson.*;
import com.mikedeejay2.mikedeejay2lib.PluginBase;
import com.mikedeejay2.mikedeejay2lib.file.DataFile;
import com.mikedeejay2.mikedeejay2lib.file.section.SectionInstancer;

/**
 * Wrapper class for a DataFile of type Json
 *
 * @author Mikedeejay2
 */
public class JsonFile extends DataFile implements SectionInstancer<JsonAccessor>
{
    // The root JsonObject that this DataFile holds
    protected JsonObject jsonObject;
    // The JsonFileIO that this DataFile uses
    private JsonFileIO jsonFileIO;
    // The root JsonAccessor that this JsonFile uses
    private JsonAccessor rootAccessor;

    public JsonFile(PluginBase plugin, String filePath)
    {
        super(plugin, filePath);
        jsonObject = null;
        this.jsonFileIO = new JsonFileIO(plugin);
        this.rootAccessor = new JsonAccessor(this, jsonObject);
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
        return jsonFileIO.saveJsonFile(file, jsonObject, throwErrors);
    }

    @Override
    public boolean updateFromJar(boolean throwErrors)
    {
        jsonFileIO.updateFromJar(filePath, jsonObject, throwErrors);
        return true;
    }

    public JsonObject getJsonObject()
    {
        return jsonObject;
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
     * @param memberName Path that contains wanted String
     * @return The wanted string. If non-existent, will return null.
     */
    public String getString(String memberName)
    {
        JsonElement element = getElement(memberName);
        if(element == null) return null;
        return element.getAsString();
    }

    @Override
    public JsonAccessor getAccessor(String name)
    {
        return (JsonAccessor)rootAccessor.getSection(name);
    }

    @Override
    public JsonAccessor getRootAccessor()
    {
        return rootAccessor;
    }
}
