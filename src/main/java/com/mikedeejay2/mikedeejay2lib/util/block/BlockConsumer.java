package com.mikedeejay2.mikedeejay2lib.util.block;

import org.bukkit.Location;
import org.bukkit.block.Block;

/**
 * A simple interface that acts as a consumer that takes
 * a <code>Block</code> and a <code>Location</code>
 *
 * @author Mikedeejay2
 */
@FunctionalInterface
public interface BlockConsumer
{
    void accept(Location location, Block block);
}
