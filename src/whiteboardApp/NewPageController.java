package whiteboardApp;

import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import java.net.URL;
import java.util.ResourceBundle;

public class NewPageController implements Initializable {
    @FXML public Button backButton, exportButton;
    @FXML public ColorPicker colorPicker;
    @FXML public Canvas canvas;
    @FXML public MenuButton tool;
    @FXML public MenuItem penTool, eraserTool, textTool;
    @FXML public ToolBar toolBar;
    @FXML public Spinner<Integer> sizeSpinner;
    @FXML public VBox canvasHolder;
    @FXML public MenuItem lineTool, circleTool, rectangleTool;
    public GraphicsContext canvasTool;

    private void toolSelected(String _tool) {
        tool.setText(_tool);
        if (_tool.equals("Pen")) {
            canvasTool.setStroke(colorPicker.getValue());
            colorPicker.setOnAction(event -> {
                canvasTool.setStroke(colorPicker.getValue());
            });
        }
        else {
            canvasTool.setStroke(canvas.getScene().getFill());
            colorPicker.setOnAction(event -> {
                canvasTool.setStroke(canvas.getScene().getFill());
            });
        }
        canvas.setOnMousePressed(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                canvasTool.beginPath();
                canvasTool.moveTo(mouseEvent.getX(), mouseEvent.getY());
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

    public void penSelected() {
        toolSelected("Pen");
    }

    public void eraserSelected() {
        toolSelected("Eraser");
    }

    // TODO
    public void textSelected() {
        tool.setText("Text");
    }

    public void drawLine() {
        tool.setText("Line");
        Line line = new Line();
        canvas.setOnMousePressed(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                canvasTool.setStroke(colorPicker.getValue());
                canvasTool.setLineWidth(sizeSpinner.getValue());
                line.setStartX(mouseEvent.getX());
                line.setStartY(mouseEvent.getY());
            }
        });
        canvas.setOnMouseReleased(mouseEvent -> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY) {
                line.setEndX(mouseEvent.getX());
                line.setEndY(mouseEvent.getY());
                canvasTool.strokeLine(line.getStartX(), line.getStartY(), line.getEndX(), line.getEndY());
            }
        });
    }

    public void drawCircle() {
        tool.setText("Circle");
        Circle circle = new Circle();
        canvas.setOnMousePressed(mouseEvent -> {
            canvasTool.setStroke(colorPicker.getValue());
            canvasTool.setLineWidth(sizeSpinner.getValue());
            canvasTool.setFill(colorPicker.getValue());
            circle.setCenterX(mouseEvent.getX());
            circle.setCenterY(mouseEvent.getY());
        });
        canvas.setOnMouseReleased(mouseEvent -> {
            circle.setRadius((Math.abs(mouseEvent.getX() - circle.getCenterX()) + Math.abs(mouseEvent.getY() + circle.getCenterY())) / 2.0);
            if (circle.getCenterX() > mouseEvent.getX()) circle.setCenterX(mouseEvent.getX());
            if (circle.getCenterY() > mouseEvent.getY()) circle.setCenterY(mouseEvent.getY());
            canvasTool.fillOval(circle.getCenterX(), circle.getCenterY(), circle.getRadius(), circle.getRadius());
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        canvasTool = canvas.getGraphicsContext2D();
        SpinnerValueFactory<Integer> sizeValue = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20);
        sizeValue.setValue(1);
        sizeSpinner.setEditable(true);
        sizeSpinner.setValueFactory(sizeValue);
        sizeSpinner.valueProperty().addListener((ChangeListener<? super Integer>) (observableValue, o, t1) -> {
            int size = sizeSpinner.getValue();
            if (size > 20) size = 20;
            sizeValue.setValue(size);
            canvasTool.setLineWidth(size);
        });
    }
}