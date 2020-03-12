package app;

import java.util.List;
import java.util.Map;

public class DBTesting{
    public static void main(String[] args){
        ActorCtrl actorCtrl = new ActorCtrl();
        ProductionCtrl prodctrl = new ProductionCtrl();
        actorCtrl.connect();
        prodctrl.connect();
        List<String> hermannsRoller = actorCtrl.fetchRoles("Hermann");
        for(String rolle : hermannsRoller){
            System.out.println(rolle);
        }
        List<String> hermannsFilmer = actorCtrl.fetchMovies("Hermann");
        for(String film : hermannsFilmer){
            System.out.println(film);
        }
        List<FilmPerson> filmPersons = actorCtrl.fetchFilmPersons();
        for(FilmPerson filmPerson : filmPersons){
            System.out.println(filmPerson);
        }
        Map<String, String> genreLeadingCompanies = prodctrl.fetchGenreLeadingCompanies();
        for (String key : genreLeadingCompanies.keySet()){
            System.out.println(key + ":" + genreLeadingCompanies.get(key));
        }
    }
}