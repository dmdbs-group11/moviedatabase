package app;

import java.sql.*;
import java.util.Properties;

public abstract class DBConn{
    protected Connection conn;
    public void connect(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Properties p = new Properties();
            p.put("user", "root");
            p.put("password", "sommer0norD");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/moviedatabase?autoReconnect=true&useSSL=false", p);
        } catch (Exception e){
            System.out.println(e);
        }
    }
}