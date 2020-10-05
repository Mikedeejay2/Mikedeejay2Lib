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
    private long startTime;
    // The list of entries that this timer holds
    private List<TimerEntry> entries;
    // The name of this timer
    private String name;

    public DebugTimer(String name)
    {
        this.name = name;
        this.entries = new ArrayList<>();
        this.startTime = System.nanoTime();
    }

    /**
     * Add a printing point (a time) to be printed
     *
     * @param pointName The name of the point
     */
    public void addPrintPoint(String pointName)
    {
        entries.add(new TimerEntry(pointName, System.nanoTime()));
    }

    /**
     * Print the report for this debug timer
     */
    public void printReport()
    {
        long endTime = System.nanoTime();
        ConsoleCommandSender printer = Bukkit.getConsoleSender();
        printer.sendMessage(ChatColor.AQUA + "Time report for \"" + name + "\":");
        double overallTime = Math.round(((endTime - startTime) / 1000000.0) * 100.0) / 100.0;
        printer.sendMessage("Overall time: " + overallTime + "ms, " +
                Math.round((50.0 / overallTime) * 100.0) / 100.0 + " times a tick, " +
                Math.round((1000.0 / overallTime) * 100.0) / 100.0 + " times a second.");

        for(int i = 0; i < entries.size(); i++)
        {
            TimerEntry entry = entries.get(i);
            long curStart = i == 0 ? startTime : entries.get(i - 1).getTime();
            long curEnd = entry.getTime();
            String pointName = entry.getName();
            double finalTime = Math.round(((curEnd - curStart) / 1000000.0) * 100) / 100.0;
            printer.sendMessage("Point \"" + pointName + "\": " + finalTime + "ms, " +
                    Math.round((50.0 / finalTime) * 100.0) / 100.0 + " times a tick, " +
                    Math.round((1000.0 / finalTime) * 100.0) / 100.0 + " times a second.");
        }
    }
}
