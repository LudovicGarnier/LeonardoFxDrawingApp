package effects;

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
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.DrawingModel;
import view.MainView;

public class GaussianBlurStage extends Stage {

	private DrawingModel model;
	private Slider radiusSlider;
	private MainView view;

	public GaussianBlurStage(DrawingModel drawingModel, MainView mainView) {
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
		GaussianBlur gaussianBlur = new GaussianBlur();
		//

		if (this.model.getImageView() != null) {
			this.model.getImageView().setEffect(gaussianBlur);
		}
		this.model.getCanvas().getGraphicsContext2D().setEffect(gaussianBlur);

		//
		applyButton.setOnAction(e -> {
			File output = new File("tmp.png");
			try {
				if (this.model.getImageView() != null) {
					this.model.getImageView().setEffect(gaussianBlur);
					this.model.getCanvas().getGraphicsContext2D().setEffect(gaussianBlur);
					ImageIO.write(SwingFXUtils.fromFXImage(this.model.getImageView().snapshot(null, null), null), "png",
							output);
					BufferedImage bufferedImage = ImageIO.read(output);
					Image image = SwingFXUtils.toFXImage(bufferedImage, null);
					ImageView tmp = new ImageView(image);
					this.model.setImageView(tmp);
					this.view.setDrawingModel(this.model);
					this.model.getImageView().setEffect(null);
				} else {
					this.model.getCanvas().getGraphicsContext2D().setEffect(gaussianBlur);
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

			this.model.getCanvas().getGraphicsContext2D().applyEffect(gaussianBlur);
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
		this.radiusSlider = createEffectSlider(gaussianBlur);
		//
		Label radiusLabelVal = new Label(radiusSlider.getValue() + "");
		//
		Label radiusLabel = new Label("Radius:");
		//
		radiusSlider.valueProperty().addListener(e -> {
			onSliderChanged(radiusSlider, radiusLabelVal);
		});
		//
		buttonBox.getChildren().addAll(applyButton, cancelButton);
		settingBox.getChildren().addAll(radiusLabel, radiusSlider, radiusLabelVal);
		box.getChildren().addAll(settingBox, buttonBox);
		Scene scene = new Scene(box, 250, 155);
		this.setTitle("Gaussian Blur");
		this.setResizable(false);
		this.initStyle(StageStyle.UTILITY);
		this.setOnCloseRequest(e -> {
			if (this.model.getImageView() != null) {
				this.model.getImageView().setEffect(null);
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

	public Slider createEffectSlider(GaussianBlur gaussianBlur) {
		Slider slider = new Slider(0, 63, 10);
		slider.setBlockIncrement(1);
		slider.setShowTickLabels(true);
		slider.setShowTickMarks(true);
		slider.valueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {

				gaussianBlur.setRadius(newValue.doubleValue());

			}

		});
		return slider;
	}
}
