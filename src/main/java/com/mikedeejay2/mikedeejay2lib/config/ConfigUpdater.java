package com.mikedeejay2.mikedeejay2lib.config;


import com.mikedeejay2.mikedeejay2lib.data.DataFile;
import com.mikedeejay2.mikedeejay2lib.data.FileType;
import com.mikedeejay2.mikedeejay2lib.data.section.SectionAccessor;
import com.mikedeejay2.mikedeejay2lib.data.section.SectionInstancer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

/**
 * An updater for updating legacy data in relation to a {@link ConfigFile}. This updater can be used to ensure data
 * is valid on newer versions of a plugin given that the structure of a configuration file has changed over time.
 * <p>
 * List of operations available:
 * <ul>
 *     <li>{@link ConfigUpdater#remove(String)} - Remove a value from a configuration file.</li>
 *     <li>{@link ConfigUpdater#relocate(String, String)} - Relocate a key in a configuration file to a new key.</li>
 *     <li>{@link ConfigUpdater#rename} - Rename a file on disk to a new name.</li>
 *     <li>{@link ConfigUpdater#convert(String, String, BiConsumer)} - Convert one file type to another.</li>
 * </ul>
 *
 * @author Mikedeejay2
 */
public final class ConfigUpdater {
    /**
     * The parent {@link ConfigFile}
     */
    private final ConfigFile file;

    /**
     * The list of {@link UpdateOperation UpdateOperations} that will occur at the time of loading the file
     */
    private final List<UpdateOperation> loadUpdates;

    /**
     * The list of {@link UpdateOperation UpdateOperations} that will occur before loading the file
     */
    private final List<UpdateOperation> preLoadUpdates;

    /**
     * Internal constructor
     *
     * @param file The parent {@link ConfigFile}
     */
    ConfigUpdater(ConfigFile file) {
        this.file = file;
        this.loadUpdates = new ArrayList<>();
        this.preLoadUpdates = new ArrayList<>();
    }

    /**
     * Remove a value from a configuration file. This update occurs when the file is loaded.
     *
     * @param key The key to remove from the configuration file.
     * @return This updater
     */
    public ConfigUpdater remove(String key) {
        this.loadUpdates.add(new UpdateRemove(key));
        return this;
    }

    /**
     * Relocate a key in a configuration file to a new key. This update occurs when the file is loaded.
     *
     * @param key            The old key to be updated
     * @param destinationKey The new key
     * @return This updater
     */
    public ConfigUpdater relocate(String key, String destinationKey) {
        this.loadUpdates.add(new UpdateRelocate(key, destinationKey));
        return this;
    }

    /**
     * Rename a file on disk to a new name. This update occurs before the file is loaded.
     *
     * @param originalPath    The original path to be updated
     * @param destinationPath The new destination path
     * @return This updater
     */
    public ConfigUpdater rename(String originalPath, String destinationPath) {
        this.preLoadUpdates.add(new UpdateRenameFile(originalPath, destinationPath));
        return this;
    }

    /**
     * Convert one file type to another. This update occurs before the file is loaded.
     *
     * @param originalPath    The original path of the file to be converted
     * @param destinationPath The destination path to save to once conversion is done
     * @param converter       The converter consumer. This consumer must account for all values that should be
     *                        converted and manually move each value from the old {@link SectionAccessor} to the new
     *                        accessor.
     * @return This updater
     */
    public ConfigUpdater convert(String originalPath, String destinationPath, BiConsumer<SectionAccessor<?, ?>, SectionAccessor<?, ?>> converter) {
        this.preLoadUpdates.add(new UpdateConvertFile(originalPath, destinationPath, converter));
        return this;
    }

    /**
     * Call all updates that should occur when a file is loaded
     */
    void updateOnLoad() {
        this.loadUpdates.forEach(operation -> operation.update(file));
    }

    /**
     * Call all updates that should occur before a file is loaded
     */
    void updatePreLoad() {
        this.preLoadUpdates.forEach(operation -> operation.update(file));
    }

    /**
     * A single update operation.
     *
     * @author Mikedeejay2
     */
    private interface UpdateOperation {
        /**
         * Update the {@link ConfigFile} with the specified operation
         *
         * @param file The {@link ConfigFile} to update
         */
        void update(ConfigFile file);
    }

    /**
     * An update operation to remove a key from a configuration file.
     *
     * @author Mikedeejay2
     */
    private static class UpdateRemove implements UpdateOperation {
        /**
         * The key to remove
         */
        private final String key;

