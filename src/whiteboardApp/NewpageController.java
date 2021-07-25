package whiteboardApp;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class NewpageController implements Initializable {
    @FXML
    public Button backButton, exportButton;
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

    private void useTool(MouseEvent mouseEvent, String _tool, char mouseAction) {
        GraphicsContext brushTool = canvas.getGraphicsContext2D();
        double size = (toolSize.getText().isEmpty() ? 10 : Double.parseDouble(toolSize.getText()));
        double x = mouseEvent.getX() - size / 2;
        double y = mouseEvent.getY() - size / 2;
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            brushTool.setFill(_tool.equals("Pen") ? colorPicker.getValue() : canvas.getScene().getFill());
            if (mouseAction == 'p') {
                brushTool.beginPath();
                brushTool.moveTo(x, y);
                brushTool.stroke();
            } else if (mouseAction == 'd') {
                brushTool.lineTo(x, y);
                brushTool.stroke();
                brushTool.closePath();
                brushTool.beginPath();
                brushTool.moveTo(x, y);
            } else {
                brushTool.lineTo(x, y);
                brushTool.stroke();
                brushTool.closePath();
            }
        }
    }

    private void toolSelected(String _tool) {
        tool.setText(_tool);
        canvas.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                useTool(mouseEvent, _tool, 'p');
            }
        });
        canvas.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                useTool(mouseEvent, _tool, 'd');
            }
        });
        canvas.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                useTool(mouseEvent, _tool, 'r');
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