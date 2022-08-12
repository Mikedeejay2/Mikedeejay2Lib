package com.mikedeejay2.mikedeejay2lib.util.version;

import org.bukkit.Bukkit;

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
    /**
     * Holds the version string like "1.16.3"
     */
    private static final String versionString;

    /**
     * An array of a Minecraft version like [1, 16, 3]
     */
    private static final int[] versionLong;

    /**
     * A short integer of the minor version (i.e 16, 15, 14)
     */
    private static final int versionShort;

    /**
     * String version of the NMS identifier
     */
    private static final String versionNMS;

    static
    {
        versionString = calcVersionString();
        versionLong = calcLongVersion();
        versionShort = calcShortVersion();
        versionNMS = calcVersionNMS();
    }

    /**
     * This class is static and should not be constructed
     */
    private MinecraftVersion()
    {
        throw new UnsupportedOperationException("Can not initialize static class MinecraftVersion");
    }

    /**
     * Calculate the version in String form. Example: "1.16.3"
     *
     * @return The calculated version String
     */
    private static String calcVersionString()
    {
        String version = Bukkit.getServer().getVersion();
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
    private static int[] calcLongVersion()
    {
        String[] splitStr = versionString.split("\\.");
        int[] arr = new int[splitStr.length];
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
    private static int calcShortVersion()
    {
        String[] splitStr = versionString.split("\\.");
        return Integer.parseInt(splitStr[1]);
    }

    /**
     * Calculate the NMS version String. Example: "v1_16_R3"
     *
     * @return The calculated NMS version String
     */
    private static String calcVersionNMS()
    {
        String raw = Bukkit.getServer().getClass().getPackage().getName();
        String[] split = raw.split("\\.");
        return split[split.length - 1];
    }

    /**
     * Get the version String that the Minecraft server is running on. Example "1.16.3"
     *
     * @return The version String
     */
    public static String getVersionString()
    {
        return versionString;
    }

    /**
     * Get the long version array that the Minecraft server is running on. Example: [1, 16, 3]
     *
     * @return The long version array
     */
    public static int[] getVersionLong()
    {
        return versionLong;
    }

    /**
     * Get the short int version that the Minecraft server is running on. Example: 16
     *
     * @return The short int version
     */
    public static int getVersionShort()
    {
        return versionShort;
    }

    /**
     * Get the NMS String version that the Minecraft server is running on. Example: "v1_16_R3"
     *
     * @return The NMS String version
     */
    public static String getVersionNMS()
    {
        return versionNMS;
    }
}
