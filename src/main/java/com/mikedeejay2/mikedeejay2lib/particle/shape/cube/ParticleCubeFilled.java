package com.mikedeejay2.mikedeejay2lib.particle.shape.cube;

import com.mikedeejay2.mikedeejay2lib.particle.shape.ParticleShape;
import com.mikedeejay2.mikedeejay2lib.util.math.MathUtil;
import org.bukkit.Location;

import java.util.List;

public class ParticleCubeFilled implements ParticleShape
{
    protected Location location;
    protected double density;
    protected double xWidth;
    protected double yWidth;
    protected double zWidth;
    protected Location loc1;
    protected Location loc2;
    boolean mode;

    public ParticleCubeFilled(Location location, double xWidth, double yWidth, double zWidth, double density)
    {
        this.location = location;
        this.density = density;
        this.xWidth = xWidth;
        this.yWidth = yWidth;
        this.zWidth = zWidth;
        this.mode = false;
    }

    public ParticleCubeFilled(Location loc1, Location loc2, double density)
    {
        this.loc1 = loc1;
        this.loc2 = loc2;
        this.density = density;
        this.mode = true;
    }

    @Override
    public List<Location> getShape()
    {
        if(mode)
        {
            return MathUtil.getCubeFilledLocations(loc1, loc2, density);
        }
        return MathUtil.getCubeFilledLocations(location, xWidth, yWidth, zWidth, density);
    }
}
