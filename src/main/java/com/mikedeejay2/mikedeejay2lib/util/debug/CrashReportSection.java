package com.mikedeejay2.mikedeejay2lib.util.debug;

import org.apache.commons.lang3.Validate;

import java.util.LinkedHashMap;
import java.util.Map;

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
     * @param title   The title of this section
     */
    public CrashReportSection(String title) {
        this.title = title;
        this.details = new LinkedHashMap<>();
    }

    public CrashReportSection addDetail(String name, String detail) {
        Validate.notNull(name, "Attempted to add null detail name to crash report");
        Validate.notNull(detail, "Attempted to add null detail to crash report");
        details.put(name, detail);
        return this;
    }

    public String getString() {
        StringBuilder builder = new StringBuilder("-- ").append(title).append(" --\n");
        for(String detailName : details.keySet()) {
            String detail = details.get(detailName);
            builder.append(detailName).append(": ").append(detail).append("\n");
        }
        return builder.toString();
    }
}
