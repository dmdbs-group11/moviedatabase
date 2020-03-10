package app;

import java.util.List;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Helloworld");
        ActorCtrl actorCtrl = new ActorCtrl();
        actorCtrl.connect();
        List<String> hermannsRoller = actorCtrl.fetchRoles("Hermann");
        for(String rolle : hermannsRoller){
            System.out.println(rolle);
        }
    }
}