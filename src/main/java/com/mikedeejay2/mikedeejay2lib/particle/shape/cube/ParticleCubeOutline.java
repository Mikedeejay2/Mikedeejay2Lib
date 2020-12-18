package com.mikedeejay2.mikedeejay2lib.particle.shape.cube;

import com.mikedeejay2.mikedeejay2lib.particle.shape.ParticleShape;
import com.mikedeejay2.mikedeejay2lib.util.math.MathUtil;
import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.util.List;

public class ParticleCubeOutline implements ParticleShape
{
    protected Location location;
    protected double density;
    protected double xWidth;
    protected double yWidth;
    protected double zWidth;
    protected Location loc1;
    protected Location loc2;
    boolean mode;

    public ParticleCubeOutline(Location location, double xWidth, double yWidth, double zWidth, double density)
    {
        this.location = location;
        this.density = density;
        this.xWidth = xWidth;
        this.yWidth = yWidth;
        this.zWidth = zWidth;
        this.mode = false;
    }

    public ParticleCubeOutline(Location loc1, Location loc2, double density)
    {
        this.loc1 = loc1;
        this.loc2 = loc2;
        this.density = density;
        this.mode = true;
    }

    @Override
    public List<Vector> getShape()
    {
        if(mode)
        {
            return MathUtil.getCubeOutlineVectors(loc1, loc2, density);
        }
        return MathUtil.getCubeOutlineVectors(location, xWidth, yWidth, zWidth, density);
    }
}
