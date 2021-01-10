package com.mikedeejay2.mikedeejay2lib.data.sql.column;

import com.mikedeejay2.mikedeejay2lib.data.sql.misc.SQLConstraint;
import com.mikedeejay2.mikedeejay2lib.data.sql.misc.SQLDataType;

public class SQLColumnInfo
{
    protected final SQLDataType type;
    protected final String name;
    protected final int[] sizes;
    protected final SQLConstraint[] constraints;
    protected final String extra;

    public SQLColumnInfo(SQLDataType type, String name, int[] sizes, SQLConstraint[] constraints, String extra)
    {
        this.type = type;
        this.name = name;
        this.sizes = sizes;
        this.constraints = constraints;
        this.extra = extra;
    }

    public SQLDataType getType()
    {
        return type;
    }

    public String getName()
    {
        return name;
    }

    public int[] getSizes()
    {
        return sizes;
    }

    public SQLConstraint[] getConstraints()
    {
        return constraints;
    }

    public String getExtra()
    {
        return extra;
    }
}
