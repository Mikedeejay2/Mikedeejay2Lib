package com.mikedeejay2.mikedeejay2lib.particle.effect;

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
    protected List<Vector> untranslatedVecs;
    protected List<Vector> translatedVecs;
    protected Vector scaleVec;
    protected Vector rotationVec;
    protected Vector translationVec;
    protected Location origin;
    protected World world;
    protected boolean baked;
    protected boolean updated;

    public ParticleEffect(Location origin)
    {
        this.shapes = new ArrayList<>();
        this.untranslatedVecs = new ArrayList<>();
        this.translatedVecs = new ArrayList<>();
        this.scaleVec = new Vector(1, 1, 1);
        this.rotationVec = new Vector(0, 0, 0);
        this.translationVec = new Vector(0, 0, 0);
        this.origin = origin;
        this.world = origin.getWorld();
        this.baked = false;
        this.updated = false;
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
            newVec.multiply(scaleVec);
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
        for(Vector vector : translatedVecs)
        {
//            world.spawnParticle();
        }
        return this;
    }
}
