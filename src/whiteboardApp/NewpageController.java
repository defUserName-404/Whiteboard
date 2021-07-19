package whiteboardApp;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;

public class NewpageController implements Initializable {

    @FXML
    public ColorPicker colorPicker;
    @FXML
    public Canvas canvas;

    GraphicsContext brushTool;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        brushTool = canvas.getGraphicsContext2D();

        canvas.setOnMouseDragged(draw -> {
            double size = 20;
            double x = draw.getX() - size / 2;
            double y = draw.getY() - size / 2;

            brushTool.setFill(colorPicker.getValue());
            brushTool.fillRoundRect(x, y, size, size, size, size);
        });
    }
}
