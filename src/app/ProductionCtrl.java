package app;

import java.sql.*;
import java.util.Map;
import java.util.HashMap;

public class ProductionCtrl extends DBConn{
    public Map<String, String> fetchGenreLeadingCompanies(){
        Map<String, String> genreLeadingCompanies = new HashMap<>();
        try{
            Statement statement = conn.createStatement();
            String query = "select KategoriNavn, Selskapsnavn from (Selskap natural join (Film natural join (Filmkategori natural join Kategori))) where ";
            ResultSet result = statement.executeQuery(query);
            while(result.next()){
                genreLeadingCompanies.put("KategoriNavn", "Selskapsnavn");
            }
        }catch(Exception e){
            System.out.println("Database error when selecting for genre leading companies\n" + e.getMessage());
        }
        return genreLeadingCompanies;
    }
}