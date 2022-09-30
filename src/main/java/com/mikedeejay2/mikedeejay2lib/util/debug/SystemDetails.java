package com.mikedeejay2.mikedeejay2lib.util.debug;

import java.lang.management.ManagementFactory;
import java.util.List;
import java.util.stream.Collectors;

public class SystemDetails {
    public static final String OPERATING_SYSTEM = String.format(
        "%s (%s) version %s", System.getProperty("os.name"), System.getProperty("os.arch"), System.getProperty("os.version"));
    public static final String JAVA_VERSION = String.format(
        "%s, %s", System.getProperty("java.version"), System.getProperty("java.vendor"));
    public static final String JAVA_VM_VERSION = String.format(
        "%s (%s), %s", System.getProperty("java.vm.name"), System.getProperty("java.vm.info"), System.getProperty("java.vm.vendor"));

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

    public static String getProcessorCount() {
        return String.valueOf(Runtime.getRuntime().availableProcessors());
    }

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
