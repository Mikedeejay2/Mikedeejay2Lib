package com.mikedeejay2.mikedeejay2lib.util.math;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.mikedeejay2.mikedeejay2lib.util.array.ArrayUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * A util class for anything that manipulates vectors.
 *
 * @author Mikedeejay2 (With some help from Spigot resources)
 */
public final class MathUtil {
    /**
     * The time in minutes that the cache should hold values
     */
    private static final long CACHE_TIME = 10;

    /**
     * The size of the caches
     */
    private static final long CACHE_SIZE = 1000;

    /**
     * Cache that holds values of circle around angles in radians with a radius of 1
     */
    private static final Cache<Double, Vector> circleCache;

    /**
     * Cache that holds values of a filled circle in relation to its density
     */
    private static final Cache<Double, List<Vector>> circleFilledCache;

    /**
     * Cache that holds hollow spheres in relation to its density
     */
    private static final Cache<Double, List<Vector>> sphereHollowCache;

    /**
     * Cache that holds filled spheres in relation to its density
     */
    private static final Cache<Double, List<Vector>> sphereFilledCache;

    static {
        CacheBuilder<Object, Object> loader = CacheBuilder.newBuilder()
                .maximumSize(CACHE_SIZE)
                .expireAfterWrite(CACHE_TIME, TimeUnit.MINUTES);
        circleCache       = loader.build();
        circleFilledCache = loader.build();
        sphereHollowCache = loader.build();
        sphereFilledCache = loader.build();
    }

    /**
     * Internal loop that iterates through x y z blocks, adding the vectors to the list as it goes
     *
     * @param list    List to add vectors to
     * @param xStart  The starting X position
     * @param yStart  The starting Y position
     * @param zStart  The starting Z position
     * @param xEnd    The ending X position
     * @param yEnd    The ending Y position
     * @param zEnd    The ending Z position
     * @param density The density of the vectors
     * @return The list
     */
    private static List<Vector> xyzLoop(List<Vector> list, double xStart, double yStart, double zStart, double xEnd, double yEnd, double zEnd, double density) {
        if(xStart > xEnd) {
            double temp = xStart;
            xStart = xEnd;
            xEnd = temp;
        }
        if(yStart > yEnd) {
            double temp = yStart;
            yStart = yEnd;
            yEnd = temp;
        }
        if(zStart > zEnd) {
            double temp = zStart;
            zStart = zEnd;
            zEnd = temp;
        }
        for(double x = xStart; x <= xEnd; x += 1.0 / density) {
            for(double y = yStart; y <= yEnd; y += 1.0 / density) {
                for(double z = zStart; z <= zEnd; z += 1.0 / density) {
                    list.add(new Vector(x, y, z));
                }
            }
        }

        return list;
    }

    /**
     * Get the vector around a circle based on a center location and a radius
     *
     * @param center        The center of the circle
     * @param radius        The radius of the circle
     * @param angleInRadian The angle in radians around the circle
     * @return The location of the angle around the circle
     */
    public static Vector getVectorAroundCircle(Location center, double radius, double angleInRadian) {
        angleInRadian = angleInRadian % (Math.PI * 2);

        Vector cached = circleCache.getIfPresent(angleInRadian);
        if(cached != null) {
            return cached.clone().multiply(radius).add(center.toVector());
        }

        double sin = Math.sin(angleInRadian);
        double cos = Math.cos(angleInRadian);

        Vector vector = new Vector(sin, 0, cos);
        circleCache.put(angleInRadian, vector);
        return vector.clone().multiply(radius).add(center.toVector());
    }

    /**
     * Get a list of vectors that represent a circle
     *
     * @param loc     The center location of the circle
     * @param radius  The radius of the circle
     * @param density The density of the circle (The amount of points around the circle that will be mapped)
     * @return The list of vectors of the circle
     */
    public static List<Vector> getCircleVectors(Location loc, double radius, double density) {
        List<Vector> list = new ArrayList<>();
        for(double i = 0; i < 360; i += 1.0 / density) {
            Vector vector = getVectorAroundCircle(loc, radius, Math.toRadians(i));
            list.add(vector);
        }
        return list;
    }

    /**
     * Get a list of locations that represent a circle
     *
     * @param loc     The center location of the circle
     * @param radius  The radius of the circle
     * @param density The density of the circle (The amount of points around the circle that will be mapped)
     * @return The list of locations of the circle
     */
    public static List<Location> getCircleLocations(Location loc, double radius, double density) {
        World world = loc.getWorld();
        List<Vector> vectorList = getCircleVectors(loc, radius, density);
        return ArrayUtil.toLocationList(vectorList, world);
    }

