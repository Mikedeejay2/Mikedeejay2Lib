package com.mikedeejay2.mikedeejay2lib.data.section;

import com.mikedeejay2.mikedeejay2lib.data.DataFile;
import org.apache.commons.lang3.Validate;
import org.bukkit.*;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.block.banner.Pattern;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.util.BlockVector;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A large abstract class for easily accessing anything in a DataFile.
 *
 * @param <D> The DataFile type that is being accessed.
 * @param <T> The default return type of the Section Accessor, see <code>get</code> and <code>set</code> methods
 * @author Mikedeejay2
 */
public abstract class SectionAccessor<D extends DataFile, T>
{

    /**
     * The parent {@link DataFile} that this <code>SectionAccessor</code> is accessing
     */
    protected final D dataFile;
    /**
     * The file path of this <code>SectionAccessor</code>
     */
    protected final String filePath;

    /**
     * Constructs a new <code>SectionAccessor</code>
     *
     * @param dataFile The {@link DataFile} that this <code>SectionAccessor</code> references
     */
    public SectionAccessor(D dataFile)
    {
        Validate.notNull(dataFile, "dataFile cannot be null");
        this.dataFile = dataFile;
        this.filePath = dataFile.getFilePath();
    }

    /**
     * Get a generic object from this section
     *
     * @param name The name of the object to get
     * @return The requested object
     */
    public abstract T get(String name);

    /**
     * Set a generic object to this section
     *
     * @param name   The name of the object to set
     * @param object The object that will be set
     */
    public abstract void set(String name, T object);

    /**
     * See if this section contains a piece of data under name
     *
     * @param name String to search for in this section
     * @return Whether this section contains the name or not
     */
    public abstract boolean contains(String name);

    /**
     * Delete an object from this section
     *
     * @param name Name of object to delete
     */
    public abstract void delete(String name);

    /**
     * Get a <code>SectionAccessor</code> in relation to this section
     *
     * @param name The name of the <code>SectionAccessor</code> to get
     * @return The requested <code>SectionAccessor</code>
     */
    public abstract SectionAccessor<D, T> getSection(String name);

    /**
     * Get all keys of this section.
     * If deep is specified, nested keys will also be gotten.
     *
     * @param deep Whether to get nested keys or not
     * @return The requested keys
     */
    public abstract Set<String> getKeys(boolean deep);

    /**
     * Get all values of this section.
     * If deep is specified, nested values will also be gotten.
     *
     * @param deep Whether to get nested values or not
     * @return The requested values
     */
    public abstract Set<T> getValues(boolean deep);

    /**
     * Get all key-value pairs of this section.
     * If deep is specified, nested key-value pairs will also be gotten.
     *
     * @param deep Whether to get nested key-value pairs or not
     * @return The requested key-value pairs
     */
    public abstract Map<String, T> getKeyValuePairs(boolean deep);

    /**
     * Get a boolean value
     *
     * @param name The key name
     * @return The requested value
     */
    public abstract boolean getBoolean(String name);

    /**
     * Get an int value
     *
     * @param name The key name
     * @return The requested value
     */
    public abstract int getInt(String name);

    /**
     * Get a float value
     *
     * @param name The key name
     * @return The requested value
     */
    public abstract float getFloat(String name);

    /**
     * Get a double value
     *
     * @param name The key name
     * @return The requested value
     */
    public abstract double getDouble(String name);

    /**
     * Get a long value
     *
     * @param name The key name
     * @return The requested value
     */
    public abstract long getLong(String name);

    /**
     * Get a String value
     *
     * @param name The key name
     * @return The requested value
     */
    public abstract String getString(String name);

    /**
     * Get an {@link ItemStack} value
     *
     * @param name The key name
     * @return The requested value
     */
    public abstract ItemStack getItemStack(String name);

    /**
     * Get a {@link Location} value
     *
     * @param name The key name
     * @return The requested value
     */
    public abstract Location getLocation(String name);

    /**
     * Get a {@link Vector} value
     *
     * @param name The key name
     * @return The requested value
     */
    public abstract Vector getVector(String name);

    /**
     * Get an {@link ItemMeta} value
     *
     * @param name The key name
     * @return The requested value
     */
    public abstract ItemMeta getItemMeta(String name);

    /**
     * Get an {@link OfflinePlayer} value
     *
     * @param name The key name
     * @return The requested value
     */
    public abstract OfflinePlayer getPlayer(String name);

