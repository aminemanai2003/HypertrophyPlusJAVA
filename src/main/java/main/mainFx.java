package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class    mainFx extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/PostCrud.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Forum management system");
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/img/Hplus.png")));
        primaryStage.show();



    }
    public static void main(String[] args) {
        launch(args);
    }
}
