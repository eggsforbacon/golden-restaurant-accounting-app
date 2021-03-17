package ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

  MainGUIController controller;

  public Main() {
    controller = new MainGUIController();
  }

  public static void main(String[] args) {
    launch();
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("splash-screen.fxml"));
    fxmlLoader.setController(controller);
    Parent root = fxmlLoader.load();
    Image icon = new Image(String.valueOf(getClass().getResource("resources/gh-icon.png")));
    primaryStage.getIcons().add(icon);
    Scene scene = new Scene(root);
    primaryStage.initStyle(StageStyle.UNDECORATED);
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  public static void wait(int millis) {
    try {
      Thread.sleep(millis);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
