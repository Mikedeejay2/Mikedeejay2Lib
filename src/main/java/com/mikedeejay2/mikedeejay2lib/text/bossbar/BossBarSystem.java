package com.mikedeejay2.mikedeejay2lib.text.bossbar;

import com.mikedeejay2.mikedeejay2lib.PluginBase;
import com.mikedeejay2.mikedeejay2lib.text.bossbar.modules.BossBarModule;
import com.mikedeejay2.mikedeejay2lib.util.chat.Chat;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import java.util.*;

/**
 * A system for boss bars that allow easy creation, animation, and modification of boss bars.
 * <p>
 * It should be noted that {@link BossBarSystem#stop()} should be called before the server is
 * reloaded / shut down to prevent ghost boss bars stuck on players' screens.
 *
 * @author Mikedeejay2
 */
public class BossBarSystem
{
    protected PluginBase plugin;
    // The title text of the boss bar
    protected String title;
    // The boss bar itself
    protected BossBar bar;
    // The color of the boss bar
    protected BarColor color;
    // The style of the boss bar
    protected BarStyle style;
    // The list of BarFlags of the boss bar
    protected List<BarFlag> flags;
    // The set of players that can see this boss bar
    protected Set<Player> players;
    // The progress of the boss bar (0.0 - 1.0)
    protected double progress;
    // The boss bar runtime
    protected BossBarRuntime runtime;
    // Whether the boss bar is visible or not
    protected boolean visible;
    // The list of boss bar modules that this system has
    protected List<BossBarModule> modules;

    /**
     * @param plugin   A reference to the plugin
     * @param title    The title String of the boss bar
     * @param color    The <tt>BarColor</tt> of the boss bar
     * @param style    The <tt>BarStyle</tt> of the boss bar
     * @param progress The progress (0.0 - 1.0) of the boss bar
     * @param flags    The <tt>BarFlags</tt> of the boss bar
     */
    public BossBarSystem(PluginBase plugin, String title, BarColor color, BarStyle style, double progress, BarFlag... flags)
    {
        this.plugin = plugin;
        this.players = new HashSet<>();
        this.title = Chat.chat(title);
        this.color = color;
        this.style = style;
        this.progress = progress;
        this.flags = new ArrayList<>(Arrays.asList(flags));
        this.visible = false;
        this.modules = new ArrayList<>();
        this.bar = Bukkit.createBossBar(this.title, this.color, this.style, flags);
        setVisible(false);
    }

    /**
     * @param plugin A reference to the plugin
     */
    public BossBarSystem(PluginBase plugin)
    {
        this(plugin, "", BarColor.WHITE, BarStyle.SOLID, 1.0);
    }

    /**
     * Display this <tt>BossBarSystem</tt> to the players added to the system
     *
     * @param delay  The delay before the boss bar should be displayed
     * @param period The period between tick updates of the boss bar
     */
    public void display(long delay, long period)
    {
        this.runtime = new BossBarRuntime(this);
        runtime.runTaskTimerAsynchronously(plugin, delay, period);
        setVisible(true);
    }

    /**
     * Display this <tt>BossBarSystem</tt> to the players added to the system
     *
     * @param period The period between tick updates of the boss bar
     */
    public void display(long period)
    {
        this.display(0, period);
    }

    /**
     * Display this <tt>BossBarSystem</tt> to the players added to the system
     */
    public void display()
    {
        this.display(0, 1);
    }

    /**
     * Stops displaying the boss bar to the players.
     * This method stops the runtime from updating and sets the visibility
     * of the boss bar to false.
     */
    public void stop()
    {
        if(runtime == null || runtime.isCancelled()) return;
        runtime.cancel();
    }

    /**
     * Add a variable amount of players to this <tt>BossBarSystem</tt>
     *
     * @param players The players to add to the system
     * @return The current <tt>BossBarSystem</tt>
     */
    public BossBarSystem addPlayers(Player... players)
    {
        for(Player player : players)
        {
            this.players.add(player);
            bar.addPlayer(player);
        }
        return this;
    }

    /**
     * Remove a variable amount of players from this <tt>BossBarSystem</tt>
     *
     * @param players The players to remove from this system
     * @return The current <tt>BossBarSystem</tt>
     */
    public BossBarSystem removePlayers(Player... players)
    {
        for(Player player : players)
        {
            this.players.remove(player);
            bar.removePlayer(player);
        }
        return this;
    }

