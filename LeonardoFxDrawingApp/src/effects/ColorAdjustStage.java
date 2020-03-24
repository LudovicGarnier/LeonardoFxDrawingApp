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
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.DrawingModel;
import view.MainView;

/**
 * 
 * @author Ludov
 *
 */
public class ColorAdjustStage extends Stage {

	private Slider contrastSlider;
	private DrawingModel model;
	private Slider hueSlider;
	private MainView view;
	private Slider brightnessSlider;
	private Slider saturationSlider;

	public ColorAdjustStage(DrawingModel drawingModel, MainView mainView) {
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
		ColorAdjust colorAdjust = new ColorAdjust();

		if (this.model.getImageView() != null) {
			this.model.getImageView().setEffect(colorAdjust);
		}
		this.model.getCanvas().getGraphicsContext2D().setEffect(colorAdjust);

		//
		applyButton.setOnAction(e -> {
			File output = new File("tmp.png");
			try {
				if (this.model.getImageView() != null) {
					this.model.getImageView().setEffect(colorAdjust);
					this.model.getCanvas().getGraphicsContext2D().setEffect(colorAdjust);
					ImageIO.write(SwingFXUtils.fromFXImage(this.model.getImageView().snapshot(null, null), null), "png",
							output);
					BufferedImage bufferedImage = ImageIO.read(output);
					Image image = SwingFXUtils.toFXImage(bufferedImage, null);
					ImageView tmp = new ImageView(image);
					this.model.setImageView(tmp);
					this.view.setDrawingModel(this.model);
					this.model.getImageView().setEffect(null);
				} else {
					this.model.getCanvas().getGraphicsContext2D().setEffect(colorAdjust);
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

			this.model.getCanvas().getGraphicsContext2D().applyEffect(colorAdjust);
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
		this.contrastSlider = createEffectSlider(colorAdjust, ColorAdjustParametersEnum.ADJUST_TYPE_CONTRAST);
		this.hueSlider = createEffectSlider(colorAdjust, ColorAdjustParametersEnum.ADJUST_TYPE_HUE);
		this.brightnessSlider = createEffectSlider(colorAdjust, ColorAdjustParametersEnum.ADJUST_TYPE_BRIGHTNESS);
		this.saturationSlider = createEffectSlider(colorAdjust, ColorAdjustParametersEnum.ADJUST_TYPE_SATURATION);
		//
		Label contrastLabelVal = new Label(contrastSlider.getValue() + "");
		Label hueLabelVal = new Label(hueSlider.getValue() + "");
		Label brightnessLabelVal = new Label(brightnessSlider.getValue() + "");
		Label saturationLabelVal = new Label(saturationSlider.getValue() + "");
		//
		Label contrastLabel = new Label("Contrast:");
		Label hueLabel = new Label("Hue:");
		Label saturationLabel = new Label("Saturation:");
		Label brightnessLabel = new Label("Brightness:");
		//
		contrastSlider.valueProperty().addListener(e -> {
			onSliderChanged(contrastSlider, contrastLabelVal);
		});
		hueSlider.valueProperty().addListener(e -> {
			onSliderChanged(hueSlider, hueLabelVal);
		});
		brightnessSlider.valueProperty().addListener(e -> {
			onSliderChanged(brightnessSlider, brightnessLabelVal);
		});
		saturationSlider.valueProperty().addListener(e -> {
			onSliderChanged(saturationSlider, saturationLabelVal);
		});
		//
		buttonBox.getChildren().addAll(applyButton, cancelButton);
		settingBox.getChildren().addAll(contrastLabel, contrastSlider, contrastLabelVal, hueLabel, hueSlider,
				hueLabelVal, brightnessLabel, brightnessSlider, brightnessLabelVal, saturationLabel, saturationSlider,
				saturationLabelVal);
		box.getChildren().addAll(settingBox, buttonBox);
		Scene scene = new Scene(box, 250, 415);
		this.setTitle("Color Adjust");
		this.setResizable(false);
		this.initStyle(StageStyle.UTILITY);this.setOnCloseRequest(e-> {
			if (this.model.getImageView() != null) {
				this.model.getImageView().setEffect(null);
			}		});
		this.setScene(scene);
		this.show();
	}

	/**
	 * 
	 */
	private void onSliderChanged(Slider slider, Label label) {
		double value = slider.getValue();
		String str = String.format("%.1f", value);
		label.setText(str);
	}

	/**
	 * 
	 * @param colorAdjust
	 * @param effectEnum
	 * @return
	 */
	private Slider createEffectSlider(ColorAdjust colorAdjust, ColorAdjustParametersEnum effectEnum) {
		Slider slider = new Slider(-1, 1, 0);
		slider.setBlockIncrement(0.1);
		slider.setShowTickLabels(true);
		slider.setShowTickMarks(true);
		slider.valueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				switch (effectEnum) {
				case ADJUST_TYPE_HUE:
					colorAdjust.setHue(newValue.doubleValue());
					break;
				case ADJUST_TYPE_CONTRAST:
					colorAdjust.setContrast(newValue.doubleValue());
					break;
				case ADJUST_TYPE_BRIGHTNESS:
					colorAdjust.setBrightness(newValue.doubleValue());
					break;
				case ADJUST_TYPE_SATURATION:
					colorAdjust.setSaturation(newValue.doubleValue());
					break;
				default:
					break;
				}

			}

		});
		return slider;
	}

}