    /**
     * Get a list of vectors that represent a filled circle
     *
     * @param loc     The center location of the circle
     * @param radius  The radius of the circle
     * @param density The density of the circle (The amount of points around the circle that will be mapped)
     * @return The list of vectors of the circle
     */
    public static List<Vector> getCircleFilledVectors(Location loc, double radius, double density) {
        density = density * radius;
        List<Vector> cached = circleFilledCache.getIfPresent(density);
        if(cached != null) {
            List<Vector> translatedList = new ArrayList<>();
            cached.forEach(vector -> translatedList.add(vector.clone().multiply(radius)));
            return offsetVectors(translatedList, loc);
        }
        List<Vector> list = new ArrayList<>();
        for(double x = -1; x <= 1; x += 1.0 / density) {
            for(double z = -1; z <= 1; z += 1.0 / density) {
                if(!(Math.sqrt((x * x) + (z * z)) <= 1)) continue;
                list.add(new Vector(x, 0, z));
            }
        }

        circleFilledCache.put(density, list);
        List<Vector> translatedList = new ArrayList<>();
        list.forEach(vector -> translatedList.add(vector.clone().multiply(radius)));
        return offsetVectors(translatedList, loc);
    }

    /**
     * Get a list of locations that represent a filled circle
     *
     * @param loc     The center location of the circle
     * @param radius  The radius of the circle
     * @param density The density of the circle (The amount of points around the circle that will be mapped)
     * @return The list of locations of the circle
     */
    public static List<Location> getCircleFilledLocations(Location loc, double radius, double density) {
        World world = loc.getWorld();
        List<Vector> vectorList = getCircleFilledVectors(loc, radius, density);
        return ArrayUtil.toLocationList(vectorList, world);
    }

    /**
     * Rotate a point around an origin
     *
     * @param origin Origin Vector
     * @param vector Vector that will be rotated
     * @param degX   The degrees in X to rotate the vector
     * @param degY   The degrees in Y to rotate the vector
     * @param degZ   The degrees in Z to rotate the vector
     * @return The new rotated vector
     */
    public static Vector rotateAroundOrigin(Vector origin, Vector vector, double degX, double degY, double degZ) {
        Vector newVec = vector.subtract(origin);
        newVec.rotateAroundX(Math.toRadians(degX));
        newVec.rotateAroundY(Math.toRadians(degY));
        newVec.rotateAroundZ(Math.toRadians(degZ));
        return newVec.add(origin);
    }

    /**
     * Get a velocity vector that points towards a location
     *
     * @param current     The current location
     * @param destination The location that should be pointed towards
     * @param multiplier  The speed multiplier
     * @return The new velocity vector
     */
    public static Vector getFacingVector(Location current, Location destination, float multiplier) {
        Vector newVec = current.toVector().subtract(destination.toVector());
        newVec.normalize().multiply(-multiplier);
        return newVec;
    }

    /**
     * Get a velocity vector that points towards a vector
     *
     * @param current     The current vector
     * @param destination The vector that should be pointed towards
     * @param multiplier  The speed multiplier
     * @return The new velocity vector
     */
    public static Vector getFacingVector(Vector current, Vector destination, float multiplier) {
        Vector newVec = current.clone().subtract(destination);
        newVec.normalize().multiply(-multiplier);
        return newVec;
    }

    /**
     * Get a list of vectors that create an outline of a sphere.
     *
     * @param loc     The origin location
     * @param radius  The radius of the sphere
     * @param density The density of the points of the sphere
     * @return A new list of vectors that create a sphere outline shape
     */
    public static List<Vector> getSphereHollowVectors(Location loc, double radius, double density) {
        density = (density * Math.PI) * radius;
        List<Vector> cached = sphereHollowCache.getIfPresent(density);
        if(cached != null) {
            List<Vector> translatedList = new ArrayList<>();
            cached.forEach(vector -> translatedList.add(vector.clone().multiply(radius)));
            return offsetVectors(translatedList, loc);
        }

        List<Vector> list = new ArrayList<>();

        for(double yLoop = 0; yLoop <= Math.PI; yLoop += Math.PI / density) {
            double tempRadius = Math.sin(yLoop);
            double y = Math.cos(yLoop);

            for(double xLoop = 0; xLoop < Math.PI * 2.0D; xLoop += Math.PI / density) {
                double x = Math.cos(xLoop) * tempRadius;
                double z = Math.sin(xLoop) * tempRadius;

                list.add(new Vector(x, y, z));
            }
        }

        sphereHollowCache.put(density, list);
        List<Vector> translatedList = new ArrayList<>();
        list.forEach(vector -> translatedList.add(vector.clone().multiply(radius)));
        return offsetVectors(translatedList, loc);
    }

