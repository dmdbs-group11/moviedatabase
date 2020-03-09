package app;

public class App {
    public static void main(String[] args) throws Exception {
        ActorCtrl actorCtrl = new ActorCtrl();
        System.out.println(actorCtrl.fetchRoles("Leonardo DiCaprio"));
    }
}