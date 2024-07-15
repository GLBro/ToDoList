package project.ui;

import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import project.listeners.ColorListener;

public class ColorButton extends Button {
  private Color color;
  private static ColorListener colorListener;

  public ColorButton(Color newColor) {
    color = newColor;
    this.setOnAction(e -> colorListener.setColor(getHex()));
    this.setPrefSize(50, 20);
    this.setStyle("-fx-background-color:"+getHex());
    this.setOnMouseEntered(e -> {
      this.setStyle("-fx-background-color:"+getHex(color.brighter())+";-fx-border-color:darkgrey");
    });
    this.setOnMouseExited(e -> {
      this.setStyle("-fx-background-color:"+getHex()+";-fx-border-color: black");
    });
  }

  private String format(double val) {
    String in = Integer.toHexString((int) Math.round(val * 255));
    return in.length() == 1 ? "0" + in : in;
  }

  public String getHex(Color value) {
    return "#" + (format(value.getRed()) + format(value.getGreen()) + format(value.getBlue()) + format(value.getOpacity()))
            .toUpperCase();
  }

  public String getHex() {
    return "#" + (format(color.getRed()) + format(color.getGreen()) + format(color.getBlue()) + format(color.getOpacity()))
            .toUpperCase();
  }

  public static void setColorListener(ColorListener listener) {
    colorListener = listener;
  }

}
