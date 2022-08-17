package com.mikedeejay2.mikedeejay2lib.util.server;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Utility class for getting values related to ticks
 *
 * @author Mikedeejay2
 */
public final class TickUtil {
    /**
     * Field for the current tick
     */
    private static Field currentTick;

    /**
     * Current for the recent tick, in the {@link TickUtil#serverObj}
     */
    private static Field recentTps;

    /**
     * The <code>MinecraftServer</code> class
     */
    private static Class<?> minecraftServer;

    /**
     * The <code>DedicatedServer</code> object
     */
    private static Object serverObj;

    static {
        try {
            minecraftServer = Class.forName("net.minecraft.server.MinecraftServer");
            currentTick = minecraftServer.getDeclaredField("currentTick");
            recentTps = minecraftServer.getDeclaredField("recentTps");
            Method getServer = minecraftServer.getDeclaredMethod("getServer");
            serverObj = getServer.invoke(minecraftServer);
        } catch(ReflectiveOperationException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get the current tick of the server
     *
     * @return The current tick
     */
    public static int getCurrentTick() {
        try {
            return (int) currentTick.get(null);
        } catch(IllegalAccessException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * Get the TPS of the server of the past minute, 5 minutes, and 15 minutes.
     *
     * @return The TPS array
     */
    public static double[] getTPS() {
        try {
            return ((double[]) recentTps.get(serverObj));
        } catch(IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
