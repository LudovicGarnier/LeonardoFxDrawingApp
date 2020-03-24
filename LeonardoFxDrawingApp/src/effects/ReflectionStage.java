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
import javafx.scene.effect.Reflection;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.DrawingModel;
import view.MainView;

public class ReflectionStage extends Stage {
	private DrawingModel model;
	private MainView view;
	private Slider topOffsetSlider;
	private Slider topOpacitySlider;
	private Slider bottomOpacitySlider;
	private Slider fractionSlider;

	public ReflectionStage(DrawingModel drawingModel, MainView mainView) {
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
		Reflection reflection = new Reflection();

		if (this.model.getImageView() != null) {
			this.model.getImageView().setEffect(reflection);
		}
		this.model.getCanvas().getGraphicsContext2D().setEffect(reflection);

		//
		applyButton.setOnAction(e -> {
			File output = new File("tmp.png");
			try {
				if (this.model.getImageView() != null) {
					this.model.getImageView().setEffect(reflection);
					this.model.getCanvas().getGraphicsContext2D().setEffect(reflection);
					ImageIO.write(SwingFXUtils.fromFXImage(this.model.getImageView().snapshot(null, null), null), "png",
							output);
					BufferedImage bufferedImage = ImageIO.read(output);
					Image image = SwingFXUtils.toFXImage(bufferedImage, null);
					ImageView tmp = new ImageView(image);
					this.model.setImageView(tmp);
					this.view.setDrawingModel(this.model);
					this.model.getImageView().setEffect(null);
				} else {
					this.model.getCanvas().getGraphicsContext2D().setEffect(reflection);
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

			this.model.getCanvas().getGraphicsContext2D().applyEffect(reflection);
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
		this.topOffsetSlider = createEffectSlider(reflection, ReflectionParametersEnum.REFLECTION_TOP_OFFSET);
		this.topOpacitySlider = createEffectSlider(reflection, ReflectionParametersEnum.REFLECTION_TOP_OPACITY);
		this.bottomOpacitySlider = createEffectSlider(reflection, ReflectionParametersEnum.REFLECTION_BOTTOM_OPACITY);
		this.fractionSlider = createEffectSlider(reflection, ReflectionParametersEnum.REFLECTION_FRACTION);
		//
		Label topOffsetLabelVal = new Label(topOffsetSlider.getValue() + "");
		Label topOpacityLabelVal = new Label(topOpacitySlider.getValue() + "");
		Label bottomOpacityLabelVal = new Label(bottomOpacitySlider.getValue() + "");
		Label fractionLabelVal = new Label(fractionSlider.getValue() + "");
		//
		Label topOffsetLabel = new Label("Top Offset:");
		Label topOpacityLabel = new Label("Top Opacity:");
		Label bottomOpacityLabel = new Label("Bottom Opacity:");
		Label fractionLabel = new Label("Fraction:");
		//
		topOffsetSlider.valueProperty().addListener(e -> {
			onSliderChanged(topOffsetSlider, topOffsetLabelVal);
		});
		topOpacitySlider.valueProperty().addListener(e -> {
			onSliderChanged(topOpacitySlider, topOpacityLabelVal);
		});
		bottomOpacitySlider.valueProperty().addListener(e -> {
			onSliderChanged(bottomOpacitySlider, bottomOpacityLabelVal);
		});
		fractionSlider.valueProperty().addListener(e -> {
			onSliderChanged(fractionSlider, fractionLabelVal);
		});
		//
		buttonBox.getChildren().addAll(applyButton, cancelButton);
		settingBox.getChildren().addAll(topOffsetLabel, topOffsetSlider, topOffsetLabelVal, topOpacityLabel,
				topOpacitySlider, topOpacityLabelVal, bottomOpacityLabel, bottomOpacitySlider, bottomOpacityLabelVal,
				fractionLabel, fractionSlider, fractionLabelVal);
		box.getChildren().addAll(settingBox, buttonBox);
		Scene scene = new Scene(box, 250, 415);
		this.setTitle("Reflection");
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
	private Slider createEffectSlider(Reflection reflection, ReflectionParametersEnum effectEnum) {
		Slider slider = new Slider();
		slider.setBlockIncrement(0.1);
		slider.setShowTickLabels(true);
		slider.setShowTickMarks(true);
		slider.valueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				switch (effectEnum) {
				case REFLECTION_TOP_OFFSET:
					slider.setBlockIncrement(0.1);
					slider.setMin(-100);
					slider.setMax(100);
					reflection.setTopOffset(newValue.doubleValue());
					break;
				case REFLECTION_TOP_OPACITY:
					slider.setBlockIncrement(0.1);
					slider.setMin(0);
					slider.setMax(1);
					reflection.setTopOpacity(newValue.doubleValue());
					break;
				case REFLECTION_BOTTOM_OPACITY:
					slider.setBlockIncrement(0.1);
					slider.setMin(0);
					slider.setMax(1);
					reflection.setBottomOpacity(newValue.doubleValue());
					break;
				case REFLECTION_FRACTION:
					slider.setBlockIncrement(0.1);
					slider.setMin(0);
					slider.setMax(1);
					reflection.setFraction(newValue.doubleValue());
					break;
				default:
					break;
				}

			}

		});
		return slider;
	}
}
