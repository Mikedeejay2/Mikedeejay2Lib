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
     * Internal loop that iterates through x y z blocks, adding the vectors to the list as it goes
     *
     * @param list List to add vectors to
     * @param xStart The starting X position
     * @param yStart The starting Y position
     * @param zStart The starting Z position
     * @param xEnd The ending X position
     * @param yEnd The ending Y position
     * @param zEnd The ending Z position
     * @param density The density of the vectors
     * @return The list
     */
    private static List<Vector> xyzLoop(List<Vector> list, double xStart, double yStart, double zStart, double xEnd, double yEnd, double zEnd, double density)
    {
        for(double x = xStart; x < xEnd; x += 1.0 / density)
        {
            for(double y = yStart; y < yEnd; y += 1.0 / density)
            {
                for(double z = zStart; z < zEnd; z += 1.0 / density)
                {
                    list.add(new Vector(x, y, z));
                }
            }
        }

        return list;
    }

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
            double tempRadius = Math.sin(yLoop);
            double y = Math.cos(yLoop) * tempRadius;

            for(double xLoop = 0; xLoop < Math.PI * 2.0D; xLoop += Math.PI / density)
            {
                double x = Math.cos(xLoop) * tempRadius;
                double z = Math.sin(xLoop) * tempRadius;

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

    /**
     * Get a list of vectors that create a filled cube
     *
     * @param loc The location of the origin of the cube
     * @param xWidth The X width of the cube
     * @param yWidth The Y width of the cube
     * @param zWidth The Z width of the cube
     * @param density The density of the sphere (1 per block, only do more if you're using particles or something that needs extra precision)
     * @return A new <tt>List</tt> of vectors that create a filled cube
     */
    public static List<Vector> getCubeFilledVectors(Location loc, double xWidth, double yWidth, double zWidth, double density)
    {
        List<Vector> list = new ArrayList<>();
        xWidth = xWidth / 2.0;
        yWidth = yWidth / 2.0;
        zWidth = zWidth / 2.0;
        double startX = loc.getBlockX() - xWidth;
        double startY = loc.getBlockY() - yWidth;
        double startZ = loc.getBlockZ() - zWidth;
        double endX = loc.getBlockX() + xWidth;
        double endY = loc.getBlockY() + yWidth;
        double endZ = loc.getBlockZ() + zWidth;

        return xyzLoop(list, startX, startY, startZ, endX, endY, endZ, density);
    }

    /**
     * Get a list of locations that create a filled cube
     *
     * @param loc The location of the origin of the cube
     * @param xWidth The X width of the cube
     * @param yWidth The Y width of the cube
     * @param zWidth The Z width of the cube
     * @param density The density of the sphere (1 per block, only do more if you're using particles or something that needs extra precision)
     * @return A new <tt>List</tt> of locations that create a filled cube
     */
    public static List<Location> getCubeFilledLocations(Location loc, double xWidth, double yWidth, double zWidth, double density)
    {
        World world = loc.getWorld();
        List<Vector> vectorList = getCubeFilledVectors(loc, xWidth, yWidth, zWidth, density);
        return ArrayUtil.toLocationList(vectorList, world);
    }

    /**
     * Get a list of vectors that create a hollow cube
     *
     * @param loc The location of the origin of the cube
     * @param xWidth The X width of the cube
     * @param yWidth The Y width of the cube
     * @param zWidth The Z width of the cube
     * @param density The density of the sphere (1 per block, only do more if you're using particles or something that needs extra precision)
     * @return A new <tt>List</tt> of vectors that create a hollow cube
     */
    public static List<Vector> getCubeHollowVectors(Location loc, double xWidth, double yWidth, double zWidth, double density)
    {
        List<Vector> list = new ArrayList<>();
        xWidth = xWidth / 2.0;
        yWidth = yWidth / 2.0;
        zWidth = zWidth / 2.0;

        double locX = loc.getX();
        double locY = loc.getY();
        double locZ = loc.getZ();

        double x1 = locX + xWidth, y1 = locY + yWidth, z1 = locZ + zWidth; // +++
        double x2 = locX + xWidth, y2 = locY + yWidth, z2 = locZ - zWidth; // ++-
        double x3 = locX - xWidth, y3 = locY + yWidth, z3 = locZ + zWidth; // -++
        double x4 = locX - xWidth, y4 = locY + yWidth, z4 = locZ - zWidth; // -+-
        double x5 = locX + xWidth, y5 = locY - yWidth, z5 = locZ + zWidth; // +-+
        double x6 = locX + xWidth, y6 = locY - yWidth, z6 = locZ - zWidth; // +--
        double x7 = locX - xWidth, y7 = locY - yWidth, z7 = locZ + zWidth; // --+
        double x8 = locX - xWidth, y8 = locY - yWidth, z8 = locZ - zWidth; // ---

        xyzLoop(list, x6, y6, z6, x1, y1, z1, density); // +X Side
        xyzLoop(list, x8, y8, z8, x3, y3, z3, density); // -X Side
        xyzLoop(list, x7, y7, z7, x1, y1, z1, density); // +Z Side
        xyzLoop(list, x8, y8, z8, x2, y2, z2, density); // -Z Side
        xyzLoop(list, x4, y4, z4, x1, y1, z1, density); // +Y Side
        xyzLoop(list, x8, y8, z8, x5, y5, z5, density); // -Z Side

        return list;
    }

    /**
     * Get a list of locations that create a hollow cube
     *
     * @param loc The location of the origin of the cube
     * @param xWidth The X width of the cube
     * @param yWidth The Y width of the cube
     * @param zWidth The Z width of the cube
     * @param density The density of the sphere (1 per block, only do more if you're using particles or something that needs extra precision)
     * @return A new <tt>List</tt> of locations that create a hollow cube
     */
    public static List<Location> getCubeHollowLocations(Location loc, double xWidth, double yWidth, double zWidth, double density)
    {
        World world = loc.getWorld();
        List<Vector> vectorList = getCubeHollowVectors(loc, xWidth, yWidth, zWidth, density);
        return ArrayUtil.toLocationList(vectorList, world);
    }

    /**
     * Get a list of vectors that create an outline of a cube
     *
     * @param loc The location of the origin of the cube
     * @param xWidth The X width of the cube
     * @param yWidth The Y width of the cube
     * @param zWidth The Z width of the cube
     * @param density The density of the sphere (1 per block, only do more if you're using particles or something that needs extra precision)
     * @return A new <tt>List</tt> of vectors that create an outline of a cube
     */
    public static List<Vector> getCubeOutlineVectors(Location loc, double xWidth, double yWidth, double zWidth, double density)
    {
        List<Vector> list = new ArrayList<>();
        xWidth = xWidth / 2.0;
        yWidth = yWidth / 2.0;
        zWidth = zWidth / 2.0;

        double locX = loc.getX();
        double locY = loc.getY();
        double locZ = loc.getZ();

        double x1 = locX + xWidth, y1 = locY + yWidth, z1 = locZ + zWidth; // +++
        double x2 = locX + xWidth, y2 = locY + yWidth, z2 = locZ - zWidth; // ++-
        double x3 = locX - xWidth, y3 = locY + yWidth, z3 = locZ + zWidth; // -++
        double x4 = locX - xWidth, y4 = locY + yWidth, z4 = locZ - zWidth; // -+-
        double x5 = locX + xWidth, y5 = locY - yWidth, z5 = locZ + zWidth; // +-+
        double x6 = locX + xWidth, y6 = locY - yWidth, z6 = locZ - zWidth; // +--
        double x7 = locX - xWidth, y7 = locY - yWidth, z7 = locZ + zWidth; // --+
        double x8 = locX - xWidth, y8 = locY - yWidth, z8 = locZ - zWidth; // ---

        // Vertical
        xyzLoop(list, x5, y5, z5, x1, y1, z1, density); // +X +Z
        xyzLoop(list, x7, y7, z7, x3, y3, z3, density); // -X +Z
        xyzLoop(list, x6, y6, z6, x2, y2, z2, density); // +X -Z
        xyzLoop(list, x8, y8, z8, x4, y4, z4, density); // -X -Z

        // Top
        xyzLoop(list, x3, y3, z3, x1, y1, z1, density); // +Y +Z
        xyzLoop(list, x4, y4, z4, x2, y2, z2, density); // +Y -Z
        xyzLoop(list, x2, y2, z2, x1, y1, z1, density); // +Y +X
        xyzLoop(list, x4, y4, z4, x3, y3, z3, density); // +Y -X

        // Bottom
        xyzLoop(list, x7, y7, z7, x5, y5, z5, density); // -Y +Z
        xyzLoop(list, x8, y8, z8, x6, y6, z6, density); // -Y -Z
        xyzLoop(list, x6, y6, z6, x5, y5, z5, density); // -Y +X
        xyzLoop(list, x8, y8, z8, x7, y7, z7, density); // -Y -X

        return list;
    }

    /**
     * Get a list of locations that create an outline of a cube
     *
     * @param loc The location of the origin of the cube
     * @param xWidth The X width of the cube
     * @param yWidth The Y width of the cube
     * @param zWidth The Z width of the cube
     * @param density The density of the sphere (1 per block, only do more if you're using particles or something that needs extra precision)
     * @return A new <tt>List</tt> of locations that create an outline of a cube
     */
    public static List<Location> getCubeOutlineLocations(Location loc, double xWidth, double yWidth, double zWidth, double density)
    {
        World world = loc.getWorld();
        List<Vector> vectorList = getCubeOutlineVectors(loc, xWidth, yWidth, zWidth, density);
        return ArrayUtil.toLocationList(vectorList, world);
    }
}
