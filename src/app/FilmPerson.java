package app;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.sql.*;

public class FilmPerson extends DBConn {
    private int id, birthYear;
    private String name, country;
    private boolean director, actor, author;
    public FilmPerson(int id, String name, int birthYear, String country, boolean director, boolean actor, boolean author){
        this.id = id;
        this.name = name;
        this.birthYear = birthYear;
        this.country = country;
        this.director = director;
        this.actor = actor;
        this.author = author;
    }
    public FilmPerson(String name, int birthYear, String country, boolean director, boolean actor, boolean author){
        this.id = uniqueID("PID", "FilmPerson");
        this.name = name;
        this.birthYear = birthYear;
        this.country = country;
        this.director = director;
        this.actor = actor;
        this.author = author;
        try{
            PreparedStatement regStatement = conn.prepareStatement("INSERT INTO FilmPerson VALUES ( (?), (?), (?), (?), (?), (?), (?) )");
            regStatement.setInt(1, id);
            regStatement.setString(2, name);
            regStatement.setInt(3, birthYear);
            regStatement.setString(4, country);
            regStatement.setBoolean(5, director);
            regStatement.setBoolean(6, actor);
            regStatement.setBoolean(7, author);
            regStatement.execute();
        }catch(Exception e){
            System.out.println("Database error when inserting film person:\n" + e);
        }
    }
    public int getID(){
        return id;
    }
    public String getName(){
        return name;
    }
    public int getBirthYear(){
        return birthYear;
    }
    public String getCountry(){
        return country;
    }
    public boolean isDirector(){
        return director;
    }
    public boolean isActor(){
        return actor;
    }
    public boolean isAuthor(){
        return author;
    }
}