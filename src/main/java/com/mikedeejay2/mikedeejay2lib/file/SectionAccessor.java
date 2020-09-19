package com.mikedeejay2.mikedeejay2lib.file;

import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.List;

public abstract class SectionAccessor<D extends DataFile>
{
    private final D dataFile;
    private final String filePath;

    public SectionAccessor(D dataFile)
    {
        this.dataFile = dataFile;
        this.filePath = dataFile.getFilePath();
    }

    public abstract Object get(String name);
    public abstract void set(String name, Object object);
    public abstract void contains(String name);
    public abstract void delete(String name);
    public abstract SectionAccessor<D> getSection(String name);
    public abstract SectionAccessor<D> getSectionNested(String fullPath);
    public abstract void getKeys(boolean deep);

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

    public abstract boolean setBoolean(String name);
    public abstract int setInt(String name);
    public abstract float setFloat(String name);
    public abstract double setDouble(String name);
    public abstract long setLong(String name);
    public abstract String setString(String name);
    public abstract ItemStack setItemStack(String name);
    public abstract Location setLocation(String name);
    public abstract Vector setVector(String name);

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
