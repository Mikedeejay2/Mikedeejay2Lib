package com.mikedeejay2.mikedeejay2lib.file;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.Set;

public abstract class SectionAccessor<D extends DataFile, T>
{
    protected final D dataFile;
    protected final String filePath;

    public SectionAccessor(D dataFile)
    {
        this.dataFile = dataFile;
        this.filePath = dataFile.getFilePath();
    }

    public abstract T get(String name);
    public abstract void set(String name, T object);
    public abstract void contains(String name);
    public abstract void delete(String name);
    public abstract SectionAccessor<D, T> getSection(String name);
    public abstract Set<String> getKeys(boolean deep);

    public abstract boolean getBoolean(String name);
    public abstract int getInt(String name);
    public abstract float getFloat(String name);
    public abstract double getDouble(String name);
    public abstract long getLong(String name);
    public abstract String getString(String name);
    public abstract ItemStack getItemStack(String name);
    public abstract Location getLocation(String name);
    public abstract Vector getVector(String name);

    public abstract List<Boolean> getBooleanList(String name);
    public abstract List<Integer> getIntList(String name);
    public abstract List<Float> getFloatList(String name);
    public abstract List<Double> getDoubleList(String name);
    public abstract List<Long> getLongList(String name);
    public abstract List<String> getStringList(String name);
    public abstract List<ItemStack> getItemStackList(String name);
    public abstract List<Location> getLocationList(String name);
    public abstract List<Vector> getVectorList(String name);

    public abstract void setBoolean(String name, boolean data);
    public abstract void setInt(String name, int data);
    public abstract void setFloat(String name, float data);
    public abstract void setDouble(String name, double data);
    public abstract void setLong(String name, long data);
    public abstract void setString(String name, String data);
    public abstract void setItemStack(String name, ItemStack data);
    public abstract void setLocation(String name, Location data);
    public abstract void setVector(String name, Vector data);

    public abstract void setBooleanList(String name, List<Boolean> data);
    public abstract void setIntList(String name, List<Integer> data);
    public abstract void setFloatList(String name, List<Float> data);
    public abstract void setDoubleList(String name, List<Double> data);
    public abstract void setLongList(String name, List<Long> data);
    public abstract void setStringList(String name, List<String> data);
    public abstract void setItemStackList(String name, List<ItemStack> data);
    public abstract void setLocationList(String name, List<Location> data);
    public abstract void setVectorList(String name, List<Vector> data);

    public D getDataFile()
    {
        return dataFile;
    }

    public String getCurrentPath()
    {
        return filePath;
    }
}