    /**
     * Add a collection of players to this <tt>BossBarSystem</tt>
     *
     * @param players The collection of players to add to this system
     * @return The current <tt>BossBarSystem</tt>
     */
    public BossBarSystem addPlayers(Collection<? extends Player> players)
    {
        for(Player player : players)
        {
            this.players.add(player);
            bar.addPlayer(player);
        }
        return this;
    }

    /**
     * Remove a collection of players from this <tt>BossBarSystem</tt>
     *
     * @param players The collection of players to remove from this system
     * @return The current <tt>BossBarSystem</tt>
     */
    public BossBarSystem removePlayers(Collection<? extends Player> players)
    {
        for(Player player : players)
        {
            this.players.remove(player);
            bar.removePlayer(player);
        }
        return this;
    }

    /**
     * Add a player to this <tt>BossBarSystem</tt>
     *
     * @param player The player to add to this system
     * @return The current <tt>BossBarSystem</tt>
     */
    public BossBarSystem addPlayer(Player player)
    {
        players.add(player);
        bar.addPlayer(player);
        return this;
    }

    /**
     * Remove a player from this <tt>BossBarSystem</tt>
     *
     * @param player The player to remove from this system
     * @return The current <tt>BossBarSystem</tt>
     */
    public BossBarSystem removePlayer(Player player)
    {
        players.remove(player);
        bar.removePlayer(player);
        return this;
    }

    /**
     * See whether this <tt>BossBarSystem</tt> contains a specified player
     *
     * @param player The player to search for
     * @return Whether the player was found in this system or not
     */
    public boolean containsPlayer(Player player)
    {
        return players.contains(player);
    }

    /**
     * Get the title of the boss bar
     *
     * @return The title of the boss bar
     */
    public String getTitle()
    {
        return title;
    }

    /**
     * Set the title of the boss bar
     *
     * @param title The new boss bar title
     */
    public void setTitle(String title)
    {
        this.title = Chat.chat(title);
        bar.setTitle(this.title);
    }

    /**
     * Get the color of the boss bar
     *
     * @return The <tt>BarColor</tt> the boss bar
     */
    public BarColor getColor()
    {
        return color;
    }

    /**
     * Set the color of the boss bar
     *
     * @param color The new <tt>BarColor</tt> of the boss bar
     */
    public void setColor(BarColor color)
    {
        this.color = color;
        bar.setColor(color);
    }

    /**
     * Get the style of the boss bar
     *
     * @return The <tt>BarStyle</tt> of the boss bar
     */
    public BarStyle getStyle()
    {
        return style;
    }

    /**
     * Set the style of the boss bar
     *
     * @param style The new <tt>BarStyle</tt> of the boss bar
     */
    public void setStyle(BarStyle style)
    {
        this.style = style;
        bar.setStyle(style);
    }

    /**
     * Get the list of <tt>BarFlags</tt> of the boss bar
     *
     * @return The <tt>BarFlags</tt> of the boss bar
     */
    public List<BarFlag> getFlags()
    {
        return flags;
    }

    /**
     * Add a <tt>BarFlag</tt> to this boss bar
     *
     * @param flag The <tt>BarFlag</tt> to add
     * @return The current <tt>BossBarSystem</tt>
     */
    public BossBarSystem addFlag(BarFlag flag)
    {
        flags.add(flag);
        bar.addFlag(flag);
        return this;
    }

    /**
     * Remove a <tt>BarFlag</tt> from this boss bar
     *
     * @param flag The <tt>BarFlag</tt> to remove
     * @return The current <tt>BossBarSystem</tt>
     */
    public BossBarSystem removeFlag(BarFlag flag)
    {
        flags.remove(flag);
        bar.removeFlag(flag);
        return this;
    }

    /**
     * Get the progress of the boss bar
     *
     * @return The progress of the boss bar
     */
    public double getProgress()
    {
        return progress;
    }

    /**
     * Set the progress of the boss bar
     *
     * @param progress The new boss bar progress
     */
    public void setProgress(double progress)
    {
        this.progress = progress;
        bar.setProgress(progress);
    }

