package com.mikedeejay2.mikedeejay2lib.particle;

import com.mikedeejay2.mikedeejay2lib.particle.shape.ParticleShape;
import com.mikedeejay2.mikedeejay2lib.util.math.MathUtil;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class ParticleEffect
{
    protected List<ParticleShape> shapes;
    protected ParticleData particleData;
    protected List<Vector> untranslatedVecs;
    protected List<Vector> translatedVecs;
    protected Vector scaleVec;
    protected Vector rotationVec;
    protected Vector translationVec;
    protected Location origin;
    protected World world;
    protected boolean baked;
    protected boolean updated;
    protected long delay;
    protected long count;

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
    }

    public ParticleEffect addShape(ParticleShape shape)
    {
        shapes.add(shape);
        return this;
    }

    public boolean containsShape(ParticleShape shape)
    {
        return shapes.contains(shape);
    }

    public ParticleEffect removeShape(int index)
    {
        shapes.remove(index);
        return this;
    }

    public ParticleEffect removeShape(ParticleShape shape)
    {
        shapes.remove(shape);
        return this;
    }

    public ParticleEffect bake()
    {
        for(ParticleShape shape : shapes)
        {
            untranslatedVecs.addAll(shape.getShape());
        }
        baked = true;
        updated = false;
        return this;
    }

    public ParticleEffect update()
    {
        translatedVecs.clear();
        Vector originVec = origin.toVector();
        double rotX = rotationVec.getX();
        double rotY = rotationVec.getY();
        double rotZ = rotationVec.getZ();
        for(Vector vector : untranslatedVecs)
        {
            Vector newVec = vector.clone();
            newVec.subtract(originVec);
            newVec.multiply(scaleVec);
            newVec.add(originVec);
            MathUtil.rotateAroundOrigin(originVec, newVec, rotX, rotY, rotZ);
            newVec.add(translationVec);
            translatedVecs.add(newVec);
        }
        updated = true;
        return this;
    }

    public ParticleEffect display()
    {
        if(!baked) return this;
        List<Vector> toDisplay = new ArrayList<>(translatedVecs);
        for(Vector vector : toDisplay)
        {
            Location location = vector.toLocation(world);
            world.spawnParticle(
                    particleData.getParticle(), location, particleData.getCount(),
                    particleData.getOffsetX(), particleData.getOffsetY(), particleData.getOffsetZ(),
                    particleData.getSpeed(), particleData.getData(), particleData.isForce());
        }
        return this;
    }

    public List<ParticleShape> getShapes()
    {
        return shapes;
    }

    public List<Vector> getUntranslatedVecs()
    {
        return untranslatedVecs;
    }

    public List<Vector> getTranslatedVecs()
    {
        return translatedVecs;
    }

    public ParticleData getParticleData()
    {
        return particleData;
    }

    public Vector getScaleVec()
    {
        return scaleVec;
    }

    public Vector getRotationVec()
    {
        return rotationVec;
    }

    public Vector getTranslationVec()
    {
        return translationVec;
    }

    public Location getOrigin()
    {
        return origin;
    }

    public boolean isBaked()
    {
        return baked;
    }

    public boolean isUpdated()
    {
        return updated;
    }

    public void setParticleData(ParticleData particleData)
    {
        this.particleData = particleData;
    }

    public void setScaleVec(Vector scaleVec)
    {
        this.scaleVec = scaleVec;
        updated = false;
    }

    public void setRotationVec(Vector rotationVec)
    {
        this.rotationVec = rotationVec;
        updated = false;
    }

    public void setTranslationVec(Vector translationVec)
    {
        this.translationVec = translationVec;
        updated = false;
    }

    public void setOrigin(Location origin)
    {
        this.origin = origin;
        updated = false;
    }

    public long getDelay()
    {
        return delay;
    }

    public void setDelay(long delay)
    {
        this.delay = delay;
    }

    public long getCount()
    {
        return count;
    }

    public void setCount(long count)
    {
        this.count = count;
    }
}
