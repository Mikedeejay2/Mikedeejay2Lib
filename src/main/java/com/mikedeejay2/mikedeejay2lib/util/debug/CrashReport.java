package com.mikedeejay2.mikedeejay2lib.util.debug;

import com.mikedeejay2.mikedeejay2lib.BukkitPlugin;
import com.mikedeejay2.mikedeejay2lib.text.PlaceholderFormatter;
import com.mikedeejay2.mikedeejay2lib.text.Text;
import com.mikedeejay2.mikedeejay2lib.util.time.FormattedTime;
import org.apache.commons.lang3.Validate;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Crash report system. Basic setup:
 * <ul>
 *     <li>Add details with {@link CrashReport#addDetail(Text)}. Default details include time, Minecraft version, and
 *     server implementation.</li>
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

    public CrashReport(BukkitPlugin plugin, boolean notifyOps, String description) {
        this.plugin = plugin;
        this.notifyOps = notifyOps;
        this.details = new LinkedHashMap<>();
        addDetail("Time", FormattedTime.getTime());
        addDetail("Description", description);
    }

    public CrashReport addDetail(String name, String detail) {
        Validate.notNull(name, "Attempted to add null detail name to crash report");
        Validate.notNull(detail, "Attempted to add null detail to crash report");
        details.put(name, detail);
        return this;
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
            builder.append(throwable).append("\n");
            for(StackTraceElement element : throwable.getStackTrace()) {
                builder.append("\t at ").append(element.toString()).append("\n");
            }
        }
        builder.append("\n");

        builder.append("A detailed walkthrough of the error, its code path and all known details is as follows:\n");
        builder.append("---------------------------------------------------------------------------------------\n\n");

        return builder.toString();
    }
}
