package com.mikedeejay2.mikedeejay2lib.data.sql.database;

import com.mikedeejay2.mikedeejay2lib.data.sql.misc.SQLColumnInfo;
import com.mikedeejay2.mikedeejay2lib.data.sql.table.SQLITable;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;

public interface SQLIDatabase
{
    SQLITable getTable(String tableName);
    SQLITable createTable(String tableName, SQLColumnInfo... info);
    void removeTable(String tableName);
    void removeTable(SQLITable table);
    void renameTable(String tableName, String newName);
    void renameTable(SQLITable tableName, String newName);
    int executeUpdate(String command);
    ResultSet executeQuery(String command);
    DatabaseMetaData getMetaData();
    ResultSet getTables();
    ResultSet getSystemTables();
}
