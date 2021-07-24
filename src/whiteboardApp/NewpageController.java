package whiteboardApp;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.ResourceBundle;

public class NewpageController implements Initializable {
    @FXML
    public ColorPicker colorPicker;
    @FXML
    public Canvas canvas;
    @FXML
    public TextField toolSize;
    @FXML
    public MenuButton tool;
    @FXML
    public MenuItem penTool, eraserTool;

    private void toolSelected(String _tool) {
        GraphicsContext brushTool = canvas.getGraphicsContext2D();
        tool.setText(_tool);

        canvas.setOnMouseClicked(draw -> {
            double size = (toolSize.getText().isEmpty() ? 20 : Double.parseDouble(toolSize.getText()));
            double x = draw.getX() - size / 2;
            double y = draw.getY() - size / 2;
            brushTool.setFill(_tool.equals("Pen") ? colorPicker.getValue() : canvas.getScene().getFill());
            brushTool.fillRoundRect(x, y, size, size, size, size);
        });

        canvas.setOnMouseDragged(draw -> {
            double size = (toolSize.getText().isEmpty() ? 20 : Double.parseDouble(toolSize.getText()));
            double x = draw.getX() - size / 2;
            double y = draw.getY() - size / 2;
            brushTool.setFill(_tool.equals("Pen") ? colorPicker.getValue() : canvas.getScene().getFill());
            brushTool.fillRoundRect(x, y, size, size, size, size);
        });
    }

    public void penSelected() {
        toolSelected("Pen");
    }

    public void eraserSelected() {
        toolSelected("Eraser");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        toolSelected("Pen");
    }
}