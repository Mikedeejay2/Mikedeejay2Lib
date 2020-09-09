package com.mikedeejay2.mikedeejay2lib.version;

import com.mikedeejay2.mikedeejay2lib.PluginBase;

public final class MinecraftVersion
{
    private static final PluginBase plugin = PluginBase.getInstance();
    private static final String versionString = plugin.getServer().getVersion();
    private static final double version = genMCVersion();

    private static double genMCVersion()
    {
        boolean flag = false;
        StringBuilder builder = new StringBuilder();
        String[] splitStr = versionString.split("\\.");
        builder.append(splitStr[0].charAt(splitStr[0].length()-1));
        if(splitStr.length == 2)
        {
            builder.append(splitStr[1].substring(0, splitStr[1].length() - 1));
        }
        else
        {
            builder.append(splitStr[1]);
            builder.append(splitStr[2].substring(splitStr[2].length() - 1));
        }

        return Double.parseDouble(builder.toString());
    }

    public static double getMCVersion()
    {
        return version;
    }
}
