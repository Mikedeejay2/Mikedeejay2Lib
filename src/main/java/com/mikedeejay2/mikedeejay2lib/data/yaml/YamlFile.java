package com.mikedeejay2.mikedeejay2lib.data.yaml;

import com.mikedeejay2.mikedeejay2lib.BukkitPlugin;
import com.mikedeejay2.mikedeejay2lib.data.DataFile;
import com.mikedeejay2.mikedeejay2lib.data.section.SectionAccessor;
import com.mikedeejay2.mikedeejay2lib.data.section.SectionInstancer;
import com.mikedeejay2.mikedeejay2lib.util.file.YamlFileIO;

/**
 * A <code>YamlFile</code> is inherited from DataFile and it actually makes working with Yaml files slightly
 * easier to work with.
 *
 * @author Mikedeejay2
 */
public class YamlFile extends DataFile implements SectionInstancer<YamlAccessor, YamlFile, Object>
{
    /**
     * The {@link EnhancedYaml} object that the <code>YamlFile</code> is accessing
     */
    protected EnhancedYaml yamlFile;
    /**
     * The root {@link YamlAccessor} for the yamlFile
     */
    protected YamlAccessor accessor;

    /**
     * Constructs a new <code>YamlFile</code>
     *
     * @param plugin   The {@link BukkitPlugin} instance
     * @param filePath The path to the file
     */
    public YamlFile(BukkitPlugin plugin, String filePath)
    {
        super(plugin, filePath);
        yamlFile = new EnhancedYaml();
        accessor = new YamlAccessor(this, yamlFile);
    }

    /**
     * Get the {@link EnhancedYaml} object for this YamlFile
     *
     * @return An <code>EnhancedYaml</code> file
     */
    public EnhancedYaml getYamlConfig()
    {
        return yamlFile;
    }

    /**
     * {@inheritDoc}
     *
     * @param throwErrors Silence any errors that this operation might produce
     * @return If this operation was successful or not
     */
    @Override
    public boolean loadFromDisk(boolean throwErrors)
    {
        isLoaded = YamlFileIO.loadIntoYamlConfig(yamlFile, file, throwErrors);
        return isLoaded;
    }

    /**
     * {@inheritDoc}
     *
     * @param throwErrors Silence any errors that this operation might produce
     * @return If this operation was successful or not
     */
    @Override
    public boolean loadFromJar(boolean throwErrors)
    {
        isLoaded = YamlFileIO.loadYamlConfigFromJar(yamlFile, filePath, plugin.classLoader(), throwErrors);
        return isLoaded;
    }

    /**
     * {@inheritDoc}
     *
     * @param throwErrors Silence any errors that this operation might produce
     * @return If this operation was successful or not
     */
    @Override
    public boolean saveToDisk(boolean throwErrors)
    {
        return YamlFileIO.saveYamlConfig(yamlFile, file, throwErrors);
    }

    /**
     * {@inheritDoc}
     *
     * @param throwErrors Silence any errors that this operation might produce
     * @return If this operation was successful or not
     */
    @Override
    public boolean updateFromJar(boolean throwErrors)
    {
        return yamlFile.updateFromJar(filePath, plugin.classLoader());
    }

    /**
     * {@inheritDoc}
     *
     * @param name The name of the section to get
     * @return The <code>SectionAccessor</code> of the requested section
     */
    @Override
    public YamlAccessor getAccessor(String name)
    {
        return accessor.getSection(name);
    }

    /**
     * {@inheritDoc}
     *
     * @return The root <code>SectionAccessor</code>
     */
    @Override
    public YamlAccessor getAccessor()
    {
        return accessor;
    }
}
