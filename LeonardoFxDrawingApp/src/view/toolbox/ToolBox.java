package view.toolbox;

import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.ImageCursor;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;
import view.MainView;

/**
 * This Class describes the Tools needed to modify a picture: -> pencil ->
 * eraser -> fillpaint -> textWriting
 * 
 * @author Ludov
 *
 */
public class ToolBox extends VBox {

	private MainView mainView;
	private ColorPicker colorPicker;
	private Slider pencilSlider;
	private Label pencilSliderLabel;
	private Button pencilToolButton = new Button();
	private Button fillingToolButton = new Button();
	private Button textToolButton = new Button();
	private Button eraserToolButton = new Button();
	private Button colorSelectionToolButton = new Button();
	private Button zoomToolButton = new Button();
	private Image pencilIcon = new Image(getClass().getResourceAsStream("/ressources/pencil-40.PNG"));
	private Image fillIcon = new Image(getClass().getResourceAsStream("/ressources/fill-40.PNG"));
	private Image colorDropper = new Image(getClass().getResourceAsStream("/ressources/color-dropper-40.PNG"));
	private Image eraser = new Image(getClass().getResourceAsStream("/ressources/eraser-40.PNG"));
	private Image text = new Image(getClass().getResourceAsStream("/ressources/text-40.PNG"));
	private Image zoomIn = new Image(getClass().getResourceAsStream("/ressources/zoom-in-40.PNG"));
	private ImageCursor pencilCursor = new ImageCursor(pencilIcon, 0, 40);
	private ImageCursor fillCursor = new ImageCursor(fillIcon, 0, 40);
	private ImageCursor colorDropperCursor = new ImageCursor(colorDropper, 0, 40);
	private ImageCursor eraserCursor = new ImageCursor(eraser, 0, 40);
	private ImageCursor textCursor = new ImageCursor(text, 0, 40);
	private ImageCursor zoomCursor = new ImageCursor(zoomIn, 0, 40);

	public ToolBox(MainView view) {
		super(5.0);
		this.mainView = view;
		GridPane gridPane = new GridPane();
		this.pencilToolButton.setGraphic(new ImageView(pencilIcon));
		this.fillingToolButton.setGraphic(new ImageView(fillIcon));
		this.textToolButton.setGraphic(new ImageView(text));
		this.eraserToolButton.setGraphic(new ImageView(eraser));
		this.colorSelectionToolButton.setGraphic(new ImageView(colorDropper));
		this.zoomToolButton.setGraphic(new ImageView(zoomIn));
		//
		this.colorPicker = createColorPicker();
		this.pencilSlider = createPencilSlider();
		//
		this.pencilToolButton.setOnAction((ActionEvent event) -> {
			onPencilButtonClicked();
		});

		this.eraserToolButton.setOnAction((ActionEvent event) -> {
			onEraserButtonClicked();
		});

		this.fillingToolButton.setOnAction((ActionEvent event) -> {
			onFillingButtonClicked();
		});

		this.colorSelectionToolButton.setOnAction((ActionEvent event) -> {
			onColorSelectionButtonClicked();
		});

		this.pencilSlider.valueProperty().addListener(e -> {
			onPencilSliderChanged();
		});

		GridPane.setConstraints(pencilToolButton, 0, 0);
		GridPane.setConstraints(fillingToolButton, 1, 0);
		GridPane.setConstraints(textToolButton, 2, 0);
		GridPane.setConstraints(eraserToolButton, 0, 1);
		GridPane.setConstraints(colorSelectionToolButton, 1, 1);
		GridPane.setConstraints(zoomToolButton, 2, 1);

		gridPane.getChildren().addAll(pencilToolButton, fillingToolButton, textToolButton, eraserToolButton,
				colorSelectionToolButton, zoomToolButton);
		this.getChildren().add(gridPane);
		this.getChildren().add(colorPicker);
		this.getChildren().addAll(pencilSlider, pencilSliderLabel);
		//
		this.setSpacing(12);
		gridPane.setPadding(new Insets(5));
	}

