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
    protected static final PluginBase plugin = PluginBase.getInstance();

    // The EnhancedYaml object that the YamlFile is accessing
    protected EnhancedYaml yamlFile;
    // The root EnhancedYamlSection for the yamlFile
    protected EnhancedYamlSection rootSection;

    public YamlFile(String filePath)
    {
        super(filePath);
        yamlFile = new EnhancedYaml();
        rootSection = new EnhancedYamlSection(yamlFile);
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
        isLoaded = YamlFileIO.loadIntoYamlConfig(yamlFile, file);
        return isLoaded;
    }

    @Override
    public boolean loadFromJar()
    {
        isLoaded = YamlFileIO.loadYamlConfigFromJar(yamlFile, filePath);
        return isLoaded;
    }

    @Override
    public boolean saveToDisk()
    {
        return YamlFileIO.saveYamlConfig(yamlFile, file);
    }

    @Override
    public boolean delete()
    {
        isLoaded = !FileIO.deleteFile(file);
        return !isLoaded;
    }

    @Override
    public boolean updateFromJar()
    {
        return yamlFile.updateFromJar(filePath);
    }
}
