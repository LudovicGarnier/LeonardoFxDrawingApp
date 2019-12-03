package view.toolbox;

import java.awt.event.MouseEvent;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.ImageCursor;
import javafx.scene.Node;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
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
	private final Button defaultCursorButton;
	private final Button pencilToolButton;
	private final Button fillingToolButton;
	private final Button textToolButton;
	private final Button eraserToolButton;
	private final Button colorSelectionToolButton;
	private final Button zoomToolButton;
	//
	private final String crayonIconUrl = "/ressources/icons8-crayon-40.png";
	private final String pinceauIconUrl = "/ressources/icons8-paint-40.png";
	private final String rollerIconUrl = "/ressources/icons8-paint-roller-40.png";
	private final String sprayerIconUrl = "/ressources/icons8-paint-sprayer-40.png";
	//
	private ComboBox<PencilComboItem> pencilStyles = new ComboBox<PencilComboItem>();
	//
	private final Image cursorIcon = new Image(getClass().getResourceAsStream("/ressources/icons8-curseur-40.png"));
	private final Image pencilIcon = new Image(getClass().getResourceAsStream("/ressources/pencil-40.PNG"));
	private final Image fillIcon = new Image(getClass().getResourceAsStream("/ressources/fill-40.PNG"));
	private final Image colorDropper = new Image(getClass().getResourceAsStream("/ressources/color-dropper-40.PNG"));
	private final Image eraser = new Image(getClass().getResourceAsStream("/ressources/eraser-40.PNG"));
	private final Image text = new Image(getClass().getResourceAsStream("/ressources/text-40.PNG"));
	private final Image zoomIn = new Image(getClass().getResourceAsStream("/ressources/zoom-in-40.PNG"));
	private final ImageCursor pencilCursor = new ImageCursor(pencilIcon, 0, 40);
	private final ImageCursor fillCursor = new ImageCursor(fillIcon, 0, 40);
	private final ImageCursor colorDropperCursor = new ImageCursor(colorDropper, 0, 40);
	private final ImageCursor eraserCursor = new ImageCursor(eraser, 0, 40);
	private final ImageCursor textCursor = new ImageCursor(text, 0, 40);
	private final ImageCursor zoomCursor = new ImageCursor(zoomIn, 0, 40);

	public ToolBox(MainView view) {
		super(5.0);
		this.mainView = view;
		GridPane gridPane = new GridPane();
		this.defaultCursorButton = new Button(null, new ImageView(cursorIcon));
		this.pencilToolButton = new Button(null, new ImageView(pencilIcon));
		this.fillingToolButton = new Button(null, new ImageView(fillIcon));
		this.textToolButton = new Button(null, new ImageView(text));
		this.eraserToolButton = new Button(null, new ImageView(eraser));
		this.colorSelectionToolButton = new Button(null, new ImageView(colorDropper));
		this.zoomToolButton = new Button(null, new ImageView(zoomIn));
		//
		this.pencilStyles = initializeComboBox();
		this.pencilStyles.getSelectionModel().selectFirst();
		this.pencilStyles.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<PencilComboItem>() {

			@Override
			public void changed(ObservableValue<? extends PencilComboItem> observable, PencilComboItem oldValue,
					PencilComboItem newValue) {
				String newvalString = newValue.getName();
				System.out.println(newvalString);
				System.out.println(newValue.getIcon());
				if (newvalString.equals(pinceauIconUrl)) {
					System.out.println("toto");
				}
			}

		});
		//
		this.colorPicker = createColorPicker();
		this.pencilSlider = createPencilSlider();
		//
		this.defaultCursorButton.setOnAction((ActionEvent event) -> {
			onDefaultButtonClicked();
		});
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

		GridPane.setConstraints(defaultCursorButton, 0, 0);
		GridPane.setConstraints(pencilToolButton, 1, 0);
		GridPane.setConstraints(fillingToolButton, 2, 0);
		GridPane.setConstraints(textToolButton, 0, 1);
		GridPane.setConstraints(eraserToolButton, 1, 1);
		GridPane.setConstraints(pencilStyles, 0, 2);
		GridPane.setConstraints(colorSelectionToolButton, 2, 1);
		GridPane.setConstraints(zoomToolButton, 2, 2);

		gridPane.getChildren().addAll(defaultCursorButton, pencilToolButton, fillingToolButton, textToolButton,
				eraserToolButton, pencilStyles, colorSelectionToolButton, zoomToolButton);
		gridPane.setVgap(3);
		this.getChildren().addAll(gridPane, colorPicker, pencilSlider, pencilSliderLabel);

		//
		this.setSpacing(12);
		gridPane.setPadding(new Insets(12));
	}

	private ComboBox<PencilComboItem> initializeComboBox() {
		final ObservableList<PencilComboItem> options = FXCollections.observableArrayList(
				new PencilComboItem("crayon", crayonIconUrl), new PencilComboItem("pinceau", pinceauIconUrl),
				new PencilComboItem("roller",rollerIconUrl), new PencilComboItem("sprayer",sprayerIconUrl));
		this.pencilStyles.setItems(options);
		this.pencilStyles.setButtonCell(new PencilComboItemListCell(null));
		this.pencilStyles.setCellFactory(listView -> new PencilComboItemListCell(this.pencilStyles));
		this.pencilStyles.setPrefWidth(40);
		this.pencilStyles.setValue(new PencilComboItem("crayon",crayonIconUrl));
		return this.pencilStyles;
	}

	/**
	 * 
	 * @author Ludov
	 *
	 */
	private static final class PencilComboItem {
		private final Image icon;
		private String name;

		public PencilComboItem(final String name, final String iconUrl) {
			this.name = name;
			this.icon = new Image(iconUrl);
		}

		public String getName() {
			return this.name;
		}

		public Image getIcon() {
			return this.icon;
		}
	}

	private static final class PencilComboItemListCell extends ListCell<PencilComboItem> {

		private final ComboBox<PencilComboItem> parent;
		private final ImageView imageView = new ImageView();
		private final Label label = new Label(null, imageView);
		private PencilComboItem lastItem;

		public PencilComboItemListCell(final ComboBox<PencilComboItem> parent) {
			super();
			this.parent = parent;
		}

		@Override
		protected void updateItem(final PencilComboItem item, final boolean empty) {
			super.updateItem(item, empty);
			lastItem = item;
			Node graphic = null;
			imageView.setImage(null);
			label.setText(null);
			if (!empty && item != null) {
				imageView.setImage(item.getIcon());
				label.setText(null);
				graphic = label;
			}
			setGraphic(graphic);
			setText(null);
		}

		private void selectInCombo(final MouseEvent mouseEvent) {
			if (parent != null) {
				parent.getSelectionModel().select(lastItem);
			}
		}
	}

	/**
	 * 
	 * @return an initialized ColorPicker
	 */
	private ColorPicker createColorPicker() {
		this.colorPicker = new ColorPicker();
		colorPicker.setValue(Color.BLACK);
		return colorPicker;
	}

	/**
	 * 
	 * @return the initialized slider
	 */
	private Slider createPencilSlider() {
		this.pencilSliderLabel = new Label("5.0");
		double defaultValue = 5.0;
		this.pencilSlider = new Slider(0, 20, defaultValue);
		this.pencilSlider.setShowTickLabels(true);
		return this.pencilSlider;
	}

	/**
	 * called when default button is clicked
	 */
	private void onDefaultButtonClicked() {
		this.mainView.setCursor(new ImageCursor());
		this.mainView.getDrawingMode().getCanvas().setOnMousePressed(e -> {
		});

		this.mainView.getDrawingMode().getCanvas().setOnMouseDragged(e -> {
		});
	}

	/**
	 * called when pencil button is clicked
	 */
	private void onPencilButtonClicked() {
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
	private void onEraserButtonClicked() {
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
	private void onFillingButtonClicked() {
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
	private void onColorSelectionButtonClicked() {
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
	private void onPencilSliderChanged() {
		GraphicsContext gc = this.mainView.getGraphicsContext();
		double value = pencilSlider.getValue();
		String str = String.format("%.1f", value);
		this.pencilSliderLabel.setText(str);
		gc.setLineWidth(value);
	}

}
