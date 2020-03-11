package app;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class GUI{
    private Stage stage;
    public GUI(){
        
    }
    public void setStage(Stage stage){
        this.stage = stage;
    }
    public Scene menuScene(){
        Button goToActorInfo = new Button("See info about actors");
        goToActorInfo.setOnAction(e -> {
            this.stage.setScene(actorInfoScene());
        });
        Button goToReview = new Button("Write a review");
        goToReview.setOnAction(e -> {
            this.stage.setScene(reviewScene());
        });
        Button goToMovie = new Button("Add movie");
        goToMovie.setOnAction(e -> {
            this.stage.setScene(addMovieScene());
        });
        Button goToGenreLeadingCompanies = new Button("See genre leading companies");
        goToGenreLeadingCompanies.setOnAction(e -> {
            this.stage.setScene(genreLeadingCompaniesScene());
        });
        return new Scene(standardLayout(false, new VBox(goToActorInfo, goToReview, goToMovie, goToGenreLeadingCompanies)));
    }
    public Scene actorInfoScene(){
        List<String> allActorsTest = new ArrayList<>(Arrays.asList("Hermann", "Jorunn", "Airin"));
        ObservableList<String> allActorsList = FXCollections.observableList(allActorsTest);
        Label actorInfoTitle = new Label("See what's new with your favorite actors");
        ComboBox<String> allActors = new ComboBox<>(allActorsList);
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
        Button addMovieButton = new Button("Add movie");
        return new Scene(standardLayout(true, new VBox(movieTitle, movieTitleField, lengthField, publicationYearField, publicationDateField, storylineField, onVideoCheck, addMovieButton)));
    }
    public Scene genreLeadingCompaniesScene(){
        TableView<String> table = new TableView<>();
        return new Scene(standardLayout(true, new VBox(table)));
    }
    public BorderPane standardLayout(boolean includeBack, VBox mainContainer){
        Label title = new Label("MOVIE DATABASE");
        VBox header = new VBox(title);
        if(includeBack){
            Button back = new Button("Go back");
            back.setOnAction(e -> {
                this.stage.setScene(this.menuScene());
            });
            header.getChildren().add(back);
        }
        BorderPane root = new BorderPane(mainContainer, header, null, null, null);
        header.setSpacing(10);
        mainContainer.setSpacing(10);
        BorderPane.setMargin(header, new Insets(10));
        BorderPane.setMargin(mainContainer, new Insets(10));
        return root;
    }
}