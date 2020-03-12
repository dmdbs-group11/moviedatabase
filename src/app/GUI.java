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
import javafx.util.StringConverter;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class GUI{
    List<FilmPerson> filmPersons;
    List<FilmPerson> actors;
    List<FilmPerson> directors;
    List<FilmPerson> authors;
    private Stage stage;
    private ActorCtrl actorCtrl;
    private ProductionCtrl productionCtrl;
    public GUI(){
        actorCtrl = new ActorCtrl();
        productionCtrl = new ProductionCtrl();
        updateFilmPersons();
    }
    public void updateFilmPersons(){
        filmPersons = actorCtrl.fetchFilmPersons();
        directors = new ArrayList<>();
        actors = new ArrayList<>();
        authors = new ArrayList<>();
        for(FilmPerson filmPerson : filmPersons){
            if(filmPerson.isDirector()){
                directors.add(filmPerson);
            }
            if(filmPerson.isActor()){
                actors.add(filmPerson);
            }
            if(filmPerson.isAuthor()){
                authors.add(filmPerson);
            }
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
        ComboBox<FilmPerson> allActors = new ComboBox<>(FXCollections.observableList(actors));
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
            List<FilmPerson> comboBoxList = new ArrayList<>();
            switch (role) {
                case "Director":
                    comboBoxList = directors;
                case "Author":
                    comboBoxList = authors;
                case "Actor":
                    comboBoxList = actors;
            }
            ComboBox<FilmPerson> existing = new ComboBox<>(FXCollections.observableList(comboBoxList));
            existing.setConverter(new StringConverter<FilmPerson>(){
                public String toString(FilmPerson filmPerson){
                    return filmPerson.getName();
                }
                public FilmPerson fromString(String string){
                    return existing.getItems().stream().filter(p -> p.getName().equals(string)).findFirst().orElse(null);
                }
            });
            Button addExisting = new Button("Add " + role);
            addExisting.setOnAction(e -> {
                switch (role) {
                    case "Director":
                        directorsToAdd.add(existing.getValue());
                    case "Author":
                        authorsToAdd.add(existing.getValue());
                    case "Actor":
                        actorsToAdd.add(existing.getValue());
                }
                roleContainer.getChildren().add(new Label(existing.getValue().getName()));
            });
            HBox existingContainer = new HBox(existing, addExisting);
            existingContainer.setSpacing(10);
            rolesContainer.getChildren().addAll(roleLabel, roleContainer, existingContainer);
        }

        VBox newPersonContainer = new VBox();
        newPersonContainer.setSpacing(10);
        Button newRole = new Button("Add new person");
        newRole.setOnAction(e -> {
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
        
        Button addMovieButton = new Button("Add movie");
        return new Scene(standardLayout(true, new VBox(movieTitle, movieTitleField, lengthField, publicationYearField, publicationDateField, storylineField, onVideoCheck, rolesContainer, addMovieButton)));
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