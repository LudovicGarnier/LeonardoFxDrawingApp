package effects;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GaussianBlurEffect {
	

	public GaussianBlurEffect(ImageView view) {
		Stage gaussianBlurStage = new Stage();
		gaussianBlurStage.setTitle("Gaussian Blur");
		Pane pane = new Pane();
		pane.setPadding(new Insets(10));
		VBox setting = new VBox();
		setting.setPadding(new Insets(12));
		//
		GaussianBlur gaussianBlur = new GaussianBlur();
		view.setEffect(gaussianBlur);
		//
		Label gaussianLabel = new Label("Radius");
		Slider gaussianBlurSlider = createEffectSlider(gaussianBlur);
		setting.getChildren().addAll(gaussianLabel, gaussianBlurSlider);
		pane.getChildren().add(setting);
		Scene gaussianBlurScene = new Scene(pane);
		gaussianBlurStage.setScene(gaussianBlurScene);
		gaussianBlurStage.show();
	}

	public Slider createEffectSlider(GaussianBlur gaussianBlur) {
		Slider slider = new Slider(0,63, 10);
		slider.setBlockIncrement(0.1);
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
