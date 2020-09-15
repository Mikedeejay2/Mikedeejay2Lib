package com.mikedeejay2.mikedeejay2lib.file.yaml;

import com.mikedeejay2.mikedeejay2lib.PluginBase;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Set;

/**
 * A configuration section with custom methods for saving and loading.
 * This mainly helps me with debugging because it's my class so I know how it works.
 */
public class EnhancedYamlSection
{
    protected static final PluginBase plugin = PluginBase.getInstance();
    // The path to the current Yaml section
    protected String pathTo;
    // The section that this object is representing
    protected ConfigurationSection section;
    // The FileConfiguration that this section is a part of
    protected EnhancedYaml file;

    /**
     * Create a new CustomYamlSection from a YamlBase
     *
     * @param base YamlBase to use
     */
    public EnhancedYamlSection(EnhancedYaml base)
    {
        file = base;
        ConfigurationSection currentSection = file.getDefaultSection();
        this.pathTo = "";
        this.section = currentSection;
    }

    /**
     * Create a new CustomYamlSection based on a new path that will be found
     * in the currentSection
     *
     * @param path Path that new section should be from
     * @param currentSection The current section (parent) of the new section
     */
    public EnhancedYamlSection(String path, EnhancedYamlSection currentSection)
    {
        file = currentSection.getCurrentFile();
        if(!currentSection.contains(path))
        {
            currentSection.createSection(path);
        }
        this.pathTo = path;
        this.section = currentSection.getSection(path).getCurrentConfigSection();
    }

    /**
     * Get the name of this section
     *
     * @return The name of this section
     */
    public String getName()
    {
        return section.getName();
    }

    /**
     * Remove a path in this section
     *
     * @param path Path to remove
     */
    public void removeSection(String path)
    {
        section.set(path, null);
    }

    /**
     * Create a new section in this section
     *
     * @param path Path to create
     */
    public void createSection(String path)
    {
        if(!section.contains(path))
        {
            section.createSection(path);
        }
    }

    /**
     * Get a section in this current section
     *
     * @param path Path to section
     * @return The requested section
     */
    public EnhancedYamlSection getSection(String path)
    {
        return new EnhancedYamlSection(path, this);
    }

    /**
     * Get the current path of this section
     *
     * @return The path to this sectionp
     */
    public String getCurrentPath()
    {
        return section.getCurrentPath();
    }

    /**
     * Returns whether a path exists in this section or not
     *
     * @param path Path to test for
     * @return If the path exists or not
     */
    public boolean contains(String path)
    {
        return section.contains(path);
    }

    /**
     * Get all keys of a section. If deep is true then it will recursively get keys within each other
     *
     * @param deep Should nested keys be returned?
     * @return All keys of this section
     */
    public Set<String> getKeys(boolean deep)
    {
        return section.getKeys(deep);
    }

    /**
     * Get this section's ConfigurationSection
     *
     * @return This section's ConfigurationSection
     */
    public ConfigurationSection getCurrentConfigSection()
    {
        return section;
    }

    /**
     * Get this section's FileConfiguration
     *
     * @return This section's FileConfiguration
     */
    public EnhancedYaml getCurrentFile()
    {
        return file;
    }

    public void saveBoolean(String path, boolean bool)
    {
        section.set(path, bool);
    }

    public boolean loadBoolean(String path)
    {
        return section.getBoolean(path);
    }

    public void saveBooleanList(String path, List<Boolean> boolList)
    {
        section.set(path, boolList);
    }

    public List<Boolean> loadBooleanList(String path)
    {
        return section.getBooleanList(path);
    }

    public void saveByteList(String path, List<Byte> byteList)
    {
        section.set(path, byteList);
    }

    public List<Byte> loadByteList(String path)
    {
        return section.getByteList(path);
    }

    public void saveCharacterList(String path, List<Character> characterList)
    {
        section.set(path, characterList);
    }

    public List<Character> loadCharacterList(String path)
    {
        return section.getCharacterList(path);
    }

    public void saveDouble(String path, double value)
    {
        section.set(path, value);
    }

    public double loadDouble(String path)
    {
        return section.getDouble(path);
    }

    public void saveDoubleList(String path, List<Double> doubleList)
    {
        section.set(path, doubleList);
    }

    public List<Double> loadDoubleList(String path)
    {
        return section.getDoubleList(path);
    }

    public void saveFloatList(String path, List<Float> floatList)
    {
        section.set(path, floatList);
    }

    public List<Float> loadFloatList(String path)
    {
        return section.getFloatList(path);
    }

    public void saveInt(String path, int value)
    {
        section.set(path, value);
    }

    public int loadInt(String path)
    {
        return section.getInt(path);
    }

    public void saveIntegerList(String path, List<Integer> integerList)
    {
        section.set(path, integerList);
    }

    public List<Integer> loadIntegerList(String path)
    {
        return section.getIntegerList(path);
    }

    public void saveItemStack(String path, ItemStack itemStack)
    {
        section.set(path, itemStack);
    }

    public ItemStack loadItemStack(String path)
    {
        return section.getItemStack(path);
    }

    public void saveLong(String path, long value)
    {
        section.set(path, value);
    }

    public long loadLong(String path)
    {
        return section.getLong(path);
    }

    public void saveLongList(String path, List<Long> longList)
    {
        section.set(path, longList);
    }

    public List<Long> getLongList(String path)
    {
        return section.getLongList(path);
    }

    public void saveLocation(String path, Location location)
    {
        section.set(path, location);
    }

    public Location loadLocation(String path)
    {
        return section.getLocation(path);
    }

    public void saveString(String path, String value)
    {
        section.set(path, value);
    }

    public String loadString(String path)
    {
        return section.getString(path);
    }

    public void saveStringList(String path, List<String> stringList)
    {
        section.set(path, stringList);
    }

    public List<String> loadStringList(String path)
    {
        return section.getStringList(path);
    }

    public void saveShortList(String path, List<Short> shortList)
    {
        section.set(path, shortList);
    }

    public List<Short> loadShortList(String path)
    {
        return section.getShortList(path);
    }
}
