package com.mikedeejay2.mikedeejay2lib.file.yaml;

import com.mikedeejay2.mikedeejay2lib.file.section.SectionAccessor;
import org.bukkit.*;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.block.banner.Pattern;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.util.BlockVector;
import org.bukkit.util.BoundingBox;
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
        if(!yaml.isConfigurationSection(name))
        {
            yaml.createSection(name);
        }
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
    public ItemMeta getItemMeta(String name)
    {
        return (ItemMeta) yaml.get(name);
    }

    @Override
    public OfflinePlayer getPlayer(String name)
    {
        return yaml.getOfflinePlayer(name);
    }

    @Override
    public AttributeModifier getAttributeModifier(String name)
    {
        return (AttributeModifier) yaml.get(name);
    }

    @Override
    public BlockVector getBlockVector(String name)
    {
        return (BlockVector) yaml.get(name);
    }

    @Override
    public BoundingBox getBoundingBox(String name)
    {
        return (BoundingBox) yaml.get(name);
    }

    @Override
    public Color getColor(String name)
    {
        return (Color) yaml.get(name);
    }

    @Override
    public FireworkEffect getFireworkEffect(String name)
    {
        return (FireworkEffect) yaml.get(name);
    }

    @Override
    public Pattern getPattern(String name)
    {
        return (Pattern) yaml.get(name);
    }

    @Override
    public PotionEffect getPotionEffect(String name)
    {
        return (PotionEffect) yaml.get(name);
    }

    @Override
    public Material getMaterial(String name)
    {
        return (Material) yaml.get(name);
    }

    @Override
    public ConfigurationSerializable getSerialized(String name)
    {
        return (ConfigurationSerializable) yaml.get(name);
    }

    @Override
    public <C extends ConfigurationSerializable> C getSerialized(String name, Class<C> clazz)
    {
        return (C)yaml.get(name);
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
        return (List<ItemStack>) yaml.getList(name);
    }

    @Override
    public List<Location> getLocationList(String name)
    {
        return (List<Location>) yaml.getList(name);
    }

    @Override
    public List<Vector> getVectorList(String name)
    {
        return (List<Vector>) yaml.getList(name);
    }

    @Override
    public List<OfflinePlayer> getPlayerList(String name)
    {
        return (List<OfflinePlayer>) yaml.getList(name);
    }

    @Override
    public List<AttributeModifier> getAttributeModifierList(String name)
    {
        return (List<AttributeModifier>) yaml.getList(name);
    }

    @Override
    public List<BlockVector> getBlockVectorList(String name)
    {
        return (List<BlockVector>) yaml.getList(name);
    }

    @Override
    public List<BoundingBox> getBoundingBoxList(String name)
    {
        return (List<BoundingBox>) yaml.getList(name);
    }

    @Override
    public List<Color> getColorList(String name)
    {
        return (List<Color>) yaml.getList(name);
    }

    @Override
    public List<FireworkEffect> getFireworkEffectList(String name)
    {
        return (List<FireworkEffect>) yaml.getList(name);
    }

    @Override
    public List<Pattern> getPatternList(String name)
    {
        return (List<Pattern>) yaml.getList(name);
    }

    @Override
    public List<PotionEffect> getPotionEffectList(String name)
    {
        return (List<PotionEffect>) yaml.getList(name);
    }

    @Override
    public List<Material> getMaterialList(String name)
    {
        return (List<Material>) yaml.getList(name);
    }

    @Override
    public List<ConfigurationSerializable> getSerializedList(String name)
    {
        return (List<ConfigurationSerializable>) yaml.getList(name);
    }

    @Override
    public <C extends ConfigurationSerializable> List<C> getSerializedList(String name, Class<C> clazz)
    {
        return (List<C>) yaml.getList(name);
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
    public void setPlayer(String name, OfflinePlayer data)
    {
        yaml.set(name, data);
    }

    @Override
    public void setAttributeModifier(String name, AttributeModifier data)
    {
        yaml.set(name, data);
    }

    @Override
    public void setBlockVector(String name, BlockVector data)
    {
        yaml.set(name, data);
    }

    @Override
    public void setBoundingBox(String name, BoundingBox data)
    {
        yaml.set(name, data);
    }

    @Override
    public void setColor(String name, Color data)
    {
        yaml.set(name, data);
    }

    @Override
    public void setFireworkEffect(String name, FireworkEffect data)
    {
        yaml.set(name, data);
    }

    @Override
    public void setPattern(String name, Pattern data)
    {
        yaml.set(name, data);
    }

    @Override
    public void setPotionEffect(String name, PotionEffect data)
    {
        yaml.set(name, data);
    }

    @Override
    public void setMaterial(String name, Material data)
    {
        yaml.set(name, data);
    }

    @Override
    public void setSerialized(String name, ConfigurationSerializable data)
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

    @Override
    public void setPlayerList(String name, List<OfflinePlayer> data)
    {
        yaml.set(name, data);
    }

    @Override
    public void setAttributeModifierList(String name, List<AttributeModifier> data)
    {
        yaml.set(name, data);
    }

    @Override
    public void setBlockVectorList(String name, List<BlockVector> data)
    {
        yaml.set(name, data);
    }

    @Override
    public void setBoundingBoxList(String name, List<BoundingBox> data)
    {
        yaml.set(name, data);
    }

    @Override
    public void setColorList(String name, List<Color> data)
    {
        yaml.set(name, data);
    }

    @Override
    public void setFireworkEffectList(String name, List<FireworkEffect> data)
    {
        yaml.set(name, data);
    }

    @Override
    public void setPatternList(String name, List<Pattern> data)
    {
        yaml.set(name, data);
    }

    @Override
    public void setPotionEffectList(String name, List<PotionEffect> data)
    {
        yaml.set(name, data);
    }

    @Override
    public void setMaterialList(String name, List<Material> data)
    {
        yaml.set(name, data);
    }

    @Override
    public void setSerializedList(String name, List<ConfigurationSerializable> data)
    {
        yaml.set(name, data);
    }
}
