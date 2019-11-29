package model;

import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.ImageView;

/**
 * 
 * @author Ludov
 *
 */
public class DrawingModel extends Parent {

	private ImageView imageView;
	private double paneWidth;
	private double paneHeight;
	private Canvas canvas;

	/**
	 * 
	 * @param width
	 * @param height
	 * @param view
	 */
	public DrawingModel(double width, double height, ImageView view) {

		this.paneHeight = height;
		this.paneWidth = width;
		this.canvas = new Canvas(width, height);
		this.imageView = view;
		if (view != null) {
			this.setImageView(view);
			this.canvas.setHeight(view.getImage().getHeight());
			this.canvas.setWidth(view.getImage().getWidth());
			this.getChildren().add(view);
			this.getChildren().add(this.canvas);
		} else {
			this.getChildren().add(this.canvas);
		}
		

	}

	public ImageView getImageView() {

		return this.imageView;
	}

	public void setImageView(ImageView imageView) {
		this.imageView = imageView;
	}

	public double getPaneWidth() {
		return this.paneWidth;
	}

	public void setPaneWidth(double paneWidth) {
		this.paneWidth = paneWidth;
	}

	public double getPaneHeight() {
		return this.paneHeight;
	}

	public void setPaneHeight(double d) {
		this.paneHeight = d;
	}

	public Canvas getCanvas() {
		return this.canvas;
	}

	public void setCanvas(Canvas canvas) {
		this.canvas = canvas;
	}

}
