package com.mikedeejay2.mikedeejay2lib.data.sql.table;

public enum SQLTableType
{
    TABLE("TABLE"),
    VIEW("VIEW"),
    SYSTEM_TABLE("SYSTEM TABLE"),
    GLOBAL_TEMPORARY("GLOBAL TEMPORARY"),
    LOCAL_TEMPORARY("LOCAL TEMPORARY"),
    ALIAS("ALIAS"),
    SYNONYM("SYNONYM"),
    ;

    private final String value;

    SQLTableType(String value)
    {
        this.value = value;
    }

    public String get()
    {
        return value;
    }
}