	/**
	 * 
	 * @return an initialized ColorPicker
	 */
	public ColorPicker createColorPicker() {
		this.colorPicker = new ColorPicker();
		colorPicker.setValue(Color.BLACK);
		return colorPicker;
	}

	/**
	 * 
	 * @return the initialized slider
	 */
	public Slider createPencilSlider() {
		this.pencilSliderLabel = new Label("5.0");
		double defaultValue = 5.0;
		this.pencilSlider = new Slider(0, 100, defaultValue);
		this.pencilSlider.setShowTickLabels(true);
		return this.pencilSlider;
	}

	/**
	 * called when pencil button is clicked
	 */
	public void onPencilButtonClicked() {
		this.mainView.setCursor(pencilCursor);

		GraphicsContext gc = this.mainView.getGraphicsContext();
		this.mainView.getDrawingMode().getCanvas().setOnMousePressed(e -> {
			gc.beginPath();
			gc.setLineCap(StrokeLineCap.ROUND);
			gc.setLineWidth(this.pencilSlider.getValue());
			gc.setStroke(colorPicker.getValue());
			gc.lineTo(e.getX(), e.getY());
			gc.stroke();
		});

		this.mainView.getDrawingMode().getCanvas().setOnMouseDragged(e -> {
			gc.setLineCap(StrokeLineCap.ROUND);
			gc.setLineWidth(this.pencilSlider.getValue());
			gc.setStroke(colorPicker.getValue());
			gc.lineTo(e.getX(), e.getY());
			gc.stroke();
		});
	}

	/**
	 * called when eraser button is clicked
	 */
	public void onEraserButtonClicked() {
		this.mainView.setCursor(eraserCursor);
		GraphicsContext gc = this.mainView.getGraphicsContext();
		this.mainView.getDrawingMode().getCanvas().setOnMousePressed(e -> {
			gc.setLineCap(StrokeLineCap.ROUND);
			gc.clearRect(e.getX(), e.getY(), this.pencilSlider.getValue(), this.pencilSlider.getValue());
			gc.lineTo(e.getX(), e.getY());
		});
		this.mainView.getDrawingMode().getCanvas().setOnMouseDragged(e -> {
			gc.setLineCap(StrokeLineCap.ROUND);
			gc.clearRect(e.getX(), e.getY(), this.pencilSlider.getValue(), this.pencilSlider.getValue());
			gc.lineTo(e.getX(), e.getY());
		});
	}

	/**
	 * called when filling button is clicked
	 */
	public void onFillingButtonClicked() {
		this.mainView.setCursor(fillCursor);
		GraphicsContext gc = this.mainView.getGraphicsContext();
		this.mainView.getDrawingMode().getCanvas().setOnMousePressed(e -> {
			gc.beginPath();
			gc.lineTo(e.getX(), e.getY());
			gc.setFill(colorPicker.getValue());
			gc.fill();
		});
		this.mainView.getDrawingMode().getCanvas().setOnMouseDragged(e -> {
			gc.lineTo(e.getX(), e.getY());
			gc.setFill(colorPicker.getValue());
			gc.fill();
		});
	}

	/**
	 * called when color selection button is clicked
	 */
	public void onColorSelectionButtonClicked() {
		this.mainView.setCursor(colorDropperCursor);
		GraphicsContext gc = this.mainView.getGraphicsContext();
		this.mainView.getDrawingMode().getCanvas().setOnMousePressed(e -> {
			Color pickedColor = (Color) gc.getFill();
			colorPicker.setValue(pickedColor);
		});
	}

	/**
	 * called when pencil slider is clicked
	 */
	public void onPencilSliderChanged() {
		GraphicsContext gc = this.mainView.getGraphicsContext();
		double value = pencilSlider.getValue();
		String str = String.format("%.1f", value);
		this.pencilSliderLabel.setText(str);
		gc.setLineWidth(value);
	}

}
