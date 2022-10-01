package com.mikedeejay2.mikedeejay2lib.util.debug;

/**
 * An exception representing a crash report. This should be caught somewhere in the application to be executed.
 *
 * @author Mikedeejay2
 */
public class ReportedException extends RuntimeException {
    /**
     * The {@link CrashReport} of this exception
     */
    protected final CrashReport crashReport;

    /**
     * Construct a new <code>ReportedException</code>
     *
     * @param crashReport The {@link CrashReport} of this exception
     */
    public ReportedException(CrashReport crashReport) {
        this.crashReport = crashReport;
    }

    @Override
    public synchronized Throwable getCause() {
        return crashReport.getThrowable();
    }

    @Override
    public String getMessage() {
        return crashReport.getDetail("Description");
    }

    /**
     * Get the {@link CrashReport} of this exception
     *
     * @return The crash report
     */
    public CrashReport getCrashReport() {
        return crashReport;
    }
}
