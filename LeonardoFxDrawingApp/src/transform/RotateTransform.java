package transform;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.DrawingModel;
import view.MainView;

public class RotateTransform extends Stage{

	
	private DrawingModel model;
	private MainView view;
	private Slider angleSlider;
	private int angle;

	public RotateTransform(DrawingModel drawingModel, MainView mainView) {
		super();
		this.model = drawingModel;
		this.view = mainView;
		VBox box = new VBox(5);
		VBox settingBox = new VBox(5);
		Button applyButton = new Button("Ok");
		Button cancelButton = new Button("Cancel");
		HBox buttonBox = new HBox(5);
		settingBox.setPrefWidth(300);
		settingBox.setPadding(new Insets(12));
		buttonBox.setPadding(new Insets(12));
		//
		Rotate rotate = new Rotate(angle);
		
		if (this.model.getImageView() != null) {
			this.model.getImageView().getTransforms().add(rotate);
		}
//		this.model.getCanvas().getGraphicsContext2D().getTransform().add

		//
		applyButton.setOnAction(e -> {
			File output = new File("tmp.png");
			try {
				if (this.model.getImageView() != null) {
					this.model.getImageView().getTransforms().add(rotate);
					this.model.getCanvas().getGraphicsContext2D().getTransform().append(rotate);
					ImageIO.write(SwingFXUtils.fromFXImage(this.model.getImageView().snapshot(null, null), null), "png",
							output);
					BufferedImage bufferedImage = ImageIO.read(output);
					Image image = SwingFXUtils.toFXImage(bufferedImage, null);
					ImageView tmp = new ImageView(image);
					this.model.setImageView(tmp);
					this.view.setDrawingModel(this.model);
					this.model.getImageView().setEffect(null);
				} else {
					this.model.getCanvas().getGraphicsContext2D().getTransform().append(rotate);
					ImageIO.write(SwingFXUtils.fromFXImage(this.model.getCanvas().snapshot(null, null), null), "png",
							output);
					BufferedImage bufferedImage = ImageIO.read(output);
					Image image = SwingFXUtils.toFXImage(bufferedImage, null);
					ImageView tmp = new ImageView(image);
					this.model.setImageView(tmp);
					this.view.setDrawingModel(this.model);
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			this.model.getCanvas().getGraphicsContext2D().getTransform().append(rotate);
			if (this.model.getImageView() != null) {
				this.model.getImageView().setEffect(null);
			}
			this.close();

		});

		cancelButton.setOnAction(e -> {
			if (this.model.getImageView() != null) {
				this.model.getImageView().setEffect(null);
			}
			this.close();
		});
		//
		this.angleSlider = createEffectSlider(rotate);
		Label angleLabelVal = new Label(angleSlider.getValue() + "");
		Label angleLabel = new Label("Rotation Angle:");
		//
		angleSlider.valueProperty().addListener(e -> {
			onSliderChanged(angleSlider, angleLabelVal);
		});
		//
		buttonBox.getChildren().addAll(applyButton, cancelButton);
		settingBox.getChildren().addAll(angleLabel, angleSlider, angleLabelVal);
		box.getChildren().addAll(settingBox, buttonBox);
		Scene scene = new Scene(box, 250, 155);
		this.setTitle("Rotate");
		this.setResizable(false);
		this.initStyle(StageStyle.UTILITY);
		this.setOnCloseRequest(e -> {
			if (this.model.getImageView() != null) {
				this.model.getImageView().getTransforms().add(null);
			}
		});
		this.setScene(scene);
		this.show();
	}

	private void onSliderChanged(Slider slider, Label label) {
		double value = slider.getValue();
		String str = String.format("%.1f", value);
		label.setText(str);

	}

	private Slider createEffectSlider(Rotate rotate) {
		Slider slider = new Slider(0, 360, 0);
		slider.setBlockIncrement(1);
		slider.setShowTickLabels(true);
		slider.setShowTickMarks(true);
		slider.valueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

				rotate.setAngle(newValue.doubleValue());

			}

		});
		return slider;
	}
}