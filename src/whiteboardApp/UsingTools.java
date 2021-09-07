package whiteboardApp;

import javafx.scene.Node;
import javafx.scene.paint.Color;

public class UsingTools extends NewPageController {
	public void usePenOrEraserTool() {
		super.canvasTool.setLineWidth(super.sizeSpinner.getValue());
		super.canvasTool.setStroke(super.tool.getText().equals("Pen") ? super.colorPicker.getValue() :
				Color.WHITESMOKE);
		super.canvasTool.strokeLine(super.previousX, super.previousY, super.endX, super.endY);
		super.previousX = super.endX;
		super.previousY = super.endY;
	}

	public void insertTextOrImage(Node node) {
		// TODO: fix the bug
		MouseControlUtil.makeDraggable(node);
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
}