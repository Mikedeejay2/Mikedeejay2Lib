package com.mikedeejay2.mikedeejay2lib.util.time;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * A simple formatting class for formatting the current time
 *
 * @author Mikedeejay2
 */
public final class FormattedTime {
    /**
     * The formatter that will format the current time
     */
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

    /**
     * A reference to the current LocalDateTime
     */
    private static final LocalDateTime now = LocalDateTime.now();

    /**
     * Get the current time formatted as "yyyy/MM/dd HH:mm:ss"
     *
     * @return The current formatted time
     */
    public static String getTime() {
        return formatter.format(now);
    }
}
