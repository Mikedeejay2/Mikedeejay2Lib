package com.mikedeejay2.mikedeejay2lib.data.json;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mikedeejay2.mikedeejay2lib.BukkitPlugin;
import com.mikedeejay2.mikedeejay2lib.data.DataFile;
import com.mikedeejay2.mikedeejay2lib.data.section.SectionInstancer;
import com.mikedeejay2.mikedeejay2lib.util.file.FileIO;
import com.mikedeejay2.mikedeejay2lib.util.file.JsonFileIO;

/**
 * Wrapper class for a DataFile of type Json
 *
 * @author Mikedeejay2
 */
public class JsonFile extends DataFile implements SectionInstancer<JsonAccessor>
{
    /**
     * The root <code>JsonObject</code> that this {@link DataFile} holds
     */
    protected JsonObject jsonObject;
    /**
     * The root JsonAccessor that this JsonFile uses
     */
    protected JsonAccessor accessor;

    /**
     * Constructor for <code>JsonFile</code>
     *
     * @param plugin The {@link BukkitPlugin} instance
     * @param filePath The path to the file that this <code>JsonFile</code> references
     */
    public JsonFile(BukkitPlugin plugin, String filePath)
    {
        super(plugin, filePath);
        jsonObject = new JsonObject();
        this.accessor = new JsonAccessor(this, jsonObject);
    }

    @Override
    public boolean loadFromDisk(boolean throwErrors)
    {
        JsonFileIO.loadJsonObjectFromDisk(file, jsonObject, throwErrors);
        return file.exists();
    }

    @Override
    public boolean loadFromJar(boolean throwErrors)
    {
        JsonFileIO.loadJsonObjectFromJar(filePath, jsonObject, plugin.classLoader(), throwErrors);
        return FileIO.getInputStreamFromJar(filePath, plugin.classLoader()) != null;
    }

    @Override
    public boolean saveToDisk(boolean throwErrors)
    {
        return JsonFileIO.saveJsonFile(file, jsonObject, throwErrors);
    }

    @Override
    public boolean updateFromJar(boolean throwErrors)
    {
        JsonFileIO.updateFromJar(plugin, filePath, jsonObject, throwErrors);
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
        return (JsonAccessor) accessor.getSection(name);
    }

    @Override
    public JsonAccessor getAccessor()
    {
        return accessor;
    }
}
