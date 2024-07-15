package project;

import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import project.scenes.AddScene;
import project.scenes.SelectScene;

public class SceneController {

  private static Stage stage;
  private static int v;
  private static int v1;

  public static void setUp(Stage newStage, int newV, int newV1) {
    stage = newStage;
    v = newV;
    v1 = newV1;
  }

  public static void switchToAddScene() {
    stage.setScene(new AddScene(new BorderPane(), v, v1));
    stage.show();
  }

  public static void switchToSelectScene() {
    stage.setScene(new SelectScene(new BorderPane(), v, v1));
    stage.show();
  }
}
