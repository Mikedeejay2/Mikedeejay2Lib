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
    private static final DateTimeFormatter formatterSlashes = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    /**
     * The formatter that will format the current time
     */
    private static final DateTimeFormatter formatterDashes = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * Get the current time formatted as "yyyy/MM/dd HH:mm:ss"
     *
     * @return The current formatted time
     */
    public static String getTime() {
        return formatterSlashes.format(LocalDateTime.now());
    }

    /**
     * Get the current time formatted as "yyyy-MM-dd HH:mm:ss"
     *
     * @return The current formatted time
     */
    public static String getTimeDashed() {
        return formatterDashes.format(LocalDateTime.now());
    }
}
