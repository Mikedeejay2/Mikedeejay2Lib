package com.mikedeejay2.mikedeejay2lib.nms.time;

import net.minecraft.server.v1_16_R3.DedicatedServer;
import org.bukkit.craftbukkit.v1_16_R3.CraftServer;

public class NMS_Time_v1_16_R3 implements NMS_Time
{
    @Override
    public int getTicks()
    {
        CraftServer craftServer = (CraftServer) org.bukkit.Bukkit.getServer();
        DedicatedServer server = craftServer.getServer();
        return server.ai();
    }
}
