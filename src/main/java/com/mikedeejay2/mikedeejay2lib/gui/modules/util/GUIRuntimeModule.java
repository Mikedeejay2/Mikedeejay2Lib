package com.mikedeejay2.mikedeejay2lib.gui.modules.util;

import com.mikedeejay2.mikedeejay2lib.BukkitPlugin;
import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.modules.GUIModule;
import com.mikedeejay2.mikedeejay2lib.runnable.EnhancedRunnable;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

/**
 * A module that runs as long as the GUI is open. Common uses include updating the GUI dynamically or constantly running
 * something when the GUI is open.
 *
 * @author Mikedeejay2
 */
public class GUIRuntimeModule implements GUIModule {
    /**
     * The {@link BukkitPlugin} instance
     */
    protected final BukkitPlugin plugin;

    /**
     * How often (in ticks) the runnable will run
     */
    protected int period;

    /**
     * The delay (in ticks) that will pass before the runnable will begin running
     */
    protected int delay;

    /**
     * The {@link ConsumerRunnable} instance. Will be null when the GUI is not open.
     */
    protected @Nullable GUIRuntimeModule.ConsumerRunnable runnable;

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
        this.plugin = plugin;
        this.delay = delay;
        this.period = period;
        this.runnable = null;
        this.consumer = consumer;
    }

    /**
     * Create the runnable when the GUI is opened
     *
     * @param player The player that is viewing the GUI
     * @param gui    The GUI
     */
    @Override
    public void onOpenHead(Player player, GUIContainer gui) {
        this.runnable = new ConsumerRunnable(gui, player, consumer);
        this.runnable.runTaskTimer(plugin, delay, period);
    }

    /**
     * Cancel and nullify the runnable when the GUI is closed
     *
     * @param player The player that is viewing the GUI
     * @param gui    The GUI
     */
    @Override
    public void onClose(Player player, GUIContainer gui) {
        this.runnable.cancel();
        this.runnable = null;
    }

    /**
     * An {@link EnhancedRunnable} that takes extra information to be relayed to a {@code Consumer<RunInfo>}
     *
     * @author Mikedeejay2
     */
    protected static class ConsumerRunnable extends EnhancedRunnable {

        /**
         * The {@link GUIContainer} instance
         */
        protected final GUIContainer gui;

        /**
         * The player viewing the GUI
         */
        protected final Player player;

        /**
         * The {@link Consumer} that takes {@link RunInfo} that is relayed information from this runnable
         */
        protected final Consumer<RunInfo> consumer;

        /**
         * Construct a new <code>ConsumerRunnable</code>
         *
         * @param gui      The {@link GUIContainer} instance
         * @param player   The player viewing the GUI
         * @param consumer The {@link Consumer} that takes {@link RunInfo} that is relayed information from this runnable
         */
        public ConsumerRunnable(GUIContainer gui, Player player, Consumer<RunInfo> consumer) {
            this.gui = gui;
            this.player = player;
            this.consumer = consumer;
        }

        /**
         * <code>onRun()</code> method that supplies the {@link ConsumerRunnable#consumer} with updated {@link RunInfo}
         */
        @Override
        public void onRun() {
            consumer.accept(new RunInfo(gui, player, count));
        }
    }

    /**
     * Information regarding the runtime. Contains {@link GUIContainer}, {@link Player}, and the run count of the
     * runtime.
     *
     * @author Mikedeejay2
     */
    public static class RunInfo {

        /**
         * The {@link GUIContainer} instance
         */
        protected final GUIContainer gui;

        /**
         * The player viewing the GUI
         */
        protected final Player player;

        /**
         * The run count of the runnable
         */
        protected final long count;

        /**
         * Construct a new <code>RunInfo</code>
         *
         * @param gui    The {@link GUIContainer} instance
         * @param player The player viewing the GUI
         * @param count  The run count of the runnable
         */
        public RunInfo(GUIContainer gui, Player player, long count) {
            this.gui = gui;
            this.player = player;
            this.count = count;
        }

        /**
         * Get the {@link GUIContainer} instance
         *
         * @return The <code>GUIContainer</code> instance
         */
        public GUIContainer getGui() {
            return gui;
        }

        /**
         * Get the {@link Player} viewing the GUI
         *
         * @return The <code>Player</code> viewing the GUI
         */
        public Player getPlayer() {
            return player;
        }

        /**
         * Get the run count of the runnable
         *
         * @return The run count of the runnable
         */
        public long getCount() {
            return count;
        }
    }
}
