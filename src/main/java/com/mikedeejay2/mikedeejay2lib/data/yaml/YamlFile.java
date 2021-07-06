package com.mikedeejay2.mikedeejay2lib.data.yaml;

import com.mikedeejay2.mikedeejay2lib.BukkitPlugin;
import com.mikedeejay2.mikedeejay2lib.data.DataFile;
import com.mikedeejay2.mikedeejay2lib.data.section.SectionInstancer;
import com.mikedeejay2.mikedeejay2lib.util.file.YamlFileIO;

/**
 * A YamlFile is inherited from DataFile and it actually makes working with Yaml files slightly
 * easier to work with.
 *
 * @author Mikedeejay2
 */
public class YamlFile extends DataFile implements SectionInstancer<YamlAccessor, YamlFile, Object>
{
    // The EnhancedYaml object that the YamlFile is accessing
    protected EnhancedYaml yamlFile;
    // The root EnhancedYamlSection for the yamlFile
    protected YamlAccessor accessor;

    public YamlFile(BukkitPlugin plugin, String filePath)
    {
        super(plugin, filePath);
        yamlFile = new EnhancedYaml();
        accessor = new YamlAccessor(this, yamlFile);
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
        isLoaded = YamlFileIO.loadIntoYamlConfig(yamlFile, file, throwErrors);
        return isLoaded;
    }

    @Override
    public boolean loadFromJar(boolean throwErrors)
    {
        isLoaded = YamlFileIO.loadYamlConfigFromJar(yamlFile, filePath, plugin.classLoader(), throwErrors);
        return isLoaded;
    }

    @Override
    public boolean saveToDisk(boolean throwErrors)
    {
        return YamlFileIO.saveYamlConfig(yamlFile, file, throwErrors);
    }

    @Override
    public boolean updateFromJar(boolean throwErrors)
    {
        return yamlFile.updateFromJar(filePath, plugin.classLoader());
    }

    @Override
    public YamlAccessor getAccessor(String name)
    {
        return (YamlAccessor) accessor.getSection(name);
    }

    @Override
    public YamlAccessor getAccessor()
    {
        return accessor;
    }
}
