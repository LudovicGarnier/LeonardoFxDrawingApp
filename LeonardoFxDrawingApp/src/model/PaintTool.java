package model;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

/**
 * @author Ludov
 *
 */
public class PaintTool extends Image {

	private String url; 

	private Shape shape;

	private Color color;

	public PaintTool(String u, double requestedWidth, double requestedHeight, boolean pRatio, boolean s, Shape shape,
			Color c) {
		super(u, requestedWidth, requestedHeight, pRatio, s);
		this.shape = shape;
		this.color = c;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Shape getShape() {
		return shape;
	}

	public void setShape(Shape shape) {
		this.shape = shape;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

}
