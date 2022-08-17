package com.mikedeejay2.mikedeejay2lib.text.bossbar;

import com.mikedeejay2.mikedeejay2lib.runnable.EnhancedRunnable;
import com.mikedeejay2.mikedeejay2lib.text.bossbar.modules.BossBarModule;
import org.bukkit.boss.BossBar;

import java.util.List;

/**
 * Runtime for the <code>BossBarSystem</code>. This runtime should only be
 * initialized through {@link BossBarSystem#display()} or similar.
 *
 * @see BossBarSystem
 *
 * @author Mikedeejay2
 */
public class BossBarRuntime extends EnhancedRunnable {
    /**
     * The boss bar system that this runtime controls
     */
    protected BossBarSystem system;

    /**
     * The boss bar object
     */
    protected BossBar bar;

    /**
     * The list of BossBarModules of this system
     */
    protected List<BossBarModule> modules;

    /**
     * Construct a new <code>BossBarRuntime</code>
     *
     * @param bar The boss bar object
     */
    public BossBarRuntime(BossBarSystem bar) {
        this.system = bar;
        this.bar = system.getBar();
        this.modules = system.getModules();
    }

    /**
     * Run the onTick for all of the <code>BossBarModules</code>
     */
    @Override
    public void onRun() {
        modules.forEach(module -> module.onTick(system));
    }

    /**
     * Remove visibility of the boss bars on their last run to prevent stuck boss bars
     */
    @Override
    public void onLastRun() {
        system.setVisible(false);
    }
}
