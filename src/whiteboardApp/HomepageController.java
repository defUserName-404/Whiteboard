package whiteboardApp;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class HomepageController extends Main {
    public void onNewButtonClicked() throws Exception {
        Parent newpage = FXMLLoader.load(getClass().getResource("newpage.fxml"));
        getScreenResolution();
        window.setScene(new Scene(newpage, screenWidth, screenHeight));
        window.show();
    }
}