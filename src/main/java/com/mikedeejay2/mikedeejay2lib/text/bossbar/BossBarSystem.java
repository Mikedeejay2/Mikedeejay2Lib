package com.mikedeejay2.mikedeejay2lib.text.bossbar;

import com.mikedeejay2.mikedeejay2lib.PluginBase;
import com.mikedeejay2.mikedeejay2lib.text.bossbar.modules.BBModule;
import com.mikedeejay2.mikedeejay2lib.util.chat.Chat;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import java.util.*;

public class BossBarSystem
{
    protected PluginBase plugin;
    protected String title;
    protected BossBar bar;
    protected BarColor color;
    protected BarStyle style;
    protected List<BarFlag> flags;
    protected Set<Player> players;
    protected double progress;
    protected BossBarRuntime runtime;
    protected boolean visible;
    protected List<BBModule> modules;

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

    public void display(long delay, long period)
    {
        this.runtime = new BossBarRuntime(this);
        runtime.runTaskTimerAsynchronously(plugin, delay, period);
        setVisible(true);
    }

    public void display(long period)
    {
        this.display(0, period);
    }

    public void display()
    {
        this.display(0, 1);
    }

    public BossBarSystem addPlayers(Player... players)
    {
        for(Player player : players)
        {
            this.players.add(player);
            bar.addPlayer(player);
        }
        return this;
    }

    public BossBarSystem removePlayers(Player... players)
    {
        for(Player player : players)
        {
            this.players.remove(player);
            bar.removePlayer(player);
        }
        return this;
    }

    public BossBarSystem addPlayers(Collection<? extends Player> players)
    {
        for(Player player : players)
        {
            this.players.add(player);
            bar.addPlayer(player);
        }
        return this;
    }

    public BossBarSystem removePlayers(Collection<? extends Player> players)
    {
        for(Player player : players)
        {
            this.players.remove(player);
            bar.removePlayer(player);
        }
        return this;
    }

    public BossBarSystem addPlayer(Player player)
    {
        players.add(player);
        bar.addPlayer(player);
        return this;
    }

    public BossBarSystem removePlayer(Player player)
    {
        players.remove(player);
        bar.removePlayer(player);
        return this;
    }

    public boolean containsPlayer(Player player)
    {
        return players.contains(player);
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = Chat.chat(title);
        bar.setTitle(this.title);
    }

    public BarColor getColor()
    {
        return color;
    }

    public void setColor(BarColor color)
    {
        this.color = color;
        bar.setColor(color);
    }

    public BarStyle getStyle()
    {
        return style;
    }

    public void setStyle(BarStyle style)
    {
        this.style = style;
        bar.setStyle(style);
    }

    public List<BarFlag> getFlags()
    {
        return flags;
    }

    public BossBarSystem addFlag(BarFlag flag)
    {
        flags.add(flag);
        bar.addFlag(flag);
        return this;
    }

    public BossBarSystem removeFlag(BarFlag flag)
    {
        flags.remove(flag);
        bar.removeFlag(flag);
        return this;
    }

    public double getProgress()
    {
        return progress;
    }

    public void setProgress(double progress)
    {
        this.progress = progress;
        bar.setProgress(progress);
    }

    public Set<Player> getPlayers()
    {
        return players;
    }

    public BossBarSystem resetPlayers()
    {
        players.clear();
        bar.removeAll();
        return this;
    }

    public boolean isVisible()
    {
        return visible;
    }

    public void setVisible(boolean visible)
    {
        this.visible = visible;
        bar.setVisible(true);
    }

    public BossBar getBar()
    {
        return bar;
    }

    public BossBarSystem addModule(BBModule module)
    {
        modules.add(module);
        return this;
    }

    public BossBarSystem removeModule(BBModule module)
    {
        modules.remove(module);
        return this;
    }

    public BossBarSystem removeModule(int index)
    {
        modules.remove(index);
        return this;
    }

    public BossBarSystem removeModule(Class<? extends BBModule> moduleClass)
    {
        for(BBModule module : modules)
        {
            if(moduleClass != module.getClass()) continue;
            modules.remove(module);
            break;
        }
        return this;
    }

    public boolean containsModule(BBModule module)
    {
        return modules.contains(module);
    }

    public boolean containsModule(Class<? extends BBModule> moduleClass)
    {
        for(BBModule module : modules)
        {
            if(moduleClass == module.getClass()) return true;
        }
        return false;
    }

    public BBModule getModule(int index)
    {
        return modules.get(index);
    }

    public <T extends BBModule> T getModule(Class<T> moduleClass)
    {
        for(BBModule module : modules)
        {
            if(moduleClass == module.getClass()) return (T) module;
        }
        return null;
    }

    public BossBarSystem resetModules()
    {
        modules.clear();
        return this;
    }

    public List<BBModule> getModules()
    {
        return modules;
    }
}
