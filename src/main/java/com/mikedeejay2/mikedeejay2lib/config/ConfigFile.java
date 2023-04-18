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

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class ConfigFile {
    protected final BukkitPlugin plugin;
    protected final DataFile dataFile;
    protected final FileType fileType;
    protected final List<ConfigValue<?>> values;
    protected final List<ConfigFile> children;
    protected final boolean loadFromJar;
    protected final Updater updater;
    private boolean modified;
    private boolean loaded;

    protected ConfigFile(BukkitPlugin plugin, String configPath, FileType fileType, boolean loadFromJar) {
        this.fileType = fileType;
        this.plugin = plugin;
        this.dataFile = fileType.create(plugin, configPath);
        this.modified = false;
        this.loaded = false;
        this.values = new ArrayList<>();
        this.children = new ArrayList<>();
        this.loadFromJar = loadFromJar;
        this.updater = new Updater(this);
    }

    private <T> ConfigValue<T> addValue(ConfigValue<T> value) {
        this.values.add(value);
        return value;
    }

    protected <T> ConfigValue<T> value(ValueType<T> type, String path, T defaultValue) {
        final ConfigValue<T> value = new ConfigValue<>(this, type, path, defaultValue);
        this.values.add(value);
        return value;
    }

    protected <T> ConfigValue<T> value(ValueType<T> type, String path, Supplier<T> defaultValueSupplier) {
        final ConfigValue<T> value = new ConfigValue<>(this, type, path, defaultValueSupplier.get());
        this.values.add(value);
        return value;
    }

    protected <T> ConfigValue<T> value(ValueType<T> type, String path) {
        return value(type, path, (T) null);
    }

    protected <T extends Collection<E>, E> ConfigValueCollection<T, E> collectionValue(ValueType<T> type, String path, T defaultValue) {
        final ConfigValueCollection<T, E> value = new ConfigValueCollection<>(this, type, path, defaultValue);
        this.values.add(value);
        return value;
    }

    protected <T extends Collection<E>, E> ConfigValueCollection<T, E> collectionValue(ValueType<T> type, String path, Supplier<T> defaultValueSupplier) {
        final ConfigValueCollection<T, E> value = new ConfigValueCollection<>(this, type, path, defaultValueSupplier.get());
        this.values.add(value);
        return value;
    }

    protected <T extends Map<K, V>, K, V> ConfigValueMap<T, K, V> mapValue(ValueType<T> type, String path, T defaultValue) {
        final ConfigValueMap<T, K, V> value = new ConfigValueMap<>(this, type, path, defaultValue);
        this.values.add(value);
        return value;
    }

    protected <T extends Map<K, V>, K, V> ConfigValueMap<T, K, V> mapValue(ValueType<T> type, String path, Supplier<T> defaultValueSupplier) {
        final ConfigValueMap<T, K, V> value = new ConfigValueMap<>(this, type, path, defaultValueSupplier.get());
        this.values.add(value);
        return value;
    }

    protected <T extends ConfigFile> T child(T configFile) {
        this.children.add(configFile);
        return configFile;
    }

    public Updater getUpdater() {
        return updater;
    }

    public boolean isModified() {
        return modified;
    }

    public void setModified(boolean modified) {
        this.modified = modified;
    }

    public boolean isLoaded() {
        return loaded;
    }

    public List<ConfigValue<?>> getValues() {
        return values;
    }

    public List<ConfigFile> getChildren() {
        return children;
    }

    public FileType getFileType() {
        return fileType;
    }

    public boolean shouldLoadFromJar() {
        return loadFromJar;
    }

    public boolean load() {
        if(loaded) return false;
        if(!internalFileExists()) {
            boolean success = true;
            if(loadFromJar) success &= internalLoadFromJar();
            success &= internalSaveToDisk();
            return success;
        }
        final boolean success = internalLoadFromDisk();
        children.forEach(ConfigFile::load);
        return success;
    }

    public boolean save() {
        if(!loaded) return false;
        boolean success = internalSaveToDisk();
        if(success) modified = false;
        children.forEach(ConfigFile::save);
        return success;
    }

    public boolean reload() {
        final boolean success = modified ? save() : load();
        children.forEach(ConfigFile::reload);
        return success;
    }

    public boolean reset() {
        if(!internalDelete()) return false;
        final boolean success = load();
        children.forEach(ConfigFile::reset);
        return success;
    }

    public boolean delete() {
        final boolean success = internalDelete();
        children.forEach(ConfigFile::delete);
        return success;
    }

    protected boolean internalLoadFromJar() {
        if(loaded) return false;
        boolean success = dataFile.loadFromJar(true);
        if(success) {
            values.forEach(ConfigValue::load);
            this.loaded = true;
        }
        return success;
    }

    protected boolean internalSaveToDisk() {
        if(!loaded) return false;
        values.forEach(ConfigValue::save);
        return dataFile.saveToDisk(true);
    }

    protected boolean internalLoadFromDisk() {
        if(loaded) return false;
        boolean success = dataFile.loadFromDisk(true);
        if(loadFromJar) {
            internalUpdateFromJar();
            internalSaveToDisk();
        }
        if(success) {
            values.forEach(ConfigValue::load);
            this.loaded = true;
        }
        return success;
    }

    protected boolean internalUpdateFromJar() {
        this.updater.update();
        return dataFile.updateFromJar(true);
    }

    protected boolean internalFileExists() {
        return dataFile.fileExists();
    }

    protected boolean internalDelete() {
        boolean success = dataFile.delete(true);
        this.loaded = !success;
        return success;
    }

    private SectionAccessor<DataFile, Object> getAccessor(String path) {
        final String[] splitPath = path.split("\\.");
        SectionAccessor<DataFile, Object> accessor = ((SectionInstancer<?, DataFile, Object>) dataFile).getAccessor();
        for(int i = 0; i < splitPath.length - 1; ++i) {
            accessor = accessor.getSection(splitPath[i]);
        }
        return accessor;
    }

    private String getName(String path) {
        final String[] splitPath = path.split("\\.");
        return splitPath[splitPath.length - 1];
    }

    public static class ConfigValue<T> {
        protected final ConfigFile file;
        protected final ValueType<T> type;
        protected final String path;
        protected final String name;
        protected T value;

        private ConfigValue(ConfigFile file, ValueType<T> type, String path, T defaultValue) {
            this.file = file;
            this.type = type;
            this.path = path;
            this.name = file.getName(path);
            this.value = defaultValue;
        }

        public T get() {
            return value;
        }

        public void set(T value) {
            this.value = value;
            this.file.modified = true;
        }

        public void load() {
            final T loaded = this.type.load(file.getAccessor(path), name);
            if(loaded != null) value = loaded;
        }

        public void save() {
            this.type.save(file.getAccessor(path), name, value);
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
    }

    public static class ConfigValueCollection<T extends Collection<E>, E> extends ConfigValue<T> {
        private ConfigValueCollection(ConfigFile file, ValueType<T> type, String path, T defaultValue) {
            super(file, type, path, defaultValue);
        }

        @Override
        public void set(T value) {
            this.value.clear();
            this.value.addAll(value);
            this.file.modified = true;
        }

        @Override
        public void load() {
            final T loaded = this.type.load(file.getAccessor(path), name);
            if(loaded != null) {
                this.value.clear();
                this.value.addAll(loaded);
            }
        }
    }

    public static class ConfigValueMap<T extends Map<K, V>, K, V> extends ConfigValue<T> {
        private ConfigValueMap(ConfigFile file, ValueType<T> type, String path, T defaultValue) {
            super(file, type, path, defaultValue);
        }

        @Override
        public void set(T value) {
            this.value.clear();
            this.value.putAll(value);
            this.file.modified = true;
        }

        @Override
        public void load() {
            final T loaded = this.type.load(file.getAccessor(path), name);
            if(loaded != null) {
                this.value.clear();
                this.value.putAll(loaded);
            }
        }
    }

    public static final class ValueType<T> {
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

        private ValueType(Loader<T> loader, Saver<T> saver) {
            this.loader = loader;
            this.saver = saver;
        }

        public <U> ValueType<U> map(Function<T, U> mapper, Function<U, T> unmapper) {
            return new ValueType<>(
                (accessor, name) -> mapper.apply(loader.load(accessor, name)),
                (accessor, name, value) -> saver.save(accessor, name, unmapper.apply(value))
            );
        }

        public ValueType<T> onLoadDo(Consumer<T> consumer) {
            return new ValueType<>(
                (accessor, name) -> {
                    final T loaded = loader.load(accessor, name);
                    consumer.accept(loaded);
                    return loaded;
                }, saver);
        }

        public ValueType<T> onSaveDo(Consumer<T> consumer) {
            return new ValueType<>(
                loader,
                (accessor, name, value) -> {
                    consumer.accept(value);
                    saver.save(accessor, name, value);
                });
        }

        public ValueType<T> onLoadReplace(Function<T, T> function) {
            return new ValueType<>((accessor, name) -> function.apply(loader.load(accessor, name)), saver);
        }

        public ValueType<T> onSaveReplace(Function<T, T> function) {
            return new ValueType<>(loader, (accessor, name, value) -> saver.save(accessor, name, function.apply(value)));
        }

        public ValueType<Boolean> bool(T trueValue, T falseValue, Function<T, Boolean> orElse) {
            return map(value -> {
                if(value.equals(trueValue)) return true;
                if(value.equals(falseValue)) return false;
                return orElse != null ? orElse.apply(value) : null;
            }, value -> value ? trueValue : falseValue);
        }

        public ValueType<Boolean> bool(T trueValue, T falseValue) {
            return bool(trueValue, falseValue, null);
        }

        public ValueType<T> orElse(T other) {
            return map(value -> value != null ? value : other, value -> value);
        }

        public ValueType<T> orElseGet(Supplier<T> other) {
            return map(value -> value != null ? value : other.get(), value -> value);
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

        public static <T> ValueType<Map<String, T>> keyValues(ValueType<T> valueType) {
            return new ValueType<>(
                (accessor, name) -> {
                    final Map<String, T> map = new LinkedHashMap<>();
                    final SectionAccessor<?, ?> section = accessor.getSection(name);
                    for(String key : section.getKeys(false)) {
                        map.put(key, valueType.load(section, key));
                    }
                    return map;
                },
                (accessor, name, map) -> {
                    final SectionAccessor<?, ?> section = accessor.getSection(name);
                    for(String key : map.keySet()) {
                        T value = map.get(key);
                        valueType.save(section, key, value);
                    }
                });
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

    public static final class Updater {
        private final ConfigFile file;
        private final List<UpdateOperation> operations;

        private Updater(ConfigFile file) {
            this.file = file;
            this.operations = new ArrayList<>();
        }

        public Updater remove(String key) {
            this.operations.add(new UpdateRemove(key));
            return this;
        }

        public Updater relocate(String key, String destinationKey) {
            this.operations.add(new UpdateRelocate(key, destinationKey));
            return this;
        }

        private void update() {
            this.operations.forEach(operation -> operation.update(file));
        }

        private static abstract class UpdateOperation {
            protected final String key;

            public UpdateOperation(String key) {
                this.key = key;
            }

            public abstract void update(ConfigFile file);
        }

        private static class UpdateRemove extends UpdateOperation {
            public UpdateRemove(String key) {
                super(key);
            }

            @Override
            public void update(ConfigFile file) {
                final SectionAccessor<DataFile, Object> accessor = file.getAccessor(key);
                final String name = file.getName(key);
                if(!accessor.contains(name)) return;
                accessor.delete(name);
            }
        }

        private static class UpdateRelocate extends UpdateOperation {
            private final String destinationKey;

            public UpdateRelocate(String key, String destinationKey) {
                super(key);
                this.destinationKey = destinationKey;
            }

            @Override
            public void update(ConfigFile file) {
                final SectionAccessor<DataFile, Object> accessor = file.getAccessor(key);
                final String name = file.getName(key);
                final String newName = file.getName(destinationKey);
                if(!accessor.contains(name)) return;
                file.getAccessor(destinationKey).set(newName, accessor.get(name));
                accessor.delete(name);
            }
        }
    }
}
