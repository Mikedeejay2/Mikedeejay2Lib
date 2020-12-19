package com.mikedeejay2.mikedeejay2lib.particle.shape.generic;

import com.mikedeejay2.mikedeejay2lib.particle.shape.ParticleShape;
import com.mikedeejay2.mikedeejay2lib.util.math.MathUtil;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ParticleShapePoint implements ParticleShape
{
    protected Location location;

    public ParticleShapePoint(Location location)
    {
        this.location = location;
    }

    @Override
    public List<Vector> getShape()
    {
        List<Vector> list = new ArrayList<>();
        list.add(location.toVector());
        return list;
    }
}
