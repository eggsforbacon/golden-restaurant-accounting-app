package ui;

import com.sun.javafx.application.LauncherImpl;
import javafx.application.Application;
import javafx.application.Preloader;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.Ingredient;
import model.PlateType;
import model.Restaurant;

import java.util.ArrayList;

public class Main extends Application {

  MainGUIController controller;

  public Main() {
	 
    controller = new MainGUIController();
    Restaurant GH = new Restaurant();
    PlateType pt = new PlateType("Tipo");
    ArrayList<Ingredient> ingredients = new ArrayList<>();
    ingredients.add(new Ingredient("Papa"));
    ingredients.add(new Ingredient("Tomate"));
    ArrayList<String> sizes = new ArrayList<>();
    sizes.add("Tamaño");
    ArrayList<Double> prices = new ArrayList<>();
    prices.add(5.0);
    GH.addProduct("Nombre", pt, ingredients, sizes, prices);
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
    int COUNT_LIMIT = 50000;
    for (int i = 0; i < COUNT_LIMIT; i++) {
      double progress = (100.0 * i) / COUNT_LIMIT;
      LauncherImpl.notifyPreloader(this, new Preloader.ProgressNotification(progress));
    }
  }
}
