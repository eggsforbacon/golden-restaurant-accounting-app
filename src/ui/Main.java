package ui;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

  MainGUIController controller = new MainGUIController();

  public static void main(String[] args) {
    launch();
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("splash-screen.fxml"));
    fxmlLoader.setController(controller);
    Parent root = fxmlLoader.load();
    Image icon = new Image("ui/resources/gh-icon.png");
    primaryStage.getIcons().add(icon);
    Scene scene = new Scene(root);
    primaryStage.initStyle(StageStyle.UNDECORATED);
    primaryStage.setScene(scene);
    System.out.println(primaryStage.getX());
    System.out.println(primaryStage.getY());
    primaryStage.setX(736.0);
    primaryStage.setY(356.0);
    primaryStage.show();
    System.out.println(primaryStage.getX());
    System.out.println(primaryStage.getY());
  }
}
