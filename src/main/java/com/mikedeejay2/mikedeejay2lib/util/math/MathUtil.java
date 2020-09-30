package com.mikedeejay2.mikedeejay2lib.util.math;

import com.mikedeejay2.mikedeejay2lib.util.array.ArrayUtil;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;

import java.util.*;

/**
 * A util class for anything that manipulates vectors.
 *
 * @author Mikedeejay2 (With some help from Spigot resources)
 */
public final class MathUtil
{
    private static Map<Double, Vector> circleRefs = new HashMap<>();
    private static Map<Double, List<Vector>> sphereOutlineRefs = new HashMap<>();
    private static Map<Double, List<Vector>> sphereFilledRefs = new HashMap<>();

    /**
     * Get the vector around a circle based on a center location and a radius
     *
     * @param center The center of the circle
     * @param radius The radius of the circle
     * @param angleInRadian The angle in radians around the circle
     * @return The location of the angle around the circle
     */
    public static Vector getVectorAroundCircle(Location center, double radius, double angleInRadian)
    {
        angleInRadian = angleInRadian % Math.PI * 2;

        if(circleRefs.containsKey(angleInRadian))
        {
            return circleRefs.get(angleInRadian);
        }

        double cos = Math.cos(angleInRadian);
        double sin = Math.sin(angleInRadian);

        Vector vector = new Vector(cos, 0, sin);
        circleRefs.put(angleInRadian, vector);
        return vector.multiply(radius).add(center.toVector());
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
     * @param radius The radius of the sphere
     * @param density The density of the points of the sphere
     * @return A new list of vectors that create a sphere outline shape
     */
    public static List<Vector> getSphereOutlineVectors(Location loc, double radius, double density)
    {
        if(sphereOutlineRefs.containsKey(density))
        {
            return sphereOutlineRefs.get(density);
        }

        List<Vector> list = new ArrayList<>();

        for(double yLoop = 0; yLoop <= Math.PI; yLoop += Math.PI / density)
        {
            double radiusX = Math.sin(yLoop);
            double radiusY = Math.sin(yLoop);
            double radiusZ = Math.sin(yLoop);
            double y = Math.cos(yLoop) * radiusY;

            for(double xLoop = 0; xLoop < Math.PI * 2.0D; xLoop += Math.PI / density)
            {
                double x = Math.cos(xLoop) * radiusX;
                double z = Math.sin(xLoop) * radiusZ;

                list.add(new Vector(x, y, z));
            }
        }

        sphereOutlineRefs.put(density, list);
        List<Vector> translatedList = new ArrayList<>();
        list.forEach(vector -> translatedList.add(vector.multiply(radius)));
        return ArrayUtil.offsetVectors(translatedList, loc);
    }

    /**
     * Get a list of locations that create an outline of a sphere.
     *
     * @param loc The origin location
     * @param radius The radius of the sphere
     * @param density The density of the points of the sphere
     * @return A new list of locations that create a sphere outline shape
     */
    public static List<Location> getSphereOutlineLocations(Location loc, double radius, double density)
    {
        World world = loc.getWorld();
        List<Vector> vectorList = getSphereOutlineVectors(loc, radius, density);
        return ArrayUtil.toLocationList(vectorList, world);
    }

    /**
     * Get a list of vectors that create a filled sphere
     *
     * @param loc The location of the origin of the sphere
     * @param radius The radius of the sphere
     * @param density The density of the sphere (1 per block, only do more if you're using particles or something that needs extra precision)
     * @return A new <tt>List</tt> of vectors that create a filled sphere
     */
    public static List<Vector> getSphereFilledVectors(Location loc, double radius, double density)
    {
        if(sphereFilledRefs.containsKey(density))
        {
            return sphereFilledRefs.get(density);
        }
        List<Vector> list = new ArrayList<>();
        for(double x = -radius; x < radius; x += 1.0 / density)
        {
            for(double y = -radius; y < radius; y += 1.0 / density)
            {
                for(double z = -radius; z < radius; z += 1.0 / density)
                {
                    if(!(Math.sqrt((x * x) + (y * y) + (z * z)) <= radius)) continue;
                    list.add(new Vector(x, y, z));
                }
            }
        }

        sphereFilledRefs.put(density, list);
        List<Vector> translatedList = new ArrayList<>();
        list.forEach(vector -> translatedList.add(vector.multiply(radius)));
        return ArrayUtil.offsetVectors(translatedList, loc);
    }

    /**
     * Get a list of locations that create a filled sphere
     *
     * @param loc The location of the origin of the sphere
     * @param radius The radius of the sphere
     * @param density The density of the sphere (1 per block, only do more if you're using particles or something that needs extra precision)
     * @return A new <tt>List</tt> of locations that create a filled sphere
     */
    public static List<Location> getSphereFilledLocations(Location loc, double radius, double density)
    {
        World world = loc.getWorld();
        List<Vector> vectorList = getSphereFilledVectors(loc, radius, density);
        return ArrayUtil.toLocationList(vectorList, world);
    }
}
