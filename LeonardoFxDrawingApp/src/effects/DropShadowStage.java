package effects;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import model.DrawingModel;
import view.MainView;

public class DropShadowStage extends Stage {

	private DrawingModel model;
	private MainView view;
	private Slider radiusSlider;
	private Slider widthSlider;
	private Slider heightSlider;
	private Slider spreadSlider;
	private ColorPicker colorPicker;
	private ComboBox<BlurType> comboBox;
	private Slider offsetXSlider;
	private Slider offsetYSlider;

	public DropShadowStage(DrawingModel drawingModel, MainView mainView) {
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
		DropShadow dropShadow = new DropShadow();

		if (this.model.getImageView() != null) {
			this.model.getImageView().setEffect(dropShadow);
		}
		this.model.getCanvas().getGraphicsContext2D().setEffect(dropShadow);

		//
		applyButton.setOnAction(e -> {
			File output = new File("tmp.png");
			try {
				if (this.model.getImageView() != null) {
					this.model.getImageView().setEffect(dropShadow);
					this.model.getCanvas().getGraphicsContext2D().setEffect(dropShadow);
					ImageIO.write(SwingFXUtils.fromFXImage(this.model.getImageView().snapshot(null, null), null), "png",
							output);
					BufferedImage bufferedImage = ImageIO.read(output);
					Image image = SwingFXUtils.toFXImage(bufferedImage, null);
					ImageView tmp = new ImageView(image);
					this.model.setImageView(tmp);
					this.view.setDrawingModel(this.model);
					this.model.getImageView().setEffect(null);
				} else {
					this.model.getCanvas().getGraphicsContext2D().setEffect(dropShadow);
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

			this.model.getCanvas().getGraphicsContext2D().applyEffect(dropShadow);
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
		this.radiusSlider = createEffectSlider(dropShadow, DropShadowParametersEnum.DROP_SHADOW_RADIUS);
		this.widthSlider = createEffectSlider(dropShadow, DropShadowParametersEnum.DROP_SHADOW_WIDTH);
		this.heightSlider = createEffectSlider(dropShadow, DropShadowParametersEnum.DROP_SHADOW_HEIGHT);
		this.spreadSlider = createEffectSlider(dropShadow, DropShadowParametersEnum.DROP_SHADOW_SPREAD);
		this.offsetXSlider = createEffectSlider(dropShadow, DropShadowParametersEnum.DROP_SHADOW_OFFSETX);
		this.offsetYSlider = createEffectSlider(dropShadow, DropShadowParametersEnum.DROP_SHADOW_OFFSETY);
		//
		this.colorPicker = createColorPicker(dropShadow);
		//
		this.comboBox = createComboBox(dropShadow);
		//
		Label radiusLabelVal = new Label(radiusSlider.getValue() + "");
		Label widthLabelVal = new Label(widthSlider.getValue() + "");
		Label heightLabelVal = new Label(heightSlider.getValue() + "");
		Label spreadLabelVal = new Label(spreadSlider.getValue() + "");
		Label offSetXLabelVal = new Label(offsetXSlider.getValue() + "");
		Label offSetYLabelVal = new Label(offsetYSlider.getValue() + "");
		//
		Label radiusLabel = new Label("Radius:");
		Label widthLabel = new Label("Width:");
		Label heightLabel = new Label("Height:");
		Label spreadLabel = new Label("Spread:");
		Label colorLabel = new Label("Color:");
		Label comboBoxLabel = new Label("Blur Type:");
		Label offSetXLabel = new Label("OffSetX");
		Label offSetYLabel = new Label("OffSetY");
		//
		radiusSlider.valueProperty().addListener(e -> {
			onSliderChanged(radiusSlider, radiusLabelVal);
		});
		widthSlider.valueProperty().addListener(e -> {
			onSliderChanged(widthSlider, widthLabelVal);
		});
		heightSlider.valueProperty().addListener(e -> {
			onSliderChanged(heightSlider, heightLabelVal);
		});
		spreadSlider.valueProperty().addListener(e -> {
			onSliderChanged(spreadSlider, spreadLabelVal);
		});
		offsetXSlider.valueProperty().addListener(e -> {
			onSliderChanged(offsetXSlider, offSetXLabelVal);
		});
		offsetYSlider.valueProperty().addListener(e -> {
			onSliderChanged(offsetYSlider, offSetXLabelVal);
		});
		colorPicker.valueProperty().addListener(e -> {
			onColorChanged(dropShadow, colorPicker);
		});
		comboBox.valueProperty().addListener(e -> {
			onComboBoxChanged(dropShadow, comboBox);
		});
		//
		buttonBox.getChildren().addAll(applyButton, cancelButton);
		settingBox.getChildren().addAll(radiusLabel, radiusSlider, radiusLabelVal, widthLabel, widthSlider,
				widthLabelVal, heightLabel, heightSlider, heightLabelVal, spreadLabel, spreadSlider, spreadLabelVal,
				colorLabel, colorPicker, comboBoxLabel, comboBox, offSetXLabel, offsetXSlider, offSetXLabelVal,
				offSetYLabel, offsetYSlider, offSetYLabelVal);
		box.getChildren().addAll(settingBox, buttonBox);
		Scene scene = new Scene(box, 250, 730);
		this.setTitle("Drop Shadow");
		this.setResizable(false);
		this.initStyle(StageStyle.UTILITY);this.setOnCloseRequest(e-> {
			if (this.model.getImageView() != null) {
				this.model.getImageView().setEffect(null);
			}
		});
		this.setScene(scene);
		this.show();
	}

	private void onComboBoxChanged(DropShadow dropShadow, ComboBox<BlurType> comboBox) {
		dropShadow.setBlurType(comboBox.getValue());

	}

	private void onColorChanged(DropShadow dropShadow, ColorPicker colorPicker) {
		Color color = colorPicker.getValue();
		dropShadow.setColor(color);

	}

	private ComboBox<BlurType> createComboBox(DropShadow dropShadow) {
		BlurType[] comboValues = { BlurType.GAUSSIAN, BlurType.ONE_PASS_BOX, BlurType.TWO_PASS_BOX,
				BlurType.THREE_PASS_BOX };

		ComboBox<BlurType> comboBox = new ComboBox<BlurType>(FXCollections.observableArrayList(comboValues));

		return comboBox;
	}

	private ColorPicker createColorPicker(DropShadow dropShadow) {
		this.colorPicker = new ColorPicker();
		colorPicker.setValue(Color.BLACK);
		return this.colorPicker;
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
	private Slider createEffectSlider(DropShadow dropShadow, DropShadowParametersEnum effectEnum) {
		Slider slider = new Slider();
		slider.setShowTickLabels(true);
		slider.setShowTickMarks(true);
		slider.valueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				switch (effectEnum) {
				case DROP_SHADOW_RADIUS:
					slider.setMin(0);
					slider.setMax(127);
					slider.setBlockIncrement(1);
					dropShadow.setRadius(newValue.doubleValue());
					break;
				case DROP_SHADOW_WIDTH:
					slider.setMin(0);
					slider.setMax(255);
					slider.setBlockIncrement(1);
					dropShadow.setWidth(newValue.doubleValue());
					break;
				case DROP_SHADOW_HEIGHT:
					slider.setMin(0);
					slider.setMax(255);
					slider.setBlockIncrement(1);
					dropShadow.setHeight(newValue.doubleValue());
					break;
				case DROP_SHADOW_SPREAD:
					slider.setMin(0);
					slider.setMax(1);
					slider.setBlockIncrement(0.1);
					dropShadow.setSpread(newValue.doubleValue());
					break;
				case DROP_SHADOW_OFFSETX:
					slider.setMin(-100);
					slider.setMax(100);
					slider.setBlockIncrement(1);
					dropShadow.setOffsetX(newValue.doubleValue());
					break;
				case DROP_SHADOW_OFFSETY:
					slider.setMin(-100);
					slider.setMax(100);
					slider.setBlockIncrement(1);
					dropShadow.setOffsetY(newValue.doubleValue());
					break;
				default:
					break;
				}

			}

		});
		return slider;
	}
}
