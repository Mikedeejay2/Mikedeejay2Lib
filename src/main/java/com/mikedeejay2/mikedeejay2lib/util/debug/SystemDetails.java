package com.mikedeejay2.mikedeejay2lib.util.debug;

import java.lang.management.ManagementFactory;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Utility class for getting basic system details
 *
 * @author Mikedeejay2
 */
public final class SystemDetails {
    /**
     * The operating system as a String. Formatted as <code>os.name (os.arch) version os.version</code>
     */
    public static final String OPERATING_SYSTEM = String.format(
        "%s (%s) version %s", System.getProperty("os.name"), System.getProperty("os.arch"), System.getProperty("os.version"));
    /**
     * The Java version as a String. Formatted as <code>java.version, java.vendor</code>
     */
    public static final String JAVA_VERSION = String.format(
        "%s, %s", System.getProperty("java.version"), System.getProperty("java.vendor"));
    /**
     * The Java VM Version as a String. Formatted as <code>java.vm.name (java.vm.info), java.vm.vendor</code>
     */
    public static final String JAVA_VM_VERSION = String.format(
        "%s (%s), %s", System.getProperty("java.vm.name"), System.getProperty("java.vm.info"), System.getProperty("java.vm.vendor"));

    /**
     * Get the current memory as a String. Formatted as<code>freeMemory bytes (freeMemory MiB) / totalMemory bytes
     * (totalMemory MiB) up to maxMemory bytes (maxMemory MiB)</code>
     *
     * @return The formatted memory String
     */
    public static String getMemory() {
        final Runtime runtime = Runtime.getRuntime();
        final long maxMemory = runtime.maxMemory();
        final long totalMemory = runtime.totalMemory();
        final long freeMemory = runtime.freeMemory();

        return String.format("%s bytes (%s MiB) / %s bytes (%s MiB) up to %s bytes (%s MiB)",
                             freeMemory, freeMemory / 1048576L,
                             totalMemory, totalMemory / 1048576L,
                             maxMemory, maxMemory / 1048576L);
    }

    /**
     * Get the count of processor as a String
     *
     * @return The processor count as a String
     */
    public static String getProcessorCount() {
        return String.valueOf(Runtime.getRuntime().availableProcessors());
    }

    /**
     * Get the JVM flags being used currently. Formatted as <code>size total; [...]</code>
     *
     * @return The JVM flags as a String
     */
    public static String getJVMFlags() {
        final List<String> flags = ManagementFactory
            .getRuntimeMXBean()
            .getInputArguments()
            .stream()
            .filter((s) -> s.startsWith("-X"))
            .collect(Collectors.toList());

        return String.format("%d total; %s", flags.size(), String.join(" ", flags));
    }
}
