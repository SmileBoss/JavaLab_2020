package ru.itis;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SimpleDataSource {
    public Connection openConnection (String url, String user, String pass){
        try {
            return DriverManager.getConnection(url, user, pass);
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
