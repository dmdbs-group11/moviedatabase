package app;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FilmPerson extends DBConn {
    private int id;
    private String name, birthYear, country;
    private boolean director, actor, author;
    public FilmPerson(int id, String name, String birthYear, String country, boolean director, boolean actor, boolean author){
        this.id = id;
        this.name = name;
        this.birthYear = birthYear;
        this.country = country;
        this.director = director;
        this.actor = actor;
        this.author = author;
    }
    public FilmPerson(String name, String birthYear, String country, boolean director, boolean actor, boolean author){
        this.name = name;
        this.birthYear = birthYear;
        this.country = country;
        this.director = director;
        this.actor = actor;
        this.author = author;
        ActorCtrl actorCtrl = new ActorCtrl();
        List<FilmPerson> filmPersons = actorCtrl.fetchFilmPersons();
        List<Integer> usedIDs = new ArrayList<>();
        for(FilmPerson filmPerson : filmPersons){
            usedIDs.add(filmPerson.getID());
        }
        this.id = Collections.max(usedIDs) + 1;
    }
    public int getID(){
        return id;
    }
    public String getName(){
        return name;
    }
    public String getBirthYear(){
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