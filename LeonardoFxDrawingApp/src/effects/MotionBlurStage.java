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
import javafx.scene.effect.MotionBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.DrawingModel;
import view.MainView;

public class MotionBlurStage extends Stage {

	private DrawingModel model;
	private MainView view;
	private Slider angleSlider;
	private Slider radiusSlider;

	public MotionBlurStage(DrawingModel drawingModel, MainView mainView) {
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
		MotionBlur motionBlur = new MotionBlur();

		if (this.model.getImageView() != null) {
			this.model.getImageView().setEffect(motionBlur);
		}
		this.model.getCanvas().getGraphicsContext2D().setEffect(motionBlur);
		//
		applyButton.setOnAction(e -> {
			File output = new File("tmp.png");
			try {
				if (this.model.getImageView() != null) {
					this.model.getImageView().setEffect(motionBlur);
					this.model.getCanvas().getGraphicsContext2D().setEffect(motionBlur);
					ImageIO.write(SwingFXUtils.fromFXImage(this.model.getImageView().snapshot(null, null), null), "png",
							output);
					BufferedImage bufferedImage = ImageIO.read(output);
					Image image = SwingFXUtils.toFXImage(bufferedImage, null);
					ImageView tmp = new ImageView(image);
					this.model.setImageView(tmp);
					this.view.setDrawingModel(this.model);
					this.model.getImageView().setEffect(null);
				} else {
					this.model.getCanvas().getGraphicsContext2D().setEffect(motionBlur);
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

			this.model.getCanvas().getGraphicsContext2D().applyEffect(motionBlur);
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
		this.angleSlider = createEffectSlider(motionBlur, MotionBlurParametersEnum.MOTION_BLUR_ANGLE);
		this.radiusSlider = createEffectSlider(motionBlur, MotionBlurParametersEnum.MOTION_BLUR_RADIUS);
		//
		Label angleLabelVal = new Label(angleSlider.getValue() + "");
		Label radiusLabelVal = new Label(radiusSlider.getValue() + "");
		//
		Label angleLabel = new Label("Angle:");
		Label radiusLabel = new Label("Radius:");
		//
		angleSlider.valueProperty().addListener(e -> {
			onSliderChanged(angleSlider, angleLabelVal);
		});
		radiusSlider.valueProperty().addListener(e -> {
			onSliderChanged(radiusSlider, radiusLabelVal);
		});
		//
		buttonBox.getChildren().addAll(applyButton, cancelButton);
		settingBox.getChildren().addAll(angleLabel, angleSlider, angleLabelVal, radiusLabel, radiusSlider,
				radiusLabelVal);
		box.getChildren().addAll(settingBox, buttonBox);
		Scene scene = new Scene(box, 250, 415);
		this.setTitle("Motion Blur");
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

	private Slider createEffectSlider(MotionBlur motionBlur, MotionBlurParametersEnum motionBlurEnum) {
		Slider slider = new Slider();
		slider.setShowTickLabels(true);
		slider.setShowTickMarks(true);
		slider.valueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				switch (motionBlurEnum) {
				case MOTION_BLUR_ANGLE:
					slider.setBlockIncrement(1);
					motionBlur.setAngle(newValue.doubleValue());
					break;
				case MOTION_BLUR_RADIUS:
					slider.setBlockIncrement(1);
					slider.setMax(63);
					slider.setMin(0);
					motionBlur.setRadius(newValue.doubleValue());
					break;
				default:
					break;
				}

			}

		});
		return slider;
	}
}
