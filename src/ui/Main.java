package ui;

import com.sun.javafx.application.LauncherImpl;
import javafx.application.Application;
import javafx.application.Preloader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.Ingredient;
import model.PlateType;
import model.Restaurant;

import java.io.IOException;
import java.util.ArrayList;

public class Main extends Application {

  MainGUIController controller;
  Restaurant GH;

  public Main() {
    GH = new Restaurant();

    try {
      controller = new MainGUIController(GH);
    } catch (IOException ioe) {
      System.out.println("ioe");
      ioe.printStackTrace();
    } catch (ClassNotFoundException cnfe) {
      System.out.println("cnfe");
      cnfe.printStackTrace();
    }
    System.out.println(GH.checkFirstTime());
  }

  public static void main(String[] args) {
    LauncherImpl.launchApplication(Main.class, GHPreloader.class, args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {

    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("main-pane.fxml"));
    fxmlLoader.setController(controller);
    Parent root = fxmlLoader.load();
    Image icon = new Image(String.valueOf(getClass().getResource("resources/gh-icon.png")));
    primaryStage.getIcons().add(icon);
    Scene scene = new Scene(root);
    primaryStage.setScene(scene);
    scene.getStylesheets().addAll(String.valueOf(getClass().getResource("css/stylesheet.css")));
    primaryStage.setMinHeight(708.0);
    primaryStage.setMinWidth(1250.0);
    primaryStage.setTitle("Golden House Restaurant: Inicio");
    primaryStage.show();

  }

  @Override
  public void init() {
    int COUNT_LIMIT = 60000;
    for (int i = 0; i < COUNT_LIMIT; i++) {
      double progress = (100.0 * i) / COUNT_LIMIT;
      LauncherImpl.notifyPreloader(this, new Preloader.ProgressNotification(progress));
    }
  }
}
