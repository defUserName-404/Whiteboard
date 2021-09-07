package whiteboardApp;

import javafx.beans.value.ChangeListener;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

public class NewPageController implements Initializable {
    @FXML public ColorPicker colorPicker;
    @FXML public Canvas canvas;
    @FXML public MenuButton tool, shapeOptions;
    @FXML public MenuItem penTool, eraserTool, textTool, lineTool, circleTool, rectangleTool, imageTool, clearTool;
    @FXML public ToolBar toolBar;
    @FXML public Spinner<Integer> sizeSpinner;
    @FXML public StackPane canvasHolder;
    @FXML public RadioMenuItem shapeFill, shapeStroke;
    private GraphicsContext canvasTool;
    private TextArea textInitializer;
    private ImageView imageViewInitializer;
    private FileChooser fileChooser;
    private double startX, startY, endX, endY, previousX, previousY;

    // currentSelectedTool in order: Pen, Eraser, Text, Shapes[Line, Rectangle, Circle], Image, clearTool
    private final boolean[][] currentSelectedTool = {{false}, {false}, {false}, {false, false, false}, {false}};

    /* ----------------------Menu Control------------------------ */
    @FXML
    private void exportButtonSelected() throws IOException {
        FileChooser.ExtensionFilter jpgFiles = new FileChooser.ExtensionFilter("Image files (*.jpg)", "*.jpg");
        FileChooser.ExtensionFilter pngFiles = new FileChooser.ExtensionFilter("Image files (*.png)", "*.png");
        fileChooser.getExtensionFilters().addAll(jpgFiles, pngFiles);
        fileChooser.setTitle("Save");
        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            WritableImage writableImage = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());
            canvasHolder.snapshot(null, writableImage);
            ImageIO.write(SwingFXUtils.fromFXImage(writableImage, null), "png", file);
        }
    }

    public void penSelected() {
        tool.setText("Pen");
        setup(0, 0);
    }

    public void eraserSelected() {
        tool.setText("Eraser");
        setup(1, 0);
    }

    public void textSelected() {
        tool.setText("Text");
        TextArea textArea = new TextArea();
        textArea.setMaxWidth(200);
        textArea.setMaxHeight(100);
        textArea.setMinWidth(200);
        textArea.setMinHeight(100);
        setup(2, 0);
        textArea.setFont(Font.font("Noto Color Emoji", 14));
        textArea.setStyle("-fx-text-fill: #8b0000;");
        textArea.setPromptText("Start Typing Here");
        textInitializer = textArea;
        canvasHolder.getChildren().add(textArea);
        MouseControlUtil.makeDraggable(textArea);
    }

    public void lineShapeSelected() {
        tool.setText("Line");
        setup(3, 0);
    }

    public void circleShapeSelected() {
        tool.setText("Circle");
        setup(3, 1);
    }

    public void rectangleShapeSelected() {
        tool.setText("Rectangle");
        setup(3, 2);
    }

    public void imageSelected() {
        tool.setText("Insert Image");
        ImageView imageView = new ImageView();
        imageView.setFitWidth(200);
        imageView.setFitHeight(100);
        imageView.setPreserveRatio(true);
        setup(4, 0);
        fileChooser.setTitle("Select a File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image files (*.jpg), (*.png)", "*.jpg", "*.png"));
        File file = fileChooser.showOpenDialog(null);
        imageView.setImage(new Image(file.toURI().toString(),  200, 100, false, false));
        imageViewInitializer = imageView;
        canvasHolder.getChildren().add(imageView);
        MouseControlUtil.makeDraggable(imageView);
    }

    public void clearAllSelected() {
        canvasTool.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        textInitializer.clear();
        canvasHolder.getChildren().removeAll(textInitializer, imageViewInitializer);
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
    public void usePenOrEraserTool() {
        canvasTool.setLineWidth(sizeSpinner.getValue());
        canvasTool.setStroke(tool.getText().equals("Pen") ? colorPicker.getValue() : Color.WHITESMOKE);
        canvasTool.strokeLine(previousX, previousY, endX, endY);
        previousX = endX;
        previousY = endY;
    }

    public void drawLine(boolean effect) {
        canvasTool.setLineWidth(sizeSpinner.getValue());
        canvasTool.setStroke(colorPicker.getValue());
        if (!effect)
            canvasTool.strokeLine(startX, startY, endX, endY);
        // TODO: Implement effects of drawing lines
    }

    public void drawCircleOrRectangle(boolean effect) {
        double positionX = endX - startX;
        double positionY = endY - startY;
        Color color = colorPicker.getValue();
        canvasTool.setLineWidth(sizeSpinner.getValue());
        if (!effect) {
            if (shapeStroke.isSelected()) {
                canvasTool.setStroke(color);
                if (tool.getText().equals("Circle"))
                    canvasTool.strokeOval(startX, startY, positionX, positionY);
                else
                    canvasTool.strokeRect(startX, startY, positionX, positionY);
            }
            else if (shapeFill.isSelected()) {
                canvasTool.setFill(color);
                if (tool.getText().equals("Circle"))
                    canvasTool.fillOval(startX, startY, positionX, positionY);
                else
                    canvasTool.fillRect(startX, startY, positionX, positionY);
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
            if (currentSelectedTool[0][0]) usePenOrEraserTool();
            else if (currentSelectedTool[1][0]) usePenOrEraserTool();
            else if (currentSelectedTool[3][0]) drawLine(true);
            else if (currentSelectedTool[3][1]) drawCircleOrRectangle(true);
            else if (currentSelectedTool[3][2]) drawCircleOrRectangle(true);
        }
    }

    @FXML
    private void mouseReleaseListener(MouseEvent mouseRelease) {
        if (mouseRelease.getButton() == MouseButton.PRIMARY) {
            if (currentSelectedTool[3][0]) drawLine(false);
            else if (currentSelectedTool[3][1]) drawCircleOrRectangle(false);
            else if (currentSelectedTool[3][2]) drawCircleOrRectangle(false);
        }
    }

    private void setup(int i, int j) {
        for (boolean[] booleans : currentSelectedTool)
            Arrays.fill(booleans, false);
        currentSelectedTool[i][j] = true;
        textInitializer.setEditable(i == 2 && j == 0);
        shapeOptions.setVisible((i == 3 && j == 1) || (i == 3 && j == 2));
    }

    /* ----------------------Initializer------------------------ */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        canvasTool = canvas.getGraphicsContext2D();
        fileChooser = new FileChooser();
        textInitializer = new TextArea();
        imageViewInitializer = new ImageView();
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