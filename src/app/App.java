package app;

import java.util.List;

public class App {
    public static void main(String[] args) throws Exception {
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
    }
}