package view;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

import javax.imageio.ImageIO;

import effects.ColorAdjustEffect;
import effects.GaussianBlurEffect;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.DrawingModel;
import view.toolbox.ToolBox;

/**
 * This class represent the main view. It is composed by different elements such
 * as the ToolBox, the canvas Model..
 * 
 * @author Ludov
 *
 */
public class MainView extends Parent {

	private MenuBar menuBar;
	private DrawingModel drawingModel;
	private StackPane canvasPane;
	private Scene scene;
	private Stage stage;
	private BorderPane borderPane;

	/**
	 * Constructor
	 * 
	 * @param cont
	 * @param stage
	 */
	public MainView(Stage stage) {
		super();
		this.drawingModel = new DrawingModel(1720, 960, null);
		this.stage = stage;
	}

	public void setCursor(ImageCursor imageCursor) {
		this.scene.setCursor(imageCursor);
	}

	/**
	 * Initialize the main view by initializing its components
	 * 
	 * @return
	 */
	public Scene initializeMainView() {
		this.canvasPane = new StackPane();
		this.canvasPane.getChildren().add(this.drawingModel);
		//
		this.menuBar = initalizeMenuBar();
		ToolBox toolBox = new ToolBox(this);
		//
		this.borderPane = new BorderPane();
		this.borderPane.setCenter(canvasPane);
		this.borderPane.setTop(this.menuBar);
		this.borderPane.setLeft(toolBox);
		this.scene = new Scene(borderPane);
		this.scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

		this.menuBar.getStyleClass().add("menuBar");
		this.canvasPane.getStyleClass().add("canvaspane");
		toolBox.getStyleClass().add("toolBox");
		return this.scene;
	}

	public DrawingModel getDrawingMode() {
		return this.drawingModel;
	}

	public void setDrawingModel(DrawingModel d) {
		this.drawingModel = d;
	}

	public StackPane getcanvasPane() {
		return this.canvasPane;
	}

	public Stage getStage() {
		return this.stage;
	}

	public BorderPane getBorderpane() {
		return this.borderPane;
	}

	/**
	 * 
	 * @return the Canvas' GraphicsContext
	 */
	public GraphicsContext getGraphicsContext() {
		return this.drawingModel.getCanvas().getGraphicsContext2D();
	}

