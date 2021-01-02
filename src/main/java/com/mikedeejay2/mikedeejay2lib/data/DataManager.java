package com.mikedeejay2.mikedeejay2lib.data;

import java.util.HashMap;
import java.util.Map;

/**
 * A data manager that is stored in PluginBase to manage all of the plugin's data objects (Files, databases, etc).
 *
 * @author Mikedeejay2
 */
public class DataManager
{
    // The hashmap of file paths to DataFiles
    protected Map<String, DataObject> data;

    public DataManager()
    {
        this.data = new HashMap<>();
    }

    public boolean containsData(DataObject object)
    {
        return data.containsValue(object);
    }

    public boolean containsIdentifier(String identifier)
    {
        return data.containsKey(identifier);
    }

    public void addData(DataObject object, String identifier)
    {
        data.put(identifier, object);
    }

    public void addFile(DataFile file)
    {
        data.put(file.getFilePath(), file);
    }

    public void removeData(DataObject object)
    {
        String identifier = null;
        for(Map.Entry<String, DataObject> entry : data.entrySet())
        {
            String curIdentifier = entry.getKey();
            DataObject curObj = entry.getValue();
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

    public DataObject getData(String identifier)
    {
        return data.get(identifier);
    }

    public <T extends DataObject> T getData(String identifer, Class<T> dataClass)
    {
        return (T) data.get(identifer);
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
