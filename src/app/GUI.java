package app;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;
import java.util.Map;
import java.util.Set;
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
    private MovieCtrl movieCtrl;
    private ReviewCtrl reviewCtrl;
    public GUI(){
        actorCtrl = new ActorCtrl();
        actorCtrl.connect();
        productionCtrl = new ProductionCtrl();
        productionCtrl.connect();
        movieCtrl = new MovieCtrl();
        movieCtrl.connect();
        reviewCtrl = new ReviewCtrl();
        reviewCtrl.connect();
        updateFilmPersons();
        updateCompanies();
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
        Button goToAddPerson = new Button("Add person");
        goToAddPerson.setOnAction(e -> {
            updateScene(addPersonScene());
        });
        Button goToGenreLeadingCompanies = new Button("See genre leading companies");
        goToGenreLeadingCompanies.setOnAction(e -> {
            updateScene(genreLeadingCompaniesScene());
        });
        return new Scene(standardLayout(false, new VBox(goToActorInfo, goToReview, goToMovie, goToAddPerson, goToGenreLeadingCompanies)));
    }
    public Scene actorInfoScene(){
        Label actorInfoTitle = new Label("See what's new with your favorite actors");
        ComboBox<String> actorSelect = new ComboBox<>(FXCollections.observableList(new ArrayList<>(actors.keySet())));
        Label actorInfo = new Label();
        Button seeActorRoles = new Button("See roles of selected actor");
        seeActorRoles.setOnAction(e -> {
            String actorName = actorSelect.getValue();
            String infoText = actorName + "s roller:\n";
            for(String role : actorCtrl.fetchRoles(actorName)){
                infoText += role + "\n";
            }
            actorInfo.setText(infoText);
        });
        Button seeActorMovies = new Button("See movies of selected actor");
        seeActorMovies.setOnAction(e -> {
            String actorName = actorSelect.getValue();
            String infoText = actorName + "s roller:\n";
            for(String movie : actorCtrl.fetchMovies(actorName)){
                infoText += movie + "\n";
            }
            actorInfo.setText(infoText); 
        });
        HBox buttonContainer = new HBox(seeActorRoles, seeActorMovies);
        buttonContainer.setSpacing(10);
        return new Scene(standardLayout(true, new VBox(actorInfoTitle, actorSelect, buttonContainer, actorInfo)));
    }
    public Scene reviewScene(){
        Label reviewTitle = new Label("Write a review");
        TextField review = new TextField();
        review.setPromptText("Your thoughts");
        TextField rating = new TextField();
        rating.setPromptText("Rating (1-10)");
        Label selectLabel = new Label("Movie/Season/Series to review:");

        Map<String, Integer> movieTitles = movieCtrl.fetchMovieTitles();
        ComboBox<String> movieSelect = new ComboBox<>(FXCollections.observableList(new ArrayList<>(movieTitles.keySet())));

        Map<String, Integer> seasonTitles = movieCtrl.fetchSeasonTitles();
        ComboBox<String> seasonSelect = new ComboBox<>(FXCollections.observableList(new ArrayList<>(seasonTitles.keySet())));

        Map<String, Integer> seriesTitles = movieCtrl.fetchSeriesTitles();
        ComboBox<String> seriesSelect = new ComboBox<>(FXCollections.observableList(new ArrayList<>(seriesTitles.keySet())));

        HBox selectContainer = new HBox(movieSelect, seasonSelect, seriesSelect);
        selectContainer.setSpacing(10);
        Button submitReview = new Button("Submit review");
        submitReview.setOnAction(e -> {
            reviewCtrl.giveReview(review.getText(), Integer.parseInt(rating.getText()), 1, seriesTitles.get(seriesSelect.getValue()), seasonTitles.get(seasonSelect.getValue()), movieTitles.get(movieSelect.getValue()));
            review.clear();
            rating.clear();
            movieSelect.getSelectionModel().clearSelection();
            seasonSelect.getSelectionModel().clearSelection();
            seriesSelect.getSelectionModel().clearSelection();
        });
        return new Scene(standardLayout(true, new VBox(reviewTitle, review, rating, selectLabel, selectContainer, submitReview)));
    }
    public Scene addMovieScene(){
        List<FilmPerson> directorsToAdd = new ArrayList<>();
        Map<FilmPerson, String> actorsToAdd = new HashMap<>();
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
            HBox existingContainer = new HBox(existing);
            if(role == "Actor"){
                TextField roleField = new TextField();
                roleField.setPromptText("Role");
                existingContainer.getChildren().add(roleField);
            }
            Button addExisting = new Button("Add " + role);
            addExisting.setOnAction(e -> {
                if(role == "Director"){
                    directorsToAdd.add(directors.get(existing.getValue()));
                }else if(role == "Actor"){
                    TextField roleTextField = (TextField) existingContainer.getChildren().get(1);
                    actorsToAdd.put(actors.get(existing.getValue()), roleTextField.getText());
                    roleTextField.clear();
                }else if(role == "Author"){
                    authorsToAdd.add(authors.get(existing.getValue()));
                }
                String addedLabel = existing.getValue();
                if(role == "Actor"){
                    addedLabel += ": " + actorsToAdd.get(actors.get(existing.getValue()));
                }
                HBox roleRow = new HBox(new Label(addedLabel));
                roleRow.setSpacing(10);
                roleContainer.getChildren().add(roleRow);
                existing.getItems().remove(existing.getValue());
            });
            existingContainer.getChildren().add(addExisting);
            existingContainer.setSpacing(10);
            rolesContainer.getChildren().addAll(roleLabel, roleContainer, existingContainer);
        }

        Label companyLabel = new Label("Production company: ");
        ComboBox<String> companySelect = new ComboBox<>(FXCollections.observableList(new ArrayList<>(companies.keySet())));

        Button addMovieButton = new Button("Add movie");
        addMovieButton.setOnAction(e -> {
            movieCtrl.newMovie(movieTitleField.getText(), Integer.parseInt(lengthField.getText()), Integer.parseInt(publicationYearField.getText()), Integer.parseInt(publicationDateField.getText()), storylineField.getText(), onVideoCheck.isSelected(), companies.get(companySelect.getValue()), null, directorsToAdd, actorsToAdd, authorsToAdd);
            updateScene(addMovieScene());
        });
        return new Scene(standardLayout(true, new VBox(movieTitle, movieTitleField, lengthField, publicationYearField, publicationDateField, storylineField, onVideoCheck, rolesContainer, companyLabel, companySelect, addMovieButton)));
    }
    public Scene addPersonScene(){
        TextField nameField = new TextField();
        nameField.setPromptText("Name");
        TextField birthYearField = new TextField();
        birthYearField.setPromptText("Birthyear");
        TextField countryField = new TextField();
        countryField.setPromptText("Country of birth");
        CheckBox directorCheck = new CheckBox("Director");
        CheckBox actorCheck = new CheckBox("Actor");
        CheckBox authorCheck = new CheckBox("Author");
        Button addPerson = new Button("Add person");
        addPerson.setOnAction(e2 -> {
            FilmPerson filmPerson = new FilmPerson(nameField.getText(), Integer.parseInt(birthYearField.getText()), countryField.getText(), directorCheck.isSelected(), actorCheck.isSelected(), authorCheck.isSelected());
            updateFilmPersons();
            nameField.clear();
            birthYearField.clear();
            countryField.clear();
            directorCheck.setSelected(false);
            actorCheck.setSelected(false);
            authorCheck.setSelected(false);
        });
        HBox roleRow = new HBox(nameField, birthYearField, countryField, directorCheck, actorCheck, authorCheck, addPerson);
        roleRow.setSpacing(10);
        return new Scene(standardLayout(true, new VBox(roleRow)));
    }
    public Scene genreLeadingCompaniesScene(){
        Map<String, String> genreLeadingCompanies = productionCtrl.fetchGenreLeadingCompanies();
        VBox genreColumn = new VBox(new Label("GENRE"));
        genreColumn.setSpacing(10);
        VBox companyColumn = new VBox(new Label("COMPANY"));
        companyColumn.setSpacing(10);
        for(String genre : genreLeadingCompanies.keySet()){
            genreColumn.getChildren().add(new Label(genre));
            companyColumn.getChildren().add(new Label(genreLeadingCompanies.get(genre)));
        }
        HBox table = new HBox(genreColumn, companyColumn);
        table.setSpacing(10);
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
        root.setMaxHeight(650);
        return root;
    }
}