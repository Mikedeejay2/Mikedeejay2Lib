package com.mikedeejay2.mikedeejay2lib.config;

import com.mikedeejay2.mikedeejay2lib.BukkitPlugin;
import com.mikedeejay2.mikedeejay2lib.data.DataFile;
import com.mikedeejay2.mikedeejay2lib.data.json.JsonFile;
import com.mikedeejay2.mikedeejay2lib.data.section.SectionAccessor;
import com.mikedeejay2.mikedeejay2lib.data.section.SectionInstancer;
import com.mikedeejay2.mikedeejay2lib.data.yaml.YamlFile;
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

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

public abstract class ConfigFile {
    protected final BukkitPlugin plugin;
    protected final DataFile dataFile;
    protected final FileType fileType;
    protected final List<ConfigValue<?>> values;
    protected final List<ConfigFile> children;
    protected final boolean loadFromJar;
    protected boolean modified;
    protected boolean loaded;

    protected ConfigFile(BukkitPlugin plugin, String configPath, FileType fileType, boolean loadFromJar) {
        this.fileType = fileType;
        this.plugin = plugin;
        this.dataFile = fileType.create(plugin, configPath);
        this.modified = false;
        this.loaded = false;
        this.values = new ArrayList<>();
        this.children = new ArrayList<>();
        this.loadFromJar = loadFromJar;
    }

    protected <T> ConfigValue<T> value(ValueType<T> type, String path) {
        final ConfigValue<T> value = new ConfigValue<>(this, type, path);
        this.values.add(value);
        return value;
    }

    protected ConfigFile child(ConfigFile configFile) {
        this.children.add(configFile);
        return configFile;
    }

    public boolean isModified() {
        return modified;
    }

    public void setModified(boolean modified) {
        this.modified = modified;
    }

    public boolean load() {
        children.forEach(ConfigFile::load);
        if(!fileExists()) {
            boolean success = true;
            if(loadFromJar) success &= loadFromJar();
            success &= saveToDisk();
            return success;
        }
        return loadFromDisk();
    }

    public boolean save() {
        children.forEach(ConfigFile::save);
        boolean success = saveToDisk();
        if(success) modified = false;
        return success;
    }

    public boolean reload() {
        children.forEach(ConfigFile::reload);
        return modified ? save() : load();
    }

    protected boolean loadFromJar() {
        boolean success = dataFile.loadFromJar(true);
        if(success) {
            values.forEach(ConfigValue::load);
            this.loaded = true;
        }
        return success;
    }

    protected boolean saveToDisk() {
        values.forEach(ConfigValue::save);
        return dataFile.saveToDisk(true);
    }

    protected boolean loadFromDisk() {
        boolean success = dataFile.loadFromDisk(true);
        success &= updateFromJar();
        success &= saveToDisk();
        if(success) {
            values.forEach(ConfigValue::load);
            this.loaded = true;
        }
        return success;
    }

    protected boolean updateFromJar() {
        return dataFile.updateFromJar(true);
    }

    protected boolean fileExists() {
        return dataFile.fileExists();
    }

    public static class ConfigValue<T> {
        private final ConfigFile file;
        private final ValueType<T> type;
        private final String path;
        private final String name;
        private T value;

        private ConfigValue(ConfigFile file, ValueType<T> type, String path) {
            this.file = file;
            this.type = type;
            this.path = path;
            final String[] splitPath = path.split("/");
            this.name = splitPath[splitPath.length - 1];
        }

        public T get() {
            return value;
        }

        public void set(T value) {
            this.value = value;
            this.file.setModified(true);
        }

        public T load() {
            return this.type.load(getAccessor(), name);
        }

        public void save() {
            this.type.save(getAccessor(), name, value);
        }

        public ValueType<T> getType() {
            return type;
        }

        public String getPath() {
            return path;
        }

        public String getName() {
            return name;
        }

        private SectionAccessor<?, ?> getAccessor() {
            final String[] splitPath = path.split("/");
            SectionAccessor<?, ?> accessor = ((SectionInstancer<?, ?, ?>) file.dataFile).getAccessor();
            for(int i = 0; i < splitPath.length - 1; ++i) {
                accessor = accessor.getSection(splitPath[i]);
            }
            return accessor;
        }
    }

