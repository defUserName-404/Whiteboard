package whiteboardApp;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HomepageController extends Main {
    public void onNewButtonClicked() throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("newpage.fxml"));

        getScreenResolution();

        window.setScene(new Scene(root, screenWidth, screenHeight));
    }
}