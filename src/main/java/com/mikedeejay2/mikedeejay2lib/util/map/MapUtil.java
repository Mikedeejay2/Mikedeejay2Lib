package com.mikedeejay2.mikedeejay2lib.util.map;

import org.bukkit.Bukkit;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapView;

/**
 * Utility class for anything to do with maps. (Minecraft maps, not Java maps.)
 *
 * @author Mikedeejay2
 */
public final class MapUtil
{
    /**
     * Increment the map size of a map.
     * <p>
     * Ex. <code>CLOSE</code> size goes to <code>NORMAL</code> and so forth.
     *
     * @param meta The input <code>MapMeta</code> to be incremented
     */
    public static void incrementMapSize(MapMeta meta)
    {
        MapView view = meta.getMapView();
        MapView newView = Bukkit.createMap(view.getWorld());
        newView.setCenterX(view.getCenterX());
        newView.setCenterZ(view.getCenterZ());
        newView.setLocked(view.isLocked());
        newView.setTrackingPosition(view.isTrackingPosition());
        newView.setUnlimitedTracking(view.isUnlimitedTracking());
        switch(view.getScale())
        {
            case CLOSEST:
                newView.setScale(MapView.Scale.CLOSE);
                break;
            case CLOSE:
                newView.setScale(MapView.Scale.NORMAL);
                break;
            case NORMAL:
                newView.setScale(MapView.Scale.FAR);
                break;
            case FAR:
                newView.setScale(MapView.Scale.FARTHEST);
                break;
            case FARTHEST:
                newView.setScale(MapView.Scale.CLOSEST);
                break;
        }
        meta.setMapView(newView);
    }
}
