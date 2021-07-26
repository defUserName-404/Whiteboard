package whiteboardApp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {
    static Stage window;
    protected double screenHeight, screenWidth;

    protected void getScreenResolution() {
        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        screenWidth = primaryScreenBounds.getWidth();
        screenHeight = primaryScreenBounds.getHeight();
    }

    @Override
    public void start(Stage _window) throws Exception {
        getScreenResolution();
        _window.setTitle("Whiteboard");
        Image icon = new Image("file: resources/assets/whiteboard.png");
        _window.getIcons().add(icon);
        window = _window;
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("homepage.fxml")));
        _window.setScene(new Scene(root, screenWidth, screenHeight));
        _window.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}