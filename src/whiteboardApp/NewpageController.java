package whiteboardApp;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
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
    public MenuButton tool;
    @FXML
    public MenuItem penTool, eraserTool, textTool;
    @FXML
    public ToolBar toolBar;
    @FXML
    public Spinner<Integer> sizeSpinner;
    public GraphicsContext canvasTool;

    private void toolSelected(String _tool) {
        tool.setText(_tool);
        canvasTool = canvas.getGraphicsContext2D();
        canvasTool.setStroke(_tool == "Pen" ? colorPicker.getValue() : canvas.getScene().getFill());
        if (_tool == "Pen") {
            colorPicker.setOnAction(event -> {
                canvasTool.setStroke(colorPicker.getValue());
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

    public void textSelected() {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        toolSelected("Pen");
        SpinnerValueFactory<Integer> sizeValue = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 20);
        sizeValue.setValue(1);
        sizeSpinner.setEditable(true);
        sizeSpinner.setValueFactory(sizeValue);
        sizeSpinner.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object o, Object t1) {
                int size = sizeSpinner.getValue();
                if (size > 20) size = 20;
                sizeValue.setValue(size);
                canvasTool.setLineWidth(size);
            }
        });
    }
}