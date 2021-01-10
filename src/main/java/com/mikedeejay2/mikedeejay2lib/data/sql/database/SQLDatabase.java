package com.mikedeejay2.mikedeejay2lib.data.sql.database;

import com.mikedeejay2.mikedeejay2lib.PluginBase;
import com.mikedeejay2.mikedeejay2lib.data.DataObject;
import com.mikedeejay2.mikedeejay2lib.data.sql.column.SQLColumnInfo;
import com.mikedeejay2.mikedeejay2lib.data.sql.misc.SQLConstraint;
import com.mikedeejay2.mikedeejay2lib.data.sql.misc.SQLDataType;
import com.mikedeejay2.mikedeejay2lib.data.sql.misc.SQLType;
import com.mikedeejay2.mikedeejay2lib.data.sql.connector.MySQLConnection;
import com.mikedeejay2.mikedeejay2lib.data.sql.connector.SQLConnection;
import com.mikedeejay2.mikedeejay2lib.data.sql.connector.SQLiteConnection;
import com.mikedeejay2.mikedeejay2lib.data.sql.table.SQLTable;
import com.mikedeejay2.mikedeejay2lib.data.sql.table.SQLTableMeta;
import com.mikedeejay2.mikedeejay2lib.data.sql.table.SQLTableType;

import java.sql.*;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SQLDatabase implements DataObject
{
    protected final PluginBase plugin;
    protected final SQLType type;
    protected SQLConnection connection;
    protected String host;
    protected int port;
    protected String username;
    protected String password;
    protected String databaseName;

    public SQLDatabase(PluginBase plugin, SQLType type)
    {
        this.plugin = plugin;
        this.type = type;
    }

    public void setInfo(String host, int port, String username, String password, String databaseName)
    {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
        this.databaseName = databaseName;
    }

    public void setInfo(String database)
    {
        this.databaseName = database;
    }

    public void connect()
    {
        if(isConnected()) return;
        switch(type)
        {
            case MYSQL:
                if(connection == null) this.connection = new MySQLConnection(host, port, username, password, databaseName, plugin);
                connection.connect();
                break;
            case SQLITE:
                if(connection == null) this.connection = new SQLiteConnection(databaseName, plugin);
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
        return databaseName;
    }

    public SQLTable getTable(String tableName)
    {
        try
        {
            ResultSet result = getMetaData().getTables(null, null, tableName, null);
            SQLTableType type = SQLTableType.valueOf(result.getString(SQLTableMeta.TABLE_TYPE.toString()));
            SQLTable table = new SQLTable(this, tableName, type);
            return table;
        }
        catch(SQLException throwables)
        {
            throwables.printStackTrace();
            return null;
        }
    }

    public SQLTable createTable(String tableName, SQLColumnInfo... info)
    {
        StringBuilder builder = new StringBuilder();
        builder.append("CREATE TABLE `")
               .append(tableName)
               .append("` (");

        List<Map.Entry<SQLConstraint, SQLColumnInfo>> endConstraints = new ArrayList<>();

        for(int index = 0; index < info.length; ++index)
        {
            SQLColumnInfo curInfo = info[index];
            String name = curInfo.getName();
            SQLConstraint[] constraints = curInfo.getConstraints();
            int[] sizes = curInfo.getSizes();
            SQLDataType type = curInfo.getType();

            builder.append("`")
                   .append(name)
                   .append("` ")
                   .append(type.getName());

            if(sizes.length > 0)
            {
                builder.append("(");
                for(int sizeI = 0; sizeI < sizes.length; ++sizeI)
                {
                    builder.append(sizes[sizeI]);
                    if(sizeI != sizes.length - 1) builder.append(", ");
                }
                builder.append(")");
            }

            for(SQLConstraint constraint : constraints)
            {
                if(constraint.atEnd())
                {
                    endConstraints.add(new AbstractMap.SimpleEntry<>(constraint, curInfo));
                    continue;
                }

                builder.append(" ");
                String constraintStr = constraint.get();
                builder.append(constraintStr);
            }

            if(index != info.length - 1) builder.append(", ");
        }

        for(Map.Entry<SQLConstraint, SQLColumnInfo> entry : endConstraints)
        {
            SQLConstraint constraint = entry.getKey();
            SQLColumnInfo curInfo = entry.getValue();
            String data = constraint.useExtra() ? curInfo.getExtra() : "`" + curInfo.getName() + "`";
            String name = constraint.get();
            builder.append(", ")
                   .append(name)
                   .append("(")
                   .append(data)
                   .append(")");
        }

        builder.append(");");

        String command = builder.toString();

        System.out.println(command);

        executeUpdate(command);
        SQLTable table = new SQLTable(this, tableName, SQLTableType.TABLE);
        return table;
    }

    public int executeUpdate(String command)
    {
        try
        {
            PreparedStatement statement = prepareStatement(command);
            return statement.executeUpdate();
        }
        catch(SQLException throwables)
        {
            throwables.printStackTrace();
            return -1;
        }
    }

    public ResultSet executeQuery(String command)
    {
        try
        {
            PreparedStatement statement = prepareStatement(command);
            return statement.executeQuery();

        }
        catch(SQLException throwables)
        {
            throwables.printStackTrace();
            return null;
        }
    }

    public int executeUpdate(PreparedStatement statement)
    {
        try
        {
            return statement.executeUpdate();
        }
        catch(SQLException throwables)
        {
            throwables.printStackTrace();
            return -1;
        }
    }

    public ResultSet executeQuery(PreparedStatement statement)
    {
        try
        {
            return statement.executeQuery();
        }
        catch(SQLException throwables)
        {
            throwables.printStackTrace();
            return null;
        }
    }

    public PreparedStatement prepareStatement(String command)
    {
        try
        {
            return this.getConnection().prepareStatement(command);
        }
        catch(SQLException throwables)
        {
            throwables.printStackTrace();
            return null;
        }
    }

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

    public ResultSet getTables(SQLTableType type)
    {
        try
        {
            return this.getMetaData().getTables(null, null, null, new String[]{type.get()});
        }
        catch(SQLException throwables)
        {
            throwables.printStackTrace();
            return null;
        }
    }
}