    /**
     * Get a list of locations that create an outline of a sphere.
     *
     * @param loc     The origin location
     * @param radius  The radius of the sphere
     * @param density The density of the points of the sphere
     * @return A new list of locations that create a sphere outline shape
     */
    public static List<Location> getSphereHollowLocations(Location loc, double radius, double density) {
        World world = loc.getWorld();
        List<Vector> vectorList = getSphereHollowVectors(loc, radius, density);
        return ArrayUtil.toLocationList(vectorList, world);
    }

    /**
     * Get a list of vectors that create a filled sphere
     *
     * @param loc     The location of the origin of the sphere
     * @param radius  The radius of the sphere
     * @param density The density of the sphere (1 per block, only do more if you're using particles or something that needs extra precision)
     * @return A new <code>List</code> of vectors that create a filled sphere
     */
    public static List<Vector> getSphereFilledVectors(Location loc, double radius, double density) {
        density = density * radius;
        List<Vector> cached = sphereFilledCache.getIfPresent(density);
        if(cached != null) {
            List<Vector> translatedList = new ArrayList<>();
            cached.forEach(vector -> translatedList.add(vector.clone().multiply(radius)));
            return offsetVectors(translatedList, loc);
        }
        List<Vector> list = new ArrayList<>();
        for(double x = -1; x <= 1; x += 1.0 / density) {
            for(double y = -1; y <= 1; y += 1.0 / density) {
                for(double z = -1; z <= 1; z += 1.0 / density) {
                    if(!(Math.sqrt((x * x) + (y * y) + (z * z)) <= 1)) continue;
                    list.add(new Vector(x, y, z));
                }
            }
        }

        sphereFilledCache.put(density, list);
        List<Vector> translatedList = new ArrayList<>();
        list.forEach(vector -> translatedList.add(vector.clone().multiply(radius)));
        return offsetVectors(translatedList, loc);
    }

    /**
     * Get a list of locations that create a filled sphere
     *
     * @param loc     The location of the origin of the sphere
     * @param radius  The radius of the sphere
     * @param density The density of the sphere (1 per block, only do more if you're using particles or something that needs extra precision)
     * @return A new <code>List</code> of locations that create a filled sphere
     */
    public static List<Location> getSphereFilledLocations(Location loc, double radius, double density) {
        World world = loc.getWorld();
        List<Vector> vectorList = getSphereFilledVectors(loc, radius, density);
        return ArrayUtil.toLocationList(vectorList, world);
    }

    /**
     * Get a list of vectors that create a filled cube
     *
     * @param loc     The location of the origin of the cube
     * @param xWidth  The X width of the cube
     * @param yWidth  The Y width of the cube
     * @param zWidth  The Z width of the cube
     * @param density The density of the sphere (1 per block, only do more if you're using particles or something that needs extra precision)
     * @return A new <code>List</code> of vectors that create a filled cube
     */
    public static List<Vector> getCubeFilledVectors(Location loc, double xWidth, double yWidth, double zWidth, double density) {
        List<Vector> list = new ArrayList<>();
        xWidth = xWidth / 2.0;
        yWidth = yWidth / 2.0;
        zWidth = zWidth / 2.0;
        double startX = loc.getX() - xWidth;
        double startY = loc.getY() - yWidth;
        double startZ = loc.getZ() - zWidth;
        double endX   = loc.getX() + xWidth;
        double endY   = loc.getY() + yWidth;
        double endZ   = loc.getZ() + zWidth;

        return xyzLoop(list, startX, startY, startZ, endX, endY, endZ, density);
    }

    /**
     * Get a list of locations that create a filled cube
     *
     * @param loc     The location of the origin of the cube
     * @param xWidth  The X width of the cube
     * @param yWidth  The Y width of the cube
     * @param zWidth  The Z width of the cube
     * @param density The density of the sphere (1 per block, only do more if you're using particles or something that needs extra precision)
     * @return A new <code>List</code> of locations that create a filled cube
     */
    public static List<Location> getCubeFilledLocations(Location loc, double xWidth, double yWidth, double zWidth, double density) {
        World world = loc.getWorld();
        List<Vector> vectorList = getCubeFilledVectors(loc, xWidth, yWidth, zWidth, density);
        return ArrayUtil.toLocationList(vectorList, world);
    }

