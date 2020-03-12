package app;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

public abstract class DBConn{
    protected Connection conn;
    public void connect(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Properties p = new Properties();
            p.put("user", "root");
            p.put("password", "password");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/moviedatabase?autoReconnect=true&useSSL=false", p);
        } catch (Exception e){
            System.out.println(e);
        }
    }
    public int uniqueID(String idName, String table){
        this.connect();
        int id = 1;
        try{
            List<Integer> usedIDs = new ArrayList<>();
            Statement statement = conn.createStatement();
            String query = "select " + idName + " from " + table;
            ResultSet result = statement.executeQuery(query);
            while(result.next()){
                usedIDs.add(Integer.parseInt(result.getString(idName)));
            }
            if(!usedIDs.isEmpty()){
                id = Collections.max(usedIDs) + 1;
            }
        }catch(Exception e){
            System.out.println("Database error when fetching unique ID:\n" + e);
        }
        return id;
    }
}