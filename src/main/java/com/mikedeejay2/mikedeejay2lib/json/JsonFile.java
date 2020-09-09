package com.mikedeejay2.mikedeejay2lib.json;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mikedeejay2.mikedeejay2lib.PluginBase;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class JsonFile
{
    private static final PluginBase plugin = PluginBase.getInstance();

    // Locale (Language that this lang file is)
    private String fileName;
    // The json of this lang file
    private JsonObject json;
    // The json parser that the json was created with
    private JsonParser parser = new JsonParser();

    /**
     * Construct a lang file
     *
     * @param fileName The file name
     */
    public JsonFile(String fileName)
    {
        this.fileName = fileName;
        load();
    }

    /**
     * Attempt to load this json file.
     *
     * @return If load was successful and file was loaded or not.
     */
    public boolean load()
    {
        InputStream input = plugin.getResource(fileName);
        if(input == null) return false;
        try
        {
            json = (JsonObject)parser.parse(new InputStreamReader(input, StandardCharsets.UTF_8));
            return true;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Get a JsonElement from the json file
     *
     * @param element Element to get
     * @return The JsonElement. If non-existent, will return null.
     */
    public JsonElement getElement(String element)
    {
        return json.get(element);
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
}
