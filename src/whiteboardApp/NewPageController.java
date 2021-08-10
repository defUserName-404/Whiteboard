package whiteboardApp;

import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import java.net.URL;
import java.util.Arrays;
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

    public GraphicsContext canvasToolFinal;
    double startX, startY, endX, endY, previousX, previousY;
    // currentSelectedTool in order: Pen, Eraser, Text, Shapes[Line, Rectangle, Circle]
    private final boolean[][] currentSelectedTool = {{false}, {false}, {false}, {false, false, false}};

    public void penSelected() {
        tool.setText("Pen");
        for (boolean[] booleans : currentSelectedTool) {
            Arrays.fill(booleans, false);
        }
        currentSelectedTool[0][0] = true;
    }

    public void eraserSelected() {
        tool.setText("Eraser");
        for (boolean[] booleans : currentSelectedTool) {
            Arrays.fill(booleans, false);
        }
        currentSelectedTool[1][0] = true;
    }

    // TODO: Implement the method
    public void textSelected() {
        tool.setText("Text");
    }

    public void lineShapeSelected() {
        tool.setText("Line");
        for (boolean[] booleans : currentSelectedTool) {
            Arrays.fill(booleans, false);
        }
        currentSelectedTool[3][0] = true;
    }

    public void drawCircle() {
        tool.setText("Circle");
    }

    private void usePenOrEraserTool(String _tool) {
        canvasToolFinal.setLineWidth(sizeSpinner.getValue());
        canvasToolFinal.setStroke(_tool.equals("Pen") ? colorPicker.getValue() : canvas.getScene().getFill());
        canvasToolFinal.strokeLine(previousX, previousY, endX, endY);
        previousX = endX;
        previousY = endY;
    }

    private void drawLine(boolean effect) {
        canvasToolFinal.setLineWidth(sizeSpinner.getValue());
        canvasToolFinal.setStroke(colorPicker.getValue());
        canvasToolFinal.strokeLine(startX, startY, endX, endY);
    }

    @FXML
    public void mousePressListener(MouseEvent mousePress) {
        if (mousePress.getButton() == MouseButton.PRIMARY) {
            this.startX = mousePress.getX();
            this.startY = mousePress.getY();
            this.previousX = mousePress.getX();
            this.previousY = mousePress.getY();
        }
    }

    @FXML
    public void mouseDragListener(MouseEvent mouseDrag) {
        if (mouseDrag.getButton() == MouseButton.PRIMARY) {
            this.endX = mouseDrag.getX();
            this.endY = mouseDrag.getY();

            if (currentSelectedTool[0][0]) usePenOrEraserTool("Pen");
            else if (currentSelectedTool[1][0]) usePenOrEraserTool("Eraser");
            else if (currentSelectedTool[2][0]) textSelected();
            else if (currentSelectedTool[3][0]) drawLine(true);
            else if (currentSelectedTool[3][1]) drawCircle();
        }
    }

    @FXML
    private void mouseReleaseListener(MouseEvent mouseRelease){
        if (mouseRelease.getButton() == MouseButton.PRIMARY) {
            if (currentSelectedTool[3][0]) drawLine(false);
            else if (currentSelectedTool[3][1]) drawCircle();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        canvasToolFinal = canvas.getGraphicsContext2D();
        SpinnerValueFactory<Integer> sizeValue = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 50);
        sizeValue.setValue(1);
        sizeSpinner.setEditable(true);
        sizeSpinner.setValueFactory(sizeValue);
        sizeSpinner.valueProperty().addListener((ChangeListener<? super Integer>) (observableValue, o, t1) -> {
            int size = sizeSpinner.getValue();
            if (size > 50) size = 50;
            sizeValue.setValue(size);
            canvasToolFinal.setLineWidth(size);
        });
    }
}