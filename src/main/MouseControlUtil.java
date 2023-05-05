package main;

import javafx.scene.Node;

public class MouseControlUtil {
	private static double orgSceneX, orgSceneY;
	private static double orgTranslateX, orgTranslateY;

	public static void makeDraggable(Node node) {
		node.setOnMousePressed(e -> {
			orgSceneX = e.getSceneX();
			orgSceneY = e.getSceneY();
			orgTranslateX = ((Node) e.getSource()).getTranslateX();
			orgTranslateY = ((Node) e.getSource()).getTranslateY();
			((Node) (e.getSource())).toFront();
		});
		node.setOnMouseDragged(e -> {
			double offsetX = e.getSceneX() - orgSceneX;
			double offsetY = e.getSceneY() - orgSceneY;
			double newTranslateX = orgTranslateX + offsetX;
			double newTranslateY = orgTranslateY + offsetY;
			((Node) (e.getSource())).setTranslateX(newTranslateX);
			((Node) (e.getSource())).setTranslateY(newTranslateY);
		});
	}
}