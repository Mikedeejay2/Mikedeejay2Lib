package com.mikedeejay2.mikedeejay2lib.data.sql.misc;

public enum SQLConstraint
{
    // Ensures that a column can not contain a NULL value
    NOT_NULL("NOT NULL"),
    // Ensures that each value in a column are different
    UNIQUE("UNIQUE"),
    // Defines the column that uniquely identifies each row. NOT NULL and UNIQUE
    PRIMARY_KEY("PRIMARY KEY"),
    // Defines a record in another table
    FOREIGN_KEY("FOREIGN KEY"),
    // Ensures that all records in a column pass a condition
    CHECK("CHECK"),
    // Sets a default value for a column when no value is specified
    DEFAULT("DEFAULT"),
    // Used to create an index in a table
    INDEX("INDEX"),
    ;

    private final String value;

    SQLConstraint(String value)
    {
        this.value = value;
    }

    public String get()
    {
        return value;
    }
}
