package com.mikedeejay2.mikedeejay2lib.util.debug;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class DebugTimer
{
    private long startTime;
    private Map<String, Long> points;
    private String name;

    public DebugTimer(String name)
    {
        this.name = name;
        this.points = new HashMap<>();
        this.startTime = System.nanoTime();
    }

    public void addPrintPoint(String pointName)
    {
        points.put(pointName, System.nanoTime());
    }

    public void printReport()
    {
        long endTime = System.nanoTime();
        ConsoleCommandSender printer = Bukkit.getConsoleSender();
        printer.sendMessage(ChatColor.AQUA + "Time report for \"" + name + "\":");
        double overallTime = (endTime - startTime) / 1000000.0;
        printer.sendMessage("Overall time: " + overallTime + "ms, " + (50.0 / overallTime) + " times a tick.");
        String[] keys = points.keySet().toArray(new String[0]);
        Long[] values = points.values().toArray(new Long[0]);
        for(int i = 0; i < points.size(); i++)
        {
            long curStart = i == 0 ? this.startTime : values[i - 1];
            long curEnd = values[i];
            String name = keys[i];
            double finalTime = (curEnd - curStart) / 1000000.0;
            printer.sendMessage("Point \"" + name + "\": " + finalTime + "ms, " + (50.0 / finalTime) + " times a tick.");
        }
    }
}
