package com.mikedeejay2.mikedeejay2lib.data;

import com.mikedeejay2.oosql.SQLObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A data manager that is stored in PluginBase to manage all of the plugin's data objects (Files, databases, etc).
 *
 * @author Mikedeejay2
 */
public class DataManager<T extends DataObject & SQLObject>
{
    // The hashmap of file paths to DataFiles
    protected Map<String, T> data;

    public DataManager()
    {
        this.data = new HashMap<>();
    }

    public boolean containsData(T object)
    {
        return data.containsValue(object);
    }

    public boolean containsIdentifier(String identifier)
    {
        return data.containsKey(identifier);
    }

    public void addData(T object, String identifier)
    {
        data.put(identifier, object);
    }

    public void addFile(DataFile file)
    {
        data.put(file.getFilePath(), (T) file);
    }

    public void removeData(T object)
    {
        String identifier = null;
        for(Map.Entry<String, T> entry : data.entrySet())
        {
            String curIdentifier = entry.getKey();
            T curObj = entry.getValue();
            if(!object.equals(curObj)) continue;
            identifier = curIdentifier;
            break;
        }
        data.remove(identifier);
    }

    public void removeData(String identifier)
    {
        data.remove(identifier);
    }

    public void removeFile(DataFile file)
    {
        data.remove(file.getFilePath());
    }

    public T getData(String identifier)
    {
        return data.get(identifier);
    }

    public <E extends T> E getData(String identifer, Class<E> dataClass)
    {
        return (E) data.get(identifer);
    }

    public DataFile getFile(String path)
    {
        return (DataFile) data.get(path);
    }

    public int getDataCount()
    {
        return data.size();
    }
}
