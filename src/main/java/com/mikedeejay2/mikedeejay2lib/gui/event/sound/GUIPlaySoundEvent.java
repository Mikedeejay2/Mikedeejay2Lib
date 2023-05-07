package com.mikedeejay2.mikedeejay2lib.gui.event.sound;

import com.mikedeejay2.mikedeejay2lib.gui.event.GUIClickEvent;
import com.mikedeejay2.mikedeejay2lib.gui.event.util.GUIAbstractClickEvent;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

/**
 * An event for playing a sound from a GUI.
 *
 * @author Mikedeejay2
 */
public class GUIPlaySoundEvent extends GUIAbstractClickEvent {
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
     * Construct a new <code>GUIPlaySoundEvent</code>
     *
     * @param sound          The {@link Sound} to play
     * @param category       The {@link SoundCategory} that the sound will play in
     * @param volume         The volume that the sound will play as
     * @param pitch          The pitch that the sound will play as
     * @param acceptedClicks The list of {@link ClickType ClickTypes} to accept
     */
    public GUIPlaySoundEvent(Sound sound, SoundCategory category, float volume, float pitch, ClickType... acceptedClicks) {
        super(acceptedClicks);
        this.sound = sound;
        this.category = category;
        this.volume = volume;
        this.pitch = pitch;
    }

    /**
     * Construct a new <code>GUIPlaySoundEvent</code>
     *
     * @param sound    The {@link Sound} to play
     * @param category The {@link SoundCategory} that the sound will play in
     * @param volume   The volume that the sound will play as
     * @param pitch    The pitch that the sound will play as
     */
    public GUIPlaySoundEvent(Sound sound, SoundCategory category, float volume, float pitch) {
        super();
        this.sound = sound;
        this.category = category;
        this.volume = volume;
        this.pitch = pitch;
    }

    /**
     * Construct a new <code>GUIPlaySoundEvent</code>
     *
     * @param sound  The {@link Sound} to play
     * @param volume The volume that the sound will play as
     * @param pitch  The pitch that the sound will play as
     */
    public GUIPlaySoundEvent(Sound sound, float volume, float pitch) {
        this(sound, null, volume, pitch);
    }

    /**
     * Construct a new <code>GUIPlaySoundEvent</code>
     *
     * @param sound          The {@link Sound} to play
     * @param volume         The volume that the sound will play as
     * @param pitch          The pitch that the sound will play as
     * @param acceptedClicks The list of {@link ClickType ClickTypes} to accept
     */
    public GUIPlaySoundEvent(Sound sound, float volume, float pitch, ClickType... acceptedClicks) {
        this(sound, null, volume, pitch, acceptedClicks);
    }

    @Override
    protected void executeClick(GUIClickEvent info) {
        if(sound == null) return;
        Player player = info.getPlayer();
        Location location = player.getLocation();
        if(category != null) {
            player.playSound(location, sound, category, volume, pitch);
        } else {
            player.playSound(location, sound, volume, pitch);
        }
    }

    /**
     * Get the {@link Sound} that is played by this event
     *
     * @return The sound
     */
    public Sound getSound() {
        return sound;
    }

    /**
     * Set the {@link Sound} that is played by this event
     *
     * @param sound The new sound
     * @return This event
     */
    public GUIPlaySoundEvent setSound(Sound sound) {
        this.sound = sound;
        return this;
    }

    /**
     * Get the {@link SoundCategory} that the sound will play in
     *
     * @return The <code>SoundCategory</code>
     */
    public SoundCategory getCategory() {
        return category;
    }

    /**
     * Set the {@link SoundCategory} that the sound will play in
     *
     * @param category The new <code>SoundCategory</code>
     * @return This event
     */
    public GUIPlaySoundEvent setCategory(SoundCategory category) {
        this.category = category;
        return this;
    }

    /**
     * Get the volume that the sound will play as
     *
     * @return The volume
     */
    public float getVolume() {
        return volume;
    }

    /**
     * Set the volume that the sound will play as
     *
     * @param volume The new volume
     * @return This event
     */
    public GUIPlaySoundEvent setVolume(float volume) {
        this.volume = volume;
        return this;
    }

    /**
     * Get the pitch that the sound will play as
     *
     * @return The pitch
     */
    public float getPitch() {
        return pitch;
    }

    /**
     * Set the pitch that the sound will play as
     *
     * @param pitch The new pitch
     * @return This event
     */
    public GUIPlaySoundEvent setPitch(float pitch) {
        this.pitch = pitch;
        return this;
    }
}
