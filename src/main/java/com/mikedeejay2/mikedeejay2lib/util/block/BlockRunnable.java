package com.mikedeejay2.mikedeejay2lib.util.block;

import org.bukkit.Location;
import org.bukkit.block.Block;

/**
 * A simple interface that acts as a runnable that takes
 * a <tt>Block</tt> and a <tt>Location</tt>
 *
 * @author Mikedeejay2
 */
public interface BlockRunnable
{
    public void run(Location location, Block block);
}
