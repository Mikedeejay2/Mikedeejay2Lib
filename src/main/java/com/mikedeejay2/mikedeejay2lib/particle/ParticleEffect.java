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
    // The list of particle shapes
    protected List<ParticleShape> shapes;
    // The particle data for displaying the particles
    protected ParticleData particleData;
    // The vectors of untranslated (baked) vectors
    protected List<Vector> untranslatedVecs;
    // The vectors of translated vectors
    protected List<Vector> translatedVecs;
    // The scale vector for this effect
    protected Vector scaleVec;
    // The rotation vector for this effect
    protected Vector rotationVec;
    // The translation vector for this effect
    protected Vector translationVec;
    // The origin location
    protected Location origin;
    // The world (from the origin location)
    protected World world;
    // Whether this effect has been baked yet
    protected boolean baked;
    // Whether this effect has been updated to the latest translations
    protected boolean updated;
    // The delay of showing this effect (delay <= 0 will ignore delay)
    protected long delay;
    // The amount of times that this effect will be visible (count <= 0 will always be visible)
    protected long count;
    // The list of effect modules
    protected List<ParticleEModule> modules;

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
     * Add a <tt>ParticleShape</tt> to this effect
     *
     * @param shape The shape to add
     * @return A reference to this <tt>ParticleEffect</tt>
     */
    public ParticleEffect addShape(ParticleShape shape)
    {
        shapes.add(shape);
        return this;
    }

    /**
     * See whether this <tt>ParticleEffect</tt> has a specific <tt>ParticleShape</tt>
     *
     * @param shape The shape to search for
     * @return Whether the shape was found or not
     */
    public boolean containsShape(ParticleShape shape)
    {
        return shapes.contains(shape);
    }

    /**
     * See whether this <tt>ParticleEffect</tt> contains a <tt>ParticleShape</tt> based off of the
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
     * Get a <tt>ParticleShape</tt> from this effect by its index in the list
     *
     * @param index The index to get the shape from
     * @return The requested <tt>ParticleShape</tt>
     */
    public ParticleShape getShape(int index)
    {
        return shapes.get(index);
    }

    /**
     * Get a <tt>ParticleShape</tt> from this effect by the class of the shape
     *
     * @param shapeClass The class of the shape to get
     * @return The requested <tt>ParticleShape</tt>, null if not found
     */
    public ParticleShape getShape(Class<? extends ParticleShape> shapeClass)
    {
        for(ParticleShape shape : shapes)
        {
            if(shapeClass == shape.getClass()) return shape;
        }
        return null;
    }

    /**
     * Remove a <tt>ParticleShape</tt> from this effect by its index in the list
     *
     * @param index The index to remove the shape at
     * @return A reference to this <tt>ParticleEffect</tt>
     */
    public ParticleEffect removeShape(int index)
    {
        shapes.remove(index);
        return this;
    }

    /**
     * Remove a <tt>ParticleShape</tt> from this effect by a reference to the shape
     *
     * @param shape The shape to remove
     * @return A reference to this <tt>ParticleEffect</tt>
     */
    public ParticleEffect removeShape(ParticleShape shape)
    {
        shapes.remove(shape);
        return this;
    }

    /**
     * Add a <tt>ParticleEModule</tt> to this effect
     *
     * @param module The module to add
     * @return A reference to this <tt>ParticleEffect</tt>
     */
    public ParticleEffect addModule(ParticleEModule module)
    {
        modules.add(module);
        return this;
    }

    /**
     * Add a <tt>ParticleEModule</tt> to this effect at a specified index
     *
     * @param module The module to add
     * @param index The index to add the module at
     * @return A reference to this <tt>ParticleEffect</tt>
     */
    public ParticleEffect addModule(ParticleEModule module, int index)
    {
        modules.add(index, module);
        return this;
    }

    /**
     * See whether this effect contains a specific <tt>ParticleEModule</tt>
     *
     * @param module The module to search for
     * @return Whether the <tt>ParticleEModule</tt> was found or not
     */
    public boolean containsModule(ParticleEModule module)
    {
        return modules.contains(module);
    }

    /**
     * See whether this effect contains a <tt>ParticleEModule</tt> based off of the
     * module's class
     *
     * @param moduleClass The class of the module to search for
     * @return Whether the <tt>ParticleEModule</tt> was found or not
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
     * Remove a <tt>ParticleEModule</tt> from this effect based off of an index
     *
     * @param index The index to remove the module at
     * @return A reference to this <tt>ParticleEffect</tt>
     */
    public ParticleEffect removeModule(int index)
    {
        modules.remove(index);
        return this;
    }

    /**
     * Remove a <tt>ParticleEModule</tt> from this effect
     *
     * @param module The module to remove
     * @return A reference to this <tt>ParticleEffect</tt>
     */
    public ParticleEffect removeModule(ParticleEModule module)
    {
        modules.remove(module);
        return this;
    }

    /**
     * Remove a <tt>ParticleModule</tt> from this effect based off of the class of the module
     *
     * @param moduleClass The class of the module to remove
     * @return A reference to this <tt>ParticleEffect</tt>
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
     * Get a <tt>ParticleEModule</tt> from this effect based off of the index of the module
     *
     * @param index The index to get the module from
     * @return The requested <tt>ParticleEModule</tt>
     */
    public ParticleEModule getModule(int index)
    {
        return modules.get(index);
    }

    /**
     * Get a <tt>ParticleEModule</tt> from this effect based off of the index of the module
     *
     * @param moduleClass The class of the module to get
     * @return The requested <tt>ParticleEModule</tt>, null if not found
     */
    public ParticleEModule getModule(Class<? extends ParticleEModule> moduleClass)
    {
        for(ParticleEModule module : modules)
        {
            if(moduleClass == module.getClass()) return module;
        }
        return null;
    }

    /**
     * Reset (clear) all modules from this <tt>ParticleEffect</tt>
     *
     * @return A reference to this <tt>ParticleEffect</tt>
     */
    public ParticleEffect resetModules()
    {
        modules.clear();
        return this;
    }

    /**
     * Process all data from <tt>ParticleShapes</tt> and turn it into raw vectors
     * for use when displaying the particles. <p>
     * This is the most intensive part of the particle system, only bake when absolutely
     * necessary. <p>
     * Bake time varies between density of shapes, amount of shapes, and complexity
     * of shapes. Average time can be between 0.5ms to 3ms.
     *
     * @return A reference to this <tt>ParticleEffect</tt>
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
     * Update this <tt>ParticleEffect</tt>. Apply transformations and run respective modules.
     *
     * @return A reference to this <tt>ParticleEffect</tt>
     */
    public ParticleEffect update()
    {
        modules.forEach(module -> module.onUpdateHead(this));
        if(updated) return this;
        List<Vector> newList = new ArrayList<>();
        Vector originVec = origin.toVector();
        double rotX = rotationVec.getX();
        double rotY = rotationVec.getY();
        double rotZ = rotationVec.getZ();
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
     * Update this <tt>ParticleEffect</tt>'s global transformations from the parent <tt>ParticleSystem</tt> <p>
     * The {@link ParticleEffect#update()} method should be called before this method, as it processes base
     * transformations.
     *
     * @param system The parent <tt>ParticleSystem</tt>
     * @return A reference to this <tt>ParticleEffect</tt>
     */
    public ParticleEffect updateSystem(ParticleSystem system)
    {
        if(system.isUpdated()) return this;
        Vector originVec = system.getOrigin().toVector();
        Vector sysScaleVec = system.getScaleVec();
        Vector sysRotationVec = system.getRotationVec();
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
     * Display this <tt>ParticleEffect</tt> in the world
     *
     * @return A reference to this <tt>ParticleEffect</tt>
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
     * Get the list of all shapes of this <tt>ParticleEffect</tt>
     *
     * @return The list of shapes
     */
    public List<ParticleShape> getShapes()
    {
        return shapes;
    }

    /**
     * Get the list of all untranslated vectors of this <tt>ParticleEffect</tt>. <p>
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
     * Get the list of all translated vectors of this <tt>ParticleEffect</tt> <p>
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
     * Get the <tt>ParticleData</tt> for this <tt>ParticleEffect</tt>
     *
     * @return The particle data of this effect
     */
    public ParticleData getParticleData()
    {
        return particleData;
    }

    /**
     * Get the scale vector of this <tt>ParticleEffect</tt>. <p>
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
     * Get the rotation vector of this <tt>ParticleEffect</tt>. <p>
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
     * Get the translation vector of this <tt>ParticleEffect</tt>. <p>
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
     * Get the origin location of this <tt>ParticleEffect</tt>. <p>
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
     * Get whether this <tt>ParticleEffect</tt> has been baked or not
     *
     * @return Whether baking has occurred or not
     */
    public boolean isBaked()
    {
        return baked;
    }

    /**
     * Get whether this <tt>ParticleEffect</tt>'s data has been updated to
     * the latest information.
     *
     * @return Whether this effect is updated or not
     */
    public boolean isUpdated()
    {
        return updated;
    }

    /**
     * Set the <tt>ParticleData</tt> of this effect
     *
     * @param particleData The new <tt>ParticleData</tt> to use
     */
    public void setParticleData(ParticleData particleData)
    {
        this.particleData = particleData;
    }

    /**
     * Set the scale vector for this <tt>ParticleEffect</tt>. <p>
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
     * Set the rotation vector for this <tt>ParticleEffect</tt>. <p>
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
     * Set the translation vector for this <tt>ParticleEffect</tt>. <p>
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
     * Set the origin location for this <tt>ParticleEffect</tt> <p>
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
     * Get the delay for this <tt>ParticleEffect</tt>
     *
     * @return The delay
     */
    public long getDelay()
    {
        return delay;
    }

    /**
     * Set the delay for this <tt>ParticleEffect</tt>
     *
     * @param delay The new delay
     */
    public void setDelay(long delay)
    {
        this.delay = delay;
    }

    /**
     * Get the count for this <tt>ParticleEffect</tt>
     *
     * @return The count
     */
    public long getCount()
    {
        return count;
    }

    /**
     * Set the count for this <tt>ParticleEffect</tt>
     *
     * @param count The new count
     */
    public void setCount(long count)
    {
        this.count = count;
    }

    /**
     * Get the list of all modules of this <tt>ParticleEffect</tt>
     *
     * @return The list of modules
     */
    public List<ParticleEModule> getModules()
    {
        return modules;
    }
}
