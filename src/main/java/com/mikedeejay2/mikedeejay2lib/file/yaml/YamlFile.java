package com.mikedeejay2.mikedeejay2lib.file.yaml;

import com.mikedeejay2.mikedeejay2lib.PluginBase;
import com.mikedeejay2.mikedeejay2lib.file.DataFile;
import com.mikedeejay2.mikedeejay2lib.file.FileIO;
import org.bukkit.configuration.file.YamlConfiguration;

public class YamlFile extends DataFile
{
    protected static final PluginBase plugin = PluginBase.getInstance();

    protected YamlConfiguration yamlFile;

    public YamlFile(String filePath)
    {
        super(filePath);
        yamlFile = new YamlConfiguration();
    }

    public YamlConfiguration getYamlConfig()
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
        isLoaded = YamlFileIO.loadYamlConfigFromJar(yamlFile, file.getPath());
        return isLoaded;
    }

    @Override
    public boolean saveToDisk()
    {
        return FileIO.saveFile(file, true);
    }

    @Override
    public boolean delete()
    {
        isLoaded = !FileIO.deleteFile(file);
        return !isLoaded;
    }
}
