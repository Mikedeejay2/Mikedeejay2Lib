package com.mikedeejay2.mikedeejay2lib.data.sql.connector;

import java.sql.Connection;

public interface SQLConnection
{
    boolean connect();

    boolean disconnect();

    Connection getConnection();

    boolean isConnected();
}
