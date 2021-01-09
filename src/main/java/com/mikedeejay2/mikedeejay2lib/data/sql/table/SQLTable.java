package com.mikedeejay2.mikedeejay2lib.data.sql.table;

import com.mikedeejay2.mikedeejay2lib.data.sql.column.SQLIColumn;
import com.mikedeejay2.mikedeejay2lib.data.sql.database.SQLDatabase;

public class SQLTable implements SQLITable
{
    protected final SQLDatabase database;
    protected String name;

    public SQLTable(SQLDatabase database, String name)
    {
        this.database = database;
        this.name = name;
    }

    @Override
    public SQLIColumn[] getColumns()
    {
        return new SQLIColumn[0];
    }

    @Override
    public void renameTable(String newName)
    {

    }

    @Override
    public SQLIColumn getColumn(String columnName)
    {
        return null;
    }

    @Override
    public SQLIColumn getColumn(int index)
    {
        return null;
    }
}