        /**
         * Internal constructor
         *
         * @param key The key to remove
         */
        private UpdateRemove(String key) {
            this.key = key;
        }

        /**
         * Remove the {@link UpdateRemove#key} from the {@link ConfigFile} if it was found.
         *
         * @param file The {@link ConfigFile} to update
         */
        @Override
        public void update(ConfigFile file) {
            final SectionAccessor<DataFile, Object> accessor = file.getAccessor(key);
            final String name = file.getName(key);
            if(!accessor.contains(name)) return;
            accessor.delete(name);
        }
    }

    /**
     * An update operation to relocate a key to a new key in a configuration file.
     *
     * @author Mikedeejay2
     */
    private static class UpdateRelocate implements UpdateOperation {
        /**
         * The original key to be relocated
         */
        private final String key;

        /**
         * The destination key, the name of the key after relocation
         */
        private final String destinationKey;

        /**
         * Internal constructor
         *
         * @param key            The original key to be relocated
         * @param destinationKey The destination key, the name of the key after relocation
         */
        private UpdateRelocate(String key, String destinationKey) {
            this.key = key;
            this.destinationKey = destinationKey;
        }

        /**
         * Relocate the key to the destination key if the key was found.
         *
         * @param file The {@link ConfigFile} to update
         */
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

    /**
     * An update operation to rename a file on the disk.
     *
     * @author Mikedeejay2
     */
    private static class UpdateRenameFile implements UpdateOperation {
        /**
         * The path to the file to be renamed
         */
        private final String originalPath;

        /**
         * The path of the file after renaming
         */
        private final String destinationPath;

        /**
         * Internal constructor
         *
         * @param originalPath    The path to the file to be renamed
         * @param destinationPath The path of the file after renaming
         */
        private UpdateRenameFile(String originalPath, String destinationPath) {
            this.originalPath = originalPath;
            this.destinationPath = destinationPath;
        }

        /**
         * Rename the original file to the destination path if found.
         *
         * @param file The {@link ConfigFile} to update
         */
        @Override
        public void update(ConfigFile file) {
            final File oldFile = new File(file.plugin.getDataFolder(), originalPath);
            final File newFile = new File(file.plugin.getDataFolder(), destinationPath);
            if(!oldFile.exists() || newFile.exists()) return;
            try {
                Files.move(oldFile.toPath(), newFile.toPath());
            } catch(IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * An update operation to convert a file to another file type.
     *
     * @author Mikedeejay2
     */
    private static class UpdateConvertFile implements UpdateOperation {
        /**
         * The path to the file to be converted
         */
        private final String originalPath;

        /**
         * The path of the file after converting
         */
        private final String destinationPath;

        /**
         * The converter consumer. This consumer must account for all values that should be converted and manually
         * move each value from the old {@link SectionAccessor} to the new accessor.
         */
        private final BiConsumer<SectionAccessor<?, ?>, SectionAccessor<?, ?>> updater;

        /**
         * Internal constructor
         *
         * @param originalPath    The path to the file to be converted
         * @param destinationPath he path of the file after converting
         * @param converter       The converter consumer. This consumer must account for all values that should be
         *                        converted and manually move each value from the old {@link SectionAccessor} to the
         *                        new accessor.
         */
        private UpdateConvertFile(String originalPath, String destinationPath, BiConsumer<SectionAccessor<?, ?>, SectionAccessor<?, ?>> converter) {
            this.originalPath = originalPath;
            this.destinationPath = destinationPath;
            this.updater = converter;
        }

        /**
         * Convert the original file from its format to the new format at the destination path using the
         * {@link UpdateConvertFile#updater} function.
         *
         * @param file The {@link ConfigFile} to update
         */
        @Override
        public void update(ConfigFile file) {
            final FileType oldType = FileType.pathToType(originalPath);
            final FileType newType = FileType.pathToType(destinationPath);
            if(oldType == null || newType == null) return;
            final ConfigFile oldFile = new ConfigFile(file.plugin, originalPath, oldType, false);
            final ConfigFile newFile = new ConfigFile(file.plugin, destinationPath, newType, false);
            if(!oldFile.internalFileExists() || newFile.internalFileExists()) return;
            oldFile.load();
            newFile.load();
            final SectionAccessor<?, ?> oldSection = ((SectionInstancer<?, ?, ?>) oldFile.dataFile).getAccessor();
            final SectionAccessor<?, ?> newSection = ((SectionInstancer<?, ?, ?>) newFile.dataFile).getAccessor();
            updater.accept(oldSection, newSection);
            newFile.save();
        }
    }
}
