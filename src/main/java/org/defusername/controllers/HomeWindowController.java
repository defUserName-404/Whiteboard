package org.defusername.controllers;

import javafx.fxml.FXML;
import org.defusername.App;

import java.io.IOException;

public class HomeWindowController {

	@FXML
	private void switchToMainWindow() throws IOException {
		App.setRoot("mainwindow");
	}

}
