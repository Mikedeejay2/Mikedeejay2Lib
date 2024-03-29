package com.mikedeejay2.mikedeejay2lib.util.time;

/**
 * Holds different conversions and helper methods for converting ticks to different time formats
 *
 * @author Mikedeejay2
 */
public enum TimeType {
    /**
     * A single tick. 1/20 of a second.
     */
    TICK,

    /**
     * One second. 1/60 of a minute.
     */
    SECOND,

    /**
     * One minute. 1/60 of an hour.
     */
    MINUTE,

    /**
     * One hour. 60 minutes.
     */
    HOUR;

    /**
     * Get ratio of ticks to 1 of a time type
     *
     * @return return this TimeType's tick length
     */
    public long tickToType() {
        switch(this) {
            case TICK: return 1;
            case SECOND: return 20;
            case MINUTE: return 1200;
            case HOUR: return 72000;
        }
        return -1;
    }

    /**
     * For cycling time types get the next type in the list
     *
     * @return The next shortest TimeType (<code>TICK to SECOND to MINUTE</code>...etc)
     */
    public TimeType getNextType() {
        switch(this) {
            case TICK: return SECOND;
            case SECOND: return MINUTE;
            case MINUTE: return HOUR;
            case HOUR: return TICK;
        }
        return null;
    }

    /**
     * A better toString method
     *
     * @return A formatted String that looks all nice and is capitalized.
     */
    public String getFormattedString() {
        switch(this) {
            case TICK: return "Tick";
            case SECOND: return "Second";
            case MINUTE: return "Minute";
            case HOUR: return "Hour";
        }
        return null;
    }
}
