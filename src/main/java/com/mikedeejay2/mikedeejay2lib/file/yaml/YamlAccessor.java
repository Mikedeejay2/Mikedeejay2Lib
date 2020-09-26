package com.mikedeejay2.mikedeejay2lib.file.yaml;

import com.mikedeejay2.mikedeejay2lib.file.section.SectionAccessor;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A Yaml accessor that overrides <tt>SectionAccessor</tt> for accessing YAML files easily
 *
 * @author Mikedeejay2
 */
public class YamlAccessor extends SectionAccessor<YamlFile, Object>
{
    // The ConfigurationSection that this SectionAccessor is accessing
    private ConfigurationSection yaml;

    public YamlAccessor(YamlFile dataFile, ConfigurationSection section)
    {
        super(dataFile);
        this.yaml = section;
    }

    @Override
    public Object get(String name)
    {
        return yaml.get(name);
    }

    @Override
    public void set(String name, Object object)
    {
        yaml.set(name, object);
    }

    @Override
    public boolean contains(String name)
    {
        return yaml.contains(name);
    }

    @Override
    public void delete(String name)
    {
        yaml.set(name, null);
    }

    @Override
    public SectionAccessor<YamlFile, Object> getSection(String name)
    {
        return new YamlAccessor(dataFile, yaml.getConfigurationSection(name));
    }

    @Override
    public Set<String> getKeys(boolean deep)
    {
        return yaml.getKeys(deep);
    }

    @Override
    public Set<Object> getValues(boolean deep)
    {
        return new HashSet<>(yaml.getValues(deep).values());
    }

    @Override
    public Map<String, Object> getKeyValuePairs(boolean deep)
    {
        return yaml.getValues(deep);
    }

    @Override
    public boolean getBoolean(String name)
    {
        return yaml.getBoolean(name);
    }

    @Override
    public int getInt(String name)
    {
        return yaml.getInt(name);
    }

    @Override
    public float getFloat(String name)
    {
        return (float)yaml.getDouble(name);
    }

    @Override
    public double getDouble(String name)
    {
        return yaml.getDouble(name);
    }

    @Override
    public long getLong(String name)
    {
        return yaml.getLong(name);
    }

    @Override
    public String getString(String name)
    {
        return yaml.getString(name);
    }

    @Override
    public ItemStack getItemStack(String name)
    {
        return yaml.getItemStack(name);
    }

    @Override
    public Location getLocation(String name)
    {
        return yaml.getLocation(name);
    }

    @Override
    public Vector getVector(String name)
    {
        return yaml.getVector(name);
    }

    @Override
    public List<Boolean> getBooleanList(String name)
    {
        return yaml.getBooleanList(name);
    }

    @Override
    public List<Integer> getIntList(String name)
    {
        return yaml.getIntegerList(name);
    }

    @Override
    public List<Float> getFloatList(String name)
    {
        return yaml.getFloatList(name);
    }

    @Override
    public List<Double> getDoubleList(String name)
    {
        return yaml.getDoubleList(name);
    }

    @Override
    public List<Long> getLongList(String name)
    {
        return yaml.getLongList(name);
    }

    @Override
    public List<String> getStringList(String name)
    {
        return yaml.getStringList(name);
    }

    @Override
    public List<ItemStack> getItemStackList(String name)
    {
        return (List<ItemStack>)yaml.getList(name);
    }

    @Override
    public List<Location> getLocationList(String name)
    {
        return (List<Location>)yaml.getList(name);
    }

    @Override
    public List<Vector> getVectorList(String name)
    {
        return (List<Vector>)yaml.getList(name);
    }

    @Override
    public void setBoolean(String name, boolean data)
    {
        yaml.set(name, data);
    }

    @Override
    public void setInt(String name, int data)
    {
        yaml.set(name, data);
    }

    @Override
    public void setFloat(String name, float data)
    {
        yaml.set(name, data);
    }

    @Override
    public void setDouble(String name, double data)
    {
        yaml.set(name, data);
    }

    @Override
    public void setLong(String name, long data)
    {
        yaml.set(name, data);
    }

    @Override
    public void setString(String name, String data)
    {
        yaml.set(name, data);
    }

    @Override
    public void setItemStack(String name, ItemStack data)
    {
        yaml.set(name, data);
    }

    @Override
    public void setLocation(String name, Location data)
    {
        yaml.set(name, data);
    }

    @Override
    public void setVector(String name, Vector data)
    {
        yaml.set(name, data);
    }

    @Override
    public void setBooleanList(String name, List<Boolean> data)
    {
        yaml.set(name, data);
    }

    @Override
    public void setIntList(String name, List<Integer> data)
    {
        yaml.set(name, data);
    }

    @Override
    public void setFloatList(String name, List<Float> data)
    {
        yaml.set(name, data);
    }

    @Override
    public void setDoubleList(String name, List<Double> data)
    {
        yaml.set(name, data);
    }

    @Override
    public void setLongList(String name, List<Long> data)
    {
        yaml.set(name, data);
    }

    @Override
    public void setStringList(String name, List<String> data)
    {
        yaml.set(name, data);
    }

    @Override
    public void setItemStackList(String name, List<ItemStack> data)
    {
        yaml.set(name, data);
    }

    @Override
    public void setLocationList(String name, List<Location> data)
    {
        yaml.set(name, data);
    }

    @Override
    public void setVectorList(String name, List<Vector> data)
    {
        yaml.set(name, data);
    }
}
