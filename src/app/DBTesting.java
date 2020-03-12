package app;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DBTesting{
    public static void main(String[] args){
        ActorCtrl actorCtrl = new ActorCtrl();
        actorCtrl.connect();
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
            System.out.println(filmPerson.getName() + ", " + filmPerson.getBirthYear() + ", " + filmPerson.getCountry() + ", Director: " + filmPerson.isDirector() + ", Actor: " + filmPerson.isActor() + ", Author: " + filmPerson.isAuthor());
        }
        List<FilmPerson> directors = filmPersons.stream().filter(FilmPerson::isDirector).collect(Collectors.toList());
        List<FilmPerson> authors = filmPersons.stream().filter(FilmPerson::isAuthor).collect(Collectors.toList());
        List<FilmPerson> actors = filmPersons.stream().filter(FilmPerson::isActor).collect(Collectors.toList());
        Map<FilmPerson, String> actorsWithRoles = new HashMap<>();
        for(FilmPerson actor : actors){
            actorsWithRoles.put(actor, "Gresk gud");
        }

        ProductionCtrl prodctrl = new ProductionCtrl();
        prodctrl.connect();
        Map<String, String> genreLeadingCompanies = prodctrl.fetchGenreLeadingCompanies();
        for (String key : genreLeadingCompanies.keySet()){
            System.out.println(key + ":" + genreLeadingCompanies.get(key));
        }
        List<Selskap> companies = prodctrl.fetchCompanies();
        for(Selskap selskap : companies){
            System.out.println(selskap.getName() + ", " + selskap.getAddress() + ", " + selskap.getCountry() + ", " + selskap.getURL());
        }

        ReviewCtrl reviewCtrl = new ReviewCtrl();
        reviewCtrl.connect();
        reviewCtrl.giveReview("Elsket episoden, spesielt Hermanns stramme kropp", 10, 1, null, null, 1);

        MovieCtrl movieCtrl = new MovieCtrl();
        movieCtrl.connect();
        Selskap viaPlay = prodctrl.fetchCompanies().get(0);
        Sesong sesong1 = prodctrl.fetchSeasons().get(0);
        movieCtrl.newMovie("Episode 3", 42, 2020, 1411, "", true, viaPlay, sesong1, directors, actorsWithRoles, authors);
    }
}