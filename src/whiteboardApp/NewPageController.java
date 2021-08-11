package whiteboardApp;

import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class NewPageController implements Initializable {
    @FXML public Button backButton, exportButton;
    @FXML public ColorPicker colorPicker;
    @FXML public Canvas canvas;
    @FXML public MenuButton tool, shapeOptions;
    @FXML public MenuItem penTool, eraserTool, textTool;
    @FXML public ToolBar toolBar;
    @FXML public Spinner<Integer> sizeSpinner;
    @FXML public StackPane canvasHolder;
    @FXML public MenuItem lineTool, circleTool, rectangleTool;
    @FXML public RadioMenuItem shapeFill, shapeStroke;
    @FXML public TextArea textArea;

    public GraphicsContext canvasTool;
    double startX, startY, endX, endY, previousX, previousY;
    // currentSelectedTool in order: Pen, Eraser, Text, Shapes[Line, Rectangle, Circle]
    private final boolean[][] currentSelectedTool = {{false}, {false}, {false}, {false, false, false}};

    /* ----------------------Menu Control------------------------ */
    public void penSelected() {
        tool.setText("Pen");
        for (boolean[] booleans : currentSelectedTool) {
            Arrays.fill(booleans, false);
        }
        currentSelectedTool[0][0] = true;
        textArea.setEditable(false);
    }

    public void eraserSelected() {
        tool.setText("Eraser");
        for (boolean[] booleans : currentSelectedTool) {
            Arrays.fill(booleans, false);
        }
        currentSelectedTool[1][0] = true;
        textArea.setEditable(false);
    }

    public void textSelected() {
        tool.setText("Text");
        for (boolean[] booleans : currentSelectedTool) {
            Arrays.fill(booleans, false);
        }
        currentSelectedTool[2][0] = true;
        insertText();
    }

    public void lineShapeSelected() {
        tool.setText("Line");
        for (boolean[] booleans : currentSelectedTool) {
            Arrays.fill(booleans, false);
        }
        currentSelectedTool[3][0] = true;
        textArea.setEditable(false);
    }

    public void circleShapeSelected() {
        tool.setText("Circle");
        shapeOptions.setVisible(true);
        for (boolean[] booleans : currentSelectedTool) {
            Arrays.fill(booleans, false);
        }
        currentSelectedTool[3][1] = true;
        textArea.setEditable(false);
    }

    public void rectangleShapeSelected() {
        tool.setText("Rectangle");
        shapeOptions.setVisible(true);
        for (boolean[] booleans : currentSelectedTool) {
            Arrays.fill(booleans, false);
        }
        currentSelectedTool[3][2] = true;
        textArea.setEditable(false);
    }

    @FXML
    private void shapeFillSelected() {
        shapeOptions.setText("Fill");
        shapeStroke.setSelected(false);
    }

    @FXML
    private void shapeStrokeSelected() {
        shapeOptions.setText("Stroke");
        shapeFill.setSelected(false);
    }

    /* ----------------------Using Tools------------------------ */
    private void usePenOrEraserTool(String _tool) {
        canvasTool.setLineWidth(sizeSpinner.getValue());
        canvasTool.setStroke(_tool.equals("Pen") ? colorPicker.getValue() : Color.WHITESMOKE);
        canvasTool.strokeLine(previousX, previousY, endX, endY);
        previousX = endX;
        previousY = endY;
    }

    private void insertText() {
        canvasTool.setLineWidth(sizeSpinner.getValue());
        canvasTool.setFill(colorPicker.getValue());
        textArea.setVisible(true);
        textArea.setEditable(true);
    }

    private void drawLine(boolean effect) {
        canvasTool.setLineWidth(sizeSpinner.getValue());
        canvasTool.setStroke(colorPicker.getValue());
        if (!effect)
            canvasTool.strokeLine(startX, startY, endX, endY);
        // TODO: Implement effects of drawing lines
    }

    private void drawCircleOrRectangle(boolean effect) {
        double positionX = endX - startX;
        double positionY = endY - startY;
        Color color = colorPicker.getValue();
        canvasTool.setLineWidth(sizeSpinner.getValue());
        if (!effect) {
            if (shapeStroke.isSelected()) {
                canvasTool.setStroke(color);
                if (tool.getText().equals("Circle")) {
                    canvasTool.strokeOval(startX, startY, positionX, positionY);
                } else {
                    canvasTool.strokeRect(startX, startY, positionX, positionY);
                }
            }
            else if (shapeFill.isSelected()){
                canvasTool.setFill(color);
                if (tool.getText().equals("Circle")) {
                    canvasTool.fillOval(startX, startY, positionX, positionY);
                } else {
                    canvasTool.fillRect(startX, startY, positionX, positionY);
                }
            }
        }
        // TODO: Implement effects of drawing circles and rectangles
    }

    /* ----------------------Handling mouse events on canvas------------------------ */
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
            else if (currentSelectedTool[3][0]) drawLine(true);
            else if (currentSelectedTool[3][1]) drawCircleOrRectangle(true);
            else if (currentSelectedTool[3][2]) drawCircleOrRectangle(true);
        }
    }

    @FXML
    private void mouseReleaseListener(MouseEvent mouseRelease){
        if (mouseRelease.getButton() == MouseButton.PRIMARY) {
            if (currentSelectedTool[3][0]) drawLine(false);
            else if (currentSelectedTool[3][1]) drawCircleOrRectangle(false);
            else if (currentSelectedTool[3][2]) drawCircleOrRectangle(false);
        }
    }

    /* ----------------------Initializer------------------------ */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        canvasTool = canvas.getGraphicsContext2D();
        SpinnerValueFactory<Integer> sizeValue = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 50);
        sizeValue.setValue(1);
        sizeSpinner.setEditable(true);
        sizeSpinner.setValueFactory(sizeValue);
        sizeSpinner.valueProperty().addListener((ChangeListener<? super Integer>) (observableValue, o, t1) -> {
            int size = sizeSpinner.getValue();
            if (size > 50) size = 50;
            sizeValue.setValue(size);
            canvasTool.setLineWidth(size);
        });
    }
}