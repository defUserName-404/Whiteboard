package main;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.util.Objects;

public class HomePageController extends Main {
	public void onNewButtonClicked() throws Exception {
		Parent newpage = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("UI/newpage.fxml")));
		getScreenResolution();
		window.setScene(new Scene(newpage, screenWidth, screenHeight));
	}
}