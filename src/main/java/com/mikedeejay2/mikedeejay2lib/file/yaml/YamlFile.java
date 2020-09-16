package com.mikedeejay2.mikedeejay2lib.file.yaml;

import com.mikedeejay2.mikedeejay2lib.PluginBase;
import com.mikedeejay2.mikedeejay2lib.file.DataFile;
import com.mikedeejay2.mikedeejay2lib.file.FileIO;

/**
 * A YamlFile is inherited from DataFile and it actually makes working with Yaml files slightly
 * easier to work with.
 *
 * @author Mikedeejay2
 */
public class YamlFile extends DataFile
{
    // The EnhancedYaml object that the YamlFile is accessing
    protected EnhancedYaml yamlFile;
    // The root EnhancedYamlSection for the yamlFile
    protected EnhancedYamlSection rootSection;
    private YamlFileIO yamlFileIO;

    public YamlFile(PluginBase plugin, String filePath)
    {
        super(plugin, filePath);
        this.yamlFileIO = new YamlFileIO(plugin);
        yamlFile = new EnhancedYaml(yamlFileIO);
        rootSection = new EnhancedYamlSection(yamlFile);
        yamlFileIO = new YamlFileIO(plugin);
    }

    /**
     * Get the EnhancedYaml object for this YamlFile
     *
     * @return An EnhancedYaml file
     */
    public EnhancedYaml getYamlConfig()
    {
        return yamlFile;
    }

    /**
     * Get a section from the EnhancedYaml file based off of a path
     *
     * @param path Path to the section being requested
     * @return The requested section
     */
    public EnhancedYamlSection getSection(String path)
    {
        return new EnhancedYamlSection(path, rootSection);
    }

    /**
     * Get the root EnhancedYamlSection
     *
     * @return The root EnhancedYamlSection
     */
    public EnhancedYamlSection getRootSection()
    {
        return rootSection;
    }

    @Override
    public boolean loadFromDisk()
    {
        isLoaded = yamlFileIO.loadIntoYamlConfig(yamlFile, file);
        return isLoaded;
    }

    @Override
    public boolean loadFromJar()
    {
        isLoaded = yamlFileIO.loadYamlConfigFromJar(yamlFile, filePath);
        return isLoaded;
    }

    @Override
    public boolean saveToDisk()
    {
        return yamlFileIO.saveYamlConfig(yamlFile, file);
    }

    @Override
    public boolean delete()
    {
        isLoaded = !fileIO.deleteFile(file);
        return !isLoaded;
    }

    @Override
    public boolean updateFromJar()
    {
        return yamlFile.updateFromJar(filePath);
    }
}
