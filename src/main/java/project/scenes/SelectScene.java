package project.scenes;

import javafx.animation.RotateTransition;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.ImageInput;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import project.SceneController;
import project.ui.Item;

import java.io.*;
import java.util.ArrayList;

public class SelectScene extends Scene {
  private BorderPane root;
  private ArrayList<String> items;
  private VBox scrollBox;
  private boolean dated;
  public SelectScene(Parent parent, double v, double v1) {
    super(parent, v, v1);
    root = (BorderPane)parent;
    items = new ArrayList<String>();
    this.getStylesheets().add("select.css");
    try {
      File file = new File("ToDoList.csv");
      if (!file.exists()) {
        file.createNewFile();
        FileWriter start = new FileWriter(file);
        start.write("Item,Description,Date,Time,Color,Priority\n");
        start.close();
      }
      BufferedReader reader = new BufferedReader(new FileReader(file));
      String line = reader.readLine();
      while (line != null) {
        items.add(line);
        line = reader.readLine();
      }
      reader.close();
    } catch (IOException e) {
      System.out.println("Error with file");
    }
    items.remove(0);
    Item.setListener(this::removeItem);
    build();
  }

  private void build() {
    var titleBox = new HBox();
    titleBox.setAlignment(Pos.CENTER);
    var title = new Label("To Do List");
    title.setId("title");
    titleBox.getChildren().add(title);
    root.setTop(titleBox);

    var scroll = new ScrollPane();
    scroll.setPrefSize(320, 320);
    scroll.setMaxSize(320, 320);
    scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
    scrollBox = new VBox(5);
    scrollBox.setAlignment(Pos.TOP_CENTER);
    sortByDate();
    dated = true;
    for (String item : items) {
      scrollBox.getChildren().add(new Item(item.split(",")));
    }
    scroll.setContent(scrollBox);
    root.setCenter(scroll);

    var addButton = new Button("Add New Item");
    addButton.setOnMouseEntered(e -> addButton.setStyle("-fx-border-color: grey"));
    addButton.setOnMouseExited(e -> addButton.setStyle("-fx-border-color: black"));
    addButton.setOnAction(e -> SceneController.switchToAddScene());
    var sortButton = new Button("Sort By Priority");
    sortButton.setOnMouseEntered(e -> sortButton.setStyle("-fx-border-color: grey"));
    sortButton.setOnMouseExited(e -> sortButton.setStyle("-fx-border-color: black"));
    sortButton.setOnAction(e -> {
      if (dated) {
        sortByPriority();
        sortButton.setText("Sort By Date");
        dated = false;
        scrollBox.getChildren().clear();
        for (String item : items) {
          scrollBox.getChildren().add(new Item(item.split(",")));
        }
      } else {
        sortByDate();
        sortButton.setText("Sort By Priority");
        dated = true;
        scrollBox.getChildren().clear();
        for (String item : items) {
          scrollBox.getChildren().add(new Item(item.split(",")));
        }
      }
    });
    var bottomBox = new HBox(50);
    bottomBox.setAlignment(Pos.CENTER);
    bottomBox.setPrefSize(300, 100);
    bottomBox.getChildren().addAll(addButton, sortButton);
    root.setBottom(bottomBox);

    var star = new Image("star.png");
    var s1 = new ImageView(star);
    s1.setPreserveRatio(true);
    s1.setFitWidth(50);
    var s2 = new ImageView(star);
    s2.setPreserveRatio(true);
    s2.setFitWidth(50);
    var s3 = new ImageView(star);
    s3.setPreserveRatio(true);
    s3.setFitWidth(50);
    var s4 = new ImageView(star);
    s4.setPreserveRatio(true);
    s4.setFitWidth(50);
    var r1 = new RotateTransition(Duration.seconds(4), s1);
    var r2 = new RotateTransition(Duration.seconds(4), s2);
    var r3 = new RotateTransition(Duration.seconds(2), s3);
    var r4 = new RotateTransition(Duration.seconds(2), s4);
    r1.setByAngle(-360);
    r2.setByAngle(360);
    r3.setByAngle(-360);
    r4.setByAngle(360);
    r1.setOnFinished(e -> r1.play());
    r2.setOnFinished(e -> r2.play());
    r3.setOnFinished(e -> r3.play());
    r4.setOnFinished(e -> r4.play());
    var leftBox = new VBox(150);
    leftBox.setAlignment(Pos.CENTER);
    leftBox.setPrefSize(50, 400);
    leftBox.getChildren().addAll(s1, s3);
    root.setLeft(leftBox);
    var rightBox = new VBox(150);
    rightBox.setAlignment(Pos.CENTER);
    rightBox.setPrefSize(50, 400);
    rightBox.getChildren().addAll(s2, s4);
    root.setRight(rightBox);
    r1.play();
    r2.play();
    r3.play();
    r4.play();


  }

