package app;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.sql.*;

public class MovieCtrl extends DBConn {
    public void newMovie(String Tittel, int Lengde, int Utgivelsesar, int Lanseringsdato, String Storyline, boolean PaVideo, Selskap Selskap, Sesong Sesong, List<FilmPerson> Regissorer, Map<FilmPerson, String> actors, List<FilmPerson> forfattere){
        int FilmID = uniqueID("FilmID", "Film");
        insertMovie(FilmID, Tittel, Lengde, Utgivelsesar, Lanseringsdato, Storyline, PaVideo, Selskap, Sesong);
        for (FilmPerson reg: Regissorer){
            insertDirector(FilmID, reg);
        }
        for (FilmPerson act: new ArrayList<>(actors.keySet())){
            insertActor(FilmID, act, actors.get(act));
        }
        for (FilmPerson auth: forfattere){
            insertAuthor(FilmID, auth);
        }
    }
    public void insertMovie(int FilmID, String Tittel, int Lengde, int Utgivelsesar, int Lanseringsdato, String Storyline, Boolean PaVideo, Selskap Selskap, Sesong Sesong){
        try{   
            int SelskapsID = Selskap.getID();
            Integer SesongID = null;
            if (Sesong != null){
                SesongID = Sesong.getID();
            }
            PreparedStatement regStatement = conn.prepareStatement("INSERT INTO Film VALUES ( (?), (?), (?), (?), (?), (?), (?), (?), (?))");
            regStatement.setInt(1, FilmID);
            regStatement.setString(2, Tittel);
            regStatement.setInt(3, Lengde);
            regStatement.setInt(4, Utgivelsesar);
            regStatement.setInt(5, Lanseringsdato);
            regStatement.setString(6, Storyline);
            regStatement.setBoolean(7, PaVideo);
            if(Sesong != null){
                regStatement.setInt(8, SesongID);
            }else{
                regStatement.setNull(8, Types.INTEGER);
            }
            regStatement.setInt(9, SelskapsID);
            regStatement.execute();
        }catch(Exception e){
            System.out.println("Database error when inserting movie:\n" + e);
        }
    }

    public void insertDirector(int FilmID, FilmPerson Regissor){
        try{
            int PID = Regissor.getID();
            PreparedStatement regStatement = conn.prepareStatement("INSERT INTO RegissorTilFilm VALUES( (?), (?))");
            regStatement.setInt(1, PID);
            regStatement.setInt(2, FilmID);
            regStatement.execute();
        }catch(Exception e){
            System.out.println("Database error when inserting director:\n" + e);
        }
    }

    public void insertActor(int FilmID, FilmPerson Skuespiller, String Rolle){
        try{
            int PID = Skuespiller.getID();
            PreparedStatement regStatement = conn.prepareStatement("INSERT INTO RolleiFilm VALUES( (?), (?), (?))");
            regStatement.setInt(1, PID);
            regStatement.setInt(2, FilmID);
            regStatement.setString(3, Rolle);
            regStatement.execute();
        }catch(Exception e){
            System.out.println("Database error when inserting actor:\n" + e);
        }
    }
    public void insertAuthor(int FilmID, FilmPerson Forfatter){
        try{
            int PID = Forfatter.getID();
            PreparedStatement regStatement = conn.prepareStatement("INSERT INTO ForfatteriFilm VALUES( (?), (?))");
            regStatement.setInt(1, PID);
            regStatement.setInt(2, FilmID);
            regStatement.execute();
        }catch(Exception e){
            System.out.println("Database error when inserting author:\n" + e);
        }
    }
}