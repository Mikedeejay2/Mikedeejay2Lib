package com.mikedeejay2.mikedeejay2lib.nms;

import com.mikedeejay2.mikedeejay2lib.PluginBase;
import com.mikedeejay2.mikedeejay2lib.nms.merchant.NMS_Merchant;
import com.mikedeejay2.mikedeejay2lib.nms.merchant.NMS_Merchant_v1_16_R3;
import com.mikedeejay2.mikedeejay2lib.nms.xpcalc.*;
import com.mikedeejay2.mikedeejay2lib.util.version.VersionEnum;

/**
 * Handles all Net Minecraft Server classes and code.
 *
 * @author Mikedeejay2
 */
public class NMSHandler
{
    protected final PluginBase plugin;
    // The NMS version in an enum
    protected VersionEnum version;

    protected NMS_XP xp;
    protected NMS_Merchant merchant;

    public NMSHandler(PluginBase plugin)
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
        switch(version)
        {
            case v1_16_R3:
            {
                xp = new NMS_XP_v1_16_R3();
                merchant = new NMS_Merchant_v1_16_R3();
            } break;
            case v1_16_R2:
            {
                xp = new NMS_XP_v1_16_R2();
            } break;
            case v1_16_R1:
            {
                xp = new NMS_XP_v1_16_R1();
            } break;
            case v1_15_R1:
            {
                xp = new NMS_XP_v1_15_R1();
            } break;
            case v1_14_R1:
            {
                xp = new NMS_XP_v1_14_R1();
            } break;
            case v1_13_R2:
            {
                xp = new NMS_XP_v1_13_R2();
            } break;
            default:
            {
                plugin.getLogger().severe(plugin.langManager().getText("errors.version_not_valid"));
            }
        }
    }

    public NMS_XP getXP()
    {
        return xp;
    }

    public NMS_Merchant getMerchant()
    {
        return merchant;
    }
}
