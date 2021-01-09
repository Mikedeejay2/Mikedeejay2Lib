package com.mikedeejay2.mikedeejay2lib.data.sql.access;

import com.mikedeejay2.mikedeejay2lib.PluginBase;
import com.mikedeejay2.mikedeejay2lib.data.sql.SQLType;
import com.mikedeejay2.mikedeejay2lib.data.sql.connector.MySQLConnection;
import com.mikedeejay2.mikedeejay2lib.data.sql.connector.SQLConnection;
import com.mikedeejay2.mikedeejay2lib.data.sql.connector.SQLiteConnection;

public class SQLDatabase
{
    protected final PluginBase plugin;
    protected final SQLType type;
    protected String name;
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
}