    public static class ValueType<T> {
        public static final ValueType<Boolean> BOOLEAN = of(SectionAccessor::getBoolean, SectionAccessor::setBoolean);
        public static final ValueType<Integer> INTEGER = of(SectionAccessor::getInt, SectionAccessor::setInt);
        public static final ValueType<Float> FLOAT = of(SectionAccessor::getFloat, SectionAccessor::setFloat);
        public static final ValueType<Double> DOUBLE = of(SectionAccessor::getDouble, SectionAccessor::setDouble);
        public static final ValueType<Long> LONG = of(SectionAccessor::getLong, SectionAccessor::setLong);
        public static final ValueType<String> STRING = of(SectionAccessor::getString, SectionAccessor::setString);
        public static final ValueType<ItemStack> ITEM_STACK = of(SectionAccessor::getItemStack, SectionAccessor::setItemStack);
        public static final ValueType<Location> LOCATION = of(SectionAccessor::getLocation, SectionAccessor::setLocation);
        public static final ValueType<Vector> VECTOR = of(SectionAccessor::getVector, SectionAccessor::setVector);
        public static final ValueType<ItemMeta> ITEM_META = of(SectionAccessor::getItemMeta, SectionAccessor::setItemMeta);
        public static final ValueType<OfflinePlayer> PLAYER = of(SectionAccessor::getPlayer, SectionAccessor::setPlayer);
        public static final ValueType<AttributeModifier> ATTRIBUTE_MODIFIER = of(SectionAccessor::getAttributeModifier, SectionAccessor::setAttributeModifier);
        public static final ValueType<BlockVector> BLOCK_VECTOR = of(SectionAccessor::getBlockVector, SectionAccessor::setBlockVector);
        public static final ValueType<BoundingBox> BOUNDING_BOX = of(SectionAccessor::getBoundingBox, SectionAccessor::setBoundingBox);
        public static final ValueType<Color> COLOR = of(SectionAccessor::getColor, SectionAccessor::setColor);
        public static final ValueType<FireworkEffect> FIREWORK_EFFECT = of(SectionAccessor::getFireworkEffect, SectionAccessor::setFireworkEffect);
        public static final ValueType<Pattern> PATTERN = of(SectionAccessor::getPattern, SectionAccessor::setPattern);
        public static final ValueType<PotionEffect> POTION_EFFECT = of(SectionAccessor::getPotionEffect, SectionAccessor::setPotionEffect);
        public static final ValueType<Material> MATERIAL = of(SectionAccessor::getMaterial, SectionAccessor::setMaterial);
        public static final ValueType<ConfigurationSerializable> CONFIGURATION_SERIALIZABLE = of(SectionAccessor::getSerialized, SectionAccessor::setSerialized);
        public static final ValueType<List<Boolean>> BOOLEAN_LIST = of(SectionAccessor::getBooleanList, SectionAccessor::setBooleanList);
        public static final ValueType<List<Integer>> INTEGER_LIST = of(SectionAccessor::getIntList, SectionAccessor::setIntList);
        public static final ValueType<List<Float>> FLOAT_LIST = of(SectionAccessor::getFloatList, SectionAccessor::setFloatList);
        public static final ValueType<List<Double>> DOUBLE_LIST = of(SectionAccessor::getDoubleList, SectionAccessor::setDoubleList);
        public static final ValueType<List<Long>> LONG_LIST = of(SectionAccessor::getLongList, SectionAccessor::setLongList);
        public static final ValueType<List<String>> STRING_LIST = of(SectionAccessor::getStringList, SectionAccessor::setStringList);
        public static final ValueType<List<ItemStack>> ITEM_STACK_LIST = of(SectionAccessor::getItemStackList, SectionAccessor::setItemStackList);
        public static final ValueType<List<Location>> LOCATION_LIST = of(SectionAccessor::getLocationList, SectionAccessor::setLocationList);
        public static final ValueType<List<Vector>> VECTOR_LIST = of(SectionAccessor::getVectorList, SectionAccessor::setVectorList);
        public static final ValueType<List<ItemMeta>> ITEM_META_LIST = of(SectionAccessor::getItemMetaList, SectionAccessor::setItemMetaList);
        public static final ValueType<List<OfflinePlayer>> PLAYER_LIST = of(SectionAccessor::getPlayerList, SectionAccessor::setPlayerList);
        public static final ValueType<List<AttributeModifier>> ATTRIBUTE_MODIFIER_LIST = of(SectionAccessor::getAttributeModifierList, SectionAccessor::setAttributeModifierList);
        public static final ValueType<List<BlockVector>> BLOCK_VECTOR_LIST = of(SectionAccessor::getBlockVectorList, SectionAccessor::setBlockVectorList);
        public static final ValueType<List<BoundingBox>> BOUNDING_BOX_LIST = of(SectionAccessor::getBoundingBoxList, SectionAccessor::setBoundingBoxList);
        public static final ValueType<List<Color>> COLOR_LIST = of(SectionAccessor::getColorList, SectionAccessor::setColorList);
        public static final ValueType<List<FireworkEffect>> FIREWORK_EFFECT_LIST = of(SectionAccessor::getFireworkEffectList, SectionAccessor::setFireworkEffectList);
        public static final ValueType<List<Pattern>> PATTERN_LIST = of(SectionAccessor::getPatternList, SectionAccessor::setPatternList);
        public static final ValueType<List<PotionEffect>> POTION_EFFECT_LIST = of(SectionAccessor::getPotionEffectList, SectionAccessor::setPotionEffectList);
        public static final ValueType<List<Material>> MATERIAL_LIST = of(SectionAccessor::getMaterialList, SectionAccessor::setMaterialList);
        public static final ValueType<List<ConfigurationSerializable>> CONFIGURATION_SERIALIZABLE_LIST = of(SectionAccessor::getSerializedList, SectionAccessor::setSerializedList);

        private final Loader<T> loader;
        private final Saver<T> saver;

        protected ValueType(Loader<T> loader, Saver<T> saver) {
            this.loader = loader;
            this.saver = saver;
        }

        public T load(SectionAccessor<?, ?> accessor, String path) {
            return this.loader.load(accessor, path);
        }

        public void save(SectionAccessor<?, ?> accessor, String path, T value) {
            this.saver.save(accessor, path, value);
        }

        public static <T> ValueType<T> of(Loader<T> loader, Saver<T> saver) {
            return new ValueType<>(loader, saver);
        }

        @FunctionalInterface
        private interface Loader<T> {
            T load(SectionAccessor<?, ?> accessor, String name);
        }

        @FunctionalInterface
        private interface Saver<T> {
            void save(SectionAccessor<?, ?> accessor, String name, T value);
        }
    }

    public enum FileType {
        YAML(YamlFile::new),
        JSON(JsonFile::new)
        ;

        private final BiFunction<BukkitPlugin, String, DataFile> generator;

        FileType(BiFunction<BukkitPlugin, String, DataFile> generator) {
            this.generator = generator;
        }

        public DataFile create(BukkitPlugin plugin, String filePath) {
            return generator.apply(plugin, filePath);
        }
    }
}
