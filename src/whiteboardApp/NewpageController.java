package whiteboardApp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.paint.Color;
import java.net.URL;
import java.util.ResourceBundle;

public class NewpageController implements Initializable {
    @FXML
    public Button backButton, exportButton, sizeButton;
    @FXML
    public ColorPicker colorPicker;
    @FXML
    public Canvas canvas;
    @FXML
    public MenuButton tool;
    @FXML
    public MenuItem penTool, eraserTool;
    @FXML
    public ToolBar toolBar;
    public GraphicsContext canvasTool;

    private void toolSelected(String _tool) {
        tool.setText(_tool);
        canvasTool = canvas.getGraphicsContext2D();
        canvasTool.setStroke(_tool == "Pen" ? Color.BLACK : canvas.getScene().getFill());
        canvasTool.setLineWidth(1);
        if (_tool == "Pen") {
            colorPicker.setOnAction(event -> {
                canvasTool.setStroke(colorPicker.getValue());
            });
        }
        canvas.setOnMousePressed(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                canvasTool.beginPath();
                canvasTool.lineTo(mouseEvent.getX(), mouseEvent.getY());
                canvasTool.stroke();
            }
        });
        canvas.setOnMouseDragged(mouseEvent ->  {
                if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                    canvasTool.lineTo(mouseEvent.getX(), mouseEvent.getY());
                    canvasTool.stroke();
                }
        });
    }

    public void sizeButtonSelected(ActionEvent actionEvent) {
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