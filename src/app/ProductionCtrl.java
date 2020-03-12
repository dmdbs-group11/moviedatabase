package app;

import java.sql.*;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProductionCtrl extends DBConn{
    public Map<String,String> fetchGenres(){
        Map<String,String> genres = new HashMap<>();
        try{
            Statement statement = conn.createStatement();   
            String query = "select KategoriID, KategoriNavn from Kategori";
            ResultSet result = statement.executeQuery(query);
            while(result.next()){
                genres.put(result.getString("KategoriID"),result.getString("KategoriNavn"));
            }
        }catch(Exception e){
            System.out.println("Database error when fetching all genres:\n" + e);
        }
        return genres;
    }

    public String fetchOneGenreLeadingCompany(String kategoriID){
        String selskap = "";

        try{
            Statement statement = conn.createStatement();
            String query = "select Selskapsnavn from (Selskap natural join (Film natural join Filmkategori)) where Filmkategori.KategoriID ='" + kategoriID + "'";
            ResultSet result = statement.executeQuery(query);
            List<String> companyList = new ArrayList<>();
            while(result.next()){
                companyList.add(result.getString("Selskapsnavn"));
            }
            Map<String, Integer> frequencymap = new HashMap<>();
            for(String s : companyList) {
                if(frequencymap.containsKey(s)) {
                  frequencymap.put(s, frequencymap.get(s)+1);
                }
                else{frequencymap.put(s, 1); }
              }
            
            boolean first = true;
            selskap = "";
            Integer currNumber = 0;

            for(String key : frequencymap.keySet()){
                if (first){
                    selskap = key;
                    currNumber = frequencymap.get(key);
                    first = false;
                }
                if (frequencymap.get(key)>currNumber){
                    selskap = key;
                    currNumber = frequencymap.get(key);
                }

             }
        }catch(Exception e){
            System.out.println("Database error when selecting for genre leading companies\n" + e.getMessage());
        }
        return selskap;
    }
    

    public Map<String, String> fetchGenreLeadingCompanies(){
        Map<String, String> genreLeadingCompanies = new HashMap<>();
        Map<String,String> allGenres = fetchGenres();
        for (String kategoriID : allGenres.keySet()){
            String selskap = fetchOneGenreLeadingCompany(kategoriID);
            genreLeadingCompanies.put(allGenres.get(kategoriID), selskap);
        }

        return genreLeadingCompanies;
    }

    public List<Sesong> fetchSeasons(){
        List<Sesong> seasons = new ArrayList<>();
        try{
            Statement statement = conn.createStatement();   
            String query = "select * from Sesong";
            ResultSet result = statement.executeQuery(query);
            while(result.next()){
                seasons.add(new Sesong(Integer.parseInt(result.getString("SesongID")), result.getString("Tittel"), result.getString("Storyline"), Integer.parseInt(result.getString("SerieID"))));
            }
        }catch(Exception e){
            System.out.println("Database error when fetching all companies:\n" + e);
        }
        return seasons;
    }

    public List<Selskap> fetchCompanies(){
        List<Selskap> companies = new ArrayList<>();
        try{
            Statement statement = conn.createStatement();   
            String query = "select * from Selskap";
            ResultSet result = statement.executeQuery(query);
            while(result.next()){
                companies.add(new Selskap(Integer.parseInt(result.getString("SelskapsID")), result.getString("Selskapsnavn"), result.getString("URL"), result.getString("adresse"), result.getString("land")));
            }
        }catch(Exception e){
            System.out.println("Database error when fetching all companies:\n" + e);
        }
        return companies;
    }
}