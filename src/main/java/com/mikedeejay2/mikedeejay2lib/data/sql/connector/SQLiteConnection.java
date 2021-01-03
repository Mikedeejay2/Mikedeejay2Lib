package com.mikedeejay2.mikedeejay2lib.data.sql.connector;

import com.mikedeejay2.mikedeejay2lib.PluginBase;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;

public class SQLiteConnection implements SQLConnection
{
    protected final PluginBase plugin;
    protected Connection connection;
    protected String database;

    public SQLiteConnection(String database, PluginBase plugin)
    {
        this.plugin = plugin;
        this.database = database;
    }

    @Override
    public synchronized boolean connect()
    {
        if(isConnected()) return false;
        File dbFile = new File(plugin.getDataFolder(), database + ".db");
        try
        {

            if(!dbFile.exists())
            {
                dbFile.getParentFile().mkdirs();
                dbFile.createNewFile();
            }
        }
        catch(Exception e)
        {
            plugin.getLogger().log(Level.SEVERE, "The file \"" + dbFile.getName() + "\" could not be loaded!", e);
        }

        try
        {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite" + dbFile);

            plugin.getLogger().severe(plugin.langManager().getTextLib(
                    "sql.sqlite.success",
                    new String[]{"NAME"},
                    new String[]{database}));
            return true;
        }
        catch(Exception e)
        {
            plugin.getLogger().severe(plugin.langManager().getTextLib(
                    "sql.sqlite.errors.unable_to_connect",
                    new String[]{"NAME"},
                    new String[]{database}));
        }
        return false;
    }

    @Override
    public synchronized boolean disconnect()
    {
        if(!isConnected()) return false;
        try
        {
            connection.close();
            connection = null;
            return true;
        }
        catch(Exception e)
        {
            plugin.getLogger().severe(plugin.langManager().getTextLib(
                    "sql.sqlite.errors.unable_to_disconnect",
                    new String[]{"NAME"},
                    new String[]{database}));
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Connection getConnection()
    {
        return connection;
    }

    @Override
    public boolean isConnected()
    {
        try
        {
            return connection != null && !connection.isClosed();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
        return false;
    }
}
