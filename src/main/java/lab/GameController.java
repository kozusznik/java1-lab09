package lab;

import javafx.animation.AnimationTimer;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Slider;

public class GameController {

	private World world;
	
	@FXML
	private Slider angleSlider;
	
	@FXML
	private Canvas canvas;
	
	private AnimationTimer animationTimer;
	
	public GameController() {
	}
	
	public void startGame() {
		this.world = new World(canvas.getWidth(), canvas.getHeight());	
		//Draw scene on a separate thread to avoid blocking UI.
		animationTimer = new AnimationTimer() {
			private Long previous;
			
			@Override
			public void handle(long now) {
				if (previous == null) {
					previous = now;
				} else {
					drawScene((now - previous)/1e9);
					previous = now;
				}
			}
		};
		angleSlider.valueProperty().addListener(this::angleChanged);
		world.setCannonAngle(angleSlider.getValue());
		animationTimer.start();
	}


	public void stopGame() {
		animationTimer.stop();
	}
	
	private void drawScene(double deltaT) {
		world.draw(canvas);
		world.simulate(deltaT);
	}
	
	@FXML
	private void firePressed() {
		System.out.println("Fire");
	}
	
	
	private void angleChanged(ObservableValue<? extends Number> observable
								, Number oldValue, Number newValue) {
		world.setCannonAngle(newValue.doubleValue());
	}

	
}
