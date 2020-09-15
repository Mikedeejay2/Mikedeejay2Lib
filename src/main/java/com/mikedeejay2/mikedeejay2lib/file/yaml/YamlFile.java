package com.mikedeejay2.mikedeejay2lib.file.yaml;

import com.mikedeejay2.mikedeejay2lib.PluginBase;
import com.mikedeejay2.mikedeejay2lib.file.DataFile;
import com.mikedeejay2.mikedeejay2lib.file.FileIO;
import org.yaml.snakeyaml.Yaml;

public class YamlFile extends DataFile
{
    protected static final PluginBase plugin = PluginBase.getInstance();

    protected EnhancedYaml yamlFile;

    public YamlFile(String filePath)
    {
        super(filePath);
        yamlFile = new EnhancedYaml();
    }

    public EnhancedYaml getYamlConfig()
    {
        return yamlFile;
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