    /**
     * Get an {@link AttributeModifier} value
     *
     * @param name The key name
     * @return The requested value
     */
    public abstract AttributeModifier getAttributeModifier(String name);

    /**
     * Get a {@link BlockVector} value
     *
     * @param name The key name
     * @return The requested value
     */
    public abstract BlockVector getBlockVector(String name);

    /**
     * Get a {@link BoundingBox} value
     *
     * @param name The key name
     * @return The requested value
     */
    public abstract BoundingBox getBoundingBox(String name);

    /**
     * Get a {@link Color} value
     *
     * @param name The key name
     * @return The requested value
     */
    public abstract Color getColor(String name);

    /**
     * Get a {@link FireworkEffect} value
     *
     * @param name The key name
     * @return The requested value
     */
    public abstract FireworkEffect getFireworkEffect(String name);

    /**
     * Get a {@link Pattern} value
     *
     * @param name The key name
     * @return The requested value
     */
    public abstract Pattern getPattern(String name);

    /**
     * Get a {@link PotionEffect} value
     *
     * @param name The key name
     * @return The requested value
     */
    public abstract PotionEffect getPotionEffect(String name);

    /**
     * Get a {@link Material} value
     *
     * @param name The key name
     * @return The requested value
     */
    public abstract Material getMaterial(String name);

    /**
     * Get a {@link ConfigurationSerializable} value
     *
     * @param name The key name
     * @return The requested value
     */
    public abstract ConfigurationSerializable getSerialized(String name);

    /**
     * Get a {@link ConfigurationSerializable} value casted to the requested class
     *
     * @param name  The key name
     * @param clazz The class type to interpret the value as
     * @param <C>   Serializable data type to be returned
     * @return The requested value
     */
    public abstract <C extends ConfigurationSerializable> C getSerialized(String name, Class<C> clazz);

    /**
     * Get a <code>List</code> of Boolean values
     *
     * @param name The key name
     * @return The requested list
     */
    public abstract List<Boolean> getBooleanList(String name);

    /**
     * Get a <code>List</code> of Integer values
     *
     * @param name The key name
     * @return The requested list
     */
    public abstract List<Integer> getIntList(String name);

    /**
     * Get a <code>List</code> of Float values
     *
     * @param name The key name
     * @return The requested list
     */
    public abstract List<Float> getFloatList(String name);

    /**
     * Get a <code>List</code> of Double values
     *
     * @param name The key name
     * @return The requested list
     */
    public abstract List<Double> getDoubleList(String name);

    /**
     * Get a <code>List</code> of Long values
     *
     * @param name The key name
     * @return The requested list
     */
    public abstract List<Long> getLongList(String name);

    /**
     * Get a <code>List</code> of String values
     *
     * @param name The key name
     * @return The requested list
     */
    public abstract List<String> getStringList(String name);

    /**
     * Get a <code>List</code> of {@link ItemStack} values
     *
     * @param name The key name
     * @return The requested list
     */
    public abstract List<ItemStack> getItemStackList(String name);

    /**
     * Get a <code>List</code> of {@link Location} values
     *
     * @param name The key name
     * @return The requested list
     */
    public abstract List<Location> getLocationList(String name);

    /**
     * Get a <code>List</code> of {@link Vector} values
     *
     * @param name The key name
     * @return The requested list
     */
    public abstract List<Vector> getVectorList(String name);

    /**
     * Get a <code>List</code> of {@link OfflinePlayer} values
     *
     * @param name The key name
     * @return The requested list
     */
    public abstract List<OfflinePlayer> getPlayerList(String name);

    /**
     * Get a <code>List</code> of {@link AttributeModifier} values
     *
     * @param name The key name
     * @return The requested list
     */
    public abstract List<AttributeModifier> getAttributeModifierList(String name);

    /**
     * Get a <code>List</code> of {@link BlockVector} values
     *
     * @param name The key name
     * @return The requested list
     */
    public abstract List<BlockVector> getBlockVectorList(String name);

    /**
     * Get a <code>List</code> of {@link BoundingBox} values
     *
     * @param name The key name
     * @return The requested list
     */
    public abstract List<BoundingBox> getBoundingBoxList(String name);

    /**
     * Get a <code>List</code> of {@link Color} values
     *
     * @param name The key name
     * @return The requested list
     */
    public abstract List<Color> getColorList(String name);

    /**
     * Get a <code>List</code> of {@link FireworkEffect} values
     *
     * @param name The key name
     * @return The requested list
     */
    public abstract List<FireworkEffect> getFireworkEffectList(String name);

