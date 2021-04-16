package ui;

import com.sun.javafx.application.LauncherImpl;
import javafx.application.Application;
import javafx.application.Preloader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.Restaurant;

import java.io.*;

/*Total warnings (16-04-21): 3 (Product) + 15 (Restaurant) -> 18 Warnings total
  Total testing souts (16-04-21) -> 0 */

public class Main extends Application {

  MainGUIController controller;
  Restaurant GH;
  private static final String RESTAURANT_PATH_FILE = "src/data/savedfiles/restaurant.z&1";

  public Main() {
    GH = new Restaurant();
    loadRestaurantData();
    controller = new MainGUIController(GH);
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
    primaryStage.setMinHeight(718.0);
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

  @Override
  public void stop() {
    ObjectOutputStream oos;
    try {
      oos = new ObjectOutputStream(new FileOutputStream(RESTAURANT_PATH_FILE));
      oos.writeObject(GH);
      oos.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public boolean loadRestaurantData() {
    File f = new File(RESTAURANT_PATH_FILE);
    boolean loaded = false;
    if(f.exists()){
      ObjectInputStream ois;
      try {
        ois = new ObjectInputStream(new FileInputStream(f));
        GH = (Restaurant)ois.readObject();
        ois.close();
      } catch (IOException | ClassNotFoundException e) {
        System.out.println("File is empty or something else went wrong.");
        e.fillInStackTrace();
        e.printStackTrace();
      }
      loaded = true;
    }
    return loaded;
  }

}
