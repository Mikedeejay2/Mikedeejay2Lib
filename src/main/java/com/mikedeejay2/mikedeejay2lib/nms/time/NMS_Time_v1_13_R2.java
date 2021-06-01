package com.mikedeejay2.mikedeejay2lib.nms.time;

import net.minecraft.server.v1_13_R2.MinecraftServer;
import org.bukkit.craftbukkit.v1_13_R2.CraftServer;

public class NMS_Time_v1_13_R2 implements NMS_Time
{
    @Override
    public int getTicks()
    {
        CraftServer craftServer = (CraftServer) org.bukkit.Bukkit.getServer();
        MinecraftServer server = craftServer.getServer();
        return server.ah();
    }
}
