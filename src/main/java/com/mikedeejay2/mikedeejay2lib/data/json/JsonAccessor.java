package com.mikedeejay2.mikedeejay2lib.data.json;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.mikedeejay2.mikedeejay2lib.data.section.SectionAccessor;
import org.apache.commons.lang.Validate;
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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Type;
import java.util.*;

/**
 * A Json accessor that overrides {@link SectionAccessor} for accessing Json files easily
 *
 * @author Mikedeejay2
 */
public class JsonAccessor extends SectionAccessor<JsonFile, JsonElement>
{

    /**
     * The <code>JsonObject</code> that this JsonAccessor is accessing
     */
    protected final JsonObject json;

    /**
     * Constructor for <code>JsonAccessor</code>
     *
     * @param dataFile The accessor's <code>JsonFile</code>
     * @param json     The <code>JsonObject</code> that this accessor is referencing
     */
    public JsonAccessor(@NotNull JsonFile dataFile, @NotNull JsonObject json)
    {
        super(dataFile);
        Validate.notNull(json, "json cannot be null");
        this.json = json;
    }

    /**
     * Get a <code>JsonElement</code> from this section
     *
     * @param name The name of the object to get
     * @return The requested <code>JsonElement</code>
     */
    @Override
    public JsonElement get(String name)
    {
        return json.get(name);
    }

    /**
     * Get a <code>JsonArray</code> from this section
     *
     * @param name The name of the object to get. If null, the returned array will be the array form of the current
     *             section of json being referenced by this accessor.
     * @return The requested array
     */
    public JsonArray getArray(@Nullable String name)
    {
        if(name == null || name.isEmpty()) return json.getAsJsonArray();
        if(!contains(name))
        {
            json.add(name, new JsonArray());
        }
        return json.get(name).getAsJsonArray();
    }

    /**
     * Get a <code>JsonObject</code> from this section
     *
     * @param name The name of the object to get. If null, the returned array will be the object form of the current
     *             section of json being referenced by this accessor.
     * @return The requested object
     */
    public JsonObject getObject(@Nullable String name)
    {
        if(name == null || name.isEmpty()) return json;
        if(!contains(name))
        {
            json.add(name, new JsonObject());
        }
        return json.get(name).getAsJsonObject();
    }

    /**
     * Set a <code>JsonElement</code> to this section
     *
     * @param name   The name of the object to set
     * @param object The <code>JsonElement</code> that will be set
     */
    @Override
    public void set(String name, JsonElement object)
    {
        json.add(name, object);
    }

    /**
     * {@inheritDoc}
     *
     * @param name String to search for in this section
     * @return Whether this section contains the name or not
     */
    @Override
    public boolean contains(String name)
    {
        return json.has(name);
    }

    /**
     * {@inheritDoc}
     *
     * @param name Name of object to delete
     */
    @Override
    public void delete(String name)
    {
        json.remove(name);
    }

    /**
     * Get a <code>JsonAccessor</code> in relation to this section
     *
     * @param name The name of the <code>JsonAccessor</code> to get
     * @return The requested <code>JsonAccessor</code>
     */
    @Override
    public JsonAccessor getSection(String name)
    {
        return new JsonAccessor(dataFile, getObject(name));
    }

    /**
     * {@inheritDoc}
     *
     * @param deep Whether to get nested keys or not
     * @return The requested keys
     */
    @Override
    public Set<String> getKeys(boolean deep)
    {
        Set<String> set = new HashSet<>();
        Set<Map.Entry<String, JsonElement>> entrySet = json.entrySet();
        getKeysRecursive(set, entrySet, deep);
        return set;
    }

