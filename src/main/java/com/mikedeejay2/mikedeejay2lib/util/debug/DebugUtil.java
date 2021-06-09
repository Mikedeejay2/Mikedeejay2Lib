package com.mikedeejay2.mikedeejay2lib.util.debug;

import java.util.logging.Logger;

/**
 * Util methods for debugging code
 *
 * @author Mikedeejay2
 */
public final class DebugUtil
{
    /**
     * Get the line number of the current line of code
     *
     * @return The current line of code that this method is being executed on
     */
    public static int getLineNumber()
    {
        return new Throwable().getStackTrace()[1].getLineNumber();
    }

    /**
     * Log the current line number
     */
    public static void printLineNumber()
    {
        Logger.getLogger("Mikedeejay2Lib Debugger")
            .warning(String.valueOf(
                new Throwable()
                    .getStackTrace()[1]
                    .getLineNumber()));
    }
}
