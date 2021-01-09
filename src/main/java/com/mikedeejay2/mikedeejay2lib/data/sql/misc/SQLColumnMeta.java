package com.mikedeejay2.mikedeejay2lib.data.sql.misc;

public enum SQLColumnMeta
{
    // Table catalog
    // STRING, NULLABLE
    TABLE_CAT,
    // Table Schema
    // STRING, NULLABLE
    TABLE_SCHEM,
    // Table Name
    // STRING
    TABLE_NAME,
    // Column Name
    // STRING
    COLUMN_NAME,
    // SQL type from java.sql.Types
    // INT
    DATA_TYPE,
    // Data source dependent type name, for a UDT the type name is fully qualified
    // STRING
    TYPE_NAME,
    // int => column size.
    // INT
    COLUMN_SIZE,
    // The number of decimal places
    // INT
    DECIMAL_DIGITS,
    // A comment describing the column
    // STRING, NULLABLE
    REMARKS,
    // Default value for a column
    // STRING, NULLABLE
    COLUMN_DEF,
    // Maximum bytes of a char column
    // INT
    CHAR_OCTET_LENGTH,
    // Index of the column in the table (starts at 1)
    // INT
    ORDINAL_POSITION,
    // Whether the column can store NULL values or not (YES = true, NO = false, empty string = unknown)
    // STRING
    IS_NULLABLE,
    // Catalog of a table that is the scope of a reference attribute
    // STRING, NULL if DATA_TYPE isn't REF
    SCOPE_CATALOG,
    // Schema of table that is the scope of a reference attribute
    // STRING, NULL if DATA_TYPE isn't REF
    SCOPE_SCHEMA,
    // Table name that this the scope of a reference attribute
    // STRING, NULL if DATA_TYPE isn't REF
    SCOPE_TABLE,
    // Source type of a distinct type or user-generated Ref type, SQL type from java.sql.Types
    // SHORT, NULL if DATA_TYPE isn't DISTINCT or user-generated REF
    SOURCE_DATA_TYPE,
    // Indicates whether this column automatically increments (YES = true, NO = false, empty string = unknown)
    // STRING
    IS_AUTOINCREMENT,
    // Indicates whether this is a generated column (YES = true, NO = false, empty string = unknown)
    // STRING
    IS_GENERATEDCOLUMN,
    ;
}
