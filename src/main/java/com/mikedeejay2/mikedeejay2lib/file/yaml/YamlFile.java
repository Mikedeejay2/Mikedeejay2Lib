package com.mikedeejay2.mikedeejay2lib.file.yaml;

import com.mikedeejay2.mikedeejay2lib.PluginBase;
import com.mikedeejay2.mikedeejay2lib.file.DataFile;
import com.mikedeejay2.mikedeejay2lib.file.section.SectionInstancer;

/**
 * A YamlFile is inherited from DataFile and it actually makes working with Yaml files slightly
 * easier to work with.
 *
 * @author Mikedeejay2
 */
public class YamlFile extends DataFile implements SectionInstancer<YamlAccessor>
{
    // The EnhancedYaml object that the YamlFile is accessing
    protected EnhancedYaml yamlFile;
    // The root EnhancedYamlSection for the yamlFile
    protected YamlAccessor rootAccessor;
    private YamlFileIO yamlFileIO;

    public YamlFile(PluginBase plugin, String filePath)
    {
        super(plugin, filePath);
        this.yamlFileIO = new YamlFileIO(plugin);
        yamlFile = new EnhancedYaml(yamlFileIO);
        rootAccessor = new YamlAccessor(this, yamlFile);
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

    @Override
    public boolean loadFromDisk(boolean throwErrors)
    {
        isLoaded = yamlFileIO.loadIntoYamlConfig(yamlFile, file, throwErrors);
        return isLoaded;
    }

    @Override
    public boolean loadFromJar(boolean throwErrors)
    {
        isLoaded = yamlFileIO.loadYamlConfigFromJar(yamlFile, filePath, throwErrors);
        return isLoaded;
    }

    @Override
    public boolean saveToDisk(boolean throwErrors)
    {
        return yamlFileIO.saveYamlConfig(yamlFile, file, throwErrors);
    }

    @Override
    public boolean updateFromJar(boolean throwErrors)
    {
        return yamlFile.updateFromJar(filePath);
    }

    @Override
    public YamlAccessor getAccessor(String name)
    {
        return (YamlAccessor)rootAccessor.getSection(name);
    }

    @Override
    public YamlAccessor getRootAccessor()
    {
        return rootAccessor;
    }
}
