package com.mikedeejay2.mikedeejay2lib.gui.event.button;

import com.mikedeejay2.mikedeejay2lib.gui.event.GUIEventInfo;
import org.apache.commons.lang.Validate;
import org.bukkit.event.inventory.ClickType;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

/**
 * A simple button that executes a {@link Consumer} on click
 *
 * @author Mikedeejay2
 */
public class GUIToggleButtonEvent extends GUIAbstractButtonEvent
{
    /**
     * The consumer that is run when the button is turned on
     */
    protected Consumer<GUIEventInfo> onConsumer;

    /**
     * The consumer that is run when the button is turned off
     */
    protected Consumer<GUIEventInfo> offConsumer;

    /**
     * The current state of whether the button is on or off
     */
    protected boolean state;

    /**
     * Construct a new <code>GUIToggleButtonEvent</code>
     *
     * @param onConsumer     The consumer that is run when the button is turned on
     * @param offConsumer    The consumer that is run when the button is turned off
     * @param initialState   The initial state (on/off) of the button
     * @param acceptedClicks The accepted {@link ClickType}s of the button
     */
    public GUIToggleButtonEvent(@NotNull Consumer<GUIEventInfo> onConsumer, @NotNull Consumer<GUIEventInfo> offConsumer, boolean initialState, ClickType... acceptedClicks)
    {
        super(acceptedClicks);
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
     * @param acceptedClicks The accepted {@link ClickType}s of the button
     */
    public GUIToggleButtonEvent(@NotNull Consumer<GUIEventInfo> onConsumer, @NotNull Consumer<GUIEventInfo> offConsumer, ClickType... acceptedClicks)
    {
        this(onConsumer, offConsumer, false, acceptedClicks);
    }

    /**
     * Construct a new <code>GUIToggleButtonEvent</code>
     *
     * @param onConsumer     The consumer that is run when the button is turned on
     * @param offConsumer    The consumer that is run when the button is turned off
     * @param initialState   The initial state (on/off) of the button
     */
    public GUIToggleButtonEvent(@NotNull Consumer<GUIEventInfo> onConsumer, @NotNull Consumer<GUIEventInfo> offConsumer, boolean initialState)
    {
        this(onConsumer, offConsumer, initialState, ClickType.LEFT, ClickType.RIGHT, ClickType.MIDDLE, ClickType.SHIFT_LEFT, ClickType.SHIFT_RIGHT);
    }

    /**
     * Construct a new <code>GUIToggleButtonEvent</code>
     *
     * @param onConsumer     The consumer that is run when the button is turned on
     * @param offConsumer    The consumer that is run when the button is turned off
     */
    public GUIToggleButtonEvent(@NotNull Consumer<GUIEventInfo> onConsumer, @NotNull Consumer<GUIEventInfo> offConsumer)
    {
        this(onConsumer, offConsumer, false);
    }

    /**
     * {@inheritDoc}
     *
     * @param info {@link GUIEventInfo} of the event
     */
    @Override
    public void execute(GUIEventInfo info)
    {
        state = !state;
        if(state)
        {
            onConsumer.accept(info);
        }
        else
        {
            offConsumer.accept(info);
        }
    }

    /**
     * Get the consumer that is run when the button is turned on
     *
     * @return The consumer that is run when the button is turned on
     */
    public Consumer<GUIEventInfo> getOnConsumer()
    {
        return onConsumer;
    }

    /**
     * Set the consumer that is run when the button is turned on
     *
     * @param consumer The new consumer
     */
    public void setOnConsumer(Consumer<GUIEventInfo> consumer)
    {
        Validate.notNull(consumer, "Button consumer can not be null");
        this.onConsumer = consumer;
    }

    /**
     * Get the consumer that is run when the button is turned off
     *
     * @return The consumer that is run when the button is turned off
     */
    public Consumer<GUIEventInfo> getOffConsumer()
    {
        return offConsumer;
    }

    /**
     * Set the consumer that is run when the button is turned on
     *
     * @param consumer The new consumer
     */
    public void setOffConsumer(Consumer<GUIEventInfo> consumer)
    {
        Validate.notNull(consumer, "Button consumer can not be null");
        this.offConsumer = consumer;
    }

    /**
     * Get whether this button is in the on state
     *
     * @return Whether the button is on
     */
    public boolean isOn()
    {
        return state;
    }

    /**
     * Get whether this button is in the off state
     *
     * @return Whether the button is off
     */
    public boolean isOff()
    {
        return !state;
    }

    /**
     * Set the state of this button (on/off). Note that this method will not activate any consumer.
     *
     * @param state The new state of the button
     */
    public void setState(boolean state)
    {
        this.state = state;
    }
}
