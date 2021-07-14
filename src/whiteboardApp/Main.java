package whiteboardApp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application {
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Whiteboard");

        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
        double screenHeight = primaryScreenBounds.getHeight();
        double screenWidth = primaryScreenBounds.getWidth();

        Parent root = FXMLLoader.load(getClass().getResource("homepage.fxml"));
        primaryStage.setScene(new Scene(root, screenWidth, screenHeight));

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