    /**
     * Get a list of vectors that create a hollow cube
     *
     * @param loc     The location of the origin of the cube
     * @param xWidth  The X width of the cube
     * @param yWidth  The Y width of the cube
     * @param zWidth  The Z width of the cube
     * @param density The density of the sphere (1 per block, only do more if you're using particles or something that needs extra precision)
     * @return A new <code>List</code> of vectors that create a hollow cube
     */
    public static List<Vector> getCubeHollowVectors(Location loc, double xWidth, double yWidth, double zWidth, double density) {
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
     * @param loc     The location of the origin of the cube
     * @param xWidth  The X width of the cube
     * @param yWidth  The Y width of the cube
     * @param zWidth  The Z width of the cube
     * @param density The density of the sphere (1 per block, only do more if you're using particles or something that needs extra precision)
     * @return A new <code>List</code> of locations that create a hollow cube
     */
    public static List<Location> getCubeHollowLocations(Location loc, double xWidth, double yWidth, double zWidth, double density) {
        World world = loc.getWorld();
        List<Vector> vectorList = getCubeHollowVectors(loc, xWidth, yWidth, zWidth, density);
        return ArrayUtil.toLocationList(vectorList, world);
    }

    /**
     * Get a list of vectors that create an outline of a cube
     *
     * @param loc     The location of the origin of the cube
     * @param xWidth  The X width of the cube
     * @param yWidth  The Y width of the cube
     * @param zWidth  The Z width of the cube
     * @param density The density of the sphere (1 per block, only do more if you're using particles or something that needs extra precision)
     * @return A new <code>List</code> of vectors that create an outline of a cube
     */
    public static List<Vector> getCubeOutlineVectors(Location loc, double xWidth, double yWidth, double zWidth, double density) {
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
     * @param loc     The location of the origin of the cube
     * @param xWidth  The X width of the cube
     * @param yWidth  The Y width of the cube
     * @param zWidth  The Z width of the cube
     * @param density The density of the sphere (1 per block, only do more if you're using particles or something that needs extra precision)
     * @return A new <code>List</code> of locations that create an outline of a cube
     */
    public static List<Location> getCubeOutlineLocations(Location loc, double xWidth, double yWidth, double zWidth, double density) {
        World world = loc.getWorld();
        List<Vector> vectorList = getCubeOutlineVectors(loc, xWidth, yWidth, zWidth, density);
        return ArrayUtil.toLocationList(vectorList, world);
    }

    /**
     * Get a list of vectors that create a filled cube
     *
     * @param loc1    The first location of the cube
     * @param loc2    The second location of the cube
     * @param density The density of the sphere (1 per block, only do more if you're using particles or something that needs extra precision)
     * @return A new <code>List</code> of vectors that create a filled cube
     */
    public static List<Vector> getCubeFilledVectors(Location loc1, Location loc2, double density) {
        List<Vector> list = new ArrayList<>();
        double startX = loc1.getX();
        double startY = loc1.getY();
        double startZ = loc1.getZ();
        double endX = loc2.getX();
        double endY = loc2.getY();
        double endZ = loc2.getZ();

        return xyzLoop(list, startX, startY, startZ, endX, endY, endZ, density);
    }

    /**
     * Get a list of locations that create a filled cube
     *
     * @param loc1    The first location of the cube
     * @param loc2    The second location of the cube
     * @param density The density of the sphere (1 per block, only do more if you're using particles or something that needs extra precision)
     * @return A new <code>List</code> of locations that create a filled cube
     */
    public static List<Location> getCubeFilledLocations(Location loc1, Location loc2, double density) {
        World world = loc1.getWorld();
        List<Vector> vectorList = getCubeFilledVectors(loc1, loc2, density);
        return ArrayUtil.toLocationList(vectorList, world);
    }

    /**
     * Get a list of vectors that create a hollow cube
     *
     * @param loc1    The first location of the cube
     * @param loc2    The second location of the cube
     * @param density The density of the sphere (1 per block, only do more if you're using particles or something that needs extra precision)
     * @return A new <code>List</code> of vectors that create a hollow cube
     */
    public static List<Vector> getCubeHollowVectors(Location loc1, Location loc2, double density) {
        List<Vector> list = new ArrayList<>();

        double loc1X = loc1.getX();
        double loc1Y = loc1.getY();
        double loc1Z = loc1.getZ();
        double loc2X = loc2.getX();
        double loc2Y = loc2.getY();
        double loc2Z = loc2.getZ();

        xyzLoop(list, loc1X, loc2Y, loc2Z, loc1X, loc1Y, loc1Z, density); // +X Side
        xyzLoop(list, loc2X, loc2Y, loc2Z, loc2X, loc1Y, loc1Z, density); // -X Side
        xyzLoop(list, loc2X, loc2Y, loc1Z, loc1X, loc1Y, loc1Z, density); // +Z Side
        xyzLoop(list, loc2X, loc2Y, loc2Z, loc1X, loc1Y, loc2Z, density); // -Z Side
        xyzLoop(list, loc2X, loc1Y, loc2Z, loc1X, loc1Y, loc1Z, density); // +Y Side
        xyzLoop(list, loc2X, loc2Y, loc2Z, loc1X, loc2Y, loc1Z, density); // -Z Side

        return list;
    }

    /**
     * Get a list of locations that create a hollow cube
     *
     * @param loc1    The first location of the cube
     * @param loc2    The second location of the cube
     * @param density The density of the sphere (1 per block, only do more if you're using particles or something that needs extra precision)
     * @return A new <code>List</code> of locations that create a hollow cube
     */
    public static List<Location> getCubeHollowLocations(Location loc1, Location loc2, double density) {
        World world = loc1.getWorld();
        List<Vector> vectorList = getCubeHollowVectors(loc1, loc2, density);
        return ArrayUtil.toLocationList(vectorList, world);
    }

    /**
     * Get a list of vectors that create an outline of a cube
     *
     * @param loc1    The first location of the cube
     * @param loc2    The second location of the cube
     * @param density The density of the sphere (1 per block, only do more if you're using particles or something that needs extra precision)
     * @return A new <code>List</code> of vectors that create an outline of a cube
     */
    public static List<Vector> getCubeOutlineVectors(Location loc1, Location loc2, double density) {

        List<Vector> list = new ArrayList<>();

        double loc1X = loc1.getX();
        double loc1Y = loc1.getY();
        double loc1Z = loc1.getZ();
        double loc2X = loc2.getX();
        double loc2Y = loc2.getY();
        double loc2Z = loc2.getZ();

        // Vertical
        xyzLoop(list, loc1X, loc2Y, loc1Z, loc1X, loc1Y, loc1Z, density); // +X +Z
        xyzLoop(list, loc2X, loc2Y, loc1Z, loc2X, loc1Y, loc1Z, density); // -X +Z
        xyzLoop(list, loc1X, loc2Y, loc2Z, loc1X, loc1Y, loc2Z, density); // +X -Z
        xyzLoop(list, loc2X, loc2Y, loc2Z, loc2X, loc1Y, loc2Z, density); // -X -Z

        // Top
        xyzLoop(list, loc2X, loc1Y, loc1Z, loc1X, loc1Y, loc1Z, density); // +Y +Z
        xyzLoop(list, loc2X, loc1Y, loc2Z, loc1X, loc1Y, loc2Z, density); // +Y -Z
        xyzLoop(list, loc1X, loc1Y, loc2Z, loc1X, loc1Y, loc1Z, density); // +Y +X
        xyzLoop(list, loc2X, loc1Y, loc2Z, loc2X, loc1Y, loc1Z, density); // +Y -X

        // Bottom
        xyzLoop(list, loc2X, loc2Y, loc1Z, loc1X, loc2Y, loc1Z, density); // -Y +Z
        xyzLoop(list, loc2X, loc2Y, loc2Z, loc1X, loc2Y, loc2Z, density); // -Y -Z
        xyzLoop(list, loc1X, loc2Y, loc2Z, loc1X, loc2Y, loc1Z, density); // -Y +X
        xyzLoop(list, loc2X, loc2Y, loc2Z, loc2X, loc2Y, loc1Z, density); // -Y -X

        return list;
    }

    /**
     * Get a list of locations that create an outline of a cube
     *
     * @param loc1    The first location of the cube
     * @param loc2    The second location of the cube
     * @param density The density of the sphere (1 per block, only do more if you're using particles or something that needs extra precision)
     * @return A new <code>List</code> of locations that create an outline of a cube
     */
    public static List<Location> getCubeOutlineLocations(Location loc1, Location loc2, double density) {
        World world = loc1.getWorld();
        List<Vector> vectorList = getCubeOutlineVectors(loc1, loc2, density);
        return ArrayUtil.toLocationList(vectorList, world);
    }

    /**
     * Get a line based off of a starting, ending, and direction vector
     *
     * @param start   The start vector to use
     * @param end     The end vector to use
     * @param density The density of the line
     * @return The list of vectors that represent the line
     */
    public static List<Vector> getLine(Vector start, Vector end, double density) {
        List<Vector> lines = new ArrayList<>();
        Vector curVec = start.clone();
        Vector lookVec = getFacingVector(start, end, 1);
        double length = Math.abs(start.distance(end));

        for(double i = 0; i < length; i += 1.0 / density) {
            Vector newVec = lookVec.clone();
            newVec.multiply(i);
            curVec.add(newVec);

            lines.add(curVec.clone());

            curVec.subtract(newVec);
        }
        return lines;
    }

    /**
     * Get a line based off of a starting and ending location
     *
     * @param start   The start location to use
     * @param end     The end location to use
     * @param density The density of the line
     * @return The list of locations that represent the line
     */
    public static List<Location> getLine(Location start, Location end, double density) {
        List<Location> lines = new ArrayList<>();
        Location curLoc = start.clone();
        Vector lookVec = start.getDirection().clone();
        double length = Math.abs(start.distance(end));

        for(double i = 0; i < length; i += 1.0 / density) {
            Vector newVec = lookVec.clone();
            newVec.multiply(i);
            curLoc.add(newVec);

            lines.add(curLoc.clone());

            curLoc.subtract(newVec);
        }
        return lines;
    }

    /**
     * Get a list of vectors that create a star
     *
     * @param location The location of the star
     * @param size     The size of the star
     * @param density  The density between each point
     * @param points   The amount of points of the star
     * @return A new <code>List</code> of locations that create a star
     */
    public static List<Vector> getStarVectors(Location location, double size, double density, int points) {
        List<Vector> star      = new ArrayList<>();
        List<Vector> starEdge  = new ArrayList<>();
        double       edgeAngle = 180.0 / points;
        double       curAngle  = 0;
        for(int i = 0; i < points; ++i) {
            double newAngle = i % 2 == 0 ? curAngle : curAngle + 180;
            Vector curVec = getVectorAroundCircle(location, size, Math.toRadians(newAngle));
            starEdge.add(curVec);
            curAngle += edgeAngle;
        }
        for(int i = 0; i < starEdge.size(); ++i) {
            Vector curVec = starEdge.get(i);
            Vector nextVec = i == starEdge.size() - 1 ? starEdge.get(0) : starEdge.get(i + 1);
            List<Vector> curLine = getLine(curVec, nextVec, density);
            star.addAll(curLine);
        }
        return star;
    }

    /**
     * Get a list of locations that create a star
     *
     * @param location The location of the star
     * @param size     The size of the star
     * @param density  The density between each point
     * @param points   The amount of points of the star
     * @return A new <code>List</code> of locations that create a star
     */
    public static List<Location> getStarLocations(Location location, double size, double density, int points) {
        World world = location.getWorld();
        List<Vector> vectorList = getStarVectors(location, size, density, points);
        return ArrayUtil.toLocationList(vectorList, world);
    }

    /**
     * Get a list of vectors that create a shape with the specific amount of edges
     *
     * @param location The location of the shape
     * @param size     The size of the shape
     * @param density  The density between each point
     * @param edges    The amount of edges on the shape
     * @return A new <code>List</code> of locations that create a shape
     */
    public static List<Vector> getShapeVectors(Location location, double size, double density, int edges) {
        List<Vector> shape = new ArrayList<>();
        List<Vector> shapeEdge = new ArrayList<>();
        double edgeAngle = 360.0 / edges;
        double curAngle = 0;
        for(int i = 0; i < edges; ++i) {
            Vector curVec = getVectorAroundCircle(location, size, Math.toRadians(curAngle));
            shapeEdge.add(curVec);
            curAngle += edgeAngle;
        }
        for(int i = 0; i < shapeEdge.size(); ++i) {
            Vector curVec = shapeEdge.get(i);
            Vector nextVec = i == shapeEdge.size() - 1 ? shapeEdge.get(0) : shapeEdge.get(i + 1);
            List<Vector> curLine = getLine(curVec, nextVec, density);
            shape.addAll(curLine);
        }
        return shape;
    }

    /**
     * Get a list of locations that create a shape with the specific amount of edges
     *
     * @param location The location of the shape
     * @param size     The size of the shape
     * @param density  The density between each point
     * @param edges    The amount of edges of the shape
     * @return A new <code>List</code> of locations that create a shape
     */
    public static List<Location> getShapeLocations(Location location, double size, double density, int edges) {
        World world = location.getWorld();
        List<Vector> vectorList = getShapeVectors(location, size, density, edges);
        return ArrayUtil.toLocationList(vectorList, world);
    }

    /**
     * Get a list of vectors that create a hollow cylinder
     *
     * @param center        The center of the cylinder
     * @param height        The height of the cylinder
     * @param radius        The radius of the cylinder
     * @param radiusDensity The density of particles in relation to the radius
     * @param heightDensity The density of particles in relation to the height
     * @return A new <code>List</code> of locations that create a cylinder
     */
    public static List<Vector> getCylinderHollowVectors(Location center, double height, double radius, double radiusDensity, double heightDensity) {
        List<Vector> cylinder = new ArrayList<>();
        List<Vector> circle = getCircleVectors(center, radius, radiusDensity);
        Vector transVec = new Vector(0, 1.0 / heightDensity, 0);

        for(double y = 0; y < height; y += 1.0 / heightDensity) {
            ArrayUtil.addClonedVectorsToList(circle, cylinder);
            addVectors(circle, transVec);
        }
        return cylinder;
    }

    /**
     * Get a list of locations that create a hollow cylinder
     *
     * @param center  The center of the cylinder
     * @param height  The height of the cylinder
     * @param radius  The radius of the cylinder
     * @param radiusDensity The density of particles in relation to the radius
     * @param heightDensity The density of particles in relation to the height
     * @return A new <code>List</code> of locations that create a cylinder
     */
    public static List<Location> getCylinderHollowLocations(Location center, double height, double radius, double radiusDensity, double heightDensity) {
        World world = center.getWorld();
        List<Vector> vectorList = getCylinderHollowVectors(center, height, radius, radiusDensity, heightDensity);
        return ArrayUtil.toLocationList(vectorList, world);
    }

    /**
     * Get a list of vectors that create a filled cylinder
     *
     * @param center  The center of the cylinder
     * @param height  The height of the cylinder
     * @param radius  The radius of the cylinder
     * @param radiusDensity The density of particles in relation to the radius
     * @param heightDensity The density of particles in relation to the height
     * @return A new <code>List</code> of locations that create a cylinder
     */
    public static List<Vector> getCylinderFilledVectors(Location center, double height, double radius, double radiusDensity, double heightDensity) {
        List<Vector> cylinder = new ArrayList<>();
        List<Vector> circle = getCircleFilledVectors(center, radius, radiusDensity);
        Vector transVec = new Vector(0, 1.0 / heightDensity, 0);

        for(double y = 0; y < height; y += 1.0 / heightDensity) {
            ArrayUtil.addClonedVectorsToList(circle, cylinder);
            addVectors(circle, transVec);
        }
        return cylinder;
    }

    /**
     * Get a list of locations that create a filled cylinder
     *
     * @param center  The center of the cylinder
     * @param height  The height of the cylinder
     * @param radius  The radius of the cylinder
     * @param radiusDensity The density of particles in relation to the radius
     * @param heightDensity The density of particles in relation to the height
     * @return A new <code>List</code> of locations that create a cylinder
     */
    public static List<Location> getCylinderFilledLocations(Location center, double height, double radius, double radiusDensity, double heightDensity) {
        World world = center.getWorld();
        List<Vector> vectorList = getCylinderFilledVectors(center, height, radius, radiusDensity, heightDensity);
        return ArrayUtil.toLocationList(vectorList, world);
    }

    /**
     * Offset a <code>List</code> of vectors by another <code>Vector</code>
     *
     * @param vectors The list of vectors that will be offset
     * @param offset  The vector to use that will offset the list of vectors
     * @return The new list of offset vectors
     */
    public static List<Vector> offsetVectors(List<Vector> vectors, Vector offset) {
        List<Vector> newVecs = new ArrayList<>();
        vectors.forEach(vector -> newVecs.add(vector.add(offset)));
        return newVecs;
    }

    /**
     * Offset a <code>List</code> of locations by another <code>Location</code>
     *
     * @param locations The list of locations that will be offset
     * @param offset    The location to use that will offset the list of locations
     * @return The new list of offset locations
     */
    public static List<Location> offsetLocations(List<Location> locations, Location offset) {
        List<Location> newLocs = new ArrayList<>();
        locations.forEach(location -> newLocs.add(location.add(offset)));
        return newLocs;
    }

    /**
     * Offset a <code>List</code> of vectors by another <code>Vector</code>
     *
     * @param vectors The list of vectors that will be offset
     * @param offset  The location to use that will offset the list of vectors
     * @return The new list of offset vectors
     */
    public static List<Vector> offsetVectors(List<Vector> vectors, Location offset) {
        List<Vector> newVecs = new ArrayList<>();
        vectors.forEach(vector -> newVecs.add(vector.add(offset.toVector())));
        return newVecs;
    }

    /**
     * Offset a <code>List</code> of locations by another <code>Location</code>
     *
     * @param locations The list of locations that will be offset
     * @param offset    The vector to use that will offset the list of locations
     * @return The new list of offset locations
     */
    public static List<Location> offsetLocations(List<Location> locations, Vector offset) {
        List<Location> newLocs = new ArrayList<>();
        locations.forEach(location -> newLocs.add(location.add(offset)));
        return newLocs;
    }

    /**
     * Add a translation <code>Vector</code> to a list of <code>Locations</code>
     *
     * @param locations   The list of <code>Locations</code> to translate
     * @param translation The translation <code>Vector</code> to use
     */
    public static void addLocations(List<Location> locations, Vector translation) {
        locations.forEach(location -> location.add(translation));
    }

    /**
     * Subtract a translation <code>Vector</code> to a list of <code>Locations</code>
     *
     * @param locations   The list of <code>Locations</code> to translate
     * @param translation The translation <code>Vector</code> to use
     */
    public static void subLocations(List<Location> locations, Vector translation) {
        locations.forEach(location -> location.subtract(translation));
    }

    /**
     * Add a translation <code>Location</code> to a list of <code>Locations</code>
     *
     * @param locations   The list of <code>Locations</code> to translate
     * @param translation The translation <code>Location</code> to use
     */
    public static void addLocations(List<Location> locations, Location translation) {
        locations.forEach(location -> location.add(translation));
    }

    /**
     * Subtract a translation <code>Location</code> to a list of <code>Locations</code>
     *
     * @param locations   The list of <code>Locations</code> to translate
     * @param translation The translation <code>Location</code> to use
     */
    public static void subLocations(List<Location> locations, Location translation) {
        locations.forEach(location -> location.subtract(translation));
    }

    /**
     * Multiply a list of <code>Locations</code> by a double
     *
     * @param locations   The list of <code>Locations</code> to translate
     * @param translation The multiplier
     */
    public static void mulLocations(List<Location> locations, double translation) {
        locations.forEach(location -> location.multiply(translation));
    }

    /**
     * Add a translation <code>Vector</code> to a list of <code>Vectors</code>
     *
     * @param vectors     The list of <code>Vectors</code> to translate
     * @param translation The translation <code>Vector</code> to use
     */
    public static void addVectors(List<Vector> vectors, Vector translation) {
        vectors.forEach(vector -> vector.add(translation));
    }

    /**
     * Subtract a translation <code>Vector</code> to a list of <code>Vectors</code>
     *
     * @param vectors     The list of <code>Vectors</code> to translate
     * @param translation The translation <code>Vector</code> to use
     */
    public static void subVectors(List<Vector> vectors, Vector translation) {
        vectors.forEach(vector -> vector.subtract(translation));
    }

    /**
     * Multiply a list of <code>Vectors</code> by a double
     *
     * @param vectors     The list of <code>Vectors</code> to translate
     * @param translation The multiplier
     */
    public static void mulVectors(List<Vector> vectors, double translation) {
        vectors.forEach(vector -> vector.multiply(translation));
    }

    /**
     * Normalize an entire list of <code>Vectors</code>
     *
     * @param vectors The list of <code>Vectors</code> to normalize
     */
    public static void normalizeList(List<Vector> vectors) {
        vectors.forEach(Vector::normalize);
    }

    /**
     * Get the nearest <code>Player</code> of a <code>Location</code>
     *
     * @param location The location to compare
     * @return The nearest player
     */
    public static Player getNearestPlayer(Location location) {
        double previousDistance = Double.MAX_VALUE;
        Player result = null;
        for(Player player : location.getWorld().getPlayers()) {
            double newDistance = player.getLocation().distanceSquared(location);
            if(newDistance < previousDistance) {
                previousDistance = newDistance;
                result = player;
            }
        }
        return result;
    }

    /**
     * Get the nearest <code>Location</code> of a list of <code>Locations</code>
     *
     * @param locations The list of locations to compare
     * @return The nearest location
     */
    public static Location getNearestLocation(List<Location> locations) {
        double previousDistance = Double.MAX_VALUE;
        Location result = null;
        for(Location location : locations) {
            double newDistance = location.distanceSquared(location);
            if(newDistance < previousDistance) {
                previousDistance = newDistance;
                result = location;
            }
        }
        return result;
    }

    /**
     * Get the nearest <code>Vector</code> of a list of <code>Vectors</code>
     *
     * @param vectors The list of locations to compare
     * @return The nearest location
     */
    public static Vector getNearestVector(List<Vector> vectors) {
        double previousDistance = Double.MAX_VALUE;
        Vector result = null;
        for(Vector location : vectors) {
            double newDistance = location.distanceSquared(location);
            if(newDistance < previousDistance) {
                previousDistance = newDistance;
                result = location;
            }
        }
        return result;
    }

    /**
     * Convert a direction vector to yaw
     *
     * @param directionVector The input direction vector
     * @return The calculated yaw
     */
    public static double directionToYaw(Vector directionVector) {
        return new Location(null, 0, 0, 0).setDirection(directionVector).getYaw();
    }

    /**
     * Convert a direction vector to pitch
     *
     * @param directionVector The input direction vector
     * @return The calculated pitch
     */
    public static double directionToPitch(Vector directionVector) {
        return new Location(null, 0, 0, 0).setDirection(directionVector).getPitch();
    }
}
