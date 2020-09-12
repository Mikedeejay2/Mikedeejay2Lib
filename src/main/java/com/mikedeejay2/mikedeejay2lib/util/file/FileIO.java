package com.mikedeejay2.mikedeejay2lib.util.file;

import com.mikedeejay2.mikedeejay2lib.PluginBase;
import com.mikedeejay2.mikedeejay2lib.file.yaml.CustomYamlSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.util.logging.Level;

public class FileIO
{
    private static final PluginBase plugin = PluginBase.getInstance();

    public static File loadFile(String fileName)
    {
        File file = new File(plugin.getDataFolder(), fileName);
        if(!file.exists())
        {
            plugin.saveResource(fileName, false);
        }
        return file;
    }

    public static void saveFile(String resourcePath, boolean replace) {
        InputStream in = plugin.getResource(resourcePath);
        File outFile = new File(plugin.getDataFolder(), resourcePath);
        int lastIndex = resourcePath.lastIndexOf('/');
        File outDir = new File(plugin.getDataFolder(), resourcePath.substring(0, Math.max(lastIndex, 0)));

        if (!outDir.exists()) outDir.mkdirs();

        try {
            if (!outFile.exists() || replace) {
                OutputStream out = new FileOutputStream(outFile);
                byte[] buf = new byte[1024];
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                out.close();
                in.close();
            }
        } catch (IOException ex)
        {
            plugin.getLogger().log(Level.SEVERE, "Could not save " + outFile.getName() + " to " + outFile, ex);
        }
    }
}
