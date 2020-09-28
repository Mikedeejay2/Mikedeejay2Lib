package com.mikedeejay2.mikedeejay2lib.util.math;

import org.bukkit.Location;
import org.bukkit.util.Vector;

/**
 * A util class for anything that manipulates vectors.
 *
 * @author Mikedeejay2 (I think, I looked at a lot of Spigot resource though)
 */
public final class MathUtil
{
    /**
     * Get the location around a circle based on a center location and a radius
     *
     * @param center The center of the circle
     * @param radius The radius of the circle
     * @param angleInRadian The angle in radians around the circle
     * @return The location of the angle around the circle
     */
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

    /**
     * Rotate a point around an origin
     *
     * @param origin Origin Vector
     * @param vector Vector that will be rotated
     * @param degX The degrees in X to rotate the vector
     * @param degY The degrees in Y to rotate the vector
     * @param degZ The degrees in Z to rotate the vector
     * @return The new rotated vector
     */
    public static Vector rotateAroundOrigin(Vector origin, Vector vector, float degX, float degY, float degZ)
    {
        Vector newVec = vector.subtract(origin);
        newVec.rotateAroundX(Math.toRadians(degX));
        newVec.rotateAroundY(Math.toRadians(degY));
        newVec.rotateAroundZ(Math.toRadians(degZ));
        return newVec.add(origin);
    }

    /**
     * Get a velocity vector that points towards a location
     *
     * @param toLook The location that should be pointed towards
     * @param currentLoc The current location
     * @param multiplier The speed multiplier
     * @return The new velocity vector
     */
    public static Vector getFacingVelocityVector(Location toLook, Location currentLoc, float multiplier)
    {
        Vector newVec = currentLoc.toVector().subtract(toLook.toVector());
        newVec.normalize().multiply(multiplier);
        return newVec;
    }
}
