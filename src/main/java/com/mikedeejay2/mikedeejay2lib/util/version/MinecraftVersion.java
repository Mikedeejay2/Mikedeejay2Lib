package com.mikedeejay2.mikedeejay2lib.util.version;

import com.mikedeejay2.mikedeejay2lib.PluginBase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A util for getting the Minecraft server's version and converting it into multiple different
 * data types.
 *
 * @author Mikedeejay2
 */
public final class MinecraftVersion
{
    // Holds the version string like "1.16.3"
    private final String versionString;
    // An array of a Minecraft version like [1, 16, 3]
    private final int[] versionLong;
    // A short integer of the minor version (i.e 16, 15, 14)
    private final int versionShort;
    // String version of the NMS identifier
    private final String versionNMS;
    // Enum version of the NMS identifier
    private final VersionEnum versionEnum;

    public MinecraftVersion(PluginBase plugin)
    {
        this.versionString = calcVersionString(plugin);
        this.versionLong = calcLongVersion();
        this.versionShort = calcShortVersion();
        this.versionEnum = calcVersionEnum(plugin);
        this.versionNMS = calcVersionNMS(plugin);
    }

    /**
     * Calculate the version in String form. Example: "1.16.3"
     *
     * @param plugin A reference to the plugin
     * @return The calculated version String
     */
    private String calcVersionString(PluginBase plugin)
    {
        String  version = plugin.getServer().getVersion();
        Pattern pattern = Pattern.compile("(\\(MC: )([\\d\\.]+)(\\))");
        Matcher matcher = pattern.matcher(version);
        if(matcher.find())
        {
            return matcher.group(2);
        }
        return null;
    }

    /**
     * Calculate the long array version. Example: [1, 16, 3]
     *
     * @return A long array version
     */
    private int[] calcLongVersion()
    {
        String[] splitStr = versionString.split("\\.");
        int[]    arr      = new int[splitStr.length];
        for(int i = 0; i < splitStr.length; ++i)
        {
            arr[i] = Integer.parseInt(splitStr[i]);
        }
        return arr;
    }

    /**
     * Calculate the short int version. Example: 16
     *
     * @return The short int version
     */
    private int calcShortVersion()
    {
        String[] splitStr = versionString.split("\\.");
        int      ver;
        if(splitStr.length == 2)
        {
            ver = Integer.parseInt(splitStr[1].substring(0, splitStr[1].length() - 1));
        }
        else
        {
            ver = Integer.parseInt(splitStr[1]);
        }
        return ver;
    }

    /**
     * Calculate the version enum. Example: v1_16_R3
     *
     * @param plugin A reference to the plugin
     * @return The calculated version enum
     */
    private VersionEnum calcVersionEnum(PluginBase plugin)
    {
        String ver = calcVersionNMS(plugin);
        return VersionEnum.valueOf(ver);
    }

    /**
     * Calculate the NMS version String. Example: "v1_16_R3"
     *
     * @param plugin A reference to the plugin
     * @return The calculated NMS version String
     */
    private String calcVersionNMS(PluginBase plugin)
    {
        String   raw   = plugin.getServer().getClass().getPackage().getName();
        String[] split = raw.split("\\.");
        return split[split.length - 1];
    }

    /**
     * Get the version String that the Minecraft server is running on. Example "1.16.3"
     *
     * @return The version String
     */
    public String getVersionString()
    {
        return versionString;
    }

    /**
     * Get the long version array that the Minecraft server is running on. Example: [1, 16, 3]
     *
     * @return The long version array
     */
    public int[] getVersionLong()
    {
        return versionLong;
    }

    /**
     * Get the short int version that the Minecraft server is running on. Example: 16
     *
     * @return The short int version
     */
    public int getVersionShort()
    {
        return versionShort;
    }

    /**
     * Get the NMS String version that the Minecraft server is running on. Example: "v1_16_R3"
     *
     * @return The NMS String version
     */
    public String getVersionNMS()
    {
        return versionNMS;
    }

    /**
     * Get the NMS Enum version that the Minecraft server is running on. Example: v1_16_R3
     *
     * @return The NMS Enum version
     * @see VersionEnum
     */
    public VersionEnum getVersionEnum()
    {
        return versionEnum;
    }
}
