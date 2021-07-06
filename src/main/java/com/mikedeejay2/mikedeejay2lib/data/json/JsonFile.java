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
public class JsonFile extends DataFile implements SectionInstancer<JsonAccessor, JsonFile, JsonElement>
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

    /**
     * {@inheritDoc}
     *
     * @param throwErrors Silence any errors that this operation might produce
     * @return If this operation was successful or not
     */
    @Override
    public boolean loadFromDisk(boolean throwErrors)
    {
        JsonFileIO.loadJsonObjectFromDisk(file, jsonObject, throwErrors);
        return file.exists();
    }

    /**
     * {@inheritDoc}
     *
     * @param throwErrors Silence any errors that this operation might produce
     * @return If this operation was successful or not
     */
    @Override
    public boolean loadFromJar(boolean throwErrors)
    {
        JsonFileIO.loadJsonObjectFromJar(filePath, jsonObject, plugin.classLoader(), throwErrors);
        return FileIO.getInputStreamFromJar(filePath, plugin.classLoader()) != null;
    }

    /**
     * {@inheritDoc}
     *
     * @param throwErrors Silence any errors that this operation might produce
     * @return If this operation was successful or not
     */
    @Override
    public boolean saveToDisk(boolean throwErrors)
    {
        return JsonFileIO.saveJsonFile(file, jsonObject, throwErrors);
    }

    /**
     * {@inheritDoc}
     *
     * @param throwErrors Silence any errors that this operation might produce
     * @return If this operation was successful or not
     */
    @Override
    public boolean updateFromJar(boolean throwErrors)
    {
        JsonFileIO.updateFromJar(plugin, filePath, jsonObject, throwErrors);
        return true;
    }

    /**
     * Get the root <code>JsonObject</code> of this <code>JsonFile</code>
     *
     * @return The root <code>JsonObject</code>
     */
    public JsonObject getJsonObject()
    {
        return jsonObject;
    }

    /**
     * Get a <code>JsonElement</code> from the json file
     *
     * @param element Element to get
     * @return The <code>JsonElement</code>. If non-existent, will return null.
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

    /**
     * {@inheritDoc}
     *
     * @param name The name of the section to get
     * @return The <code>SectionAccessor</code> of the requested section
     */
    @Override
    public JsonAccessor getAccessor(String name)
    {
        return accessor.getSection(name);
    }

    /**
     * {@inheritDoc}
     *
     * @return The root <code>SectionAccessor</code>
     */
    @Override
    public JsonAccessor getAccessor()
    {
        return accessor;
    }
}
