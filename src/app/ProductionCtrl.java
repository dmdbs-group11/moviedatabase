package app;

import java.sql.*;
import java.util.Map;
import java.util.HashMap;

public class ProductionCtrl extends DBConn{
    public Map<String, String> fetchGenreLeadingCompanies(){
        Map<String, String> genreLeadingCompanies = new HashMap<>();
        try{
            Statement statement = conn.createStatement();
            String query = "";
        }catch(Exception e){
            System.out.println("Database error when selecting for genre leading companies\n" + e.getMessage());
        }
    }
}