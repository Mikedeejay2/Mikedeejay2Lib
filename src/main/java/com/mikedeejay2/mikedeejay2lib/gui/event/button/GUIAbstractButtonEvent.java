package com.mikedeejay2.mikedeejay2lib.gui.event.button;

import com.mikedeejay2.mikedeejay2lib.gui.event.GUIEvent;
import com.mikedeejay2.mikedeejay2lib.gui.event.GUIEventInfo;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.event.inventory.ClickType;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Abstract GUI Button event for button types to extend.
 *
 * @author Mikedeejay2
 */
public abstract class GUIAbstractButtonEvent<T extends GUIAbstractButtonEvent<T>> implements GUIEvent {
    /**
     * A List of the accepted {@link ClickType}s that will activate the button.
     */
    protected final List<ClickType> acceptedClicks;

    /**
     * The {@link Sound} to play on click. Can be null.
     */
    protected @Nullable Sound sound;

    /**
     * The {@link SoundCategory} that the sound will play in
     */
    protected SoundCategory soundCategory;

    /**
     * The volume that the sound will play as
     */
    protected float volume;

    /**
     * The pitch that the sound will play as
     */
    protected float pitch;

    /**
     * Constructor for {@link GUIAbstractButtonEvent}
     */
    public GUIAbstractButtonEvent() {
        this.acceptedClicks = new ArrayList<>(Arrays.asList(
            ClickType.LEFT, ClickType.RIGHT, ClickType.MIDDLE, ClickType.SHIFT_LEFT, ClickType.SHIFT_RIGHT));
        this.sound = null;
        this.soundCategory = SoundCategory.MASTER;
        this.volume = 0.5f;
        this.pitch = 1;
    }

    @Override
    public void execute(GUIEventInfo info) {
        if(sound != null) {
            info.getPlayer().playSound(info.getPlayer(), sound, soundCategory, volume, pitch);
        }
    }

    /**
     * Get List of the accepted {@link ClickType}s that will activate the button.
     *
     * @return The list of <code>ClickTypes</code>
     */
    public List<ClickType> getAcceptedClicks() {
        return acceptedClicks;
    }

    /**
     * Add a {@link ClickType} to this button
     *
     * @param click The <code>ClickType</code> to add
     */
    public T addClick(ClickType click) {
        acceptedClicks.add(click);
        return (T) this;
    }

    /**
     * Add multiple {@link ClickType}s to this button
     *
     * @param clicks The <code>ClickTypes</code> to add
     */
    public T addClicks(ClickType... clicks) {
        acceptedClicks.addAll(Arrays.asList(clicks));
        return (T) this;
    }

    /**
     * Remove a {@link ClickType} from this button
     *
     * @param click The <code>ClickType</code> to remove
     */
    public T removeClick(ClickType click) {
        acceptedClicks.remove(click);
        return (T) this;
    }

    /**
     * Remove multiple {@link ClickType}s from this button
     *
     * @param clicks The <code>ClickTypes</code> to remove
     */
    public T removeClicks(ClickType... clicks) {
        acceptedClicks.removeAll(Arrays.asList(clicks));
        return (T) this;
    }

    /**
     * Set the click types for this button
     *
     * @param clicks The <code>ClickTypes</code> to use
     * @return This button
     */
    public T setClicks(ClickType... clicks) {
        acceptedClicks.clear();
        addClicks(clicks);
        return (T) this;
    }

    /**
     * Get whether a specific {@link ClickType} is an accepted click for this button
     *
     * @param click The <code>ClickType</code> to test
     * @return Whether the <code>ClickType</code> is an accepted click
     */
    public boolean isValidClick(ClickType click) {
        return acceptedClicks.contains(click);
    }

    /**
     * Get the {@link Sound} that is played by this event
     *
     * @return The sound
     */
    public @Nullable Sound getSound() {
        return sound;
    }

    /**
     * Set the {@link Sound} that is played by this event
     *
     * @param sound The new sound
     */
    public T setSound(Sound sound) {
        this.sound = sound;
        return (T) this;
    }

    /**
     * Set the {@link Sound} that is played by this event
     *
     * @param sound The new sound
     * @param category The new <code>SoundCategory</code>
     * @param pitch The new pitch
     * @param volume The new volume
     */
    public T setSound(Sound sound, SoundCategory category, float pitch, float volume) {
        this.sound = sound;
        setSoundCategory(category);
        setSoundPitch(pitch);
        setSoundVolume(volume);
        return (T) this;
    }

    /**
     * Set the {@link Sound} that is played by this event
     *
     * @param sound The new sound
     * @param pitch The new pitch
     * @param volume The new volume
     */
    public T setSound(Sound sound, float pitch, float volume) {
        this.sound = sound;
        setSoundPitch(pitch);
        setSoundVolume(volume);
        return (T) this;
    }

    /**
     * Get the {@link SoundCategory} that the sound will play in
     *
     * @return The <code>SoundCategory</code>
     */
    public SoundCategory getSoundCategory() {
        return soundCategory;
    }

    /**
     * Set the {@link SoundCategory} that the sound will play in
     *
     * @param category The new <code>SoundCategory</code>
     */
    public T setSoundCategory(SoundCategory category) {
        this.soundCategory = category;
        return (T) this;
    }

    /**
     * Get the volume that the sound will play as
     *
     * @return The volume
     */
    public float getSoundVolume() {
        return volume;
    }

    /**
     * Set the volume that the sound will play as
     *
     * @param volume The new volume
     */
    public T setSoundVolume(float volume) {
        this.volume = volume;
        return (T) this;
    }

    /**
     * Get the pitch that the sound will play as
     *
     * @return The pitch
     */
    public float getSoundPitch() {
        return pitch;
    }

    /**
     * Set the pitch that the sound will play as
     *
     * @param pitch The new pitch
     */
    public T setSoundPitch(float pitch) {
        this.pitch = pitch;
        return (T) this;
    }
}
