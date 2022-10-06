package com.mikedeejay2.mikedeejay2lib.gui.event.button;

import com.mikedeejay2.mikedeejay2lib.gui.event.GUIEventInfo;
import com.mikedeejay2.mikedeejay2lib.gui.event.sound.GUIPlaySoundEvent;
import org.apache.commons.lang3.Validate;
import org.bukkit.event.inventory.ClickType;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

/**
 * A simple button that executes a {@link Consumer} on click
 *
 * @author Mikedeejay2
 */
public class GUIButtonEvent extends GUIPlaySoundEvent {
    /**
     * The consumer that is run when the button is clicked
     */
    protected Consumer<GUIEventInfo> consumer;

    /**
     * Construct a new <code>GUIButtonEvent</code>
     *
     * @param consumer The consumer that is run when the button is clicked
     */
    public GUIButtonEvent(@NotNull Consumer<GUIEventInfo> consumer, ClickType... acceptedClicks) {
        super(null, null, 1, 1, acceptedClicks);
        Validate.notNull(consumer, "Button consumer can not be null");
        this.consumer = consumer;
    }

    /**
     * Construct a new <code>GUIButtonEvent</code>
     *
     * @param consumer The consumer that is run when the button is clicked
     */
    public GUIButtonEvent(@NotNull Consumer<GUIEventInfo> consumer) {
        super(null, null, 1, 1);
        Validate.notNull(consumer, "Button consumer can not be null");
        this.consumer = consumer;
    }

    /**
     * Call the button consumer on a valid click
     *
     * @param info {@link GUIEventInfo} of the event
     */
    @Override
    protected void executeClick(GUIEventInfo info) {
        consumer.accept(info);
        super.executeClick(info);
    }

    /**
     * Get the consumer that is run when the button is clicked
     *
     * @return The consumer for the button click
     */
    public Consumer<GUIEventInfo> getConsumer() {
        return consumer;
    }

    /**
     * Set the consumer that is run when the button is clicked
     *
     * @param consumer The new consumer
     * @return This event
     */
    public GUIButtonEvent setConsumer(@NotNull Consumer<GUIEventInfo> consumer) {
        Validate.notNull(consumer, "Button consumer can not be null");
        this.consumer = consumer;
        return this;
    }
}