  private void removeItem(Item item) {
    items.remove(item.getInfo());
    try {
      File file = new File("ToDoList.csv");
      FileWriter empty = new FileWriter(file, false);
      empty.write("");
      empty.close();
      FileWriter writer = new FileWriter(file, true);
      writer.write("Item,Description,Date,Time,Color,Priority\n");
      for (String info : items) {
        writer.write(info+"\n");
      }
      writer.close();
    } catch (IOException e) {
      System.out.println("File error");
    }
    scrollBox.getChildren().remove(item);
  }

  public void sortByDate() {
    ArrayList<String> newItems = new ArrayList<String>();
    int size = items.size();
    for (int i=0; i<size; i++) {
      String[] ans = items.get(0).split(",");
      int min = 0;
      for (int j=0; j<items.size(); j++) {
        String[] details = items.get(j).split(",");
        if (Integer.parseInt(details[2].substring(6, 10)) < Integer.parseInt(ans[2].substring(6, 10))) {
          min = j;
          ans = details;
        } else if (Integer.parseInt(details[2].substring(6, 10)) == Integer.parseInt(ans[2].substring(6, 10))) {
          if ((Integer.parseInt(details[2].substring(3, 5)) < Integer.parseInt(ans[2].substring(3, 5)))) {
            min = j;
            ans = details;
          } else if (Integer.parseInt(details[2].substring(3, 5)) == Integer.parseInt(ans[2].substring(3, 5))) {
            if ((Integer.parseInt(details[2].substring(0, 2)) < Integer.parseInt(ans[2].substring(0, 2)))) {
              min = j;
              ans = details;
            } else if ((Integer.parseInt(details[2].substring(0, 2)) == Integer.parseInt(ans[2].substring(0, 2)))) {
              if (Integer.parseInt(details[3].substring(0, 2)) < Integer.parseInt(ans[3].substring(0, 2))) {
                min = j;
                ans = details;
              } else if (Integer.parseInt(details[3].substring(0, 2)) == Integer.parseInt(ans[3].substring(0, 2))) {
                if (Integer.parseInt(details[3].substring(3, 5)) < Integer.parseInt(ans[3].substring(3, 5))) {
                  min = j;
                  ans = details;
                }
              }
            }
          }
        }
      }
      newItems.add(items.get(min));
      items.remove(min);
      if (!items.isEmpty()) {
        min = 0;
        ans = items.get(0).split(",");
      }
    }
    items = newItems;
  }

  public void sortByPriority() {
    ArrayList<String> newItems = new ArrayList<String>();
    int size = items.size();
    String[] type = new String[5];
    type[0] = "Urgent";
    type[1] = "Prioritise";
    type[2] = "Needs Doing";
    type[3] = "Complete Others First";
    type[4] = "Background Task";
    for (int i=0; i<size; i++) {
      for (int j=0; j<items.size(); j++) {
        String details = items.get(j).split(",")[5];
        if (details.equals(type[i])) {
          newItems.add(items.get(j));
        }
      }
    }
    items = newItems;
  }
}
