package com.mikedeejay2.mikedeejay2lib.version;

import com.mikedeejay2.mikedeejay2lib.PluginBase;
import com.mikedeejay2.mikedeejay2lib.chat.Chat;

public final class MinecraftVersion
{
    private static final PluginBase plugin = PluginBase.getInstance();
    private static final String versionString = plugin.getServer().getVersion();
    private static final int[] version = genMCVersion();

    private static int[] genMCVersion()
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

    public static int[] getMCVersion()
    {
        return version;
    }
}
