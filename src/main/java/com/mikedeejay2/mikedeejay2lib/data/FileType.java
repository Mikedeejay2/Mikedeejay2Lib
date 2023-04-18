package com.mikedeejay2.mikedeejay2lib.data;


import com.mikedeejay2.mikedeejay2lib.BukkitPlugin;
import com.mikedeejay2.mikedeejay2lib.data.json.JsonFile;
import com.mikedeejay2.mikedeejay2lib.data.yaml.YamlFile;
import org.apache.commons.io.FilenameUtils;

import java.util.function.BiFunction;

/**
 * File type enumeration. Used for dynamic creation of file types.
 *
 * @author Mikedeejay2
 */
public enum FileType {
    /**
     * The YAML file type. Instantiates {@link YamlFile} for extensions "yaml" and "yml"
     */
    YAML(YamlFile::new, "yaml", "yml"),

    /**
     * The JSON file type. Instantiates {@link JsonFile} for extensions "json"
     */
    JSON(JsonFile::new, "json");

    /**
     * The function used to create a {@link DataFile} of the specified type
     */
    private final BiFunction<BukkitPlugin, String, DataFile> generator;

    /**
     * Array of file extensions associated with the file type
     */
    private final String[] extensions;

    /**
     * Internal constructor
     *
     * @param generator  The function used to create a {@link DataFile} of the specified type
     * @param extensions Array of file extensions associated with the file type
     */
    FileType(BiFunction<BukkitPlugin, String, DataFile> generator, String... extensions) {
        this.generator = generator;
        this.extensions = extensions;
    }

    /**
     * Create a new {@link DataFile} of the file type
     *
     * @param plugin   The {@link BukkitPlugin} instance
     * @param filePath The path to the file
     * @return The newly created {@link DataFile}
     */
    public DataFile create(BukkitPlugin plugin, String filePath) {
        return generator.apply(plugin, filePath);
    }

    /**
     * Get the array of file extensions associated with the file type
     *
     * @return The array of file extensions
     */
    public String[] getExtensions() {
        return extensions;
    }

    /**
     * Get the {@link FileType} of a path based off of its file extension
     *
     * @param path The path of the file
     * @return The {@link FileType} of the path, if detected
     */
    public static FileType pathToType(String path) {
        final String extension = FilenameUtils.getExtension(path);
        for(FileType value : values()) {
            for(String curExt : value.getExtensions()) {
                if(curExt.equals(extension)) return value;
            }
        }
        return null;
    }
}