    /**
     * Get a <code>List</code> of {@link Pattern} values
     *
     * @param name The key name
     * @return The requested list
     */
    public abstract List<Pattern> getPatternList(String name);

    /**
     * Get a <code>List</code> of {@link PotionEffect} values
     *
     * @param name The key name
     * @return The requested list
     */
    public abstract List<PotionEffect> getPotionEffectList(String name);

    /**
     * Get a <code>List</code> of {@link Material} values
     *
     * @param name The key name
     * @return The requested list
     */
    public abstract List<Material> getMaterialList(String name);

    /**
     * Get a <code>List</code> of {@link ConfigurationSerializable} values
     *
     * @param name The key name
     * @return The requested list
     */
    public abstract List<ConfigurationSerializable> getSerializedList(String name);

    /**
     * Get a <code>List</code> of {@link ConfigurationSerializable} values
     *
     * @param name  The key name
     * @param clazz The class type to interpret the value as
     * @param <C>   Serializable data type to be returned
     * @return The requested list
     */
    public abstract <C extends ConfigurationSerializable> List<C> getSerializedList(String name, Class<C> clazz);

    /**
     * Set a Boolean value
     *
     * @param name The key name
     * @param data The data to set to the key
     */
    public abstract void setBoolean(String name, boolean data);

    /**
     * Set an Integer value
     *
     * @param name The key name
     * @param data The data to set to the key
     */
    public abstract void setInt(String name, int data);

    /**
     * Set a Float value
     *
     * @param name The key name
     * @param data The data to set to the key
     */
    public abstract void setFloat(String name, float data);

    /**
     * Set a Double value
     *
     * @param name The key name
     * @param data The data to set to the key
     */
    public abstract void setDouble(String name, double data);

    /**
     * Set a Long value
     *
     * @param name The key name
     * @param data The data to set to the key
     */
    public abstract void setLong(String name, long data);

    /**
     * Set a String value
     *
     * @param name The key name
     * @param data The data to set to the key
     */
    public abstract void setString(String name, String data);

    /**
     * Set an {@link ItemStack} value
     *
     * @param name The key name
     * @param data The data to set to the key
     */
    public abstract void setItemStack(String name, ItemStack data);

    /**
     * Set a {@link Location} value
     *
     * @param name The key name
     * @param data The data to set to the key
     */
    public abstract void setLocation(String name, Location data);

    /**
     * Set a {@link Vector} value
     *
     * @param name The key name
     * @param data The data to set to the key
     */
    public abstract void setVector(String name, Vector data);

    /**
     * Set an {@link OfflinePlayer} value
     *
     * @param name The key name
     * @param data The data to set to the key
     */
    public abstract void setPlayer(String name, OfflinePlayer data);

    /**
     * Set an {@link AttributeModifier} value
     *
     * @param name The key name
     * @param data The data to set to the key
     */
    public abstract void setAttributeModifier(String name, AttributeModifier data);

    /**
     * Set a {@link BlockVector} value
     *
     * @param name The key name
     * @param data The data to set to the key
     */
    public abstract void setBlockVector(String name, BlockVector data);

    /**
     * Set a {@link BoundingBox} value
     *
     * @param name The key name
     * @param data The data to set to the key
     */
    public abstract void setBoundingBox(String name, BoundingBox data);

    /**
     * Set a {@link Color} value
     *
     * @param name The key name
     * @param data The data to set to the key
     */
    public abstract void setColor(String name, Color data);

    /**
     * Set a {@link FireworkEffect} value
     *
     * @param name The key name
     * @param data The data to set to the key
     */
    public abstract void setFireworkEffect(String name, FireworkEffect data);

    /**
     * Set a {@link Pattern} value
     *
     * @param name The key name
     * @param data The data to set to the key
     */
    public abstract void setPattern(String name, Pattern data);

    /**
     * Set a {@link PotionEffect} value
     *
     * @param name The key name
     * @param data The data to set to the key
     */
    public abstract void setPotionEffect(String name, PotionEffect data);

    /**
     * Set a {@link Material} value
     *
     * @param name The key name
     * @param data The data to set to the key
     */
    public abstract void setMaterial(String name, Material data);

    /**
     * Set a {@link ConfigurationSerializable} value
     *
     * @param name The key name
     * @param data The data to set to the key
     */
    public abstract void setSerialized(String name, ConfigurationSerializable data);

    /**
     * Set a <code>List</code> of Boolean values
     *
     * @param name The key name
     * @param data The <code>List</code> of data to set to the key
     */
    public abstract void setBooleanList(String name, List<Boolean> data);

