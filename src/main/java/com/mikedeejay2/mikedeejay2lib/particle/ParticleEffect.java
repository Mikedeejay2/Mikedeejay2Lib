package com.mikedeejay2.mikedeejay2lib.particle;

import com.mikedeejay2.mikedeejay2lib.particle.module.effect.ParticleEModule;
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

    public ParticleEffect addModule(ParticleEModule module)
    {
        modules.add(module);
        return this;
    }

    public ParticleEffect addModule(ParticleEModule module, int index)
    {
        modules.add(index, module);
        return this;
    }

    public boolean containsModule(ParticleEModule module)
    {
        return modules.contains(module);
    }

    public ParticleEffect removeModule(int index)
    {
        modules.remove(index);
        return this;
    }

    public ParticleEffect removeModule(ParticleEModule module)
    {
        modules.remove(module);
        return this;
    }

    public ParticleEffect resetModules()
    {
        modules.clear();
        return this;
    }

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

    public List<ParticleEModule> getModules()
    {
        return modules;
    }
}
