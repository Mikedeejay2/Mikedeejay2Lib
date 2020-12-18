package com.mikedeejay2.mikedeejay2lib.particle.shape.generic;

import com.mikedeejay2.mikedeejay2lib.particle.shape.ParticleShape;
import com.mikedeejay2.mikedeejay2lib.util.math.MathUtil;
import org.bukkit.Location;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ParticlePoint implements ParticleShape
{
    protected Location location;

    public ParticlePoint(Location location)
    {
        this.location = location;
    }

    @Override
    public List<Location> getShape()
    {
        List<Location> list = new ArrayList<>();
        list.add(location);
        return list;
    }
}
