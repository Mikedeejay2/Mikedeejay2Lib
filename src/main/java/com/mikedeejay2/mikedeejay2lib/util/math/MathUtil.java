package com.mikedeejay2.mikedeejay2lib.util.math;

import org.bukkit.Location;
import org.bukkit.util.Vector;

public final class MathUtil
{
    public static Location getLocationAroundCircle(Location center, double radius, double angleInRadian)
    {
        angleInRadian = angleInRadian % Math.PI * 2;
        double cos = radius * Math.cos(angleInRadian);
        double sin = radius * Math.sin(angleInRadian);
        float yaw = center.getYaw();
        float pitch = center.getPitch();
        double x = center.getX();
        double y = center.getY();
        double z = center.getZ();

        Vector direction = center.getDirection();

        if( yaw < 0 ) yaw += 360;
        yaw %= 360;
        int i = (int)((yaw + 8) / 22.5);

        Vector offset = null;

        switch(i)
        {
            case 16: case 15: case 0: case 1: case 2:       // west
            offset = new Vector(sin, cos, 0);
            break;
            case 3: case 4: case 5: case 6:                 // north
            offset = new Vector(0, cos, sin);
            break;
            case 7: case 8: case 9: case 10:                // east
            offset = new Vector(sin, cos, 0);
            break;
            case 11: case 12: case 13: case 14:             // south
            offset = new Vector(0, cos, sin);
            break;
        }

        if(Math.abs(pitch) > 67.5)
        {
            offset = new Vector(cos, 0, sin);
        }

        return new Location(center.getWorld(), x + offset.getX(), y + offset.getY(), z + offset.getZ(), yaw, pitch);
    }
}
