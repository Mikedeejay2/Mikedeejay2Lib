package com.mikedeejay2.mikedeejay2lib.gui.modules.util;

import com.mikedeejay2.mikedeejay2lib.BukkitPlugin;

import java.util.function.Consumer;

/**
 * A module that runs as long as the GUI is open. Common uses include updating the GUI dynamically or constantly running
 * something when the GUI is open.
 *
 * @author Mikedeejay2
 */
public class GUIRuntimeModule extends GUIAbstractRuntimeModule {
    /**
     * The <code>Consumer</code> that is run while the GUI is open
     */
    protected Consumer<RunInfo> consumer;

    /**
     * Construct a new <code>GUIRuntimeModule</code>
     *
     * @param plugin   The {@link BukkitPlugin} instance
     * @param delay    The delay (in ticks) that will pass before the runnable will begin running
     * @param period   How often (in ticks) the runnable will run
     * @param consumer The <code>Consumer</code> that is run while the GUI is open
     */
    public GUIRuntimeModule(BukkitPlugin plugin, int delay, int period, Consumer<RunInfo> consumer) {
        super(plugin, delay, period);
        this.consumer = consumer;
    }

    @Override
    protected Consumer<GUIAbstractRuntimeModule.RunInfo> getConsumer() {
        return consumer;
    }
}
