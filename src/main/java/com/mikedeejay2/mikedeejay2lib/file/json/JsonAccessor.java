package com.mikedeejay2.mikedeejay2lib.file.json;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.mikedeejay2.mikedeejay2lib.file.section.SectionAccessor;
import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.lang.reflect.Type;
import java.util.*;

public class JsonAccessor extends SectionAccessor<JsonFile, JsonElement>
{
    private JsonObject json;

    public JsonAccessor(JsonFile dataFile, JsonObject json)
    {
        super(dataFile);
        this.json = json;
    }

    @Override
    public JsonElement get(String name)
    {
        return json.get(name);
    }

    public JsonArray getArray(String name)
    {
        return json.get(name).getAsJsonArray();
    }

    public JsonObject getObject(String name)
    {
        if(name.isEmpty() || name == null) return json;
        return json.get(name).getAsJsonObject();
    }

    @Override
    public void set(String name, JsonElement object)
    {
        json.add(name, object);
    }

    @Override
    public void contains(String name)
    {
        json.has(name);
    }

    @Override
    public void delete(String name)
    {
        json.remove(name);
    }

    @Override
    public SectionAccessor<JsonFile, JsonElement> getSection(String name)
    {
        return new JsonAccessor(dataFile, getObject(name));
    }

    @Override
    public Set<String> getKeys(boolean deep)
    {
        HashSet<String> set = new HashSet<>();
        Set<Map.Entry<String, JsonElement>> entrySet = json.entrySet();
        getKeysRecursive(set, entrySet, deep);
        return set;
    }

    private void getKeysRecursive(HashSet<String> set, Set<Map.Entry<String, JsonElement>> entrySet, boolean deep)
    {
        for(Map.Entry<String, JsonElement> element : entrySet)
        {
            String key = element.getKey();
            JsonElement jsonElement = element.getValue();
            set.add(key);
            if(!(jsonElement.isJsonObject() && deep)) continue;
            getKeysRecursive(set, jsonElement.getAsJsonObject().entrySet(), deep);
        }
    }

    @Override
    public Set<JsonElement> getValues(boolean deep)
    {
        HashSet<JsonElement> set = new HashSet<>();
        Set<Map.Entry<String, JsonElement>> entrySet = json.entrySet();
        getValuesRecursive(set, entrySet, deep);
        return set;
    }

    private void getValuesRecursive(Set<JsonElement> set, Set<Map.Entry<String, JsonElement>> entrySet, boolean deep)
    {
        for(Map.Entry<String, JsonElement> element : entrySet)
        {
            JsonElement jsonElement = element.getValue();
            set.add(jsonElement);
            if(!(jsonElement.isJsonObject() && deep)) continue;
            getValuesRecursive(set, jsonElement.getAsJsonObject().entrySet(), deep);
        }
    }

    @Override
    public Map<String, JsonElement> getKeyValuePairs(boolean deep)
    {
        Map<String, JsonElement> set = new HashMap<>();
        Set<Map.Entry<String, JsonElement>> entrySet = json.entrySet();
        getKeyValuePairsRecursive(set, entrySet, deep);
        return set;
    }

    private void getKeyValuePairsRecursive(Map<String, JsonElement> set, Set<Map.Entry<String, JsonElement>> entrySet, boolean deep)
    {
        for(Map.Entry<String, JsonElement> element : entrySet)
        {
            String key = element.getKey();
            JsonElement jsonElement = element.getValue();
            set.put(key, jsonElement);
            if(!(jsonElement.isJsonObject() && deep)) continue;
            getKeyValuePairsRecursive(set, jsonElement.getAsJsonObject().entrySet(), deep);
        }
    }

    @Override
    public boolean getBoolean(String name)
    {
        return get(name).getAsBoolean();
    }

    @Override
    public int getInt(String name)
    {
        return get(name).getAsInt();
    }

    @Override
    public float getFloat(String name)
    {
        return get(name).getAsFloat();
    }

    @Override
    public double getDouble(String name)
    {
        return get(name).getAsDouble();
    }

    @Override
    public long getLong(String name)
    {
        return get(name).getAsLong();
    }

    @Override
    public String getString(String name)
    {
        return get(name).getAsString();
    }

    @Override
    public ItemStack getItemStack(String name)
    {
        Map<String, Object> map = getSerialized(getObject(name));
        return ItemStack.deserialize(map);
    }

    @Override
    public Location getLocation(String name)
    {
        Map<String, Object> map = getSerialized(getObject(name));
        return Location.deserialize(map);
    }

    @Override
    public Vector getVector(String name)
    {
        Map<String, Object> map = getSerialized(getObject(name));
        return Vector.deserialize(map);
    }

    @Override
    public List<Boolean> getBooleanList(String name)
    {
        List<Boolean> list = new ArrayList<>();
        JsonArray array = getArray(name);
        int size = array.size();
        array.forEach(element -> {
            list.add(element.getAsBoolean());
        });
        return list;
    }

    @Override
    public List<Integer> getIntList(String name)
    {
        List<Integer> list = new ArrayList<>();
        JsonArray array = getArray(name);
        int size = array.size();
        array.forEach(element -> {
            list.add(element.getAsInt());
        });
        return list;
    }

    @Override
    public List<Float> getFloatList(String name)
    {
        List<Float> list = new ArrayList<>();
        JsonArray array = getArray(name);
        int size = array.size();
        array.forEach(element -> {
            list.add(element.getAsFloat());
        });
        return list;
    }

