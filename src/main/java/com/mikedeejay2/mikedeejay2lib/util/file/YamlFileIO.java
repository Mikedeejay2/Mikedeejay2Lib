package com.mikedeejay2.mikedeejay2lib.util.file;

import com.mikedeejay2.mikedeejay2lib.data.yaml.EnhancedYaml;

import java.io.File;
import java.io.Reader;

/**
 * Util class for saving and loading YAML from and to disk.
 *
 * @author Mikedeejay2
 */
public final class YamlFileIO {
    /**
     * Load a File into a YamlConfiguration
     *
     * @param config      YamlConfiguration that will be loaded into
     * @param file        File that will be loaded
     * @param throwErrors Whether this method should throw errors if something goes wrong or not
     * @return Whether load was successful or not
     */
    public static boolean loadIntoYamlConfig(EnhancedYaml config, File file, boolean throwErrors) {
        try {
            config.load(file);
        } catch(Exception e) {
            if(throwErrors) FileIO.logFileCouldNotBeLoaded(file.getPath(), e);
            return false;
        }
        return true;
    }

    /**
     * Load a file in the plugin's jar into a YamlConfiguration
     *
     * @param config      YamlConfiguration that will be loaded into
     * @param filePath    Path to the file. This should NOT include plugin.getDataFolder()
     * @param classLoader The <code>ClassLoader</code> to get the resource from
     * @param throwErrors Whether this method should throw errors if something goes wrong or not
     * @return Whether load was successful or not
     */
    public static boolean loadYamlConfigFromJar(EnhancedYaml config, String filePath, ClassLoader classLoader, boolean throwErrors) {
        Reader reader = FileIO.getReaderFromJar(filePath, classLoader, throwErrors);
        try {
            config.load(reader);
        } catch(Exception e) {
            if(throwErrors) FileIO.logFileCouldNotBeLoaded(filePath, e);
            return false;
        }
        return true;
    }

    /**
     * Save a yaml file to disk.
     *
     * @param config      The EnhancedYaml file being saved
     * @param file        The file to save to
     * @param throwErrors Whether this method should throw errors if something goes wrong or not
     * @return Whether load was successful or not
     */
    public static boolean saveYamlConfig(EnhancedYaml config, File file, boolean throwErrors) {
        try {
            config.save(file);
        } catch(Exception e) {
            if(throwErrors) FileIO.logFileCouldNotBeSaved(file.getPath(), e);
            return false;
        }
        return true;
    }
}
