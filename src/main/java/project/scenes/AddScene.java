package project.scenes;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import project.SceneController;
import project.ui.ColorButton;
import project.ui.HashField;
import project.ui.LimitedField;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class AddScene extends Scene {

  private BorderPane root;
  private HashField colorField;
  private TextField enterToDo;
  private TextArea enterDesc;
  private LimitedField day;
  private LimitedField month;
  private LimitedField year;
  private LimitedField hour;
  private LimitedField min;
  private ToggleGroup group;
  private Label warning;
  public AddScene(Parent parent, double v, double v1) {
    super(parent, v, v1);
    root = (BorderPane) parent;
    this.getStylesheets().add("add.css");
    root.setPrefSize(v, v1);
    build();
  }

  public void build() {
    var title = new Label("Add New Item");
    title.setId("title");
    var titleBox = new HBox();
    titleBox.setAlignment(Pos.CENTER);
    titleBox.getChildren().add(title);
    root.setTop(titleBox);

    var entryBox = new VBox(20);
    root.setCenter(entryBox);

    var toDo = new Label("What needs doing?");
    enterToDo = new TextField();
    enterToDo.setMaxSize(300, 50);
    var toDoBox = new VBox();
    toDoBox.setAlignment(Pos.TOP_LEFT);
    toDoBox.getChildren().addAll(toDo, enterToDo);

    var desc = new Label("Enter description:");
    enterDesc = new TextArea();
    enterDesc.setPrefSize(300, 150);
    enterDesc.setMaxSize(300, 150);
    var descBox = new VBox();
    descBox.setAlignment(Pos.TOP_LEFT);
    descBox.getChildren().addAll(desc, enterDesc);

    var time = new Label("Enter DD/MM/YYYY HH:MM");
    var dateBox = new HBox(5);
    day = new LimitedField(2);
    var slash1 = new Label("/");
    slash1.setStyle("-fx-font-size: 30px");
    month = new LimitedField(2);
    var slash2 = new Label("/");
    slash2.setStyle("-fx-font-size: 30px");
    year = new LimitedField(4);
    var gap = new Label("         ");
    hour = new LimitedField(2);
    var colon = new Label(":");
    colon.setStyle("-fx-font-size: 30px");
    min = new LimitedField(2);
    dateBox.getChildren().addAll(day, slash1, month, slash2, year, gap, hour, colon, min);
    var timeBox = new VBox();
    timeBox.setAlignment(Pos.TOP_LEFT);
    timeBox.getChildren().addAll(time, dateBox);

    var color = new Label("Choose a color:");
    var colorChoice = new HBox(10);
    var white = new ColorButton(Color.WHITE);
    var red = new ColorButton(Color.RED);
    var blue = new ColorButton(Color.BLUE);
    var green = new ColorButton(Color.GREEN);
    var yellow = new ColorButton(Color.YELLOW);
    var purple = new ColorButton(Color.PURPLE);
    var orange = new ColorButton(Color.ORANGE);
    var hashtag = new Label("#");
    hashtag.setStyle("-fx-font-size: 30px");
    colorField = new HashField(8);
    ColorButton.setColorListener(this::setColor);
    colorChoice.setAlignment(Pos.CENTER_LEFT);
    colorChoice.getChildren().addAll(white, red, blue, green, yellow, purple, orange, hashtag, colorField);
    var colorBox = new VBox();
    colorBox.setAlignment(Pos.TOP_LEFT);
    colorBox.getChildren().addAll(color, colorChoice);


    var halfBox = new VBox(20);
    halfBox.getChildren().addAll(toDoBox, descBox);
    var radioBox = new VBox(20);
    radioBox.setAlignment(Pos.TOP_LEFT);
    var radioText = new Label("Set Priority:");
    group = new ToggleGroup();
    var r1 = new RadioButton("Urgent");
    r1.setToggleGroup(group);
    var r2 = new RadioButton("Prioritise");
    r2.setToggleGroup(group);
    var r3 = new RadioButton("Needs Doing");
    r3.setToggleGroup(group);
    var r4 = new RadioButton("Complete Others First");
    r4.setToggleGroup(group);
    var r5 = new RadioButton("Background Task");
    r5.setToggleGroup(group);
    r5.setSelected(true);
    radioBox.getChildren().addAll(radioText, r1, r2, r3, r4, r5);
    var wideBox = new HBox(10);
    wideBox.setAlignment(Pos.TOP_LEFT);
    wideBox.getChildren().addAll(halfBox, radioBox);

    entryBox.getChildren().addAll(wideBox, timeBox, colorBox);

    var create = new Button("Create");
    create.setId("create");
    create.setOnAction(e -> addToFile());
    create.setOnMouseEntered(e -> {
      create.setStyle("-fx-border-color: lightsalmon; -fx-background-color: gold; -fx-text-fill: white");
    });
    create.setOnMouseExited(e -> {
      create.setStyle("-fx-border-color: tomato; -fx-background-color: orange; -fx-text-fill: gold");
    });
    warning = new Label("");
    var createBox = new VBox();
    createBox.setAlignment(Pos.CENTER);
    createBox.getChildren().addAll(create, warning);
    root.setBottom(createBox);
  }

  private void setColor(String hash) {
    hash = hash.replace("#", "");
    colorField.setText(hash);
    for (int i=0; i<6; i++) {
      colorField.forward();
    }
  }

  private void addToFile() {
    String sColor = colorField.getText().toUpperCase();
    if (sColor.length() < 8) {
      sColor = "FFFFFFFF";
    }
    if (!checkDate()) {
      warning.setText("Enter A Valid Date");
    } else {
      sColor = "#" + sColor;
      warning.setText("");
      String sDate = day.getText()+"/"+month.getText()+"/"+year.getText();
      String sTime = hour.getText()+":"+min.getText();
      String sItem = enterToDo.getText();
      String sDesc = enterDesc.getText();
      if (sDesc.contains("\n")) {
        sDesc = sDesc.replace("\n", "\\n");
      }
      if (sDesc.contains(",")) {
        sDesc = sDesc.replace(",", "///");
      }
      if (sItem.contains(",")) {
        sItem = sItem.replace(",", "///");
      }
      RadioButton selected = (RadioButton)group.getSelectedToggle();
      String sPri = selected.getText();
      String out = sItem+","+sDesc+","+sDate+","+sTime+","+sColor+","+sPri+"\n";
      try {
        File file = new File("ToDoList.csv");
        if (!file.exists()) {
          file.createNewFile();
          FileWriter start = new FileWriter(file);
          start.write("Item,Description,Date,Time,Color,Priority\n");
          start.close();
        }
        FileWriter writer = new FileWriter(file, true);
        writer.write(out);
        writer.close();
        SceneController.switchToSelectScene();
      } catch (IOException e) {
        System.out.println("Error with file");
      }
    }
  }

  private boolean checkDate() {
    try {
      int iDay = Integer.parseInt(day.getText());
      int iMonth = Integer.parseInt(month.getText());
      int iYear = Integer.parseInt(year.getText());
      int iHour = Integer.parseInt(hour.getText());
      int iMin = Integer.parseInt(min.getText());
      int numOfDays = 31;
      if (iMonth == 9 || iMonth == 4 || iMonth == 6 || iMonth == 11) {
        numOfDays = 30;
      } else if (iMonth == 2 && iYear%4 == 0) {
        numOfDays = 29;
      } else if (iMonth == 2) {
        numOfDays = 28;
      }
      if (iDay <= 0 || iDay > numOfDays) {
        return false;
      }
      if (iHour < 0 || iHour > 23) {
        return false;
      }
      if (iMin < 0 || iMin > 59) {
        return false;
      }
      return true;
    } catch (NumberFormatException e) {
      return false;
    }
  }
}
