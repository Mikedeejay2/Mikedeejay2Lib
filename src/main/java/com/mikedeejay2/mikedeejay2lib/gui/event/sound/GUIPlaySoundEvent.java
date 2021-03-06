package com.mikedeejay2.mikedeejay2lib.gui.event.sound;

import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.event.GUIEvent;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * An event for playing a sound from a GUI.
 *
 * @author Mikedeejay2
 */
public class GUIPlaySoundEvent implements GUIEvent
{
    protected Sound sound;
    protected SoundCategory category;
    protected float volume;
    protected float pitch;
    protected ClickType clickType;

    public GUIPlaySoundEvent(Sound sound, SoundCategory category, float volume, float pitch, ClickType clickType)
    {
        this.sound = sound;
        this.category = category;
        this.volume = volume;
        this.pitch = pitch;
        this.clickType = clickType;
    }

    public GUIPlaySoundEvent(Sound sound, SoundCategory category, float volume, float pitch)
    {
        this(sound, category, volume, pitch, null);
    }

    public GUIPlaySoundEvent(Sound sound, float volume, float pitch)
    {
        this(sound, null, volume, pitch, null);
    }

    public GUIPlaySoundEvent(Sound sound, float volume, float pitch, ClickType clickType)
    {
        this(sound, null, volume, pitch, clickType);
    }

    @Override
    public void execute(InventoryClickEvent event, GUIContainer gui)
    {
        if(clickType != null && event.getClick() != clickType) return;
        Player player = (Player) event.getWhoClicked();
        Location location = player.getLocation();
        if(category != null)
        {
            player.playSound(location, sound, category, volume, pitch);
        }
        else
        {
            player.playSound(location, sound, volume, pitch);
        }
    }
}
