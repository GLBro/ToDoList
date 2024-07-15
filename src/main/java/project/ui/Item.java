package project.ui;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import project.listeners.DeleteListener;

public class Item extends HBox {
  private boolean collapsed;
  private String[] details;
  private static DeleteListener deleteListener;

  public Item(String[] details) {
    this.setId("item");
    this.setOnMouseEntered(e -> this.setStyle("-fx-border-width: 5px"));
    this.setOnMouseExited(e -> this.setStyle("-fx-border-width: 2px"));
    this.setOnMouseClicked(e -> alterCollapse());
    if (details[4].equals("#00000000"))  {
      details[4] = "#FFFFFFFF";
    }
    this.setStyle("-fx-text-fill: "+details[4]+";");
    collapsed = true;
    this.details = details;
    if (details[1].contains("\\n")) {
      details[1] = details[1].replace("\\n", "\n");
    }
    if (details[1].contains("///")) {
      details[1] = details[1].replace("///", ",");
    }
    if (details[0].contains("///")) {
      details[0] = details[0].replace("///", ",");
    }
    collapse();
  }

  private void collapse() {
    collapsed = true;
    this.setPrefSize(300, 50);
    this.setMaxSize(300, 50);
    var leftBox = new HBox();
    leftBox.setAlignment(Pos.CENTER_LEFT);
    leftBox.setPrefSize(200, 50);
    leftBox.setPrefSize(200, 50);
    var rightBox = new HBox(5);
    rightBox.setAlignment(Pos.CENTER_RIGHT);
    rightBox.setPrefSize(100, 50);
    rightBox.setPrefSize(100, 50);
    var name = new Label(details[0]);
    name.setStyle("-fx-text-fill: "+details[4]+";");
    var date = new Label(details[2]);
    date.setStyle("-fx-text-fill: "+details[4]+"; -fx-font-size: 10px;");
    var x = new Button("X");
    x.setPrefSize(10, 10);
    x.setMaxSize(10, 10);
    x.setStyle("-fx-font-family: Impact; -fx-text-fill: red; -fx-background-color: black; -fx-border-color: grey; -fx-border-width: 0px");
    x.setOnMouseEntered(e -> x.setStyle("-fx-font-family: Impact; -fx-text-fill: red; -fx-background-color: black; -fx-border-color: grey; -fx-border-width: 2px"));
    x.setOnMouseExited(e -> x.setStyle("-fx-font-family: Impact; -fx-text-fill: red; -fx-background-color: black; -fx-border-color: grey; -fx-border-width: 0px"));
    x.setOnAction(e -> deleteListener.delete(this));
    leftBox.getChildren().add(name);
    rightBox.getChildren().addAll(date, x);
    this.getChildren().addAll(leftBox, rightBox);
  }

  private void decollapse() {
    collapsed = false;
    this.setPrefSize(300, 150);
    this.setMaxSize(300, 150);
    var leftBox = new VBox();
    leftBox.setAlignment(Pos.CENTER_LEFT);
    leftBox.setPrefSize(150, 150);
    leftBox.setPrefSize(150, 150);
    var rightBox = new HBox(5);
    rightBox.setAlignment(Pos.CENTER_RIGHT);
    rightBox.setPrefSize(150, 150);
    rightBox.setPrefSize(150, 150);
    var name = new Text(details[0]);
    name.setFill(Color.valueOf(details[4]));
    name.setFont(new Font("Britannic Bold", 15));
    var nameFlow = new TextFlow();
    nameFlow.getChildren().add(name);
    var desc = new Text(details[1]);
    desc.setFill(Color.WHITE);
    desc.setFont(new Font("Britannic Bold", 10));
    var descFlow = new TextFlow();
    descFlow.getChildren().add(desc);
    var date = new Label(details[2]);
    date.setStyle("-fx-text-fill: "+details[4]+"; -fx-font-size: 10px;");
    var time = new Label(details[3]);
    time.setStyle("-fx-text-fill: "+details[4]+"; -fx-font-size: 10px;");
    var priority = new Label(details[5]);
    priority.setStyle("-fx-text-fill: "+details[4]+"; -fx-font-size: 10px;");
    var infoBox = new VBox(5);
    infoBox.setAlignment(Pos.CENTER_RIGHT);
    infoBox.getChildren().addAll(date, time, priority);
    var x = new Button("X");
    x.setPrefSize(10, 10);
    x.setMaxSize(10, 10);
    x.setStyle("-fx-font-family: Impact; -fx-text-fill: red; -fx-background-color: black; -fx-border-color: grey; -fx-border-width: 0px");
    x.setOnMouseEntered(e -> x.setStyle("-fx-font-family: Impact; -fx-text-fill: red; -fx-background-color: black; -fx-border-color: grey; -fx-border-width: 2px"));
    x.setOnMouseExited(e -> x.setStyle("-fx-font-family: Impact; -fx-text-fill: red; -fx-background-color: black; -fx-border-color: grey; -fx-border-width: 0px"));
    x.setOnAction(e -> deleteListener.delete(this));
    leftBox.getChildren().addAll(nameFlow, descFlow);
    rightBox.getChildren().addAll(infoBox, x);
    this.getChildren().addAll(leftBox, rightBox);
  }

  private void alterCollapse() {
    this.getChildren().clear();
    if (collapsed) {
      decollapse();
    } else {
      collapse();
    }
  }

  public static void setListener(DeleteListener listener) {
    deleteListener = listener;
  }

  public String getInfo() {
    return details[0].replace(",", "///")+","+details[1].replace("\n", "\\n").replace(",", "///")+","+details[2]+","+details[3]+","+details[4]+","+details[5];
  }
}
