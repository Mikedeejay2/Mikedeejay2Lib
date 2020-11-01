package com.mikedeejay2.mikedeejay2lib.file.json;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.mikedeejay2.mikedeejay2lib.file.section.SectionAccessor;
import org.bukkit.*;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.block.banner.Pattern;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.util.BlockVector;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;

import java.lang.reflect.Type;
import java.util.*;

/**
 * A Json accessor that overrides <tt>SectionAccessor</tt> for accessing Json files easily
 *
 * @author Mikedeejay2
 */
public class JsonAccessor extends SectionAccessor<JsonFile, JsonElement>
{
    // The JsonObject that this JsonAccessor is accessing
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
        if(!contains(name))
        {
            json.add(name, new JsonArray());
        }
        return json.get(name).getAsJsonArray();
    }

    public JsonObject getObject(String name)
    {
        if(name == null || name.isEmpty()) return json;
        if(!contains(name))
        {
            json.add(name, new JsonObject());
        }
        return json.get(name).getAsJsonObject();
    }

    @Override
    public void set(String name, JsonElement object)
    {
        json.add(name, object);
    }

    @Override
    public boolean contains(String name)
    {
        return json.has(name);
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
        return getSerialized(getObject(name), ItemStack.class);
    }

    @Override
    public Location getLocation(String name)
    {
        return getSerialized(getObject(name), Location.class);
    }

    @Override
    public Vector getVector(String name)
    {
        return getSerialized(getObject(name), Vector.class);
    }

    @Override
    public ItemMeta getItemMeta(String name)
    {
        return getSerialized(getObject(name), ItemMeta.class);
    }

    @Override
    public OfflinePlayer getPlayer(String name)
    {
        return getSerialized(getObject(name), OfflinePlayer.class);
    }

    @Override
    public AttributeModifier getAttributeModifier(String name)
    {
        return getSerialized(getObject(name), AttributeModifier.class);
    }

    @Override
    public BlockVector getBlockVector(String name)
    {
        return getSerialized(getObject(name), BlockVector.class);
    }

    @Override
    public BoundingBox getBoundingBox(String name)
    {
        return getSerialized(getObject(name), BoundingBox.class);
    }

    @Override
    public Color getColor(String name)
    {
        return getSerialized(getObject(name), Color.class);
    }

    @Override
    public FireworkEffect getFireworkEffect(String name)
    {
        return getSerialized(getObject(name), FireworkEffect.class);
    }

    @Override
    public Pattern getPattern(String name)
    {
        return getSerialized(getObject(name), Pattern.class);
    }

    @Override
    public PotionEffect getPotionEffect(String name)
    {
        return getSerialized(getObject(name), PotionEffect.class);
    }

    @Override
    public List<Boolean> getBooleanList(String name)
    {
        List<Boolean> list = new ArrayList<>();
        JsonArray array = getArray(name);
        array.forEach(element -> list.add(element.getAsBoolean()));
        return list;
    }

    @Override
    public List<Integer> getIntList(String name)
    {
        List<Integer> list = new ArrayList<>();
        JsonArray array = getArray(name);
        array.forEach(element -> list.add(element.getAsInt()));
        return list;
    }

    @Override
    public List<Float> getFloatList(String name)
    {
        List<Float> list = new ArrayList<>();
        JsonArray array = getArray(name);
        array.forEach(element -> list.add(element.getAsFloat()));
        return list;
    }

    @Override
    public List<Double> getDoubleList(String name)
    {
        List<Double> list = new ArrayList<>();
        JsonArray array = getArray(name);
        array.forEach(element -> list.add(element.getAsDouble()));
        return list;
    }

    @Override
    public List<Long> getLongList(String name)
    {
        List<Long> list = new ArrayList<>();
        JsonArray array = getArray(name);
        array.forEach(element -> list.add(element.getAsLong()));
        return list;
    }

    @Override
    public List<String> getStringList(String name)
    {
        List<String> list = new ArrayList<>();
        JsonArray array = getArray(name);
        array.forEach(element -> list.add(element.getAsString()));
        return list;
    }

    @Override
    public List<ItemStack> getItemStackList(String name)
    {
        List<ItemStack> list = new ArrayList<>();
        JsonArray array = getArray(name);
        array.forEach(element -> list.add(new JsonAccessor(dataFile, element.getAsJsonObject()).getItemStack(null)));
        return list;
    }

    @Override
    public List<Location> getLocationList(String name)
    {
        List<Location> list = new ArrayList<>();
        JsonArray array = getArray(name);
        array.forEach(element -> list.add(new JsonAccessor(dataFile, element.getAsJsonObject()).getLocation(null)));
        return list;
    }

    @Override
    public List<Vector> getVectorList(String name)
    {
        List<Vector> list = new ArrayList<>();
        JsonArray array = getArray(name);
        array.forEach(element -> list.add(new JsonAccessor(dataFile, element.getAsJsonObject()).getVector(null)));
        return list;
    }

    @Override
    public List<OfflinePlayer> getPlayerList(String name)
    {
        List<OfflinePlayer> list = new ArrayList<>();
        JsonArray array = getArray(name);
        array.forEach(element -> list.add(new JsonAccessor(dataFile, element.getAsJsonObject()).getPlayer(null)));
        return list;
    }

    @Override
    public List<AttributeModifier> getAttributeModifierList(String name)
    {
        List<AttributeModifier> list = new ArrayList<>();
        JsonArray array = getArray(name);
        array.forEach(element -> list.add(new JsonAccessor(dataFile, element.getAsJsonObject()).getAttributeModifier(null)));
        return list;
    }

    @Override
    public List<BlockVector> getBlockVectorList(String name)
    {
        List<BlockVector> list = new ArrayList<>();
        JsonArray array = getArray(name);
        array.forEach(element -> list.add(new JsonAccessor(dataFile, element.getAsJsonObject()).getBlockVector(null)));
        return list;
    }

    @Override
    public List<BoundingBox> getBoundingBoxList(String name)
    {
        List<BoundingBox> list = new ArrayList<>();
        JsonArray array = getArray(name);
        array.forEach(element -> list.add(new JsonAccessor(dataFile, element.getAsJsonObject()).getBoundingBox(null)));
        return list;
    }

    @Override
    public List<Color> getColorList(String name)
    {
        List<Color> list = new ArrayList<>();
        JsonArray array = getArray(name);
        array.forEach(element -> list.add(new JsonAccessor(dataFile, element.getAsJsonObject()).getColor(null)));
        return list;
    }

    @Override
    public List<FireworkEffect> getFireworkEffectList(String name)
    {
        List<FireworkEffect> list = new ArrayList<>();
        JsonArray array = getArray(name);
        array.forEach(element -> list.add(new JsonAccessor(dataFile, element.getAsJsonObject()).getFireworkEffect(null)));
        return list;
    }

    @Override
    public List<Pattern> getPatternList(String name)
    {
        List<Pattern> list = new ArrayList<>();
        JsonArray array = getArray(name);
        array.forEach(element -> list.add(new JsonAccessor(dataFile, element.getAsJsonObject()).getPattern(null)));
        return list;
    }

    @Override
    public List<PotionEffect> getPotionEffectList(String name)
    {
        List<PotionEffect> list = new ArrayList<>();
        JsonArray array = getArray(name);
        array.forEach(element -> list.add(new JsonAccessor(dataFile, element.getAsJsonObject()).getPotionEffect(null)));
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
    public void setPlayer(String name, OfflinePlayer data)
    {
        setSerializable(getObject(name), data);
    }

    @Override
    public void setAttributeModifier(String name, AttributeModifier data)
    {
        setSerializable(getObject(name), data);
    }

    @Override
    public void setBlockVector(String name, BlockVector data)
    {
        setSerializable(getObject(name), data);
    }

    @Override
    public void setBoundingBox(String name, BoundingBox data)
    {
        setSerializable(getObject(name), data);
    }

    @Override
    public void setColor(String name, Color data)
    {
        setSerializable(getObject(name), data);
    }

    @Override
    public void setFireworkEffect(String name, FireworkEffect data)
    {
        setSerializable(getObject(name), data);
    }

    @Override
    public void setPattern(String name, Pattern data)
    {
        setSerializable(getObject(name), data);
    }

    @Override
    public void setPotionEffect(String name, PotionEffect data)
    {
        setSerializable(getObject(name), data);
    }

    @Override
    public void setBooleanList(String name, List<Boolean> data)
    {
        JsonArray array = getArray(name);
        data.forEach(array::add);
        json.add(name, array);
    }

    @Override
    public void setIntList(String name, List<Integer> data)
    {
        JsonArray array = getArray(name);
        data.forEach(array::add);
        json.add(name, array);
    }

    @Override
    public void setFloatList(String name, List<Float> data)
    {
        JsonArray array = getArray(name);
        data.forEach(array::add);
        json.add(name, array);
    }

    @Override
    public void setDoubleList(String name, List<Double> data)
    {
        JsonArray array = getArray(name);
        data.forEach(array::add);
        json.add(name, array);
    }

    @Override
    public void setLongList(String name, List<Long> data)
    {
        JsonArray array = getArray(name);
        data.forEach(array::add);
        json.add(name, array);
    }

    @Override
    public void setStringList(String name, List<String> data)
    {
        JsonArray array = getArray(name);
        data.forEach(array::add);
        json.add(name, array);
    }

    @Override
    public void setItemStackList(String name, List<ItemStack> data)
    {
        setSerializableList(name, data);
    }

    @Override
    public void setLocationList(String name, List<Location> data)
    {
        setSerializableList(name, data);
    }

    @Override
    public void setVectorList(String name, List<Vector> data)
    {
        setSerializableList(name, data);
    }

    @Override
    public void setPlayerList(String name, List<OfflinePlayer> data)
    {
        setSerializableList(name, data);
    }

    @Override
    public void setAttributeModifierList(String name, List<AttributeModifier> data)
    {
        setSerializableList(name, data);
    }

    @Override
    public void setBlockVectorList(String name, List<BlockVector> data)
    {
        setSerializableList(name, data);
    }

    @Override
    public void setBoundingBoxList(String name, List<BoundingBox> data)
    {
        setSerializableList(name, data);
    }

    @Override
    public void setColorList(String name, List<Color> data)
    {
        setSerializableList(name, data);
    }

    @Override
    public void setFireworkEffectList(String name, List<FireworkEffect> data)
    {
        setSerializableList(name, data);
    }

    @Override
    public void setPatternList(String name, List<Pattern> data)
    {
        setSerializableList(name, data);
    }

    @Override
    public void setPotionEffectList(String name, List<PotionEffect> data)
    {
        setSerializableList(name, data);
    }

    /**
     * Add a <tt>ConfigurationSerializable</tt> object to a <tt>JsonObject</tt>
     *
     * @param jsonObject The <tt>JsonObject</tt> to add the data to
     * @param data The data to add to the jsonObject
     */
    private void setSerializable(JsonObject jsonObject, ConfigurationSerializable data)
    {
        Map<String, Object> map = data.serialize();
        Gson gson = new GsonBuilder().create();
        JsonElement typeElement = gson.toJsonTree(ConfigurationSerialization.getAlias(data.getClass()));
        jsonObject.add(ConfigurationSerialization.SERIALIZED_TYPE_KEY, typeElement);
        for(Map.Entry<String, Object> entry : map.entrySet())
        {
            String memberName = entry.getKey();
            Object object = entry.getValue();
            if(object instanceof ConfigurationSerializable)
            {
                ConfigurationSerializable meta = (ConfigurationSerializable)object;
                jsonObject.add(memberName, new JsonObject());
                setSerializable(jsonObject.getAsJsonObject(memberName), meta);
                continue;
            }
            JsonElement element = gson.toJsonTree(object);
            jsonObject.add(memberName, element);
        }
    }

    /**
     * Get a <tt>ConfigurationSerializable</tt> object from a <tt>JsonObject</tt>
     *
     * @param jsonObject Object to get the <tt>ConfigurationSerializable</tt> from
     * @param clazz The class that the object should be serialized to
     * @param <T> The type of <tt>ConfigurationSerializable</tt>, same as class type
     * @return The requested <tt>ConfigurationSerializable</tt>
     */
    private <T extends ConfigurationSerializable> T getSerialized(JsonObject jsonObject, Class<T> clazz)
    {
        Map<String, Object> map = getSerializedMap(jsonObject.entrySet());
        return (T)ConfigurationSerialization.deserializeObject(map, clazz);
    }

    private Map<String, Object> getSerializedMap(Set<Map.Entry<String, JsonElement>> set)
    {
        Map<String, Object> map = new HashMap<>();
        Gson gson = new GsonBuilder().create();
        for(Map.Entry<String, JsonElement> entry : set)
        {
            String member = entry.getKey();
            Type type = new TypeToken<Object>(){}.getType();
            Object object = gson.fromJson(entry.getValue(), type);
            if(object instanceof Map)
            {
                Map<String, Object> newMap = (Map<String, Object>) object;
                map.put(member, getSerialized(newMap));
                continue;
            }
            map.put(member, object);
        }
        return map;
    }

    private <T extends ConfigurationSerializable> T getSerialized(Map<String, Object> map)
    {
        return (T)ConfigurationSerialization.deserializeObject(map);
    }

    /**
     * Set a serializable list to the current json.
     *
     * @param name The memberName that the list will be added under
     * @param data The list of data to add
     */
    private void setSerializableList(String name, List<? extends ConfigurationSerializable> data)
    {
        JsonArray array = new JsonArray();
        data.forEach(vector -> {
            JsonObject object = new JsonObject();
            setSerializable(object, vector);
            array.add(object);
        });
        json.add(name, array);
    }
}
