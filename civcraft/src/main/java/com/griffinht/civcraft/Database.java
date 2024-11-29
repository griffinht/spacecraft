package com.griffinht.civcraft;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database implements AutoCloseable {
    private final Connection connection;
    
    public Database(String dataFolder) throws SQLException {
        System.out.println("Connecting to database");
        connection = DriverManager.getConnection("jdbc:sqlite:" + dataFolder + "/civcraft.db");
    }
    
    public Connection getConnection() {
        return connection;
    }
    
    @Override
    public void close() throws SQLException {
        System.out.println("Closing database connection");
        connection.close();
    }
}