    /**
     * Get keys recursively from the json file. Internal method to process deep getting of keys.
     *
     * @param set      The String set to fill with keys
     * @param entrySet The entrySet of the current json object
     * @param deep     Whether to iterate deep through the json to get all keys
     */
    private void getKeysRecursive(Set<String> set, Set<Map.Entry<String, JsonElement>> entrySet, boolean deep)
    {
        for(Map.Entry<String, JsonElement> element : entrySet)
        {
            String key = element.getKey();
            JsonElement jsonElement = element.getValue();
            set.add(key);
            if(!(jsonElement.isJsonObject() && deep)) continue;
            getKeysRecursive(set, jsonElement.getAsJsonObject().entrySet(), true);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param deep Whether to get nested values or not
     * @return The requested values
     */
    @Override
    public Set<JsonElement> getValues(boolean deep)
    {
        Set<JsonElement> set = new HashSet<>();
        Set<Map.Entry<String, JsonElement>> entrySet = json.entrySet();
        getValuesRecursive(set, entrySet, deep);
        return set;
    }

    /**
     * Get values recursively from the json file. Internal method to process deep getting of values.
     *
     * @param set      The <code>JsonElement</code> set to fill with values
     * @param entrySet The entrySet of the current json object
     * @param deep     Whether to iterate deep through the json to get all values
     */
    private void getValuesRecursive(Set<JsonElement> set, Set<Map.Entry<String, JsonElement>> entrySet, boolean deep)
    {
        for(Map.Entry<String, JsonElement> element : entrySet)
        {
            JsonElement jsonElement = element.getValue();
            set.add(jsonElement);
            if(!(jsonElement.isJsonObject() && deep)) continue;
            getValuesRecursive(set, jsonElement.getAsJsonObject().entrySet(), true);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param deep Whether to get nested key-value pairs or not
     * @return The requested key-value pairs
     */
    @Override
    public Map<String, JsonElement> getKeyValuePairs(boolean deep)
    {
        Map<String, JsonElement> set = new LinkedHashMap<>();
        Set<Map.Entry<String, JsonElement>> entrySet = json.entrySet();
        getKeyValuePairsRecursive(set, entrySet, deep);
        return set;
    }

    /**
     * Get key value pairs recursively from the json file. Internal method to process deep getting of key value pairs.
     *
     * @param map      The map to fill with key value pairs
     * @param entrySet The entrySet of the current json object
     * @param deep     Whether to iterate deep through the json to get all key value pairs
     */
    private void getKeyValuePairsRecursive(Map<String, JsonElement> map, Set<Map.Entry<String, JsonElement>> entrySet, boolean deep)
    {
        for(Map.Entry<String, JsonElement> element : entrySet)
        {
            String key = element.getKey();
            JsonElement jsonElement = element.getValue();
            map.put(key, jsonElement);
            if(!(jsonElement.isJsonObject() && deep)) continue;
            getKeyValuePairsRecursive(map, jsonElement.getAsJsonObject().entrySet(), true);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @param name The key name
     * @return The requested value
     */
    @Override
    public boolean getBoolean(String name)
    {
        return get(name).getAsBoolean();
    }

    /**
     * {@inheritDoc}
     *
     * @param name The key name
     * @return The requested value
     */
    @Override
    public int getInt(String name)
    {
        return get(name).getAsInt();
    }

    /**
     * {@inheritDoc}
     *
     * @param name The key name
     * @return The requested value
     */
    @Override
    public float getFloat(String name)
    {
        return get(name).getAsFloat();
    }

    /**
     * {@inheritDoc}
     *
     * @param name The key name
     * @return The requested value
     */
    @Override
    public double getDouble(String name)
    {
        return get(name).getAsDouble();
    }

    /**
     * {@inheritDoc}
     *
     * @param name The key name
     * @return The requested value
     */
    @Override
    public long getLong(String name)
    {
        return get(name).getAsLong();
    }

    /**
     * {@inheritDoc}
     *
     * @param name The key name
     * @return The requested value
     */
    @Override
    public String getString(String name)
    {
        return get(name).getAsString();
    }

    /**
     * {@inheritDoc}
     *
     * @param name The key name
     * @return The requested value
     */
    @Override
    public ItemStack getItemStack(String name)
    {
        return getSerializedInternal(getObject(name), ItemStack.class);
    }

    /**
     * {@inheritDoc}
     *
     * @param name The key name
     * @return The requested value
     */
    @Override
    public Location getLocation(String name)
    {
        return getSerializedInternal(getObject(name), Location.class);
    }

    /**
     * {@inheritDoc}
     *
     * @param name The key name
     * @return The requested value
     */
    @Override
    public Vector getVector(String name)
    {
        return getSerializedInternal(getObject(name), Vector.class);
    }

    /**
     * {@inheritDoc}
     *
     * @param name The key name
     * @return The requested value
     */
    @Override
    public ItemMeta getItemMeta(String name)
    {
        return getSerializedInternal(getObject(name), ItemMeta.class);
    }

    /**
     * {@inheritDoc}
     *
     * @param name The key name
     * @return The requested value
     */
    @Override
    public OfflinePlayer getPlayer(String name)
    {
        return getSerializedInternal(getObject(name), OfflinePlayer.class);
    }

    /**
     * {@inheritDoc}
     *
     * @param name The key name
     * @return The requested value
     */
    @Override
    public AttributeModifier getAttributeModifier(String name)
    {
        return getSerializedInternal(getObject(name), AttributeModifier.class);
    }

    /**
     * {@inheritDoc}
     *
     * @param name The key name
     * @return The requested value
     */
    @Override
    public BlockVector getBlockVector(String name)
    {
        return getSerializedInternal(getObject(name), BlockVector.class);
    }

    /**
     * {@inheritDoc}
     *
     * @param name The key name
     * @return The requested value
     */
    @Override
    public BoundingBox getBoundingBox(String name)
    {
        return getSerializedInternal(getObject(name), BoundingBox.class);
    }

    /**
     * {@inheritDoc}
     *
     * @param name The key name
     * @return The requested value
     */
    @Override
    public Color getColor(String name)
    {
        return getSerializedInternal(getObject(name), Color.class);
    }

    /**
     * {@inheritDoc}
     *
     * @param name The key name
     * @return The requested value
     */
    @Override
    public FireworkEffect getFireworkEffect(String name)
    {
        return getSerializedInternal(getObject(name), FireworkEffect.class);
    }

    /**
     * {@inheritDoc}
     *
     * @param name The key name
     * @return The requested value
     */
    @Override
    public Pattern getPattern(String name)
    {
        return getSerializedInternal(getObject(name), Pattern.class);
    }

    /**
     * {@inheritDoc}
     *
     * @param name The key name
     * @return The requested value
     */
    @Override
    public PotionEffect getPotionEffect(String name)
    {
        return getSerializedInternal(getObject(name), PotionEffect.class);
    }

    /**
     * {@inheritDoc}
     *
     * @param name The key name
     * @return The requested value
     */
    @Override
    public Material getMaterial(String name)
    {
        return Material.matchMaterial(getString(name));
    }

    /**
     * {@inheritDoc}
     *
     * @param name The key name
     * @return The requested value
     */
    @Override
    public ConfigurationSerializable getSerialized(String name)
    {
        return getSerializedInternal(getObject(name));
    }

    /**
     * {@inheritDoc}
     *
     * @param name  The key name
     * @param clazz The class type to interpret the value as
     * @param <C>
     * @return The requested value
     */
    @Override
    public <C extends ConfigurationSerializable> C getSerialized(String name, Class<C> clazz)
    {
        return getSerializedInternal(getObject(name), clazz);
    }

    /**
     * {@inheritDoc}
     *
     * @param name The key name
     * @return The requested list
     */
    @Override
    public List<Boolean> getBooleanList(String name)
    {
        List<Boolean> list = new ArrayList<>();
        JsonArray array = getArray(name);
        array.forEach(element -> list.add(element.getAsBoolean()));
        return list;
    }

    /**
     * {@inheritDoc}
     *
     * @param name The key name
     * @return The requested list
     */
    @Override
    public List<Integer> getIntList(String name)
    {
        List<Integer> list = new ArrayList<>();
        JsonArray array = getArray(name);
        array.forEach(element -> list.add(element.getAsInt()));
        return list;
    }

    /**
     * {@inheritDoc}
     *
     * @param name The key name
     * @return The requested list
     */
    @Override
    public List<Float> getFloatList(String name)
    {
        List<Float> list = new ArrayList<>();
        JsonArray array = getArray(name);
        array.forEach(element -> list.add(element.getAsFloat()));
        return list;
    }

    /**
     * {@inheritDoc}
     *
     * @param name The key name
     * @return The requested list
     */
    @Override
    public List<Double> getDoubleList(String name)
    {
        List<Double> list = new ArrayList<>();
        JsonArray array = getArray(name);
        array.forEach(element -> list.add(element.getAsDouble()));
        return list;
    }

    /**
     * {@inheritDoc}
     *
     * @param name The key name
     * @return The requested list
     */
    @Override
    public List<Long> getLongList(String name)
    {
        List<Long> list = new ArrayList<>();
        JsonArray array = getArray(name);
        array.forEach(element -> list.add(element.getAsLong()));
        return list;
    }

    /**
     * {@inheritDoc}
     *
     * @param name The key name
     * @return The requested list
     */
    @Override
    public List<String> getStringList(String name)
    {
        List<String> list = new ArrayList<>();
        JsonArray array = getArray(name);
        array.forEach(element -> list.add(element.getAsString()));
        return list;
    }

    /**
     * {@inheritDoc}
     *
     * @param name The key name
     * @return The requested list
     */
    @Override
    public List<ItemStack> getItemStackList(String name)
    {
        List<ItemStack> list = new ArrayList<>();
        JsonArray array = getArray(name);
        array.forEach(element -> list.add(new JsonAccessor(dataFile, element.getAsJsonObject()).getItemStack(null)));
        return list;
    }

    /**
     * {@inheritDoc}
     *
     * @param name The key name
     * @return The requested list
     */
    @Override
    public List<Location> getLocationList(String name)
    {
        List<Location> list = new ArrayList<>();
        JsonArray array = getArray(name);
        array.forEach(element -> list.add(new JsonAccessor(dataFile, element.getAsJsonObject()).getLocation(null)));
        return list;
    }

    /**
     * {@inheritDoc}
     *
     * @param name The key name
     * @return The requested list
     */
    @Override
    public List<Vector> getVectorList(String name)
    {
        List<Vector> list = new ArrayList<>();
        JsonArray array = getArray(name);
        array.forEach(element -> list.add(new JsonAccessor(dataFile, element.getAsJsonObject()).getVector(null)));
        return list;
    }

    /**
     * {@inheritDoc}
     *
     * @param name The key name
     * @return The requested list
     */
    @Override
    public List<OfflinePlayer> getPlayerList(String name)
    {
        List<OfflinePlayer> list = new ArrayList<>();
        JsonArray array = getArray(name);
        array.forEach(element -> list.add(new JsonAccessor(dataFile, element.getAsJsonObject()).getPlayer(null)));
        return list;
    }

    /**
     * {@inheritDoc}
     *
     * @param name The key name
     * @return The requested list
     */
    @Override
    public List<AttributeModifier> getAttributeModifierList(String name)
    {
        List<AttributeModifier> list = new ArrayList<>();
        JsonArray array = getArray(name);
        array.forEach(element -> list.add(new JsonAccessor(dataFile, element.getAsJsonObject()).getAttributeModifier(null)));
        return list;
    }

    /**
     * {@inheritDoc}
     *
     * @param name The key name
     * @return The requested list
     */
    @Override
    public List<BlockVector> getBlockVectorList(String name)
    {
        List<BlockVector> list = new ArrayList<>();
        JsonArray array = getArray(name);
        array.forEach(element -> list.add(new JsonAccessor(dataFile, element.getAsJsonObject()).getBlockVector(null)));
        return list;
    }

    /**
     * {@inheritDoc}
     *
     * @param name The key name
     * @return The requested list
     */
    @Override
    public List<BoundingBox> getBoundingBoxList(String name)
    {
        List<BoundingBox> list = new ArrayList<>();
        JsonArray array = getArray(name);
        array.forEach(element -> list.add(new JsonAccessor(dataFile, element.getAsJsonObject()).getBoundingBox(null)));
        return list;
    }

    /**
     * {@inheritDoc}
     *
     * @param name The key name
     * @return The requested list
     */
    @Override
    public List<Color> getColorList(String name)
    {
        List<Color> list = new ArrayList<>();
        JsonArray array = getArray(name);
        array.forEach(element -> list.add(new JsonAccessor(dataFile, element.getAsJsonObject()).getColor(null)));
        return list;
    }

    /**
     * {@inheritDoc}
     *
     * @param name The key name
     * @return The requested list
     */
    @Override
    public List<FireworkEffect> getFireworkEffectList(String name)
    {
        List<FireworkEffect> list = new ArrayList<>();
        JsonArray array = getArray(name);
        array.forEach(element -> list.add(new JsonAccessor(dataFile, element.getAsJsonObject()).getFireworkEffect(null)));
        return list;
    }

    /**
     * {@inheritDoc}
     *
     * @param name The key name
     * @return The requested list
     */
    @Override
    public List<Pattern> getPatternList(String name)
    {
        List<Pattern> list = new ArrayList<>();
        JsonArray array = getArray(name);
        array.forEach(element -> list.add(new JsonAccessor(dataFile, element.getAsJsonObject()).getPattern(null)));
        return list;
    }

    /**
     * {@inheritDoc}
     *
     * @param name The key name
     * @return The requested list
     */
    @Override
    public List<PotionEffect> getPotionEffectList(String name)
    {
        List<PotionEffect> list = new ArrayList<>();
        JsonArray array = getArray(name);
        array.forEach(element -> list.add(new JsonAccessor(dataFile, element.getAsJsonObject()).getPotionEffect(null)));
        return list;
    }

    /**
     * {@inheritDoc}
     *
     * @param name The key name
     * @return The requested list
     */
    @Override
    public List<Material> getMaterialList(String name)
    {
        List<Material> list = new ArrayList<>();
        JsonArray array = getArray(name);
        array.forEach(element -> list.add(Material.matchMaterial(element.getAsString())));
        return list;
    }

    /**
     * {@inheritDoc}
     *
     * @param name The key name
     * @return The requested list
     */
    @Override
    public List<ConfigurationSerializable> getSerializedList(String name)
    {
        List<ConfigurationSerializable> list = new ArrayList<>();
        JsonArray array = getArray(name);
        array.forEach(element -> list.add(new JsonAccessor(dataFile, element.getAsJsonObject()).getSerialized((String) null)));
        return list;
    }

    /**
     * {@inheritDoc}
     *
     * @param name  The key name
     * @param clazz The class type to interpret the value as
     * @param <C>   Serializable data type to be returned
     * @return The requested list
     */
    @Override
    public <C extends ConfigurationSerializable> List<C> getSerializedList(String name, Class<C> clazz)
    {
        List<C> list = new ArrayList<>();
        JsonArray array = getArray(name);
        array.forEach(element -> list.add(new JsonAccessor(dataFile, element.getAsJsonObject()).getSerialized(null, clazz)));
        return list;
    }

    /**
     * {@inheritDoc}
     *
     * @param name The key name
     * @param data The data to set to the key
     */
    @Override
    public void setBoolean(String name, boolean data)
    {
        json.addProperty(name, data);
    }

    /**
     * {@inheritDoc}
     *
     * @param name The key name
     * @param data The data to set to the key
     */
    @Override
    public void setInt(String name, int data)
    {
        json.addProperty(name, data);
    }

    /**
     * {@inheritDoc}
     *
     * @param name The key name
     * @param data The data to set to the key
     */
    @Override
    public void setFloat(String name, float data)
    {
        json.addProperty(name, data);
    }

    /**
     * {@inheritDoc}
     *
     * @param name The key name
     * @param data The data to set to the key
     */
    @Override
    public void setDouble(String name, double data)
    {
        json.addProperty(name, data);
    }

    /**
     * {@inheritDoc}
     *
     * @param name The key name
     * @param data The data to set to the key
     */
    @Override
    public void setLong(String name, long data)
    {
        json.addProperty(name, data);
    }

    /**
     * {@inheritDoc}
     *
     * @param name The key name
     * @param data The data to set to the key
     */
    @Override
    public void setString(String name, String data)
    {
        json.addProperty(name, data);
    }

    /**
     * {@inheritDoc}
     *
     * @param name The key name
     * @param data The data to set to the key
     */
    @Override
    public void setItemStack(String name, ItemStack data)
    {
        setSerializedInternal(getObject(name), data);
    }

    /**
     * {@inheritDoc}
     *
     * @param name The key name
     * @param data The data to set to the key
     */
    @Override
    public void setLocation(String name, Location data)
    {
        setSerializedInternal(getObject(name), data);
    }

    /**
     * {@inheritDoc}
     *
     * @param name The key name
     * @param data The data to set to the key
     */
    @Override
    public void setVector(String name, Vector data)
    {
        setSerializedInternal(getObject(name), data);
    }

    /**
     * {@inheritDoc}
     *
     * @param name The key name
     * @param data The data to set to the key
     */
    @Override
    public void setPlayer(String name, OfflinePlayer data)
    {
        setSerializedInternal(getObject(name), data);
    }

    /**
     * {@inheritDoc}
     *
     * @param name The key name
     * @param data The data to set to the key
     */
    @Override
    public void setAttributeModifier(String name, AttributeModifier data)
    {
        setSerializedInternal(getObject(name), data);
    }

    /**
     * {@inheritDoc}
     *
     * @param name The key name
     * @param data The data to set to the key
     */
    @Override
    public void setBlockVector(String name, BlockVector data)
    {
        setSerializedInternal(getObject(name), data);
    }

    /**
     * {@inheritDoc}
     *
     * @param name The key name
     * @param data The data to set to the key
     */
    @Override
    public void setBoundingBox(String name, BoundingBox data)
    {
        setSerializedInternal(getObject(name), data);
    }

    /**
     * {@inheritDoc}
     *
     * @param name The key name
     * @param data The data to set to the key
     */
    @Override
    public void setColor(String name, Color data)
    {
        setSerializedInternal(getObject(name), data);
    }

    /**
     * {@inheritDoc}
     *
     * @param name The key name
     * @param data The data to set to the key
     */
    @Override
    public void setFireworkEffect(String name, FireworkEffect data)
    {
        setSerializedInternal(getObject(name), data);
    }

    /**
     * {@inheritDoc}
     *
     * @param name The key name
     * @param data The data to set to the key
     */
    @Override
    public void setPattern(String name, Pattern data)
    {
        setSerializedInternal(getObject(name), data);
    }

    /**
     * {@inheritDoc}
     *
     * @param name The key name
     * @param data The data to set to the key
     */
    @Override
    public void setPotionEffect(String name, PotionEffect data)
    {
        setSerializedInternal(getObject(name), data);
    }

    /**
     * {@inheritDoc}
     *
     * @param name The key name
     * @param data The data to set to the key
     */
    @Override
    public void setMaterial(String name, Material data)
    {
        setString(name, data.toString());
    }

    /**
     * {@inheritDoc}
     *
     * @param name The key name
     * @param data The data to set to the key
     */
    @Override
    public void setSerialized(String name, ConfigurationSerializable data)
    {
        setSerializedInternal(getObject(name), data);
    }

    /**
     * {@inheritDoc}
     *
     * @param name The key name
     * @param data The <code>List</code> of data to set to the key
     */
    @Override
    public void setBooleanList(String name, List<Boolean> data)
    {
        JsonArray array = getArray(name);
        data.forEach(array::add);
        json.add(name, array);
    }

    /**
     * {@inheritDoc}
     *
     * @param name The key name
     * @param data The <code>List</code> of data to set to the key
     */
    @Override
    public void setIntList(String name, List<Integer> data)
    {
        JsonArray array = getArray(name);
        data.forEach(array::add);
        json.add(name, array);
    }

    /**
     * {@inheritDoc}
     *
     * @param name The key name
     * @param data The <code>List</code> of data to set to the key
     */
    @Override
    public void setFloatList(String name, List<Float> data)
    {
        JsonArray array = getArray(name);
        data.forEach(array::add);
        json.add(name, array);
    }

    /**
     * {@inheritDoc}
     *
     * @param name The key name
     * @param data The <code>List</code> of data to set to the key
     */
    @Override
    public void setDoubleList(String name, List<Double> data)
    {
        JsonArray array = getArray(name);
        data.forEach(array::add);
        json.add(name, array);
    }

    /**
     * {@inheritDoc}
     *
     * @param name The key name
     * @param data The <code>List</code> of data to set to the key
     */
    @Override
    public void setLongList(String name, List<Long> data)
    {
        JsonArray array = getArray(name);
        data.forEach(array::add);
        json.add(name, array);
    }

    /**
     * {@inheritDoc}
     *
     * @param name The key name
     * @param data The <code>List</code> of data to set to the key
     */
    @Override
    public void setStringList(String name, List<String> data)
    {
        JsonArray array = getArray(name);
        data.forEach(array::add);
        json.add(name, array);
    }

    /**
     * {@inheritDoc}
     *
     * @param name The key name
     * @param data The <code>List</code> of data to set to the key
     */
    @Override
    public void setItemStackList(String name, List<ItemStack> data)
    {
        setSerializedListInternal(name, data);
    }

    /**
     * {@inheritDoc}
     *
     * @param name The key name
     * @param data The <code>List</code> of data to set to the key
     */
    @Override
    public void setLocationList(String name, List<Location> data)
    {
        setSerializedListInternal(name, data);
    }

    /**
     * {@inheritDoc}
     *
     * @param name The key name
     * @param data The <code>List</code> of data to set to the key
     */
    @Override
    public void setVectorList(String name, List<Vector> data)
    {
        setSerializedListInternal(name, data);
    }

    /**
     * {@inheritDoc}
     *
     * @param name The key name
     * @param data The <code>List</code> of data to set to the key
     */
    @Override
    public void setPlayerList(String name, List<OfflinePlayer> data)
    {
        setSerializedListInternal(name, data);
    }

    /**
     * {@inheritDoc}
     *
     * @param name The key name
     * @param data The <code>List</code> of data to set to the key
     */
    @Override
    public void setAttributeModifierList(String name, List<AttributeModifier> data)
    {
        setSerializedListInternal(name, data);
    }

    /**
     * {@inheritDoc}
     *
     * @param name The key name
     * @param data The <code>List</code> of data to set to the key
     */
    @Override
    public void setBlockVectorList(String name, List<BlockVector> data)
    {
        setSerializedListInternal(name, data);
    }

    /**
     * {@inheritDoc}
     *
     * @param name The key name
     * @param data The <code>List</code> of data to set to the key
     */
    @Override
    public void setBoundingBoxList(String name, List<BoundingBox> data)
    {
        setSerializedListInternal(name, data);
    }

    /**
     * {@inheritDoc}
     *
     * @param name The key name
     * @param data The <code>List</code> of data to set to the key
     */
    @Override
    public void setColorList(String name, List<Color> data)
    {
        setSerializedListInternal(name, data);
    }

    /**
     * {@inheritDoc}
     *
     * @param name The key name
     * @param data The <code>List</code> of data to set to the key
     */
    @Override
    public void setFireworkEffectList(String name, List<FireworkEffect> data)
    {
        setSerializedListInternal(name, data);
    }

    /**
     * {@inheritDoc}
     *
     * @param name The key name
     * @param data The <code>List</code> of data to set to the key
     */
    @Override
    public void setPatternList(String name, List<Pattern> data)
    {
        setSerializedListInternal(name, data);
    }

    /**
     * {@inheritDoc}
     *
     * @param name The key name
     * @param data The <code>List</code> of data to set to the key
     */
    @Override
    public void setPotionEffectList(String name, List<PotionEffect> data)
    {
        setSerializedListInternal(name, data);
    }

    /**
     * {@inheritDoc}
     *
     * @param name The key name
     * @param data The <code>List</code> of data to set to the key
     */
    @Override
    public void setMaterialList(String name, List<Material> data)
    {
        JsonArray array = getArray(name);
        data.forEach(material -> array.add(material.toString()));
        json.add(name, array);
    }

    /**
     * {@inheritDoc}
     *
     * @param name The key name
     * @param data The <code>List</code> of data to set to the key
     */
    @Override
    public void setSerializedList(String name, List<ConfigurationSerializable> data)
    {
        setSerializedListInternal(name, data);
    }

    /**
     * Add a <code>ConfigurationSerializable</code> object to a <code>JsonObject</code>
     *
     * @param jsonObject The <code>JsonObject</code> to add the data to
     * @param data       The data to add to the jsonObject
     */
    private void setSerializedInternal(JsonObject jsonObject, ConfigurationSerializable data)
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
                setSerializedInternal(jsonObject.getAsJsonObject(memberName), meta);
                continue;
            }
            JsonElement element = gson.toJsonTree(object);
            jsonObject.add(memberName, element);
        }
    }

    /**
     * Get a <code>ConfigurationSerializable</code> object from a <code>JsonObject</code>
     *
     * @param jsonObject Object to get the <code>ConfigurationSerializable</code> from
     * @param clazz      The class that the object should be serialized to
     * @param <T>        The type of <code>ConfigurationSerializable</code>, same as class type
     * @return The requested <code>ConfigurationSerializable</code>
     */
    private <T extends ConfigurationSerializable> T getSerializedInternal(JsonObject jsonObject, Class<T> clazz)
    {
        Map<String, Object> map = getSerializedMap(jsonObject.entrySet());
        return clazz.cast(ConfigurationSerialization.deserializeObject(map, clazz));
    }

    /**
     * Get a <code>ConfigurationSerializable</code> object from a <code>JsonObject</code>
     *
     * @param jsonObject Object to get the <code>ConfigurationSerializable</code> from
     * @return The requested <code>ConfigurationSerializable</code>
     */
    private ConfigurationSerializable getSerializedInternal(JsonObject jsonObject)
    {
        Map<String, Object> map = getSerializedMap(jsonObject.entrySet());
        return ConfigurationSerialization.deserializeObject(map);
    }

    /**
     * Converts a json entrySet to a <code>Map</code> of <code>String</code>-<code>Object</code>
     *
     * @param set The json entrySet
     * @return The new <code>Map</code>
     */
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

    /**
     * Get a {@link ConfigurationSerializable} from a <code>Map</code>
     *
     * @param map The map to interpret from
     * @return The new <code>ConfigurationSerializable</code>
     */
    private ConfigurationSerializable getSerialized(Map<String, Object> map)
    {
        return ConfigurationSerialization.deserializeObject(map);
    }

    /**
     * Set a serializable list to the current json.
     *
     * @param name The memberName that the list will be added under
     * @param data The list of data to add
     */
    private void setSerializedListInternal(String name, List<? extends ConfigurationSerializable> data)
    {
        JsonArray array = new JsonArray();
        data.forEach(vector -> {
            JsonObject object = new JsonObject();
            setSerializedInternal(object, vector);
            array.add(object);
        });
        json.add(name, array);
    }
}
