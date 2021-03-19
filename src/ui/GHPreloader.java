package ui;

import javafx.application.Preloader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class GHPreloader extends Preloader {

    private Stage preloaderStage;
    private Scene scene;

    @Override
    public void start(Stage primaryStage) {
        preloaderStage = primaryStage;
        preloaderStage.setScene(scene);
        preloaderStage.initStyle(StageStyle.UNDECORATED);
        Image icon = new Image(String.valueOf(getClass().getResource("resources/gh-icon.png")));
        primaryStage.getIcons().add(icon);
        preloaderStage.show();
    }

    @Override
    public void init() throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("splash-screen.fxml"));
        fxmlLoader.setController(MainGUIController.class);
        Parent root = fxmlLoader.load();
        scene = new Scene(root);
    }

    @Override
    public void handleStateChangeNotification(StateChangeNotification info) {
        StateChangeNotification.Type type = info.getType();
        if (type == StateChangeNotification.Type.BEFORE_START) preloaderStage.hide();
    }
}
