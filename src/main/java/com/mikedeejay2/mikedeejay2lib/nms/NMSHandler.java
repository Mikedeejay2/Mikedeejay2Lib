package com.mikedeejay2.mikedeejay2lib.nms;

import com.mikedeejay2.mikedeejay2lib.BukkitPlugin;
import com.mikedeejay2.mikedeejay2lib.nms.merchant.*;
import com.mikedeejay2.mikedeejay2lib.nms.time.*;
import com.mikedeejay2.mikedeejay2lib.nms.xpcalc.*;
import com.mikedeejay2.mikedeejay2lib.util.version.VersionEnum;
import org.jetbrains.annotations.Nullable;

/**
 * Handles all Net Minecraft Server classes and code.
 *
 * @author Mikedeejay2
 */
public class NMSHandler
{
    /**
     * The {@link BukkitPlugin} instance
     */
    protected final BukkitPlugin plugin;
    // The NMS version in an enum
    protected @Nullable VersionEnum version;

    protected @Nullable NMS_XP xp;
    protected @Nullable NMS_Merchant merchant;
    protected @Nullable NMS_Time time;

    public NMSHandler(BukkitPlugin plugin)
    {
        this.plugin = plugin;
        this.version = plugin.getMCVersion().getVersionEnum();
        constructClasses();
    }

    /**
     * Constructs the NMS classes of the correct version using the version enum.
     */
    private void constructClasses()
    {
        if(version == null)
        {
            plugin.sendSevere(
                String.format("The server's Minecraft version (%s) is not a valid version for this plugin. Errors may occur.",
                              plugin.getMCVersion().getVersionString()));
            return;
        }
        switch(version)
        {
            case v1_17_R1:
            {
                xp = new NMS_XP_v1_17_R1();
                merchant = new NMS_Merchant_v1_17_R1();
                time = new NMS_Time_v1_17_R1();
            } break;
            case v1_16_R3:
            {
                xp = new NMS_XP_v1_16_R3();
                merchant = new NMS_Merchant_v1_16_R3();
                time = new NMS_Time_v1_16_R3();
            } break;
            case v1_16_R2:
            {
                xp = new NMS_XP_v1_16_R2();
                merchant = new NMS_Merchant_v1_16_R2();
                time = new NMS_Time_v1_16_R2();
            } break;
            case v1_16_R1:
            {
                xp = new NMS_XP_v1_16_R1();
                merchant = new NMS_Merchant_v1_16_R1();
                time = new NMS_Time_v1_16_R1();
            } break;
            case v1_15_R1:
            {
                xp = new NMS_XP_v1_15_R1();
                merchant = new NMS_Merchant_v1_15_R1();
                time = new NMS_Time_v1_15_R1();
            } break;
            case v1_14_R1:
            {
                xp = new NMS_XP_v1_14_R1();
                merchant = new NMS_Merchant_v1_14_R1();
                time = new NMS_Time_v1_14_R1();
            } break;
            case v1_13_R2:
            {
                xp = new NMS_XP_v1_13_R2();
                time = new NMS_Time_v1_13_R2();
                merchant = null; // Merchant is different in pre-1.14
            } break;
            default:
            {
                plugin.sendSevere(
                    String.format("The server's Minecraft version (%s) is not a valid version for this plugin. Errors may occur.",
                                  plugin.getMCVersion().getVersionString()));
            }
        }
    }

    public @Nullable NMS_XP getXP()
    {
        return xp;
    }

    public @Nullable NMS_Merchant getMerchant()
    {
        return merchant;
    }

    public @Nullable NMS_Time getTime()
    {
        return time;
    }
}
