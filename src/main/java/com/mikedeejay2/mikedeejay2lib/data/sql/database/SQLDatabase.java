package com.mikedeejay2.mikedeejay2lib.data.sql.database;

import com.mikedeejay2.mikedeejay2lib.PluginBase;
import com.mikedeejay2.mikedeejay2lib.data.sql.misc.SQLColumnInfo;
import com.mikedeejay2.mikedeejay2lib.data.sql.misc.SQLType;
import com.mikedeejay2.mikedeejay2lib.data.sql.connector.MySQLConnection;
import com.mikedeejay2.mikedeejay2lib.data.sql.connector.SQLConnection;
import com.mikedeejay2.mikedeejay2lib.data.sql.connector.SQLiteConnection;
import com.mikedeejay2.mikedeejay2lib.data.sql.table.SQLITable;
import com.mikedeejay2.mikedeejay2lib.data.sql.table.SQLTable;

import java.sql.*;

public class SQLDatabase implements SQLIDatabase
{
    protected final PluginBase plugin;
    protected final SQLType type;
    protected SQLConnection connection;
    protected String host;
    protected int port;
    protected String username;
    protected String password;
    protected String database;

    public SQLDatabase(PluginBase plugin, SQLType type)
    {
        this.plugin = plugin;
        this.type = type;
    }

    public void setInfo(String host, int port, String username, String password, String database)
    {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
        this.database = database;
    }

    public void setInfo(String database)
    {
        this.database = database;
    }

    public void connect()
    {
        if(isConnected()) return;
        switch(type)
        {
            case MYSQL:
                if(connection == null) this.connection = new MySQLConnection(host, port, username, password, database, plugin);
                connection.connect();
                break;
            case SQLITE:
                if(connection == null) this.connection = new SQLiteConnection(database, plugin);
                connection.connect();
                break;
        }
    }

    public void disconnect()
    {
        if(!isConnected()) return;
        connection.disconnect();
    }

    public boolean isConnected()
    {
        return connection != null && connection.isConnected();
    }

    public SQLConnection getSQLConnection()
    {
        return connection;
    }

    public Connection getConnection()
    {
        return connection.getConnection();
    }

    public SQLType getType()
    {
        return type;
    }

    public String getHost()
    {
        return host;
    }

    public int getPort()
    {
        return port;
    }

    public String getUsername()
    {
        return username;
    }

    public String getPassword()
    {
        return password;
    }

    public String getDatabaseName()
    {
        return database;
    }

    @Override
    public SQLITable getTable(String tableName)
    {
        SQLTable table = new SQLTable(this, tableName);
        return table;
    }

    @Override
    public SQLITable createTable(String tableName, SQLColumnInfo... info)
    {
        return null;
    }

    @Override
    public void removeTable(String tableName)
    {

    }

    @Override
    public void removeTable(SQLITable table)
    {

    }

    @Override
    public int executeUpdate(String command)
    {
        try
        {
            PreparedStatement statement = this.getConnection().prepareStatement(command);
            return statement.executeUpdate();

        }
        catch(SQLException throwables)
        {
            throwables.printStackTrace();
            return -1;
        }
    }

    @Override
    public ResultSet executeQuery(String command)
    {
        try
        {
            PreparedStatement statement = this.getConnection().prepareStatement(command);
            return statement.executeQuery();

        }
        catch(SQLException throwables)
        {
            throwables.printStackTrace();
            return null;
        }
    }

    @Override
    public DatabaseMetaData getMetaData()
    {
        try
        {
            return this.getConnection().getMetaData();
        }
        catch(SQLException throwables)
        {
            throwables.printStackTrace();
            return null;
        }
    }

    @Override
    public ResultSet getTables()
    {
        try
        {
            return this.getMetaData().getTables(null, null, null, new String[]{"TABLE"});
        }
        catch(SQLException throwables)
        {
            throwables.printStackTrace();
            return null;
        }
    }

    @Override
    public ResultSet getSystemTables()
    {
        try
        {
            return this.getMetaData().getTables(null, null, null, new String[]{"SYSTEM TABLE"});
        }
        catch(SQLException throwables)
        {
            throwables.printStackTrace();
            return null;
        }
    }
}
