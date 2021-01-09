package com.mikedeejay2.mikedeejay2lib.data.sql.column;

import com.mikedeejay2.mikedeejay2lib.data.sql.misc.SQLConstraint;
import com.mikedeejay2.mikedeejay2lib.data.sql.misc.SQLDataType;

public class SQLColumn implements SQLIColumn
{
    protected SQLDataType type;
    protected String name;
    protected int[] sizes;
    protected SQLConstraint[] constraints;

    public SQLColumn(SQLDataType type, String name, int[] sizes, SQLConstraint[] constraints)
    {
        this.type = type;
        this.name = name;
        this.sizes = sizes;
        this.constraints = constraints;
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
}
