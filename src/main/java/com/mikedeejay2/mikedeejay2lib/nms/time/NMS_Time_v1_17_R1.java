package com.mikedeejay2.mikedeejay2lib.nms.time;

import net.minecraft.server.dedicated.DedicatedServer;
import org.bukkit.craftbukkit.v1_17_R1.CraftServer;

public class NMS_Time_v1_17_R1 implements NMS_Time
{
    @Override
    public int getTicks()
    {
        CraftServer craftServer = (CraftServer) org.bukkit.Bukkit.getServer();
        DedicatedServer server = craftServer.getServer();
        return server.ai();
    }
}
