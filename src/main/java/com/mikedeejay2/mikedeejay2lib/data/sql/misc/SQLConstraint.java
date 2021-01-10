package com.mikedeejay2.mikedeejay2lib.data.sql.misc;

public enum SQLConstraint
{
    // Ensures that a column can not contain a NULL value
    NOT_NULL("NOT NULL"),
    // Ensures that each value in a column are different
    UNIQUE("UNIQUE"),
    // Defines the column that uniquely identifies each row. NOT NULL and UNIQUE
    PRIMARY_KEY("PRIMARY KEY", true),
    // Defines a record in another table
    FOREIGN_KEY("FOREIGN KEY", true),
    // Ensures that all records in a column pass a condition
    CHECK("CHECK", true, true),
    // Sets a default value for a column when no value is specified
    DEFAULT("DEFAULT"),
    // Whether to auto increment the column
    AUTO_INCREMENT("AUTO_INCREMENT")
    ;

    private final String value;
    private final boolean end;
    private final boolean useExtra;

    SQLConstraint(String value, boolean end, boolean useExtra)
    {
        this.value = value;
        this.end = end;
        this.useExtra = useExtra;
    }

    SQLConstraint(String value, boolean end)
    {
        this(value, end, false);
    }

    SQLConstraint(String value)
    {
        this(value, false, false);
    }

    public String get()
    {
        return value;
    }

    public boolean atEnd()
    {
        return end;
    }

    public boolean useExtra()
    {
        return useExtra;
    }
}
