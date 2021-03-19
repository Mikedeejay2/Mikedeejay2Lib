package com.mikedeejay2.mikedeejay2lib.util.debug;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;

import java.util.*;

/**
 * A timer that records the milliseconds between construction and printing the report
 *
 * @author Mikedeejay2
 */
public final class DebugTimer
{
    // The time that the debug timer was started
    protected long startTime;
    // The list of entries that this timer holds
    protected List<TimerEntry> entries;
    // The name of this timer
    protected String name;
    // The overall difference from internal processing
    protected long overallDifference;

    public DebugTimer(String name)
    {
        this.name = name;
        this.entries = new ArrayList<>();
        this.overallDifference = 0;
        this.startTime = System.nanoTime();
    }

    /**
     * Add a printing point (a time) to be printed
     *
     * @param pointName The name of the point
     */
    public void addPrintPoint(String pointName)
    {
        long startTime = System.nanoTime();
        TimerEntry entry = new TimerEntry(pointName, startTime);
        entries.add(entry);
        long endTime = System.nanoTime();
        long difference = endTime - startTime;
        overallDifference += difference;
        long finalTime = startTime - overallDifference;
        entry.setTime(finalTime);
    }

    /**
     * Print the report for this debug timer
     *
     * @param decimalPlaces The amount of decimal places to print
     */
    public void printReport(int decimalPlaces)
    {
        long endTime = System.nanoTime();
        ConsoleCommandSender printer = Bukkit.getConsoleSender();
        double multiplier = Math.pow(10.0, decimalPlaces);
        printer.sendMessage(ChatColor.AQUA + "Time report for \"" + name + "\":");
        double overallNonRounded = ((endTime - startTime) / 1000000.0);
        double overallTime = Math.round(overallNonRounded * multiplier) / multiplier;
        int overallTick = (int)(Math.round(50.0 / overallNonRounded));
        int overallSecond = (int)(Math.round(1000.0 / overallNonRounded));
        printer.sendMessage("Overall time: " + overallTime + "ms, " +
                overallTick + " times a tick, " +
                overallSecond + " times a second.");

        for(int i = 0; i < entries.size(); i++)
        {
            TimerEntry entry = entries.get(i);
            long curStart = i == 0 ? startTime : entries.get(i - 1).getTime();
            long curEnd = entry.getTime();
            String pointName = entry.getName();
            double finalNonRounded = ((curEnd - curStart) / 1000000.0);
            double finalTime = Math.round(finalNonRounded * multiplier) / multiplier;
            int perTick = (int)(Math.round(50.0 / finalNonRounded));
            int perSecond = (int)(Math.round(1000.0 / finalNonRounded));
            printer.sendMessage("Point \"" + pointName + "\": " + finalTime + "ms, " +
                    perTick + " times a tick, " +
                    perSecond + " times a second.");
        }
    }

    /**
     * Get the end time in milliseconds of the timer. Using this method will break the print method, as
     * it prematurely ends the timer to return the accurate time.
     *
     * @param decimalPlaces The decimal places to return
     * @return The end time of the timer
     */
    public double getEndTime(int decimalPlaces)
    {
        long endTime = System.nanoTime();
        double overallNonRounded = ((endTime - startTime) / 1000000.0);
        double multiplier = Math.pow(10.0, decimalPlaces);
        return Math.round(overallNonRounded * multiplier) / multiplier;
    }

    /**
     * Print the report for this debug timer with the default 2 decimal places
     */
    public void printReport()
    {
        printReport(2);
    }
}
