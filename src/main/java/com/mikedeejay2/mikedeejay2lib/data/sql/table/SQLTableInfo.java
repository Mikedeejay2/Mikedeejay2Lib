package com.mikedeejay2.mikedeejay2lib.data.sql.table;

public class SQLTableInfo
{
    protected final String name;
    protected final SQLTableType type;

    public SQLTableInfo(String name, SQLTableType type)
    {
        this.name = name;
        this.type = type;
    }

    public String getName()
    {
        return name;
    }

    public SQLTableType getType()
    {
        return type;
    }
}
