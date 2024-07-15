package project.ui;

import javafx.scene.control.TextField;

public class HashField extends TextField {
  private int max;
  private String contents;
  public HashField(int limit) {
    max = limit;
    contents = this.getText();
    this.setPrefSize(30+(10*max), 20);
    this.setMaxSize(30+(10*max), 20);
    this.setOnKeyTyped(e -> {
      contents = this.getText();
      if (!contents.isEmpty()) {
        char newChar = contents.charAt(contents.length() - 1);
        contents = contents.toUpperCase();
        if (!(((int) newChar >= 47 && (int) newChar <= 58) || ((int)newChar >= 65 && (int)newChar <= 70)) || contents.length() > max) {
          if ((int) newChar >= 97 && (int) newChar <= 102) {
            contents = contents.toUpperCase();
            this.setText(contents);
            for (int i = 0; i < contents.length(); i++) {
              this.forward();
            }
          } else {
            contents = contents.substring(0, contents.length() - 1);
            this.setText(contents);
            for (int i = 0; i < max; i++) {
              this.forward();
            }
          }
        }
      }
    });
  }
}
