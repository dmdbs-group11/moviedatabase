package app;

import java.sql.*;

public class ReviewCtrl extends DBConn{
    public void giveReview(String AnmeldelsesID, String Tekst, String Rating, String BID, String SerieID, String SesongID, String FilmID){
        insertReview(AnmeldelsesID, Tekst, Rating, BID);
        insertRelation(AnmeldelsesID, SerieID, SesongID, FilmID);
    }

    public void insertReview(String AnmeldelsesID, String Tekst, String Rating, String BID){
        try{
            Statement statement = conn.createStatement();   
            String query = "insert into Anmeldelse values(" + AnmeldelsesID + ", '" + Tekst + "', " + Rating + ", " + BID + ")";
            statement.executeQuery(query);
        }catch(Exception e){
            System.out.println("Database error when inserting review:\n" + e);
        }
    }

    public void insertRelation(String AnmeldelsesID, String SerieID, String SesongID, String FilmID){
        try{
            Statement statement = conn.createStatement();
            String query = "";
            if (SerieID != null){
                query = "insert into SerieAnmeldelse values(" + AnmeldelsesID + ", " + SerieID + ")";
            }
            else if(SesongID != null){
                query = "insert into SesongAnmeldelse values(" + AnmeldelsesID + ", " + SesongID + ")";
            } 
            else if(FilmID != null){
                query = "insert into FilmAnmeldelse values(" + AnmeldelsesID + ", " + FilmID + ")";
            } 
            else{
                System.out.print("Nå har du gjort noe feil her");
            }
            statement.executeQuery(query);
        }catch(Exception e){
        System.out.println("Database error when inserting review relation:\n" + e);
    }
    }
}