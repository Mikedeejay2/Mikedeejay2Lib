package com.mikedeejay2.mikedeejay2lib.data.sql.access;

import com.mikedeejay2.mikedeejay2lib.PluginBase;
import com.mikedeejay2.mikedeejay2lib.data.sql.SQLType;
import com.mikedeejay2.mikedeejay2lib.data.sql.connector.MySQLConnection;
import com.mikedeejay2.mikedeejay2lib.data.sql.connector.SQLConnection;

public class SQLAccessor
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
    protected boolean connected;

    public SQLAccessor(PluginBase plugin, SQLType type)
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

    public void connect()
    {
        switch(type)
        {
            case MYSQL:
                this.connection = new MySQLConnection(host, port, username, password, database, plugin);
                connection.connect();
                this.connected = true;
                break;
            case SQLITE:
                break;
        }
    }

    public void disconnect()
    {

    }
}
