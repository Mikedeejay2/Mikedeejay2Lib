package com.mikedeejay2.mikedeejay2lib.data.sql.table;

import com.mikedeejay2.mikedeejay2lib.data.sql.column.SQLIColumn;
import com.mikedeejay2.mikedeejay2lib.data.sql.misc.SQLColumnInfo;

import java.sql.ResultSet;

public interface SQLITable
{
    SQLIColumn[] getColumns();
    void renameTable(String newName);
    SQLIColumn getColumn(String columnName);
    SQLIColumn getColumn(int index);
}
