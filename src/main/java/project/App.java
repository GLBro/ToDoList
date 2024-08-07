package project;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import project.scenes.AddScene;


/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {
        SceneController.setUp(stage, 500, 500);
        SceneController.switchToSelectScene();

    }

    public static void main(String[] args) {
        launch();
    }

}