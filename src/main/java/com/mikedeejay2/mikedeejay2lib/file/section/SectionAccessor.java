package com.mikedeejay2.mikedeejay2lib.file.section;

import com.mikedeejay2.mikedeejay2lib.file.DataFile;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.block.banner.Pattern;
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
 * Only the strongest of programmers can inherit this class. This class is
 * without a doubt the hardest class to inherit in this plugin.
 *
 * @param <D> The DataFile type that is being accessed.
 * @param <T> The default return type of the Section Accessor, see <tt>get</tt> and <tt>set</tt> methods
 *
 * @author Mikedeejay2
 */
public abstract class SectionAccessor<D extends DataFile, T>
{
    // The parent DataFile that this SectionAccessor is accessing
    protected final D dataFile;
    // The file path of this SectionAccessor
    protected final String filePath;

    public SectionAccessor(D dataFile)
    {
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
     * @param name The name of the object to set
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
     * Get a <tt>SectionAccessor</tt> in relation to this section
     *
     * @param name The name of the <tt>SectionAccessor</tt> to get
     * @return The requested <tt>SectionAccessor</tt>
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

    /*
     * Everything below this point is self explanatory.
     * Sorry for no Javadocs, you should be able to figure it out.
     */

    public abstract boolean getBoolean(String name);
    public abstract int getInt(String name);
    public abstract float getFloat(String name);
    public abstract double getDouble(String name);
    public abstract long getLong(String name);
    public abstract String getString(String name);
    public abstract ItemStack getItemStack(String name);
    public abstract Location getLocation(String name);
    public abstract Vector getVector(String name);
    public abstract ItemMeta getItemMeta(String name);
    public abstract OfflinePlayer getPlayer(String name);
    public abstract AttributeModifier getAttributeModifier(String name);
    public abstract BlockVector getBlockVector(String name);
    public abstract BoundingBox getBoundingBox(String name);
    public abstract Color getColor(String name);
    public abstract FireworkEffect getFireworkEffect(String name);
    public abstract Pattern getPattern(String name);
    public abstract PotionEffect getPotionEffect(String name);

    public abstract List<Boolean> getBooleanList(String name);
    public abstract List<Integer> getIntList(String name);
    public abstract List<Float> getFloatList(String name);
    public abstract List<Double> getDoubleList(String name);
    public abstract List<Long> getLongList(String name);
    public abstract List<String> getStringList(String name);
    public abstract List<ItemStack> getItemStackList(String name);
    public abstract List<Location> getLocationList(String name);
    public abstract List<Vector> getVectorList(String name);
    public abstract List<OfflinePlayer> getPlayerList(String name);
    public abstract List<AttributeModifier> getAttributeModifierList(String name);
    public abstract List<BlockVector> getBlockVectorList(String name);
    public abstract List<BoundingBox> getBoundingBoxList(String name);
    public abstract List<Color> getColorList(String name);
    public abstract List<FireworkEffect> getFireworkEffectList(String name);
    public abstract List<Pattern> getPatternList(String name);
    public abstract List<PotionEffect> getPotionEffectList(String name);

    public abstract void setBoolean(String name, boolean data);
    public abstract void setInt(String name, int data);
    public abstract void setFloat(String name, float data);
    public abstract void setDouble(String name, double data);
    public abstract void setLong(String name, long data);
    public abstract void setString(String name, String data);
    public abstract void setItemStack(String name, ItemStack data);
    public abstract void setLocation(String name, Location data);
    public abstract void setVector(String name, Vector data);
    public abstract void setPlayer(String name, OfflinePlayer data);
    public abstract void setAttributeModifier(String name, AttributeModifier data);
    public abstract void setBlockVector(String name, BlockVector data);
    public abstract void setBoundingBox(String name, BoundingBox data);
    public abstract void setColor(String name, Color data);
    public abstract void setFireworkEffect(String name, FireworkEffect data);
    public abstract void setPattern(String name, Pattern data);
    public abstract void setPotionEffect(String name, PotionEffect data);

    public abstract void setBooleanList(String name, List<Boolean> data);
    public abstract void setIntList(String name, List<Integer> data);
    public abstract void setFloatList(String name, List<Float> data);
    public abstract void setDoubleList(String name, List<Double> data);
    public abstract void setLongList(String name, List<Long> data);
    public abstract void setStringList(String name, List<String> data);
    public abstract void setItemStackList(String name, List<ItemStack> data);
    public abstract void setLocationList(String name, List<Location> data);
    public abstract void setVectorList(String name, List<Vector> data);
    public abstract void setPlayerList(String name, List<OfflinePlayer> data);
    public abstract void setAttributeModifierList(String name, List<AttributeModifier> data);
    public abstract void setBlockVectorList(String name, List<BlockVector> data);
    public abstract void setBoundingBoxList(String name, List<BoundingBox> data);
    public abstract void setColorList(String name, List<Color> data);
    public abstract void setFireworkEffectList(String name, List<FireworkEffect> data);
    public abstract void setPatternList(String name, List<Pattern> data);
    public abstract void setPotionEffectList(String name, List<PotionEffect> data);

    public D getDataFile()
    {
        return dataFile;
    }

    public String getCurrentPath()
    {
        return filePath;
    }
}
