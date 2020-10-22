package com.mikedeejay2.mikedeejay2lib.util.version;

import com.mikedeejay2.mikedeejay2lib.PluginBase;

/**
 * A util for getting this Minecraft server's version and turning it into an
 * easily comparable array
 *
 * @author Mikedeejay2
 */
public final class MinecraftVersion
{
    // Holds the version string, like "git.paper-RC_03 (MC:1.16.2)" or something like that
    private final String versionString;
    // An array of a Minecraft version like [1, 16, 3], you probably only care about version[1]
    private final int[] version;

    public MinecraftVersion(PluginBase plugin)
    {
        this.versionString = plugin.getServer().getVersion();
        this.version = genMCVersion();
    }

    /**
     * Generates an int array of this server's Minecraft version for easy access
     *
     * @return Int Array that represents this server's Minecraft version
     */
    private int[] genMCVersion()
    {
        boolean flag = false;
        String[] splitStr = versionString.split("\\.");
        int[] arr = new int[3];
        arr[0] = Integer.parseInt(String.valueOf(splitStr[0].charAt(splitStr[0].length()-1)));
        if(splitStr.length == 2)
        {
            arr[1] = Integer.parseInt(splitStr[1].substring(0, splitStr[1].length() - 1));
        }
        else
        {
            arr[1] = Integer.parseInt(splitStr[1]);
            arr[2] = Integer.parseInt(splitStr[2].substring(0, splitStr[2].length() - 1));
        }

        return arr;
    }

    /**
     * Get the Minecraft version array
     *
     * @return The Minecraft version array
     */
    public int[] getMCVersion()
    {
        return version;
    }
}
