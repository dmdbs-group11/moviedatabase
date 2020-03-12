package app;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class ReviewCtrl extends DBConn{
    public void giveReview(String Tekst, Integer Rating, Integer BID, Integer SerieID, Integer SesongID, Integer FilmID){
        int anmeldelsesID = uniqueID("AnmeldelsesID", "Anmeldelse");
        insertReview(anmeldelsesID, Tekst, Rating, BID);
        if (SerieID != null){
            insertSerieAnmeldelse(anmeldelsesID, SerieID);
        }
        else if(SesongID != null){
            insertSesongAnmeldelse(anmeldelsesID, SesongID);
        }
        else if(FilmID != null){
            insertFilmAnmeldelse(anmeldelsesID, FilmID);
        }else{    
            System.out.println("Nå har du failet (i livet)");
        }

    }

    public void insertReview(Integer AnmeldelsesID, String Tekst, Integer Rating, Integer BID){
        try{
            PreparedStatement regstatement = conn.prepareStatement("insert into Anmeldelse values( (?), (?), (?), (?))");
            regstatement.setInt(1, AnmeldelsesID);
            regstatement.setString(2, Tekst);
            regstatement.setInt(3, Rating);
            regstatement.setInt(4, BID);
            regstatement.execute();
        }catch(Exception e){
            System.out.println("Database error when inserting review:\n" + e);
        }
    }

    public void insertSerieAnmeldelse(Integer AnmeldelsesID, Integer SerieID){
        try{
            PreparedStatement regstatement = conn.prepareStatement("insert into SerieAnmeldelse values(?, ?)");  
                regstatement.setInt(1, AnmeldelsesID);
                regstatement.setInt(2, SerieID);
            regstatement.execute();
        }catch(Exception e){
        System.out.println("Database error when inserting review relation:\n" + e);
        }
    }

    public void insertSesongAnmeldelse(Integer AnmeldelsesID, Integer SesongID){
        try{
            PreparedStatement regstatement = conn.prepareStatement("insert into SesongAnmeldelse values(?, ?)");  
                regstatement.setInt(1, AnmeldelsesID);
                regstatement.setInt(2, SesongID);
            regstatement.execute();
        }catch(Exception e){
        System.out.println("Database error when inserting review relation:\n" + e);
        }
    }
    public void insertFilmAnmeldelse(Integer AnmeldelsesID, Integer FilmID){
        try{
            PreparedStatement regstatement = conn.prepareStatement("insert into FilmAnmeldelse values(?, ?)");  
                regstatement.setInt(1, AnmeldelsesID);
                regstatement.setInt(2, FilmID);
            regstatement.execute();
        }catch(Exception e){
        System.out.println("Database error when inserting review relation:\n" + e);
        }
    }
    public Map<String, Integer> fetchEpisodeTitles(int seasonID){
        Map<String, Integer> episodeTitles = new HashMap<>();
        try{
            Statement statement = conn.createStatement();   
            String query = "select FilmID, Tittel from Film where SesongID=" + seasonID;
            ResultSet result = statement.executeQuery(query);
            while(result.next()){
                episodeTitles.put(result.getString("Tittel"), Integer.parseInt(result.getString("FilmID")));
            }
        }catch(Exception e){
            System.out.println("Database error when fetching movie titles:\n" + e);
        }
        return episodeTitles;
    }
    public Map<String, Integer> fetchSeasonTitles(int seriesID){
        Map<String, Integer> seasonTitles = new HashMap<>();
        try{
            Statement statement = conn.createStatement();   
            String query = "select SesongID, Tittel from Sesong where SerieID=" + seriesID;
            ResultSet result = statement.executeQuery(query);
            while(result.next()){
                seasonTitles.put(result.getString("Tittel"), Integer.parseInt(result.getString("SesongID")));
            }
        }catch(Exception e){
            System.out.println("Database error when fetching season titles:\n" + e);
        }
        return seasonTitles;
    }
    public Map<String, Integer> fetchSeriesTitles(){
        Map<String, Integer> seriesTitles = new HashMap<>();
        try{
            Statement statement = conn.createStatement();   
            String query = "select SerieID, Tittel from Serie";
            ResultSet result = statement.executeQuery(query);
            while(result.next()){
                seriesTitles.put(result.getString("Tittel"), Integer.parseInt(result.getString("SerieID")));
            }
        }catch(Exception e){
            System.out.println("Database error when fetching series titles:\n" + e);
        }
        return seriesTitles;
    }
}