package com.mikedeejay2.mikedeejay2lib.file.json;

import com.google.gson.JsonObject;
import com.mikedeejay2.mikedeejay2lib.file.SectionAccessor;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.List;

public class EnhancedJsonObject extends SectionAccessor<JsonFile>
{
    private JsonObject json;

    public EnhancedJsonObject(JsonFile dataFile)
    {
        super(dataFile);
        this.json = dataFile.jsonObject;
    }

    @Override
    public Object get(String name)
    {
        return null;
    }

    @Override
    public void set(String name, Object object)
    {

    }

    @Override
    public void contains(String name)
    {

    }

    @Override
    public void delete(String name)
    {

    }

    @Override
    public SectionAccessor<JsonFile> getSection(String name)
    {
        return null;
    }

    @Override
    public SectionAccessor<JsonFile> getSectionNested(String fullPath)
    {
        return null;
    }

    @Override
    public void getKeys(boolean deep)
    {

    }

    @Override
    public boolean getBoolean(String name)
    {
        return false;
    }

    @Override
    public int getInt(String name)
    {
        return 0;
    }

    @Override
    public float getFloat(String name)
    {
        return 0;
    }

    @Override
    public double getDouble(String name)
    {
        return 0;
    }

    @Override
    public long getLong(String name)
    {
        return 0;
    }

    @Override
    public String getString(String name)
    {
        return null;
    }

    @Override
    public ItemStack getItemStack(String name)
    {
        return null;
    }

    @Override
    public Location getLocation(String name)
    {
        return null;
    }

    @Override
    public Vector getVector(String name)
    {
        return null;
    }

    @Override
    public List<Boolean> getBooleanList(String name)
    {
        return null;
    }

    @Override
    public List<Integer> getIntList(String name)
    {
        return null;
    }

    @Override
    public List<Float> getFloatList(String name)
    {
        return null;
    }

    @Override
    public List<Double> getDoubleList(String name)
    {
        return null;
    }

    @Override
    public List<Long> getLongList(String name)
    {
        return null;
    }

    @Override
    public List<String> getStringList(String name)
    {
        return null;
    }

    @Override
    public List<ItemStack> getItemStackList(String name)
    {
        return null;
    }

    @Override
    public List<Location> getLocationList(String name)
    {
        return null;
    }

    @Override
    public List<Vector> getVectorList(String name)
    {
        return null;
    }

    @Override
    public boolean setBoolean(String name)
    {
        return false;
    }

    @Override
    public int setInt(String name)
    {
        return 0;
    }

    @Override
    public float setFloat(String name)
    {
        return 0;
    }

    @Override
    public double setDouble(String name)
    {
        return 0;
    }

    @Override
    public long setLong(String name)
    {
        return 0;
    }

    @Override
    public String setString(String name)
    {
        return null;
    }

    @Override
    public ItemStack setItemStack(String name)
    {
        return null;
    }

    @Override
    public Location setLocation(String name)
    {
        return null;
    }

    @Override
    public Vector setVector(String name)
    {
        return null;
    }

    @Override
    public void setBooleanList(String name, List<Boolean> data)
    {

    }

    @Override
    public void setIntList(String name, List<Integer> data)
    {

    }

    @Override
    public void setFloatList(String name, List<Float> data)
    {

    }

    @Override
    public void setDoubleList(String name, List<Double> data)
    {

    }

    @Override
    public void setLongList(String name, List<Long> data)
    {

    }

    @Override
    public void setStringList(String name, List<String> data)
    {

    }

    @Override
    public void setItemStackList(String name, List<ItemStack> data)
    {

    }

    @Override
    public void setLocationList(String name, List<Location> data)
    {

    }

    @Override
    public void setVectorList(String name, List<Vector> data)
    {

    }
}
