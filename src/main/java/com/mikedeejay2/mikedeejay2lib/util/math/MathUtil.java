package com.mikedeejay2.mikedeejay2lib.util.math;

import com.mikedeejay2.mikedeejay2lib.util.array.ArrayUtil;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

/**
 * A util class for anything that manipulates vectors.
 *
 * @author Mikedeejay2 (With some help from Spigot resources)
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
    public static Vector getFacingVector(Location toLook, Location currentLoc, float multiplier)
    {
        Vector newVec = currentLoc.toVector().subtract(toLook.toVector());
        newVec.normalize().multiply(-multiplier);
        return newVec;
    }

    /**
     * Get a list of vectors that create an outline of a sphere.
     *
     * @param loc The origin location
     * @param xWidth The width of the sphere in X
     * @param yWidth The width of the sphere in Y
     * @param zWidth The width of the sphere in Z
     * @param density The density of the points of the sphere
     * @return A new list of vectors that create a sphere outline shape
     */
    public static List<Vector> getSphereOutlineVectors(Location loc, double xWidth, double yWidth, double zWidth, double density)
    {
        List<Vector> list = new ArrayList<>();

        for(double yLoop = 0; yLoop <= Math.PI; yLoop += Math.PI / density)
        {
            double radiusX = Math.sin(yLoop * xWidth);
            double radiusY = Math.sin(yLoop * yWidth);
            double radiusZ = Math.sin(yLoop * zWidth);
            double y = Math.cos(yLoop) * radiusY;

            for(double xLoop = 0; xLoop < Math.PI * 2.0D; xLoop += Math.PI / density)
            {
                double x = Math.cos(xLoop) * radiusX;
                double z = Math.sin(xLoop) * radiusZ;

                list.add(new Vector(x, y, z));
            }
        }
        return list;
    }

    /**
     * Get a list of locations that create an outline of a sphere.
     *
     * @param loc The origin location
     * @param xWidth The width of the sphere in X
     * @param yWidth The width of the sphere in Y
     * @param zWidth The width of the sphere in Z
     * @param density The density of the points of the sphere
     * @return A new list of locations that create a sphere outline shape
     */
    public static List<Location> getSphereOutlineLocations(Location loc, double xWidth, double yWidth, double zWidth, double density)
    {
        World world = loc.getWorld();
        List<Vector> vectorList = getSphereOutlineVectors(loc, xWidth, yWidth, zWidth, density);
        return ArrayUtil.toLocationList(vectorList, world);
    }
}
