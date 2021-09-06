package whiteboardApp;

import javafx.scene.Node;

public class MouseControlUtil {
	private static double mousePositionX, mousePositionY;

	public static void makeDraggable(Node node) {
		node.setOnMousePressed(mouseEvent -> {
			mousePositionX = mouseEvent.getX();
			mousePositionY = mouseEvent.getY();
		});
		node.setOnMouseDragged(mouseEvent -> {
			node.setLayoutX(mouseEvent.getSceneX() - mousePositionX);
			node.setLayoutY(mouseEvent.getSceneY() - mousePositionY);
		});
	}
}