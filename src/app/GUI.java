package app;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class GUI{
    Map<String, FilmPerson> filmPersons = new HashMap<>();
    Map<String, FilmPerson> actors = new HashMap<>();
    Map<String, FilmPerson> directors = new HashMap<>();
    Map<String, FilmPerson> authors = new HashMap<>();
    Map<String, Selskap> companies = new HashMap<>();
    private Stage stage;
    private ActorCtrl actorCtrl;
    private ProductionCtrl productionCtrl;
    public GUI(){
        actorCtrl = new ActorCtrl();
        actorCtrl.connect();
        productionCtrl = new ProductionCtrl();
        productionCtrl.connect();
        updateFilmPersons();
        updateCompanies();
        for(String directorName : directors.keySet()){
            System.out.println(directorName);
        }
        for(String actorName : actors.keySet()){
            System.out.println(actorName);
        }
        for(String authorName : authors.keySet()){
            System.out.println(authorName);
        }
    }
    public void updateFilmPersons(){
        List<FilmPerson> filmPersonList = actorCtrl.fetchFilmPersons();
        for(FilmPerson filmPerson : filmPersonList){
            filmPersons.put(filmPerson.getName(), filmPerson);
            if(filmPerson.isDirector()){
                directors.put(filmPerson.getName(), filmPerson);
            }
            if(filmPerson.isActor()){
                actors.put(filmPerson.getName(), filmPerson);
            }
            if(filmPerson.isAuthor()){
                authors.put(filmPerson.getName(), filmPerson);
            }
        }
    }
    public void updateCompanies(){
        List<Selskap> companyList = productionCtrl.fetchCompanies();
        for(Selskap selskap : companyList){
            companies.put(selskap.getName(), selskap);
        }
    }
    public void setStage(Stage stage){
        this.stage = stage;
    }
    public void updateScene(Scene scene){
        stage.setScene(scene);
    }
    public Scene menuScene(){
        Button goToActorInfo = new Button("See info about actors");
        goToActorInfo.setOnAction(e -> {
            updateScene(actorInfoScene());
        });
        Button goToReview = new Button("Write a review");
        goToReview.setOnAction(e -> {
            updateScene(reviewScene());
        });
        Button goToMovie = new Button("Add movie");
        goToMovie.setOnAction(e -> {
            updateScene(addMovieScene());
        });
        Button goToGenreLeadingCompanies = new Button("See genre leading companies");
        goToGenreLeadingCompanies.setOnAction(e -> {
            updateScene(genreLeadingCompaniesScene());
        });
        return new Scene(standardLayout(false, new VBox(goToActorInfo, goToReview, goToMovie, goToGenreLeadingCompanies)));
    }
    public Scene actorInfoScene(){
        Label actorInfoTitle = new Label("See what's new with your favorite actors");
        ComboBox<String> allActors = new ComboBox<>(FXCollections.observableList(new ArrayList<>(actors.keySet())));
        Button seeActorRoles = new Button("See roles of selected actor");
        Button seeActorMovies = new Button("See movies of selected actor");
        return new Scene(standardLayout(true, new VBox(actorInfoTitle, allActors, seeActorRoles, seeActorMovies)));
    }
    public Scene reviewScene(){
        Label reviewTitle = new Label("Write a review");
        TextField review = new TextField();
        Button submitReview = new Button("Submit review");
        return new Scene(standardLayout(true, new VBox(reviewTitle, review, submitReview)));
    }
    public Scene addMovieScene(){
        List<FilmPerson> directorsToAdd = new ArrayList<>();
        List<FilmPerson> actorsToAdd = new ArrayList<>();
        List<FilmPerson> authorsToAdd = new ArrayList<>();
        Label movieTitle = new Label("Add a movie");
        TextField movieTitleField = new TextField();
        movieTitleField.setPromptText("Movie title");
        TextField lengthField = new TextField();
        lengthField.setPromptText("Length (in minutes)");
        TextField publicationYearField = new TextField();
        publicationYearField.setPromptText("Publication year (YYYY)");
        TextField publicationDateField = new TextField();
        publicationDateField.setPromptText("Publication date (DDMM)");
        TextField storylineField = new TextField();
        storylineField.setPromptText("Storyline");
        CheckBox onVideoCheck = new CheckBox("On video");

        VBox rolesContainer = new VBox();
        rolesContainer.setSpacing(10);
        List<String> roles = new ArrayList<>(Arrays.asList("Director", "Author", "Actor"));
        for(String role : roles){
            Label roleLabel = new Label(role + "s: ");
            VBox roleContainer = new VBox();
            Set<String> comboBoxContent = new HashSet<>();
            if(role == "Director"){
                comboBoxContent = directors.keySet();
            }else if(role == "Actor"){
                comboBoxContent = actors.keySet();
            }else if(role == "Author"){
                comboBoxContent = authors.keySet();
            }
            ComboBox<String> existing = new ComboBox<>(FXCollections.observableList(new ArrayList<>(comboBoxContent)));
            Button addExisting = new Button("Add " + role);
            addExisting.setOnAction(e -> {
                switch (role) {
                    case "Director":
                        directorsToAdd.add(directors.get(existing.getValue()));
                    case "Actor":
                        actorsToAdd.add(actors.get(existing.getValue()));
                    case "Author":
                        authorsToAdd.add(authors.get(existing.getValue()));
                }
                roleContainer.getChildren().add(new Label(existing.getValue()));
                existing.getItems().remove(existing.getValue());
            });
            HBox existingContainer = new HBox(existing, addExisting);
            existingContainer.setSpacing(10);
            rolesContainer.getChildren().addAll(roleLabel, roleContainer, existingContainer);
        }

        VBox newPersonContainer = new VBox();
        newPersonContainer.setSpacing(10);
        Button newPerson = new Button("Add new person");
        newPerson.setOnAction(e -> {
            TextField nameField = new TextField();
            nameField.setPromptText("Name");
            TextField birthYearField = new TextField();
            birthYearField.setPromptText("Birthyear");
            TextField countryField = new TextField();
            countryField.setPromptText("Country of birth");
            CheckBox directorCheck = new CheckBox("Director");
            CheckBox actorCheck = new CheckBox("Actor");
            CheckBox authorCheck = new CheckBox("Author");
            Button addPerson = new Button("Add");
            addPerson.setOnAction(e2 -> {
                FilmPerson filmPerson = new FilmPerson(nameField.getText(), birthYearField.getText(), countryField.getText(), directorCheck.isSelected(), actorCheck.isSelected(), authorCheck.isSelected());
                updateFilmPersons();
            });
            HBox roleRow = new HBox(nameField, birthYearField, countryField, directorCheck, actorCheck, authorCheck, addPerson);
            roleRow.setSpacing(10);
            newPersonContainer.getChildren().add(roleRow);
        });

        Label companyLabel = new Label("Production company: ");
        ComboBox<String> companySelect = new ComboBox<>(FXCollections.observableList(new ArrayList<>(companies.keySet())));
        Button addMovieButton = new Button("Add movie");
        return new Scene(standardLayout(true, new VBox(movieTitle, movieTitleField, lengthField, publicationYearField, publicationDateField, storylineField, onVideoCheck, rolesContainer, newPersonContainer, newPerson, companyLabel, companySelect, addMovieButton)));
    }
    public Scene genreLeadingCompaniesScene(){
        TableView<String> table = new TableView<>();
        return new Scene(standardLayout(true, new VBox(table)));
    }
    public ScrollPane standardLayout(boolean includeBack, VBox mainContainer){
        Label title = new Label("MOVIE DATABASE");
        VBox header = new VBox(title);
        if(includeBack){
            Button back = new Button("Go back");
            back.setOnAction(e -> {
                this.stage.setScene(this.menuScene());
            });
            header.getChildren().add(back);
        }
        VBox container = new VBox(header, mainContainer);
        header.setSpacing(10);
        mainContainer.setSpacing(10);
        VBox.setMargin(header, new Insets(10));
        VBox.setMargin(mainContainer, new Insets(10));
        ScrollPane root = new ScrollPane(container);
        root.setPrefSize(250, 400);
        return root;
    }
}