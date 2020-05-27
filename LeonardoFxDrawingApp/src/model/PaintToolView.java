package model;

import javafx.scene.image.ImageView;

public class PaintToolView extends ImageView {

	private PaintTool paintTool;

	public PaintToolView() {
	}
	
	public PaintToolView(PaintTool paintTool) {
		this.paintTool = paintTool;
	}

	public PaintTool getPaintTool() {
		return paintTool;
	}

	public void setPaintTool(PaintTool paintTool) {
		this.paintTool = paintTool;
	}
	
	
}
