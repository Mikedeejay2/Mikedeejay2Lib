package com.mikedeejay2.mikedeejay2lib.data.sql.table;

public enum SQLTableMeta
{
    // Table catalog
    // STRING, NULLABLE
    TABLE_CAT,
    // Table schema
    // STRING, NULLABLE
    TABLE_SCHEM,
    // Table name
    // STRING
    TABLE_NAME,
    // Table type. Typical types are "TABLE", "VIEW", "SYSTEM TABLE", "GLOBAL TEMPORARY", "LOCAL TEMPORARY", "ALIAS", "SYNONYM".
    // STRING
    TABLE_TYPE,
    // Comment of the table
    // STRING
    REMARKS,
    // The types catalog
    // STRING, NULLABLE
    TYPE_CAT,
    // The types schema
    // STRING, NULLABLE
    TYPE_SCHEM,
    // Type name
    // STRING, NULLABLE
    TYPE_NAME,
    // Name of the identifier of a typed table
    // STRING, NULLABLE
    SELF_REFERENCING_COL_NAME,
    // Specifies how values in SELF_REFERENCING_COL_NAME are created. Values are "SYSTEM", "USER", "DERIVED".
    // STRING, NULLABLE
    REF_GENERATION,
    ;
}
