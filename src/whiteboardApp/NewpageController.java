package whiteboardApp;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;

public class NewpageController {
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

    private void useTool() {

    }

    private void toolSelected(String _tool) {
        GraphicsContext brushTool = canvas.getGraphicsContext2D();
        tool.setText(_tool);

        canvas.setOnMouseClicked(draw -> {
            double size = Double.parseDouble(toolSize.getText());
            double x = draw.getX() - size / 2;
            double y = draw.getY() - size / 2;

            if (_tool.equals("Pen"))
                brushTool.setFill(colorPicker.getValue());
            else
                brushTool.setFill(canvas.getScene().getFill());

            brushTool.fillRoundRect(x, y, size, size, size, size);
        });

        canvas.setOnMouseDragged(draw -> {
            double size = Double.parseDouble(toolSize.getText());
            double x = draw.getX() - size / 2;
            double y = draw.getY() - size / 2;

            if (_tool.equals("Pen"))
                brushTool.setFill(colorPicker.getValue());
            else
                brushTool.setFill(canvas.getScene().getFill());

            brushTool.fillRoundRect(x, y, size, size, size, size);
        });
    }

    public void penSelected() {
        toolSelected("Pen");
    }

    public void eraserSelected() {
        toolSelected("Eraser");
    }
}