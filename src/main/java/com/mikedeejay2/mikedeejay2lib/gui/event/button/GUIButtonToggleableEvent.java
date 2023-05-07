package com.mikedeejay2.mikedeejay2lib.gui.event.button;

import com.mikedeejay2.mikedeejay2lib.gui.event.GUIClickEvent;
import com.mikedeejay2.mikedeejay2lib.gui.event.sound.GUIPlaySoundEvent;
import com.mikedeejay2.mikedeejay2lib.gui.item.GUIItem;
import com.mikedeejay2.mikedeejay2lib.item.ItemBuilder;
import org.apache.commons.lang3.Validate;
import org.bukkit.event.inventory.ClickType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

/**
 * A simple button that executes a {@link Consumer} on click with an on or off state
 *
 * @author Mikedeejay2
 */
public class GUIButtonToggleableEvent extends GUIPlaySoundEvent {
    /**
     * The consumer that is run when the button is turned on
     */
    protected Consumer<GUIClickEvent> onConsumer;

    /**
     * The consumer that is run when the button is turned off
     */
    protected Consumer<GUIClickEvent> offConsumer;

    /**
     * The current state of whether the button is on or off
     */
    protected boolean state;

    /**
     * Item to display when the button is in the ON state
     */
    protected @Nullable ItemBuilder onItem;

    /**
     * Item to display when the button is in the OFF state
     */
    protected @Nullable ItemBuilder offItem;

    /**
     * Construct a new <code>GUIToggleButtonEvent</code>
     *
     * @param onConsumer     The consumer that is run when the button is turned on
     * @param offConsumer    The consumer that is run when the button is turned off
     * @param initialState   The initial state (on/off) of the button
     * @param acceptedClicks The list of {@link ClickType ClickTypes} to accept
     */
    public GUIButtonToggleableEvent(@NotNull Consumer<GUIClickEvent> onConsumer, @NotNull Consumer<GUIClickEvent> offConsumer, boolean initialState, ClickType... acceptedClicks) {
        super(null, null, 1, 1, acceptedClicks);
        Validate.notNull(onConsumer, "Button consumer can not be null");
        Validate.notNull(offConsumer, "Button consumer can not be null");
        this.onConsumer = onConsumer;
        this.offConsumer = offConsumer;
        this.state = initialState;
    }

    /**
     * Construct a new <code>GUIToggleButtonEvent</code>
     *
     * @param onConsumer     The consumer that is run when the button is turned on
     * @param offConsumer    The consumer that is run when the button is turned off
     * @param acceptedClicks The list of {@link ClickType ClickTypes} to accept
     */
    public GUIButtonToggleableEvent(@NotNull Consumer<GUIClickEvent> onConsumer, @NotNull Consumer<GUIClickEvent> offConsumer, ClickType... acceptedClicks) {
        this(onConsumer, offConsumer, false, acceptedClicks);
    }

    /**
     * Construct a new <code>GUIToggleButtonEvent</code>
     *
     * @param onConsumer     The consumer that is run when the button is turned on
     * @param offConsumer    The consumer that is run when the button is turned off
     * @param initialState   The initial state (on/off) of the button
     */
    public GUIButtonToggleableEvent(@NotNull Consumer<GUIClickEvent> onConsumer, @NotNull Consumer<GUIClickEvent> offConsumer, boolean initialState) {
        super(null, null, 1, 1);
        Validate.notNull(onConsumer, "Button consumer can not be null");
        Validate.notNull(offConsumer, "Button consumer can not be null");
        this.onConsumer = onConsumer;
        this.offConsumer = offConsumer;
        this.state = initialState;
    }

    /**
     * Construct a new <code>GUIToggleButtonEvent</code>
     *
     * @param onConsumer     The consumer that is run when the button is turned on
     * @param offConsumer    The consumer that is run when the button is turned off
     */
    public GUIButtonToggleableEvent(@NotNull Consumer<GUIClickEvent> onConsumer, @NotNull Consumer<GUIClickEvent> offConsumer) {
        this(onConsumer, offConsumer, false);
    }

