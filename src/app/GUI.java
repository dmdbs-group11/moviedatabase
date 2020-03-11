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
import java.util.ArrayList;
import java.util.Arrays;

public class GUI{
    List<String> testList = new ArrayList<>(Arrays.asList("Hermann", "Jorunn", "Airin"));
    private Stage stage;
    private ActorCtrl actorCtrl;
    private ProductionCtrl productionCtrl;
    public GUI(){
        actorCtrl = new ActorCtrl();
        productionCtrl = new ProductionCtrl();
    }
    public void setStage(Stage stage){
        this.stage = stage;
    }
    public void updateScene(Scene scene){
        stage.setScene(scene);
    }
    public void updateFilmPersons(){

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
        ComboBox<String> allActors = new ComboBox<>(FXCollections.observableList(testList));
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
            roleContainer.setSpacing(10);
            Button newRole = new Button("Add new " + role.toLowerCase());
            newRole.setOnAction(e -> {
                TextField nameField = new TextField();
                nameField.setPromptText("Name");
                TextField birthYearField = new TextField();
                birthYearField.setPromptText("Birthyear");
                TextField countryField = new TextField();
                countryField.setPromptText("Country of birth");
                Button addRole = new Button("Add");
                addRole.setOnAction(e2 -> {
                    //actorCtrl.addPerson(nameField.getText(), birthYearField.getText(), countryField.getText());
                    updateFilmPersons();
                });
                HBox roleRow = new HBox(nameField, birthYearField, countryField, addRole);
                roleRow.setSpacing(10);
                roleContainer.getChildren().add(roleRow);
            });
            List<String> comboBoxList = new ArrayList<>();
            switch (role) {
                case "Director":
                    comboBoxList = testList;
                case "Author":
                    comboBoxList = testList;
                case "Actor":
                    comboBoxList = testList;
            }
            ComboBox<String> existing = new ComboBox<>(FXCollections.observableList(comboBoxList));
            Button addExisting = new Button("Add " + role);
            addExisting.setOnAction(e -> {
                Label added = new Label(existing.getValue());
                roleContainer.getChildren().add(added);
            });
            HBox existingContainer = new HBox(existing, addExisting);
            existingContainer.setSpacing(10);
            rolesContainer.getChildren().addAll(roleLabel, roleContainer, newRole, existingContainer);
        }
        
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