    @Override
    public List<Double> getDoubleList(String name)
    {
        List<Double> list = new ArrayList<>();
        JsonArray array = getArray(name);
        int size = array.size();
        array.forEach(element -> {
            list.add(element.getAsDouble());
        });
        return list;
    }

    @Override
    public List<Long> getLongList(String name)
    {
        List<Long> list = new ArrayList<>();
        JsonArray array = getArray(name);
        int size = array.size();
        array.forEach(element -> {
            list.add(element.getAsLong());
        });
        return list;
    }

    @Override
    public List<String> getStringList(String name)
    {
        List<String> list = new ArrayList<>();
        JsonArray array = getArray(name);
        int size = array.size();
        array.forEach(element -> {
            list.add(element.getAsString());
        });
        return list;
    }

    @Override
    public List<ItemStack> getItemStackList(String name)
    {
        List<ItemStack> list = new ArrayList<>();
        JsonArray array = getArray(name);
        int size = array.size();
        array.forEach(element -> {
            list.add(new JsonAccessor(dataFile, element.getAsJsonObject()).getItemStack(name));
        });
        return list;
    }

    @Override
    public List<Location> getLocationList(String name)
    {
        List<Location> list = new ArrayList<>();
        JsonArray array = getArray(name);
        int size = array.size();
        array.forEach(element -> {
            list.add(new JsonAccessor(dataFile, element.getAsJsonObject()).getLocation(name));
        });
        return list;
    }

    @Override
    public List<Vector> getVectorList(String name)
    {
        List<Vector> list = new ArrayList<>();
        JsonArray array = getArray(name);
        int size = array.size();
        array.forEach(element -> {
            list.add(new JsonAccessor(dataFile, element.getAsJsonObject()).getVector(name));
        });
        return list;
    }

    @Override
    public void setBoolean(String name, boolean data)
    {
        json.addProperty(name, data);
    }

    @Override
    public void setInt(String name, int data)
    {
        json.addProperty(name, data);
    }

    @Override
    public void setFloat(String name, float data)
    {
        json.addProperty(name, data);
    }

    @Override
    public void setDouble(String name, double data)
    {
        json.addProperty(name, data);
    }

    @Override
    public void setLong(String name, long data)
    {
        json.addProperty(name, data);
    }

    @Override
    public void setString(String name, String data)
    {
        json.addProperty(name, data);
    }

    @Override
    public void setItemStack(String name, ItemStack data)
    {
        setSerializable(getObject(name), data);
    }

    @Override
    public void setLocation(String name, Location data)
    {
        setSerializable(getObject(name), data);
    }

    @Override
    public void setVector(String name, Vector data)
    {
        setSerializable(getObject(name), data);
    }

    @Override
    public void setBooleanList(String name, List<Boolean> data)
    {
        JsonArray array = new JsonArray();
        data.forEach(array::add);
        json.add(name, new JsonArray());
    }

    @Override
    public void setIntList(String name, List<Integer> data)
    {
        JsonArray array = new JsonArray();
        data.forEach(array::add);
        json.add(name, new JsonArray());
    }

    @Override
    public void setFloatList(String name, List<Float> data)
    {
        JsonArray array = new JsonArray();
        data.forEach(array::add);
        json.add(name, new JsonArray());
    }

    @Override
    public void setDoubleList(String name, List<Double> data)
    {
        JsonArray array = new JsonArray();
        data.forEach(array::add);
        json.add(name, new JsonArray());
    }

    @Override
    public void setLongList(String name, List<Long> data)
    {
        JsonArray array = new JsonArray();
        data.forEach(array::add);
        json.add(name, new JsonArray());
    }

    @Override
    public void setStringList(String name, List<String> data)
    {
        JsonArray array = new JsonArray();
        data.forEach(array::add);
        json.add(name, new JsonArray());
    }

    @Override
    public void setItemStackList(String name, List<ItemStack> data)
    {
        JsonArray array = new JsonArray();
        data.forEach(item -> {
            JsonObject object = new JsonObject();
            setSerializable(object, item);
            array.add(object);
        });
        json.add(name, new JsonArray());
    }

    @Override
    public void setLocationList(String name, List<Location> data)
    {
        JsonArray array = new JsonArray();
        data.forEach(location -> {
            JsonObject object = new JsonObject();
            setSerializable(object, location);
            array.add(object);
        });
        json.add(name, new JsonArray());
    }

    @Override
    public void setVectorList(String name, List<Vector> data)
    {
        JsonArray array = new JsonArray();
        data.forEach(vector -> {
            JsonObject object = new JsonObject();
            setSerializable(object, vector);
            array.add(object);
        });
        json.add(name, new JsonArray());
    }

    private void setSerializable(JsonObject jsonObject, ConfigurationSerializable data)
    {
        Map<String, Object> map = data.serialize();
        Gson gson = new GsonBuilder().create();
        for(Map.Entry<String, Object> entry : map.entrySet())
        {
            String memberName = entry.getKey();
            Object object = entry.getValue();
            Type type = new TypeToken<Object>(){}.getType();
            JsonElement element = gson.toJsonTree(object, type);
            jsonObject.add(memberName, element);
        }
    }

    private Map<String, Object> getSerialized(JsonObject jsonObject)
    {
        Set<Map.Entry<String, JsonElement>> set = jsonObject.entrySet();
        Map<String, Object> map = new HashMap<>();
        Gson gson = new GsonBuilder().create();
        for(Map.Entry<String, JsonElement> entry : set)
        {
            String member = entry.getKey();
            Type type = new TypeToken<Object>(){}.getType();
            Object object = gson.fromJson(entry.getValue(), type);
            map.put(member, object);
        }
        return map;
    }
}
