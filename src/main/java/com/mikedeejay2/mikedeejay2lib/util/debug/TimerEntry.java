package com.mikedeejay2.mikedeejay2lib.util.debug;

/**
 * A timer's entry (A point to be printed)
 *
 * @author Mikedeejay2
 */
public class TimerEntry
{
    // The time of the entry
    protected long time;
    // The name of the entry
    protected String name;

    public TimerEntry(String name, long time)
    {
        this.time = time;
        this.name = name;
    }

    /**
     * Get the time
     *
     * @return The time
     */
    public long getTime()
    {
        return time;
    }

    /**
     * Get the name
     *
     * @return The name
     */
    public String getName()
    {
        return name;
    }
}