	/**
	 * 
	 * @return an initialized MenuBar
	 */
	public MenuBar initalizeMenuBar() {
		this.menuBar = new MenuBar();
		Menu fileMenu = new Menu("File");
		Menu helpMenu = new Menu("Help");
		// File Menu
		MenuItem newFileItem = new MenuItem("New");
		MenuItem openFileItem = new MenuItem("Open");
		MenuItem closeFileItem = new MenuItem("Close");
		MenuItem saveFileItem = new MenuItem("Save");
		MenuItem saveAsFileItem = new MenuItem("Save As");
		MenuItem printFileItem = new MenuItem("Print");
		MenuItem exitItem = new MenuItem("Exit");
		fileMenu.getItems().addAll(newFileItem, openFileItem, new SeparatorMenuItem(), closeFileItem, saveFileItem,
				saveAsFileItem, new SeparatorMenuItem(), printFileItem, new SeparatorMenuItem(), exitItem);
		// Edit Menu
		Menu editMenu = new Menu("Edit");
		MenuItem undoItem = new MenuItem("Undo");
		MenuItem redoItem = new MenuItem("Redo");
		MenuItem preferencesItem = new MenuItem("Preferences");
		editMenu.getItems().addAll(undoItem, redoItem, new SeparatorMenuItem(), preferencesItem);
		// Transform Menu
		Menu transformMenu = new Menu("Transform");
		MenuItem rotateItem = new MenuItem("Rotate");
		MenuItem translateItem = new MenuItem("Translate");
		MenuItem scalingItem = new MenuItem("Scale");
		MenuItem shearingItem = new MenuItem("Shearing");
		MenuItem transformationItem = new MenuItem("Transformation");
		transformMenu.getItems().addAll(rotateItem, translateItem, scalingItem, shearingItem, transformationItem);
		// Effect Menu
		Menu effectMenu = new Menu("Effect");
		MenuItem bloomItem = new MenuItem("Bloom");
		MenuItem colorAdjustItem = new MenuItem("Color Adjust");
		colorAdjustItem.setOnAction(e -> {
//			if (this.drawingModel.getImageView() != null) {
			System.out.println(this.drawingModel.getImageView());
			new ColorAdjustEffect(this.drawingModel, this);

//			}
		});
		//
		Menu blurtMenu = new Menu("Blur");
		MenuItem gaussianBlurItem = new MenuItem("Gaussian Blur");
		gaussianBlurItem.setOnAction(e -> {
			GaussianBlurEffect gaussianBlur = new GaussianBlurEffect(this.drawingModel.getImageView());
		});
		MenuItem motionBlurItem = new MenuItem("Motion Blur");
		//
		Menu shadowtMenu = new Menu("Shadow");
		MenuItem dropShadowItem = new MenuItem("Drop Shadow");
		MenuItem innerShadowItem = new MenuItem("Inner Shadow");
		//
		MenuItem glowItem = new MenuItem("Glow");
		MenuItem lightingItem = new MenuItem("Lighting");
		MenuItem reflectionItem = new MenuItem("Reflection");
		MenuItem sepiaToneItem = new MenuItem("Sepia Tone");
		MenuItem perspectiveTransformItem = new MenuItem("Perspective Transform");

		shadowtMenu.getItems().addAll(dropShadowItem, innerShadowItem);
		blurtMenu.getItems().addAll(gaussianBlurItem, motionBlurItem);
		effectMenu.getItems().addAll(bloomItem, blurtMenu, shadowtMenu, glowItem, lightingItem, reflectionItem,
				colorAdjustItem, sepiaToneItem, perspectiveTransformItem);
		//
		newFileItem.setMnemonicParsing(true);
		newFileItem.setAccelerator(new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN));
		openFileItem.setMnemonicParsing(true);
		openFileItem.setAccelerator(new KeyCodeCombination(KeyCode.O, KeyCombination.CONTROL_DOWN));
		closeFileItem.setMnemonicParsing(true);
		closeFileItem.setAccelerator(new KeyCodeCombination(KeyCode.W, KeyCombination.CONTROL_DOWN));
		saveFileItem.setMnemonicParsing(true);
		saveFileItem.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));
		saveAsFileItem.setMnemonicParsing(true);
		saveAsFileItem.setAccelerator(
				new KeyCodeCombination(KeyCode.S, KeyCombination.SHORTCUT_DOWN, KeyCombination.SHIFT_DOWN));
		printFileItem.setMnemonicParsing(true);
		printFileItem.setAccelerator(new KeyCodeCombination(KeyCode.P, KeyCombination.CONTROL_DOWN));
		exitItem.setMnemonicParsing(true);
		exitItem.setAccelerator(new KeyCodeCombination(KeyCode.X, KeyCombination.CONTROL_DOWN));
		//
		undoItem.setMnemonicParsing(true);
		undoItem.setAccelerator(new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN));
		redoItem.setMnemonicParsing(true);
		redoItem.setAccelerator(new KeyCodeCombination(KeyCode.Y, KeyCombination.CONTROL_DOWN));
		//
		newFileItem.setOnAction(e -> {
			newFile();
		});
		openFileItem.setOnAction(e -> {
			openFileChooser();
		});
		closeFileItem.setOnAction(e -> {
			closeFile();
		});
		saveAsFileItem.setOnAction(e -> {
			saveAsFileChooser();
		});
		exitItem.setOnAction(e -> {
			exit();
		});

		this.menuBar.getMenus().add(fileMenu);
		this.menuBar.getMenus().add(editMenu);
		this.menuBar.getMenus().add(transformMenu);
		this.menuBar.getMenus().add(effectMenu);
		this.menuBar.getMenus().add(helpMenu);
		return menuBar;
	}

	/**
	 * Create a New File
	 */
	public void newFile() {
		Alert alertNewFile = new Alert(AlertType.NONE, "", ButtonType.CANCEL, ButtonType.OK);
		alertNewFile.setTitle("New Canvas");
		DialogPane dialogPane = alertNewFile.getDialogPane();
		dialogPane.setPadding(new Insets(10));
		dialogPane.setPrefHeight(150);
		VBox setting = new VBox();
		setting.setPrefWidth(150);
		//
		TextField heightTextField = new TextField("");
		TextField widthTextField = new TextField("");
		//
		Label heightLabel = new Label("Height");
		Label widthLabel = new Label("Width");
		setting.getChildren().addAll(heightLabel, heightTextField, widthLabel, widthTextField);
		//
		dialogPane.setContent(setting);
		alertNewFile.setDialogPane(dialogPane);
		Optional<ButtonType> result = alertNewFile.showAndWait();
		if (!result.isPresent()) {

		} else if (result.get() == ButtonType.OK) {
			closeFile();

		} else if (result.get() == ButtonType.CANCEL) {
			alertNewFile.close();
		}
	}

	/**
	 * Save the DrawingModel ImageView and Canvas
	 */
	public void saveAsFileChooser() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save Picture");
		fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		fileChooser.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("All Images", "*.jpg", "*.jpeg", "*.png", "*.bmp", "*.gif"),
				new FileChooser.ExtensionFilter("JPG", "*.jpg"), new FileChooser.ExtensionFilter("JPEG", "*.jpeg"),
				new FileChooser.ExtensionFilter("PNG", "*.png"), new FileChooser.ExtensionFilter("BMP", "*.bmp"),
				new FileChooser.ExtensionFilter("GIF", "*.gif"));

		File file = fileChooser.showSaveDialog(getStage());
		if (file != null) {
			try {
				WritableImage writableImage = new WritableImage((int) this.drawingModel.getCanvas().getWidth(),
						(int) this.drawingModel.getCanvas().getHeight());
				this.drawingModel.snapshot(null, writableImage);
				RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
				ImageIO.write(renderedImage, "png", file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Open a selected File
	 */
	public void openFileChooser() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Select Picture");
		fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		fileChooser.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("All Images", "*.jpg", "*.jpeg", "*.png", "*.bmp", "*.gif"),
				new FileChooser.ExtensionFilter("JPG", "*.jpg"), new FileChooser.ExtensionFilter("JPEG", "*.jpeg"),
				new FileChooser.ExtensionFilter("PNG", "*.png"), new FileChooser.ExtensionFilter("BMP", "*.bmp"),
				new FileChooser.ExtensionFilter("GIF", "*.gif"));

		File file = fileChooser.showOpenDialog(null);

		try {
			BufferedImage bufferedImage = ImageIO.read(file);
			Image image = SwingFXUtils.toFXImage(bufferedImage, null);
			System.out.println("File opened --> " + file.toURI().toString());
			ImageView view = new ImageView(image);
			DrawingModel tmp = this.drawingModel;
			// si l'image est un portrait plus grand que le pane actuel
			if (image.getHeight() > image.getWidth() && image.getHeight() > this.drawingModel.getPaneHeight()) {
				view.setFitHeight(100);
				view.setPreserveRatio(true);
				this.drawingModel = new DrawingModel(view.getFitWidth(), view.getFitHeight(), view);
				view.setSmooth(true);
				view.setCache(true);
				// si l'image est un paysage plus grand que le pane actuel
			} else if (image.getWidth() > image.getHeight() && image.getWidth() > this.drawingModel.getPaneWidth()) {
				view.setFitWidth(this.drawingModel.getPaneWidth());
				view.setPreserveRatio(true);
				view.setSmooth(true);
				view.setCache(true);
				System.out.println();
				this.drawingModel = new DrawingModel(view.getFitWidth(), view.getFitHeight(), view);
			} else {
				this.drawingModel = new DrawingModel(image.getWidth(), image.getHeight(), view);
			}
//			ScrollPane scrollPane = new ScrollPane(drawingModel);
//			scrollPane.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
//			scrollPane.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
//			this.mainView.getBorderpane().setCenter(scrollPane);
			this.drawingModel.setImageView(view);
			this.getBorderpane().setCenter(this.drawingModel);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Exit the app
	 */
	public void exit() {
		System.exit(1);
	}

	/**
	 * Close the drawingModel
	 */
	public void closeFile() {
		this.getBorderpane().setCenter(null);
	}
}
