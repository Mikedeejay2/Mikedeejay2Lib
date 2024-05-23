package com.mikedeejay2.mikedeejay2lib.util.debug;

import com.mikedeejay2.mikedeejay2lib.BukkitPlugin;
import com.mikedeejay2.mikedeejay2lib.text.PlaceholderFormatter;
import com.mikedeejay2.mikedeejay2lib.text.Text;
import com.mikedeejay2.mikedeejay2lib.util.file.FileIO;
import com.mikedeejay2.mikedeejay2lib.util.time.FormattedTime;
import com.mikedeejay2.mikedeejay2lib.util.version.MinecraftVersion;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.apache.commons.lang3.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.mikedeejay2.mikedeejay2lib.util.chat.ChatConverter.*;
import static net.md_5.bungee.api.chat.ClickEvent.Action.*;
import static net.md_5.bungee.api.chat.HoverEvent.Action.*;

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
     * The map of details that will be included with the crash report
     */
    protected Map<String, String> details;
    /**
     * The throwable associated with this crash report
     */
    protected @Nullable Throwable throwable;
    /**
     * The sections of this crash report
     */
    protected Map<String, CrashReportSection> sections;
    /**
     * Whether to notify players with OP
     */
    protected boolean notifyOps;
    /**
     * Information about the crash. Printed to console after the crash report and to players if <code>notifyOps</code>
     * is true. Should include details such as a generic statement about what happened and where to report the crash
     * report.
     * <p>
     * This is <strong>NOT</strong> the crash report! This is helper information printed after the crash report to aid
     * users in submitting a bug report.
     */
    protected List<Text> crashInfo;
    /**
     * Whether to create a file in <code>plugins/plugin_name/crash-reports</code>
     */
    protected boolean createFile;

    /**
     * Construct a new <code>CrashReport</code>
     *
     * @param plugin      The {@link BukkitPlugin} of this crash report
     * @param description The description of this crash report
     * @param notifyOps   Whether to notify players with OP upon execution of the crash report
     * @param createFile  Whether to create a file in <code>plugins/plugin_name/crash-reports</code>
     */
    public CrashReport(BukkitPlugin plugin, String description, boolean notifyOps, boolean createFile) {
        this.plugin = plugin;
        this.details = new LinkedHashMap<>();
        this.sections = new LinkedHashMap<>();
        this.notifyOps = notifyOps;
        this.crashInfo = new ArrayList<>();
        this.createFile = createFile;
        addDetail("Time", FormattedTime.getTime());
        addDetail("Description", description);
    }

    /**
     * Generate the head of the crash report. Contains a thread name and its stack trace.
     *
     * @return The head section
     */
    private CrashReportSection getHead() {
        CrashReportSection head = new CrashReportSection("Head");
        head.addDetail("Thread", Thread.currentThread().getName());
        head.addDetail("Stacktrace", "\n" + formatStackTrace(Thread.currentThread().getStackTrace()));
        return head;
    }

    /**
     * Generate the system details of the crash report.
     *
     * @return The system details section
     */
    private CrashReportSection getSystemDetails() {
        CrashReportSection section = new CrashReportSection("System Details");
        section.addDetail("Minecraft Version", MinecraftVersion.VERSION);
        section.addDetail("Plugin Name", plugin.getName());
        section.addDetail("Plugin Version", plugin.getDescription().getVersion());
        section.addDetail("Plugin API Version", plugin.getDescription().getAPIVersion());
        section.addDetail("Operating System", SystemDetails.OPERATING_SYSTEM);
        section.addDetail("Java Version", SystemDetails.JAVA_VERSION);
        section.addDetail("Java VM Version", SystemDetails.JAVA_VM_VERSION);
        section.addDetail("Memory", SystemDetails.getMemory());
        section.addDetail("CPUs", SystemDetails.getProcessorCount());
        section.addDetail("JVM Flags", SystemDetails.getJVMFlags());

        section.addDetail("CraftBukkit Information", getServerDetails());
        return section;
    }

    /**
     * Modified from <code>org.bukkit.craftbukkit.CraftCrashReport</code>. Generates the server details of the crash
     * report.
     *
     * @return The String of server details
     */
    private static String getServerDetails() {
        StringBuilder builder = new StringBuilder();
        builder.append("\n    Running: ").append(Bukkit.getName()).append(" version ")
            .append(Bukkit.getVersion()).append(" (Implementing API version ")
            .append(Bukkit.getBukkitVersion()).append(") ")
            .append(Bukkit.getServer().getOnlineMode());
        builder.append("\n    Plugins:");
        for (Plugin plugin : Bukkit.getPluginManager().getPlugins()) {
            PluginDescriptionFile description = plugin.getDescription();
            boolean legacy = description.getAPIVersion() == null;
            builder.append("\n      ").append(description.getFullName())
                .append(legacy ? "*" : "").append(' ')
                .append(description.getMain()).append(' ')
                .append(Arrays.toString(description.getAuthors().toArray()));
        }
        builder.append("\n    Warnings: ").append(Bukkit.getWarningState().name());
        builder.append("\n    ").append("Scheduler: ").append(Bukkit.getScheduler());
        builder.append("\n    Force Loaded Chunks:");
        for (World world : Bukkit.getWorlds()) {
            builder.append("\n      ").append(world.getName()).append(": {");
            for (Map.Entry<Plugin, Collection<Chunk>> entry : world.getPluginChunkTickets().entrySet()) {
                builder.append(' ').append(entry.getKey().getDescription().getFullName()).append(": ")
                    .append(entry.getValue().size());
            }
            builder.append("}");
        }
        return builder.toString();
    }

    /**
     * Add a world to this crash report. This only needs to be specified if the crash has occurred in a specific world.
     *
     * @param name  The name of the section
     * @param world The world where the crash has occurred in
     * @return The created section
     */
    public CrashReportSection addWorld(String name, World world) {
        CrashReportSection section = addSection(name);
        section.addDetail("All players", world.getPlayers().size() + " total; [" + formatEntities(world.getPlayers()) + "]");
        section.addDetail("Chunk stats", String.valueOf(world.getLoadedChunks().length));
        section.addDetail("Level dimension", world.getEnvironment().toString().toLowerCase());
        section.addDetail("Level spawn location", formatBlockLocation(world.getSpawnLocation()));
        section.addDetail("Level time", world.getGameTime() + " game time, " + world.getTime() + " day time");
        section.addDetail("Level name", world.getName());
        section.addDetail("Level game mode", "Game mode: " + plugin.getServer().getDefaultGameMode().toString().toLowerCase() +
            ". Hardcore: " + world.isHardcore());
        section.addDetail("Level weather", "Rain time: " + world.getWeatherDuration() + " (now: " + !world.isClearWeather() + "), " +
            "thunder time: " + world.getThunderDuration() + " (now: " + world.isThundering() + ")");
        return section;
    }

    /**
     * Add an entity to this crash report. This only needs to be specified if the crash report occurred because of the
     * entity.
     *
     * @param name   The name of the section
     * @param entity The entity that has caused the crash
     * @return The created section
     */
    public CrashReportSection addEntity(String name, Entity entity) {
        CrashReportSection section = addSection(name);
        section.addDetail("Entity Type", entity.getType().toString().toLowerCase() +
            (entity.getType().getEntityClass() != null ? " (" + entity.getType().getEntityClass().getCanonicalName() + ")" : ""));
        section.addDetail("Entity ID", String.valueOf(entity.getEntityId()));
        section.addDetail("Entity Name", entity.getName());
        section.addDetail("Entity's Exact location", formatLocation(entity.getLocation()));
        section.addDetail("Entity's Block location", formatBlockLocation(entity.getLocation()));
        final Vector velocity = entity.getVelocity();
        section.addDetail("Entity's Momentum", String.format(
            "%.2f,%.2f,%.2f", velocity.getX(), velocity.getY(), velocity.getZ()));
        section.addDetail("Entity's Passengers", "[" + formatEntities(entity.getPassengers()) + "]");
        section.addDetail("Entity's Vehicle", entity.getVehicle() != null ? formatEntity(entity.getVehicle()) : "none");
        return section;
    }

    /**
     * Add a detail. This is displayed at the top of the crash report and should only contain basic information to be
     * displayed to the user. For advanced information, create a section using {@link CrashReport#addSection(String)}.
     *
     * @param name   The name of this detail
     * @param detail The detail as a String
     * @return This crash report
     */
    public CrashReport addDetail(String name, String detail) {
        Validate.notNull(name, "Attempted to add null detail name to crash report");
        details.put(name, detail);
        return this;
    }

    /**
     * Add a section. This is display in the middle of the crash report, and used for debugging in relation to the
     * crash that occurred. The returned {@link CrashReportSection} should have details added to it using
     * {@link CrashReportSection#addDetail(String, String)}.
     *
     * @param title The title of the section
     * @return The new {@link CrashReportSection}
     */
    public CrashReportSection addSection(String title) {
        return addSection(new CrashReportSection(title));
    }

    /**
     * Add a section. This is display in the middle of the crash report, and used for debugging in relation to the
     * crash that occurred. The returned {@link CrashReportSection} should have details added to it using
     * {@link CrashReportSection#addDetail(String, String)}.
     * <p>
     * This is a utility method. It should only be used when {@link CrashReport#addSection(String)} can't
     * be used.
     *
     * @param section The {@link CrashReportSection}
     * @return The new {@link CrashReportSection}
     */
    public CrashReportSection addSection(CrashReportSection section) {
        sections.put(section.getTitle(), section);
        return section;
    }

    /**
     * Add information about the crash. Printed to console after the crash report and to players if
     * <code>notifyOps</code> is true. Should include details such as a generic statement about what happened and where
     * to report the crash report.
     *
     * @param text The text to be added as information
     * @return This crash report
     */
    public CrashReport addInfo(Text text) {
        Validate.notNull(text, "Attempted to add null crash information");
        crashInfo.add(text);
        return this;
    }

    /**
     * Execute this crash report, printing the crash report to the console. If {@link CrashReport#notifyOps()} is true,
     * players with OP will also be notified in-game about the crash, with the ability to copy the crash report in its
     * entirety to clipboard.
     */
    public void execute() {
        final String report = getReport();
        plugin.sendSevere("\n&c" + report);
        for(Text text : crashInfo) {
            plugin.sendInfo(text);
        }

        if(notifyOps) executeNotifyOps(report);
        if(createFile) executeCreateFile(report);
    }

    /**
     * Build the String of this crash report
     *
     * @return This crash report as a String
     */
    public String getReport() {
        StringBuilder builder = new StringBuilder();
        builder.append("---- ").append(plugin.getName()).append(" Crash Report ----\n\n");
        for(String detailName : details.keySet()) {
            String detail = details.get(detailName);
            builder.append(detailName).append(": ").append(detail).append("\n");
        }
        builder.append("\n");

        if(throwable != null) {
            builder.append(throwable).append("\n")
                .append(formatStackTrace(throwable.getStackTrace()));
            Throwable cause = throwable.getCause();
            while(cause != null) {
                builder.append("Caused by: ").append(cause).append("\n")
                    .append(formatStackTrace(cause.getStackTrace()));
                cause = cause.getCause();
            }
            builder.append("\n");
        }
        builder.append("A detailed walkthrough of the error, its code path and all known details is as follows:\n");
        builder.append("---------------------------------------------------------------------------------------\n");

        builder.append("\n").append(getHead().getString()).append("\n\n");

        for(CrashReportSection section : sections.values()) {
            builder.append(getSectionString(section)).append("\n\n");
        }

        builder.append(getSectionString(getSystemDetails()));

        return builder.toString();
    }

    /**
     * Execute notifying players with OP about the crash.
     *
     * @param report The generated crash report
     */
    protected void executeNotifyOps(String report) {
        final Text textMessage = Text.of("&c").concat("crash_report.message")
            .placeholder(PlaceholderFormatter.of("plugin", plugin.getName())).color();
        final Text textCopy = Text.of("&6").concat("crash_report.copy_report").color();

        for(Player player : Bukkit.getOnlinePlayers()) {
            if(!player.isOp()) continue;
            final String stringMessage = textMessage.get(player);
            final String stringCopy = textCopy.get(player);
            BaseComponent[] componentsMessage = TextComponent.fromLegacyText(stringMessage + " ");
            BaseComponent[] componentsCopy = TextComponent.fromLegacyText(stringCopy);
            setClickEvent(componentsCopy, getClickEvent(COPY_TO_CLIPBOARD, report));
            setHoverEvent(componentsCopy, getHoverEvent(SHOW_TEXT, stringCopy));

            player.spigot().sendMessage(componentsMessage);
            player.spigot().sendMessage(componentsCopy);

            for(Text text : crashInfo) {
                plugin.sendMessage(player, text);
            }
        }
    }

    /**
     * Execute creation of a crash report file.
     *
     * @param crashReport The generated crash report
     */
    protected void executeCreateFile(String crashReport) {
        String fileName = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH.mm.ss").format(LocalDateTime.now());
        File file = new File(plugin.getDataFolder(), "crash-reports/crash-" + fileName + ".txt");
        FileIO.saveFile(file, new ByteArrayInputStream(crashReport.getBytes()), false, true);
    }

    /**
     * Helper method for retrieving the String form of a {@link CrashReportSection}
     *
     * @param section The {@link CrashReportSection} to get
     * @return The section as a String
     */
    private static String getSectionString(CrashReportSection section) {
        return section.getString().replace("\n", "\n\t");
    }

    /**
     * Helper method to format a stack trace to a String
     *
     * @param elements The array of stack trace elements
     * @return The formatted String
     */
    protected static String formatStackTrace(StackTraceElement[] elements) {
        final StringBuilder builder = new StringBuilder();
        for(StackTraceElement element : elements) {
            builder.append("\tat ").append(element.toString()).append("\n");
        }
        return builder.toString();
    }

    /**
     * Helper method for formatting a block location
     *
     * @param location The location to format
     * @return The formatted String
     */
    protected static String formatBlockLocation(Location location) {
        return location.getBlockX() + "," + location.getBlockY() + "," + location.getBlockZ();
    }

    /**
     * Helper method for formatting a location
     *
     * @param location The location to format
     * @return The formatted String
     */
    protected static String formatLocation(Location location) {
        return String.format("%.2f,%.2f,%.2f", location.getX(), location.getY(), location.getZ());
    }

    /**
     * Helper method for formatting a list of entities
     *
     * @param entities The entities to be formatted
     * @return The formatted String
     */
    protected static String formatEntities(List<? extends Entity> entities) {
        final StringBuilder builder = new StringBuilder();
        for(int i = 0; i < entities.size(); ++i) {
            Entity entity = entities.get(i);
            builder.append(formatEntity(entity));
            if(i < entities.size() - 1) builder.append(", ");
        }
        return builder.toString();
    }

    /**
     * Helper method for formatting an entity
     *
     * @param entity The entity to be formatted
     * @return The formatted String
     */
    protected static String formatEntity(Entity entity) {
        return String.format(
            "%s(uuid=%s, world=%s, location=%s)",
            entity.getName(), entity.getUniqueId(), entity.getWorld().getName(), formatLocation(entity.getLocation()));
    }

    /**
     * Get the map of details that will be included with the crash report
     *
     * @return The map of details
     */
    public Map<String, String> getDetails() {
        return details;
    }

    /**
     * Get a detail of this crash report based off of the name of the detail.
     *
     * @param name The name of the detail to retrieve
     * @return The retrieved detail
     */
    public String getDetail(String name) {
        return details.get(name);
    }

    /**
     * Get the map of sections included in this crash report
     *
     * @return The map of sections
     */
    public Map<String, CrashReportSection> getSections() {
        return sections;
    }

    /**
     * Get a section of this crash report based off of the name of the section.
     *
     * @param name The name of the section to retrieve
     * @return The retrieved section
     */
    public CrashReportSection getSection(String name) {
        return sections.get(name);
    }

    /**
     * Get information about the crash. Printed to console after the crash report and to players if
     * <code>notifyOps</code> is true. Should include details such as a generic statement about what happened and where
     * to report the crash report.
     * <p>
     * This is <strong>NOT</strong> the crash report! This is helper information printed after the crash report to aid
     * users in submitting a bug report.
     *
     * @return The crash information
     */
    public List<Text> getCrashInfo() {
        return crashInfo;
    }

    /**
     * Get the throwable of this crash report.
     *
     * @return The throwable, null if it doesn't exist
     */
    public @Nullable Throwable getThrowable() {
        return throwable;
    }

    /**
     * Set the throwable of this crash report
     *
     * @param throwable The throwable
     * @return This crash report
     */
    public CrashReport setThrowable(@Nullable Throwable throwable) {
        this.throwable = throwable;
        return this;
    }

    /**
     * Whether this crash report notifies players with OP of the crash
     *
     * @return Whether this crash report notifies players with OP
     */
    public boolean notifyOps() {
        return notifyOps;
    }

    /**
     * Set whether this crash report notifies players with OP of the crash
     *
     * @param notifyOps The new notify OPs state
     */
    public void setNotifyOps(boolean notifyOps) {
        this.notifyOps = notifyOps;
    }

    /**
     * Get whether to create a file in <code>plugins/plugin_name/crash-reports</code>
     *
     * @return Whether to create a file
     */
    public boolean isCreateFile() {
        return createFile;
    }

    /**
     * Set whether to create a file in <code>plugins/plugin_name/crash-reports</code>
     *
     * @param createFile New create file state
     */
    public void setCreateFile(boolean createFile) {
        this.createFile = createFile;
    }
}
