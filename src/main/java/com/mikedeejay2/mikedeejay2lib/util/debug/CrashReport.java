package com.mikedeejay2.mikedeejay2lib.util.debug;

import com.mikedeejay2.mikedeejay2lib.BukkitPlugin;
import com.mikedeejay2.mikedeejay2lib.text.Text;
import com.mikedeejay2.mikedeejay2lib.util.time.FormattedTime;
import com.mikedeejay2.mikedeejay2lib.util.version.MinecraftVersion;
import org.apache.commons.lang3.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

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

    public CrashReport(BukkitPlugin plugin, String description) {
        this.plugin = plugin;
        this.details = new LinkedHashMap<>();
        this.sections = new ArrayList<>();
        addDetail("Time", FormattedTime.getTime());
        addDetail("Description", description);
    }

    private CrashReportSection getHead() {
        CrashReportSection head = new CrashReportSection("Head");
        head.addDetail("Thread", Thread.currentThread().getName());
        head.addDetail("Stacktrace", "\n" + formatStackTrace(Thread.currentThread().getStackTrace()));
        return head;
    }

    private CrashReportSection getSystemDetails() {
        CrashReportSection section = new CrashReportSection("System Details");
        section.addDetail("Minecraft Version", MinecraftVersion.getVersionString());
        section.addDetail("Plugin Name", plugin.getName());
        section.addDetail("Plugin Version", plugin.getDescription().getVersion());
        section.addDetail("Plugin API Version", plugin.getDescription().getAPIVersion());
        section.addDetail("Operating System", SystemDetails.OPERATING_SYSTEM);
        section.addDetail("Java Version", SystemDetails.JAVA_VERSION);
        section.addDetail("Java VM Version", SystemDetails.JAVA_VM_VERSION);
        section.addDetail("Memory", SystemDetails.getMemory());
        section.addDetail("CPUs", SystemDetails.getMemory());
        section.addDetail("JVM Flags", SystemDetails.getJVMFlags());

        section.addDetail("CraftBukkit Information", getServerDetails());
        return section;
    }

    /**
     * Modified from <code>org.bukkit.craftbukkit.CraftCrashReport</code>
     *
     * @return Server details
     */
    private static String getServerDetails() {
        StringBuilder builder = new StringBuilder();
        builder.append("\n\tRunning: ").append(Bukkit.getName()).append(" version ")
            .append(Bukkit.getVersion()).append(" (Implementing API version ")
            .append(Bukkit.getBukkitVersion()).append(") ")
            .append(Bukkit.getServer().getOnlineMode());
        builder.append("\n\tPlugins: {");
        for (Plugin plugin : Bukkit.getPluginManager().getPlugins()) {
            PluginDescriptionFile description = plugin.getDescription();
            boolean legacy = description.getAPIVersion() == null;
            builder.append(' ').append(description.getFullName())
                .append(legacy ? "*" : "").append(' ')
                .append(description.getMain()).append(' ')
                .append(Arrays.toString(description.getAuthors().toArray())).append(',');
        }
        builder.append("}\n\tWarnings: ").append(Bukkit.getWarningState().name());
        builder.append("}\n\t").append("Scheduler: ").append(Bukkit.getScheduler());
        builder.append("\n\tForce Loaded Chunks: {");
        for (World world : Bukkit.getWorlds()) {
            builder.append(' ').append(world.getName()).append(": {");
            for (Map.Entry<Plugin, Collection<Chunk>> entry : world.getPluginChunkTickets().entrySet()) {
                builder.append(' ').append(entry.getKey().getDescription().getFullName()).append(": ")
                    .append(entry.getValue().size()).append(',');
            }
            builder.append("},");
        }
        builder.append("}");
        return builder.toString();
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
        plugin.sendSevere(buildReport());
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

        builder.append("\n").append(getHead().getString()).append("\n\n");

        for(CrashReportSection section : sections) {
            builder.append(getSectionString(section)).append("\n\n");
        }

        builder.append(getSectionString(getSystemDetails()));

        return builder.toString();
    }

    @NotNull
    private static String getSectionString(CrashReportSection section) {
        return section.getString().replace("\n", "\n\t");
    }

    protected static String formatStackTrace(StackTraceElement[] elements) {
        final StringBuilder builder = new StringBuilder();
        for(StackTraceElement element : elements) {
            builder.append("\tat ").append(element.toString()).append("\n");
        }
        return builder.toString();
    }

    protected static String formatLocation(Location location) {
        return location.getBlockX() + "," + location.getBlockY() + "," + location.getBlockZ();
    }
}
