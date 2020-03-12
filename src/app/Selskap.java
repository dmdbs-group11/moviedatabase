package app;

import java.sql.*;

public class Selskap extends DBConn {

    private int selskapsID;
    private String selskapsNavn;
    private String url;
    private String adresse;
    private String land;
    private PreparedStatement regStatement;

    public Selskap(int selskapsID, String selskapsNavn, String url, String adresse, String land){
        this.selskapsID = selskapsID;
        this.selskapsNavn = selskapsNavn;
        this.url = url;
        this.adresse = adresse;
        this.land = land;
    }
    public Selskap(String selskapsNavn, String url, String adresse, String land){
        this.selskapsNavn = selskapsNavn;
        this.url = url;
        this.adresse = adresse;
        this.land = land;
        this.selskapsID = uniqueID("SelskapsID", "Selskap");
        try{
            regStatement = conn.prepareStatement("INSERT INTO Selskap VALUES ( (?), (?), (?), (?), (?))");
            regStatement.setInt(1, selskapsID);
            regStatement.setString(2, selskapsNavn);
            regStatement.setString(3, url);
            regStatement.setString(4, adresse);
            regStatement.setString(5, land);
            regStatement.execute();
        }catch(Exception e){
            System.out.println("Database error when inserting review:\n" + e);
        }
    }
    public int getID(){
        return selskapsID;
    }
    public String getName(){
        return selskapsNavn;
    }
    public String getURL(){
        return url;
    }
    public String getAddress(){
        return adresse;
    }
    public String getCountry(){
        return land;
    }
}