    /**
     * Get the set of all players that are linked to this boss bar
     *
     * @return The list of players
     */
    public Set<Player> getPlayers()
    {
        return players;
    }

    /**
     * Reset (remove all) all player in this boss bar
     *
     * @return The current <tt>BossBarSystem</tt>
     */
    public BossBarSystem resetPlayers()
    {
        players.clear();
        bar.removeAll();
        return this;
    }

    /**
     * Get whether this boss bar is currently visible or not
     *
     * @return Visibility state
     */
    public boolean isVisible()
    {
        return visible;
    }

    /**
     * Set whether this boss bar is currently visible
     *
     * @param visible The new visibility state
     */
    public void setVisible(boolean visible)
    {
        this.visible = visible;
        bar.setVisible(true);
    }

    /**
     * Get the <tt>BossBar</tt> of this system
     *
     * @return The <tt>BossBar</tt>
     */
    public BossBar getBar()
    {
        return bar;
    }

    /**
     * Add a <tt>BossBarModule</tt> to this system
     *
     * @param module The <tt>BossBarModule</tt> to add to this system
     * @return The current <tt>BossBarSystem</tt>
     */
    public BossBarSystem addModule(BossBarModule module)
    {
        modules.add(module);
        return this;
    }

    /**
     * Remove a <tt>BossBarModule</tt> from this system based off of a reference to the module
     *
     * @param module The <tt>BossBarModule</tt> to remove from this system
     * @return The current <tt>BossBarSystem</tt>
     */
    public BossBarSystem removeModule(BossBarModule module)
    {
        modules.remove(module);
        return this;
    }

    /**
     * Remove a <tt>BossBarModule</tt> from this system based off of the index of the module
     *
     * @param index The index to remove the module at
     * @return The current <tt>BossBarSystem</tt>
     */
    public BossBarSystem removeModule(int index)
    {
        modules.remove(index);
        return this;
    }

    /**
     * Remove a <tt>BossBarModule</tt> based off of the class of the module
     *
     * @param moduleClass The class of the module to remove
     * @return The current <tt>BossBarSystem</tt>
     */
    public BossBarSystem removeModule(Class<? extends BossBarModule> moduleClass)
    {
        for(BossBarModule module : modules)
        {
            if(moduleClass != module.getClass()) continue;
            modules.remove(module);
            break;
        }
        return this;
    }

    /**
     * See whether this system contains a specific <tt>BossBarModule</tt>
     *
     * @param module The <tt>BossBarModule</tt> to search for
     * @return Whether the module was found or not
     */
    public boolean containsModule(BossBarModule module)
    {
        return modules.contains(module);
    }

    /**
     * See whether this system contains a <tt>BossBarModule</tt> of the specified class type
     *
     * @param moduleClass The class of the module to search for
     * @return Whether a module of the class was found or not
     */
    public boolean containsModule(Class<? extends BossBarModule> moduleClass)
    {
        for(BossBarModule module : modules)
        {
            if(moduleClass == module.getClass()) return true;
        }
        return false;
    }

    /**
     * Get a <tt>BossBarModule</tt> based off of the index of the module
     *
     * @param index The index of the module to get
     * @return The requested <tt>BossBarModule</tt>
     */
    public BossBarModule getModule(int index)
    {
        return modules.get(index);
    }

    /**
     * Get a <tt>BossBarModule</tt> based off of the class of the module
     *
     * @param moduleClass The class of the <tt>BossBarModule</tt>
     * @param <T>         The class type of the module
     * @return The requested <tt>BossBarModule</tt>, null if not found
     */
    public <T extends BossBarModule> T getModule(Class<T> moduleClass)
    {
        for(BossBarModule module : modules)
        {
            if(moduleClass == module.getClass()) return (T) module;
        }
        return null;
    }

    /**
     * Reset (clear) all modules from this <tt>BossBarSystem</tt>
     *
     * @return The current <tt>BossBarSystem</tt>
     */
    public BossBarSystem resetModules()
    {
        modules.clear();
        return this;
    }

    /**
     * Get the list of <tt>BossBarModules</tt> that are in this system
     *
     * @return The list of <tt>BossBarModules</tt>
     */
    public List<BossBarModule> getModules()
    {
        return modules;
    }
}