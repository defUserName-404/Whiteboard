package whiteboardApp;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
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

    private void useTool(MouseEvent mouseEvent, String _tool) {
        GraphicsContext brushTool = canvas.getGraphicsContext2D();
        MouseButton mouseButton = mouseEvent.getButton();
        double size = (toolSize.getText().isEmpty() ? 20 : Double.parseDouble(toolSize.getText()));
        double x = mouseEvent.getX() - size / 2;
        double y = mouseEvent.getY() - size / 2;
        if (mouseButton == MouseButton.PRIMARY) {
            brushTool.setFill(_tool.equals("Pen") ? colorPicker.getValue() : canvas.getScene().getFill());
            brushTool.fillRoundRect(x, y, size, size, size, size);
        }
    }

    private void toolSelected(String _tool) {
        tool.setText(_tool);
        canvas.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                useTool(mouseEvent, _tool);
            }
        });
        canvas.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                useTool(mouseEvent, _tool);
            }
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