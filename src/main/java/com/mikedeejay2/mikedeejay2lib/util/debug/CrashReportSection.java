package com.mikedeejay2.mikedeejay2lib.util.debug;

import org.apache.commons.lang3.Validate;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * A section of a {@link CrashReport}. Contains information about the crash report intended for use with debugging.
 *
 * @see CrashReport
 * @author Mikedeejay2
 */
public class CrashReportSection {
    /**
     * The title of this section
     */
    protected String title;
    /**
     * The details
     */
    protected Map<String, String> details;

    /**
     * Construct a new <code>CrashReportSection</code>
     *
     * @param title The title of this section
     */
    public CrashReportSection(String title) {
        this.title = title;
        this.details = new LinkedHashMap<>();
    }

    /**
     * Add a detail to this section
     *
     * @param name   The name of the detail
     * @param detail The detail as a String
     * @return This section
     */
    public CrashReportSection addDetail(String name, String detail) {
        Validate.notNull(name, "Attempted to add null detail name to crash report");
        Validate.notNull(detail, "Attempted to add null detail to crash report");
        details.put(name, detail);
        return this;
    }

    /**
     * Get this section as a String
     *
     * @return This section as a String
     */
    public String getString() {
        StringBuilder builder = new StringBuilder("-- ").append(title).append(" --\n");
        for(String detailName : details.keySet()) {
            String detail = details.get(detailName);
            builder.append(detailName).append(": ").append(detail).append("\n");
        }
        return builder.toString().trim();
    }
}
