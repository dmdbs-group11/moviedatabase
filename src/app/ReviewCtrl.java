package app;

import java.sql.PreparedStatement;

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
}