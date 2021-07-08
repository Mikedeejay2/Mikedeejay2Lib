package com.mikedeejay2.mikedeejay2lib.data;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * A data manager to manage data objects (Files, databases, etc).
 *
 * @author Mikedeejay2
 */
public class DataManager
{
    /**
     * The map of file paths to DataFiles
     */
    protected final Map<String, Object> data;

    /**
     * Construct a new <code>DataManager</code>
     */
    public DataManager()
    {
        this.data = new LinkedHashMap<>();
    }

    /**
     * See whether this manager contains a specific object of data
     *
     * @param object The object to check for
     * @return If the object was found or not
     */
    public boolean containsData(Object object)
    {
        return data.containsValue(object);
    }

    /**
     * See whether this manager contains a specific identifier for data
     *
     * @param identifier The identifier to check for
     * @return If the identifier was found or not
     */
    public boolean containsIdentifier(String identifier)
    {
        return data.containsKey(identifier);
    }

    /**
     * Add data to this manager with a set identifier
     *
     * @param object The object to add to this manager
     * @param identifier The identifier String used for retrieving the object
     */
    public void addData(Object object, String identifier)
    {
        data.put(identifier, object);
    }

    /**
     * Add a {@link DataFile} to this manager
     *
     * @param file The <code>DataFile</code> to add
     */
    public void addFile(DataFile file)
    {
        data.put(file.getFilePath(), file);
    }

    /**
     * Remove an object based off of the object's instance
     *
     * @param object The object to remove
     */
    public void removeData(Object object)
    {
        String identifier = null;
        for(Map.Entry<String, Object> entry : data.entrySet())
        {
            String curIdentifier = entry.getKey();
            Object curObj = entry.getValue();
            if(!object.equals(curObj)) continue;
            identifier = curIdentifier;
            break;
        }
        data.remove(identifier);
    }

    /**
     * Remove data based off of the data's String identifier
     *
     * @param identifier The identifier to remove
     */
    public void removeData(String identifier)
    {
        data.remove(identifier);
    }

    /**
     * Remove a {@link DataFile} from this manager
     *
     * @param file The <code>DataFile</code> to remove
     */
    public void removeFile(DataFile file)
    {
        data.remove(file.getFilePath());
    }

    /**
     * Get data based off of its String identifier
     *
     * @param identifier The identifier used to get the object
     * @return The requested Object
     */
    public Object getData(String identifier)
    {
        return data.get(identifier);
    }

    /**
     * Get data based off of its String identifier and casted to a specific class
     *
     * @param identifier The identifier used to get the object
     * @param dataClass  The Class representing the
     * @param <T>        The data type of the object
     * @return The requested Object casted to the <code>dataClass</code>
     */
    public <T> T getData(String identifier, Class<T> dataClass)
    {
        return dataClass.cast(data.get(identifier));
    }

    /**
     * Get a {@link DataFile} based off of the path to the file
     *
     * @param path The path to the file including name
     * @return The requested <code>DataFile</code>
     */
    public DataFile getFile(String path)
    {
        return (DataFile) data.get(path);
    }

    /**
     * Get the count of data Objects being held in this manager
     *
     * @return The data count
     */
    public int getDataCount()
    {
        return data.size();
    }
}