    /**
     * Set a <code>List</code> of Integer values
     *
     * @param name The key name
     * @param data The <code>List</code> of data to set to the key
     */
    public abstract void setIntList(String name, List<Integer> data);

    /**
     * Set a <code>List</code> of Float values
     *
     * @param name The key name
     * @param data The <code>List</code> of data to set to the key
     */
    public abstract void setFloatList(String name, List<Float> data);

    /**
     * Set a <code>List</code> of Double values
     *
     * @param name The key name
     * @param data The <code>List</code> of data to set to the key
     */
    public abstract void setDoubleList(String name, List<Double> data);

    /**
     * Set a <code>List</code> of Long values
     *
     * @param name The key name
     * @param data The <code>List</code> of data to set to the key
     */
    public abstract void setLongList(String name, List<Long> data);

    /**
     * Set a <code>List</code> of String values
     *
     * @param name The key name
     * @param data The <code>List</code> of data to set to the key
     */
    public abstract void setStringList(String name, List<String> data);

    /**
     * Set a <code>List</code> of {@link ItemStack} values
     *
     * @param name The key name
     * @param data The <code>List</code> of data to set to the key
     */
    public abstract void setItemStackList(String name, List<ItemStack> data);

    /**
     * Set a <code>List</code> of {@link Location} values
     *
     * @param name The key name
     * @param data The <code>List</code> of data to set to the key
     */
    public abstract void setLocationList(String name, List<Location> data);

    /**
     * Set a <code>List</code> of {@link Vector} values
     *
     * @param name The key name
     * @param data The <code>List</code> of data to set to the key
     */
    public abstract void setVectorList(String name, List<Vector> data);

    /**
     * Set a <code>List</code> of {@link OfflinePlayer} values
     *
     * @param name The key name
     * @param data The <code>List</code> of data to set to the key
     */
    public abstract void setPlayerList(String name, List<OfflinePlayer> data);

    /**
     * Set a <code>List</code> of {@link AttributeModifier} values
     *
     * @param name The key name
     * @param data The <code>List</code> of data to set to the key
     */
    public abstract void setAttributeModifierList(String name, List<AttributeModifier> data);

    /**
     * Set a <code>List</code> of {@link BlockVector} values
     *
     * @param name The key name
     * @param data The <code>List</code> of data to set to the key
     */
    public abstract void setBlockVectorList(String name, List<BlockVector> data);

    /**
     * Set a <code>List</code> of {@link BoundingBox} values
     *
     * @param name The key name
     * @param data The <code>List</code> of data to set to the key
     */
    public abstract void setBoundingBoxList(String name, List<BoundingBox> data);

    /**
     * Set a <code>List</code> of {@link Color} values
     *
     * @param name The key name
     * @param data The <code>List</code> of data to set to the key
     */
    public abstract void setColorList(String name, List<Color> data);

    /**
     * Set a <code>List</code> of {@link FireworkEffect} values
     *
     * @param name The key name
     * @param data The <code>List</code> of data to set to the key
     */
    public abstract void setFireworkEffectList(String name, List<FireworkEffect> data);

    /**
     * Set a <code>List</code> of {@link Pattern} values
     *
     * @param name The key name
     * @param data The <code>List</code> of data to set to the key
     */
    public abstract void setPatternList(String name, List<Pattern> data);

    /**
     * Set a <code>List</code> of {@link PotionEffect} values
     *
     * @param name The key name
     * @param data The <code>List</code> of data to set to the key
     */
    public abstract void setPotionEffectList(String name, List<PotionEffect> data);

    /**
     * Set a <code>List</code> of {@link Material} values
     *
     * @param name The key name
     * @param data The <code>List</code> of data to set to the key
     */
    public abstract void setMaterialList(String name, List<Material> data);

    /**
     * Set a <code>List</code> of {@link ConfigurationSerializable} values
     *
     * @param name The key name
     * @param data The <code>List</code> of data to set to the key
     */
    public abstract void setSerializedList(String name, List<ConfigurationSerializable> data);

    /**
     * Get the {@link DataFile} that this <code>SectionAccessor</code> reference
     *
     * @return The <code>DataFile</code> of this accessor
     */
    public D getDataFile()
    {
        return dataFile;
    }

    /**
     * Get the current path of this <code>SectionAccessor</code>; where this accessor is located in relation to the root
     * of the file
     *
     * @return The current path
     */
    public String getCurrentPath()
    {
        return filePath;
    }
}
