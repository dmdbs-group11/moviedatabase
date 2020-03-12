package app;

import java.sql.PreparedStatement;

public class Sesong extends DBConn {

    private int sesongID;
    private String tittel;
    private String storyline;
    private int serieID;
    private PreparedStatement regStatement;

    public Sesong(int sesongID, String tittel, String storyline, int serieID){
        this.sesongID = sesongID;
        this.tittel = tittel;
        this.storyline = storyline;
        this.serieID = serieID;
    }

    public Sesong(String tittel, String storyline, int serieID){
        this.tittel = tittel;
        this.storyline = storyline;
        this.serieID = serieID;
        this.sesongID = uniqueID("SesongID", "Sesong");
        try{
            regStatement = conn.prepareStatement("INSERT INTO Sesong VALUES ( (?), (?), (?), (?) ");
            regStatement.setInt(1, sesongID);
            regStatement.setString(2, tittel);
            regStatement.setString(3, storyline);
            regStatement.setInt(4, serieID);
            regStatement.execute();
        }catch(Exception e){
            System.out.println("Database error when inserting season:\n" + e);
        }
    }

    public int getID(){
        return this.sesongID;
    }
    public String getTitle(){
        return tittel;
    }
    public String getStoryline(){
        return storyline;
    }
    public int getSeriesID(){
        return serieID;
    }
}