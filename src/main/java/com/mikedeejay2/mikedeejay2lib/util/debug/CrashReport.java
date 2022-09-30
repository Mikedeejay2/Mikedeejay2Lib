package com.mikedeejay2.mikedeejay2lib.util.debug;

import com.mikedeejay2.mikedeejay2lib.BukkitPlugin;
import com.mikedeejay2.mikedeejay2lib.text.Text;
import com.mikedeejay2.mikedeejay2lib.util.time.FormattedTime;
import com.mikedeejay2.mikedeejay2lib.util.version.MinecraftVersion;
import org.apache.commons.lang3.Validate;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Crash report system. Basic setup:
 * <ul>
 *     <li>Add details with {@link CrashReport#addDetail(String, String)}. Default details include time and Description.</li>
 *     <li>Execute the crash report using {@link CrashReport#execute()}. This will print the crash report to console,
 *     and if notify OPs is enabled, will send a message to players with OP as well.</li>
 *     <li>After execution, it is recommended to disable the plugin.</li>
 * </ul>
 *
 * @author Mikedeejay2
 */
public class CrashReport {
    /**
     * The {@link BukkitPlugin} of this crash report
     */
    protected final BukkitPlugin plugin;
    /**
     * Whether to notify players with OP in-game
     */
    protected boolean notifyOps;
    /**
     * The list of {@link Text} details that will be included with the crash report
     */
    protected Map<String, String> details;
    /**
     * The throwable associated with this crash report
     */
    protected @Nullable Throwable throwable;
    /**
     * The sections of this crash report
     */
    protected List<CrashReportSection> sections;

    public CrashReport(BukkitPlugin plugin, boolean notifyOps, String description) {
        this.plugin = plugin;
        this.notifyOps = notifyOps;
        this.details = new LinkedHashMap<>();
        this.sections = new ArrayList<>();
        addDetail("Time", FormattedTime.getTimeDashed());
        addDetail("Description", description);
    }

    private CrashReportSection getHead() {
        CrashReportSection head = new CrashReportSection("Head");
        head.addDetail("Thread", Thread.currentThread().getName());
        StringBuilder stack = new StringBuilder("\n");
        for(StackTraceElement element : Thread.currentThread().getStackTrace()) {
            stack.append("\tat ").append(element.toString()).append("\n");
        }
        head.addDetail("Stacktrace", stack.toString());
        return head;
    }

    private CrashReportSection getSystemDetails() {
        CrashReportSection section = new CrashReportSection("System Details");
        section.addDetail("Minecraft Version", MinecraftVersion.getVersionString());
        section.addDetail("Operating System", SystemDetails.OPERATING_SYSTEM);
        section.addDetail("Java Version", SystemDetails.JAVA_VERSION);
        section.addDetail("Java VM Version", SystemDetails.JAVA_VM_VERSION);
        section.addDetail("Memory", SystemDetails.getMemory());
        section.addDetail("CPUs", SystemDetails.getMemory());
        section.addDetail("JVM Flags", SystemDetails.getJVMFlags());
        return section;
    }

    public CrashReport addAffectedLevel(World world) {
        CrashReportSection section = addSection("Affected level");
        section.addDetail("All players", world.getPlayers().size() + " total; " + world.getPlayers());
        section.addDetail("Chunk stats", String.valueOf(world.getLoadedChunks().length));
        section.addDetail("Level dimension", world.getEnvironment().toString().toLowerCase());
        section.addDetail("Level spawn location", formatLocation(world.getSpawnLocation()));
        section.addDetail("Level time", world.getGameTime() + " game time, " + world.getTime() + " day time");
        section.addDetail("Level name", world.getName());
        section.addDetail("Level game mode", "Game mode: " + plugin.getServer().getDefaultGameMode().toString().toLowerCase() +
            ". Hardcore: " + world.isHardcore());
        section.addDetail("Level weather", "Rain time: " + world.getWeatherDuration() + " (now: " + !world.isClearWeather() + "), " +
            "thunder time: " + world.getThunderDuration() + " (now: " + world.isThundering() + ")");
        return this;
    }

    public CrashReport addDetail(String name, String detail) {
        Validate.notNull(name, "Attempted to add null detail name to crash report");
        Validate.notNull(detail, "Attempted to add null detail to crash report");
        details.put(name, detail);
        return this;
    }

    public CrashReportSection addSection(String title) {
        CrashReportSection section = new CrashReportSection(title);
        sections.add(section);
        return section;
    }

    public boolean isNotifyOps() {
        return notifyOps;
    }

    public void setNotifyOps(boolean notifyOps) {
        this.notifyOps = notifyOps;
    }

    public Map<String, String> getDetails() {
        return details;
    }

    public @Nullable Throwable getThrowable() {
        return throwable;
    }

    public CrashReport setThrowable(@Nullable Throwable throwable) {
        this.throwable = throwable;
        return this;
    }

    public void execute() {
        String report = buildReport();

        plugin.sendSevere(report);
    }

    private String buildReport() {
        StringBuilder builder = new StringBuilder();
        builder.append("---- ").append(plugin.getName()).append(" Crash Report ----\n\n");
        for(String detailName : details.keySet()) {
            String detail = details.get(detailName);
            builder.append(detailName).append(": ").append(detail).append("\n");
        }
        builder.append("\n");

        if(throwable != null) {
            builder.append(throwable).append("\n")
                .append(formatStackTrace(throwable.getStackTrace()))
                .append("\n");
        }
        builder.append("A detailed walkthrough of the error, its code path and all known details is as follows:\n");
        builder.append("---------------------------------------------------------------------------------------\n");

        builder.append("\n").append(getHead().getString());

        for(CrashReportSection section : sections) {
            builder.append("\n").append(section.getString()).append("\n");
        }
        if(!sections.isEmpty()) builder.append("\n");

        builder.append(getSystemDetails().getString());

        return builder.toString();
    }

    protected static String formatStackTrace(StackTraceElement[] elements) {
        final StringBuilder builder = new StringBuilder();
        for(StackTraceElement element : elements) {
            builder.append("\tat ").append(element.toString()).append("\n");
        }
        return builder.toString();
    }

    protected static String formatLocation(Location location) {
        return location.getX() + "," + location.getY() + "," + location.getZ();
    }
}