    /**
     * Toggle the button on a valid click
     *
     * @param info {@link GUIClickEvent} of the event
     */
    @Override
    protected void executeClick(GUIClickEvent info) {
        changeState(!state, info.getGUIItem());
        if(state) {
            onConsumer.accept(info);
        } else {
            offConsumer.accept(info);
        }
        super.executeClick(info);
    }

    /**
     * Update the item representation of the button
     *
     * @param item The <code>GUIItem</code> representing the button
     */
    protected void updateItem(GUIItem item) {
        if(state && onItem != null) {
            item.set(onItem);
        } else if(!state && offItem != null) {
            item.set(offItem);
        }
    }

    /**
     * Changes the state of this button without calling the related consumer. The item of this button will also be
     * updated if it is not null.
     *
     * @param state The new state of the button
     * @param item The <code>GUIItem</code> representing this button
     */
    public void changeState(boolean state, GUIItem item) {
        setState(state);
        updateItem(item);
    }

    /**
     * Get the consumer that is run when the button is turned on
     *
     * @return The consumer that is run when the button is turned on
     */
    public Consumer<GUIClickEvent> getOnConsumer() {
        return onConsumer;
    }

    /**
     * Set the consumer that is run when the button is turned on
     *
     * @param consumer The new consumer
     * @return This event
     */
    public GUIButtonToggleableEvent setOnConsumer(Consumer<GUIClickEvent> consumer) {
        Validate.notNull(consumer, "Button consumer can not be null");
        this.onConsumer = consumer;
        return this;
    }

    /**
     * Get the consumer that is run when the button is turned off
     *
     * @return The consumer that is run when the button is turned off
     */
    public Consumer<GUIClickEvent> getOffConsumer() {
        return offConsumer;
    }

    /**
     * Set the consumer that is run when the button is turned on
     *
     * @param consumer The new consumer
     * @return This event
     */
    public GUIButtonToggleableEvent setOffConsumer(Consumer<GUIClickEvent> consumer) {
        Validate.notNull(consumer, "Button consumer can not be null");
        this.offConsumer = consumer;
        return this;
    }

    /**
     * Get whether this button is in the on state
     *
     * @return Whether the button is on
     */
    public boolean isOn() {
        return state;
    }

    /**
     * Get whether this button is in the off state
     *
     * @return Whether the button is off
     */
    public boolean isOff() {
        return !state;
    }

    /**
     * Set the state of this button (on/off). Note that this method will not activate any consumer.
     *
     * @param state The new state of the button
     */
    protected void setState(boolean state) {
        this.state = state;
    }

    /**
     * Get the {@link ItemBuilder} to display when the button is in the ON state
     *
     * @return The {@link ItemBuilder} to display when the button is in the ON state
     */
    public @Nullable ItemBuilder getOnItem() {
        return onItem;
    }

    /**
     * Set the {@link ItemBuilder} to display when the button is in the ON state
     *
     * @param onItem The new {@link ItemBuilder}
     * @return This event
     */
    public GUIButtonToggleableEvent setOnItem(@Nullable ItemBuilder onItem) {
        this.onItem = onItem;
        return this;
    }

    /**
     * Get the {@link ItemBuilder} to display when the button is in the OFF state
     *
     * @return The {@link ItemBuilder} to display when the button is in the OFF state
     */
    public @Nullable ItemBuilder getOffItem() {
        return offItem;
    }

    /**
     * Set the {@link ItemBuilder} to display when the button is in the OFF state
     *
     * @param offItem The new {@link ItemBuilder}
     * @return This event
     */
    public GUIButtonToggleableEvent setOffItem(@Nullable ItemBuilder offItem) {
        this.offItem = offItem;
        return this;
    }
}
