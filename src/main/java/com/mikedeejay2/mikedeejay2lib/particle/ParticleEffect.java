package com.mikedeejay2.mikedeejay2lib.particle;

import com.mikedeejay2.mikedeejay2lib.particle.module.effect.ParticleEModule;
import com.mikedeejay2.mikedeejay2lib.particle.shape.ParticleShape;
import com.mikedeejay2.mikedeejay2lib.util.math.MathUtil;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a group of particle shapes to create a particle effect.
 * Shapes can be added through {@link ParticleEffect#addShape(ParticleShape)}
 * 
 * @see ParticleShape
 *
 * @author Mikedeejay2
 */
public class ParticleEffect
{
    /**
     * The list of particle shapes
     */
    protected List<ParticleShape> shapes;

    /**
     * The particle data for displaying the particles
     */
    protected ParticleData particleData;

    /**
     * The vectors of untranslated (baked) vectors
     */
    protected List<Vector> untranslatedVecs;

    /**
     * The vectors of translated vectors
     */
    protected List<Vector> translatedVecs;

    /**
     * The scale vector for this effect
     */
    protected Vector scaleVec;

    /**
     * The rotation vector for this effect
     */
    protected Vector rotationVec;

    /**
     * The translation vector for this effect
     */
    protected Vector translationVec;

    /**
     * The origin location
     */
    protected Location origin;

    /**
     * The world (from the origin location)
     */
    protected World world;

    /**
     * Whether this effect has been baked yet
     */
    protected boolean baked;

    /**
     * Whether this effect has been updated to the latest translations
     */
    protected boolean updated;

    /**
     * The delay of showing this effect (delay <= 0 will ignore delay)
     */
    protected long delay;

    /**
     * The amount of times that this effect will be visible (count <= 0 will always be visible)
     */
    protected long count;

    /**
     * The list of effect modules
     */
    protected List<ParticleEModule> modules;

    /**
     * Construct a new <code>ParticleEffect</code>
     *
     * @param origin       The origin location
     * @param particleData The particle data for displaying the particles
     */
    public ParticleEffect(Location origin, ParticleData particleData)
    {
        this.shapes = new ArrayList<>();
        this.untranslatedVecs = new ArrayList<>();
        this.translatedVecs = new ArrayList<>();
        this.scaleVec = new Vector(1, 1, 1);
        this.rotationVec = new Vector(0, 0, 0);
        this.translationVec = new Vector(0, 0, 0);
        this.particleData = particleData;
        this.origin = origin;
        this.world = origin.getWorld();
        this.baked = false;
        this.updated = false;
        this.delay = 0;
        this.count = 0;
        this.modules = new ArrayList<>();
    }

    /**
     * Add a <code>ParticleShape</code> to this effect
     *
     * @param shape The shape to add
     * @return A reference to this <code>ParticleEffect</code>
     */
    public ParticleEffect addShape(ParticleShape shape)
    {
        shapes.add(shape);
        return this;
    }

    /**
     * See whether this <code>ParticleEffect</code> has a specific <code>ParticleShape</code>
     *
     * @param shape The shape to search for
     * @return Whether the shape was found or not
     */
    public boolean containsShape(ParticleShape shape)
    {
        return shapes.contains(shape);
    }

    /**
     * See whether this <code>ParticleEffect</code> contains a <code>ParticleShape</code> based off of the
     * shape's class.
     *
     * @param shapeClass The class of the shape to search for
     * @return Whether a shape with the class was found or not
     */
    public boolean containsShape(Class<? extends ParticleShape> shapeClass)
    {
        for(ParticleShape shape : shapes)
        {
            if(shapeClass == shape.getClass()) return true;
        }
        return false;
    }

    /**
     * Get a <code>ParticleShape</code> from this effect by its index in the list
     *
     * @param index The index to get the shape from
     * @return The requested <code>ParticleShape</code>
     */
    public ParticleShape getShape(int index)
    {
        return shapes.get(index);
    }

    /**
     * Get a <code>ParticleShape</code> from this effect by the class of the shape
     *
     * @param shapeClass The class of the shape to get
     * @param <T>        The shape type
     * @return The requested <code>ParticleShape</code>, null if not found
     */
    public <T extends ParticleShape> T getShape(Class<T> shapeClass)
    {
        for(ParticleShape shape : shapes)
        {
            if(shapeClass == shape.getClass()) return (T) shape;
        }
        return null;
    }

    /**
     * Remove a <code>ParticleShape</code> from this effect by its index in the list
     *
     * @param index The index to remove the shape at
     * @return A reference to this <code>ParticleEffect</code>
     */
    public ParticleEffect removeShape(int index)
    {
        shapes.remove(index);
        return this;
    }

    /**
     * Remove a <code>ParticleShape</code> from this effect by a reference to the shape
     *
     * @param shape The shape to remove
     * @return A reference to this <code>ParticleEffect</code>
     */
    public ParticleEffect removeShape(ParticleShape shape)
    {
        shapes.remove(shape);
        return this;
    }

    /**
     * Add a <code>ParticleEModule</code> to this effect
     *
     * @param module The module to add
     * @return A reference to this <code>ParticleEffect</code>
     */
    public ParticleEffect addModule(ParticleEModule module)
    {
        modules.add(module);
        return this;
    }

    /**
     * Add a <code>ParticleEModule</code> to this effect at a specified index
     *
     * @param module The module to add
     * @param index  The index to add the module at
     * @return A reference to this <code>ParticleEffect</code>
     */
    public ParticleEffect addModule(ParticleEModule module, int index)
    {
        modules.add(index, module);
        return this;
    }

    /**
     * See whether this effect contains a specific <code>ParticleEModule</code>
     *
     * @param module The module to search for
     * @return Whether the <code>ParticleEModule</code> was found or not
     */
    public boolean containsModule(ParticleEModule module)
    {
        return modules.contains(module);
    }

    /**
     * See whether this effect contains a <code>ParticleEModule</code> based off of the
     * module's class
     *
     * @param moduleClass The class of the module to search for
     * @return Whether the <code>ParticleEModule</code> was found or not
     */
    public boolean containsModule(Class<? extends ParticleEModule> moduleClass)
    {
        for(ParticleEModule module : modules)
        {
            if(moduleClass == module.getClass()) return true;
        }
        return false;
    }

    /**
     * Remove a <code>ParticleEModule</code> from this effect based off of an index
     *
     * @param index The index to remove the module at
     * @return A reference to this <code>ParticleEffect</code>
     */
    public ParticleEffect removeModule(int index)
    {
        modules.remove(index);
        return this;
    }

    /**
     * Remove a <code>ParticleEModule</code> from this effect
     *
     * @param module The module to remove
     * @return A reference to this <code>ParticleEffect</code>
     */
    public ParticleEffect removeModule(ParticleEModule module)
    {
        modules.remove(module);
        return this;
    }

    /**
     * Remove a <code>ParticleModule</code> from this effect based off of the class of the module
     *
     * @param moduleClass The class of the module to remove
     * @return A reference to this <code>ParticleEffect</code>
     */
    public ParticleEffect removeModule(Class<? extends ParticleEModule> moduleClass)
    {
        for(ParticleEModule module : modules)
        {
            if(moduleClass != module.getClass()) continue;
            modules.remove(module);
            break;
        }
        return this;
    }

    /**
     * Get a <code>ParticleEModule</code> from this effect based off of the index of the module
     *
     * @param index The index to get the module from
     * @return The requested <code>ParticleEModule</code>
     */
    public ParticleEModule getModule(int index)
    {
        return modules.get(index);
    }

    /**
     * Get a <code>ParticleEModule</code> from this effect based off of the index of the module
     *
     * @param moduleClass The class of the module to get
     * @param <T>         The type of particle module
     * @return The requested <code>ParticleEModule</code>, null if not found
     */
    public <T extends ParticleEModule> T getModule(Class<T> moduleClass)
    {
        for(ParticleEModule module : modules)
        {
            if(moduleClass == module.getClass()) return (T) module;
        }
        return null;
    }

    /**
     * Reset (clear) all modules from this <code>ParticleEffect</code>
     *
     * @return A reference to this <code>ParticleEffect</code>
     */
    public ParticleEffect resetModules()
    {
        modules.clear();
        return this;
    }

    /**
     * Process all data from <code>ParticleShapes</code> and turn it into raw vectors
     * for use when displaying the particles.
     * <p>
     * This is the most intensive part of the particle system, only bake when absolutely
     * necessary.
     * <p>
     * Bake time varies between density of shapes, amount of shapes, and complexity
     * of shapes. Average time can be between 0.5ms to 3ms.
     *
     * @return A reference to this <code>ParticleEffect</code>
     */
    public ParticleEffect bake()
    {
        modules.forEach(module -> module.onBakeHead(this));
        List<Vector> newList = new ArrayList<>();
        for(ParticleShape shape : shapes)
        {
            newList.addAll(shape.getShape());
        }
        untranslatedVecs = newList;
        modules.forEach(module -> module.onBakeTail(this));
        baked = true;
        updated = false;
        return this;
    }

    /**
     * Update this <code>ParticleEffect</code>. Apply transformations and run respective modules.
     *
     * @return A reference to this <code>ParticleEffect</code>
     */
    public ParticleEffect update()
    {
        modules.forEach(module -> module.onUpdateHead(this));
        if(updated) return this;
        List<Vector> newList = new ArrayList<>();
        Vector originVec = origin.toVector();
        double rotX      = rotationVec.getX();
        double rotY      = rotationVec.getY();
        double rotZ      = rotationVec.getZ();
        for(Vector vector : untranslatedVecs)
        {
            Vector newVec = vector.clone();
            if(scaleVec.getX() != 1.0 || scaleVec.getY() != 1.0 || scaleVec.getZ() != 1.0)
            {
                newVec.subtract(originVec);
                newVec.multiply(scaleVec);
                newVec.add(originVec);
            }
            if(rotX != 1.0 || rotY != 1.0 || rotZ != 1.0)
            {
                MathUtil.rotateAroundOrigin(originVec, newVec, rotX, rotY, rotZ);
            }
            if(translationVec.getX() != 1.0 || translationVec.getY() != 1.0 || translationVec.getZ() != 1.0)
            {
                newVec.add(translationVec);
            }
            newList.add(newVec);
        }
        translatedVecs = newList;
        modules.forEach(module -> module.onUpdateTail(this));
        updated = true;
        return this;
    }

    /**
     * Update this <code>ParticleEffect</code>'s global transformations from the parent <code>ParticleSystem</code> <p>
     * The {@link ParticleEffect#update()} method should be called before this method, as it processes base
     * transformations.
     *
     * @param system The parent <code>ParticleSystem</code>
     * @return A reference to this <code>ParticleEffect</code>
     */
    public ParticleEffect updateSystem(ParticleSystem system)
    {
        if(system.isUpdated()) return this;
        Vector originVec         = system.getOrigin().toVector();
        Vector sysScaleVec       = system.getScaleVec();
        Vector sysRotationVec    = system.getRotationVec();
        Vector sysTranslationVec = system.getTranslationVec();
        double rotX = sysRotationVec.getX();
        double rotY = sysRotationVec.getY();
        double rotZ = sysRotationVec.getZ();
        for(Vector vector : translatedVecs)
        {
            if(sysScaleVec.getX() != 1.0 || sysScaleVec.getY() != 1.0 || sysScaleVec.getZ() != 1.0)
            {
                vector.subtract(originVec);
                vector.multiply(scaleVec);
                vector.add(originVec);
            }
            if(rotX != 1.0 || rotY != 1.0 || rotZ != 1.0)
            {
                MathUtil.rotateAroundOrigin(originVec, vector, rotX, rotY, rotZ);
            }
            if(sysTranslationVec.getX() != 1.0 || sysTranslationVec.getY() != 1.0 || sysTranslationVec.getZ() != 1.0)
            {
                vector.add(translationVec);
            }
        }
        return this;
    }

    /**
     * Display this <code>ParticleEffect</code> in the world
     *
     * @return A reference to this <code>ParticleEffect</code>
     */
    public ParticleEffect display()
    {
        modules.forEach(module -> module.onDisplayHead(this));
        for(int i = 0; i < translatedVecs.size(); ++i)
        {
            Vector vector = translatedVecs.get(i);
            Location location = vector.toLocation(world);
            world.spawnParticle(
                    particleData.getParticle(), location, particleData.getCount(),
                    particleData.getOffsetX(), particleData.getOffsetY(), particleData.getOffsetZ(),
                    particleData.getSpeed(), particleData.getData(), particleData.isForce());
        }
        modules.forEach(module -> module.onDisplayTail(this));
        return this;
    }

    /**
     * Get the list of all shapes of this <code>ParticleEffect</code>
     *
     * @return The list of shapes
     */
    public List<ParticleShape> getShapes()
    {
        return shapes;
    }

    /**
     * Get the list of all untranslated vectors of this <code>ParticleEffect</code>.
     * <p>
     * Modifying this list without cloning it could result in concurrency issues if
     * the particle is currently being displayed!
     *
     * @return The untranslated vectors list
     */
    public List<Vector> getUntranslatedVecs()
    {
        return untranslatedVecs;
    }

    /**
     * Get the list of all translated vectors of this <code>ParticleEffect</code>
     * <p>
     * Modifying this list without cloning it could result in concurrency issues if
     * the particle is currently being displayed!
     *
     * @return The translated vectors list
     */
    public List<Vector> getTranslatedVecs()
    {
        return translatedVecs;
    }

    /**
     * Get the <code>ParticleData</code> for this <code>ParticleEffect</code>
     *
     * @return The particle data of this effect
     */
    public ParticleData getParticleData()
    {
        return particleData;
    }

    /**
     * Get the scale vector of this <code>ParticleEffect</code>.
     * <p>
     * If modified, {@link ParticleEffect#setScaleVec(Vector)} should also
     * be called to propagate updates.
     *
     * @return The scale vector
     */
    public Vector getScaleVec()
    {
        return scaleVec;
    }

    /**
     * Get the rotation vector of this <code>ParticleEffect</code>.
     * <p>
     * If modified, {@link ParticleEffect#setRotationVec(Vector)} should also
     * be called to propagate updates.
     *
     * @return The rotation vector
     */
    public Vector getRotationVec()
    {
        return rotationVec;
    }

    /**
     * Get the translation vector of this <code>ParticleEffect</code>.
     * <p>
     * If modified, {@link ParticleEffect#setScaleVec(Vector)} should also
     * be called to propagate updates.
     *
     * @return The translation vector
     */
    public Vector getTranslationVec()
    {
        return translationVec;
    }

    /**
     * Get the origin location of this <code>ParticleEffect</code>.
     * <p>
     * If modified, {@link ParticleEffect#setOrigin(Location)} should also
     * be called to propagate updates.
     *
     * @return The origin location
     */
    public Location getOrigin()
    {
        return origin;
    }

    /**
     * Get whether this <code>ParticleEffect</code> has been baked or not
     *
     * @return Whether baking has occurred or not
     */
    public boolean isBaked()
    {
        return baked;
    }

    /**
     * Get whether this <code>ParticleEffect</code>'s data has been updated to
     * the latest information.
     *
     * @return Whether this effect is updated or not
     */
    public boolean isUpdated()
    {
        return updated;
    }

    /**
     * Set the <code>ParticleData</code> of this effect
     *
     * @param particleData The new <code>ParticleData</code> to use
     */
    public void setParticleData(ParticleData particleData)
    {
        this.particleData = particleData;
    }

    /**
     * Set the scale vector for this <code>ParticleEffect</code>.
     * <p>
     * This method must be called when updating the scale vector in order
     * to propagate updates.
     *
     * @param scaleVec The new scale vector
     */
    public void setScaleVec(Vector scaleVec)
    {
        this.scaleVec = scaleVec;
        updated = false;
    }

    /**
     * Set the rotation vector for this <code>ParticleEffect</code>.
     * <p>
     * This method must be called when updating the rotation vector in order
     * to propagate updates.
     *
     * @param rotationVec The new rotation vector
     */
    public void setRotationVec(Vector rotationVec)
    {
        this.rotationVec = rotationVec;
        updated = false;
    }

    /**
     * Set the translation vector for this <code>ParticleEffect</code>.
     * <p>
     * This method must be called when updating the translation vector in order
     * to propagate updates.
     *
     * @param translationVec The new translation vector
     */
    public void setTranslationVec(Vector translationVec)
    {
        this.translationVec = translationVec;
        updated = false;
    }

    /**
     * Set the origin location for this <code>ParticleEffect</code>
     * <p>
     * This method must be called when updating the origin location in order
     * to propagate updates.
     *
     * @param origin The new origin location
     */
    public void setOrigin(Location origin)
    {
        this.origin = origin;
        updated = false;
    }

    /**
     * Get the delay for this <code>ParticleEffect</code>
     *
     * @return The delay
     */
    public long getDelay()
    {
        return delay;
    }

    /**
     * Set the delay for this <code>ParticleEffect</code>
     *
     * @param delay The new delay
     */
    public void setDelay(long delay)
    {
        this.delay = delay;
    }

    /**
     * Get the count for this <code>ParticleEffect</code>
     *
     * @return The count
     */
    public long getCount()
    {
        return count;
    }

    /**
     * Set the count for this <code>ParticleEffect</code>
     *
     * @param count The new count
     */
    public void setCount(long count)
    {
        this.count = count;
    }

    /**
     * Get the list of all modules of this <code>ParticleEffect</code>
     *
     * @return The list of modules
     */
    public List<ParticleEModule> getModules()
    {
        return modules;
    }
}
