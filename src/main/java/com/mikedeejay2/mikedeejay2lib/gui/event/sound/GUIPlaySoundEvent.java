package com.mikedeejay2.mikedeejay2lib.gui.event.sound;

import com.mikedeejay2.mikedeejay2lib.gui.GUIContainer;
import com.mikedeejay2.mikedeejay2lib.gui.event.GUIEvent;
import com.mikedeejay2.mikedeejay2lib.gui.event.GUIEventInfo;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.Nullable;

/**
 * An event for playing a sound from a GUI.
 *
 * @author Mikedeejay2
 */
public class GUIPlaySoundEvent implements GUIEvent
{
    /**
     * The {@link Sound} to play
     */
    protected Sound sound;

    /**
     * The {@link SoundCategory} that the sound will play in
     */
    protected SoundCategory category;

    /**
     * The volume that the sound will play as
     */
    protected float volume;

    /**
     * The pitch that the sound will play as
     */
    protected float pitch;

    /**
     * The {@link ClickType} needed to play the sound
     */
    protected @Nullable ClickType clickType;

    /**
     * Construct a new <code>GUIPlaySoundEvent</code>
     *
     * @param sound The {@link Sound} to play
     * @param category The {@link SoundCategory} that the sound will play in
     * @param volume The volume that the sound will play as
     * @param pitch The pitch that the sound will play as
     * @param clickType The {@link ClickType} needed to play the sound
     */
    public GUIPlaySoundEvent(Sound sound, SoundCategory category, float volume, float pitch, @Nullable ClickType clickType)
    {
        this.sound = sound;
        this.category = category;
        this.volume = volume;
        this.pitch = pitch;
        this.clickType = clickType;
    }

    /**
     * Construct a new <code>GUIPlaySoundEvent</code>
     *
     * @param sound The {@link Sound} to play
     * @param category The {@link SoundCategory} that the sound will play in
     * @param volume The volume that the sound will play as
     * @param pitch The pitch that the sound will play as
     */
    public GUIPlaySoundEvent(Sound sound, SoundCategory category, float volume, float pitch)
    {
        this(sound, category, volume, pitch, null);
    }

    /**
     * Construct a new <code>GUIPlaySoundEvent</code>
     *
     * @param sound The {@link Sound} to play
     * @param volume The volume that the sound will play as
     * @param pitch The pitch that the sound will play as
     */
    public GUIPlaySoundEvent(Sound sound, float volume, float pitch)
    {
        this(sound, null, volume, pitch, null);
    }

    /**
     * Construct a new <code>GUIPlaySoundEvent</code>
     *
     * @param sound The {@link Sound} to play
     * @param volume The volume that the sound will play as
     * @param pitch The pitch that the sound will play as
     * @param clickType The {@link ClickType} needed to play the sound
     */
    public GUIPlaySoundEvent(Sound sound, float volume, float pitch, ClickType clickType)
    {
        this(sound, null, volume, pitch, clickType);
    }

    /**
     * {@inheritDoc}
     *
     * @param info {@link GUIEventInfo} of the event
     */
    @Override
    public void execute(GUIEventInfo info)
    {
        if(clickType != null && info.getClick() != clickType) return;
        Player player = info.getPlayer();
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

    /**
     * Get the {@link Sound} that is played by this event
     *
     * @return The sound
     */
    public Sound getSound()
    {
        return sound;
    }

    /**
     * Set the {@link Sound} that is played by this event
     *
     * @param sound The new sound
     */
    public void setSound(Sound sound)
    {
        this.sound = sound;
    }

    /**
     * Get the {@link SoundCategory} that the sound will play in
     *
     * @return The <code>SoundCategory</code>
     */
    public SoundCategory getCategory()
    {
        return category;
    }

    /**
     * Set the {@link SoundCategory} that the sound will play in
     *
     * @param category The new <code>SoundCategory</code>
     */
    public void setCategory(SoundCategory category)
    {
        this.category = category;
    }

    /**
     * Get the volume that the sound will play as
     *
     * @return The volume
     */
    public float getVolume()
    {
        return volume;
    }

    /**
     * Set the volume that the sound will play as
     *
     * @param volume The new volume
     */
    public void setVolume(float volume)
    {
        this.volume = volume;
    }

    /**
     * Get the pitch that the sound will play as
     *
     * @return The pitch
     */
    public float getPitch()
    {
        return pitch;
    }

    /**
     * Set the pitch that the sound will play as
     *
     * @param pitch The new pitch
     */
    public void setPitch(float pitch)
    {
        this.pitch = pitch;
    }

    /**
     * The {@link ClickType} needed to play the sound. May be null if not specified
     *
     * @return The <code>ClickType</code>
     */
    public @Nullable ClickType getClickType()
    {
        return clickType;
    }

    /**
     * Set the {@link ClickType} needed to play the sound
     *
     * @param clickType The new <code>ClickType</code>
     */
    public void setClickType(@Nullable ClickType clickType)
    {
        this.clickType = clickType;
    }
}
