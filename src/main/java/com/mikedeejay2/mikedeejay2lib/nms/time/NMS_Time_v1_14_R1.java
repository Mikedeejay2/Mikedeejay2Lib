package com.mikedeejay2.mikedeejay2lib.nms.time;

import net.minecraft.server.v1_14_R1.DedicatedServer;
import org.bukkit.craftbukkit.v1_14_R1.CraftServer;

public class NMS_Time_v1_14_R1 implements NMS_Time
{
    @Override
    public int getTicks()
    {
        CraftServer craftServer = (CraftServer) org.bukkit.Bukkit.getServer();
        DedicatedServer server = craftServer.getServer();
        return server.aj();
    }
}
