	package lab;

import javafx.animation.AnimationTimer;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

public class GameController {

	private World world;
	
	@FXML
	private Slider angleSlider;
	
	@FXML
	private Slider strengthSlider;
	
	@FXML
	private Canvas canvas;
	
	@FXML
	private Label shoots;
	
	@FXML
	private Label hits;
	
	private AnimationTimer animationTimer;
	
	public GameController() {
	}
	
	public void startGame() {
		this.world = new World(canvas.getWidth(), canvas.getHeight());
		this.world.setGameListener(new GameListenerImpl());
		//Draw scene on a separate thread to avoid blocking UI.
		animationTimer = new AnimationTimerImpl();
		angleSlider.valueProperty().addListener(this::angleChanged);
		world.setCannonAngle(angleSlider.getValue());
		
		strengthSlider.valueProperty().addListener(this::strenghtChanged);
		world.setCannonStrength(strengthSlider.getValue());
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
		world.fire();
	}
	
	
	private void angleChanged(ObservableValue<? extends Number> observable
								, Number oldValue, Number newValue) {
		world.setCannonAngle(newValue.doubleValue());
	}

	private void strenghtChanged(ObservableValue<? extends Number> observable
			, Number oldValue, Number newValue) {
		world.setCannonStrength(newValue.doubleValue());
	}
	
	private final class AnimationTimerImpl extends AnimationTimer {
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
	}

	private class GameListenerImpl implements GameListener {

		@Override
		public void stateChanged(int shoots, int hits) {
			GameController.this.shoots.setText("" + shoots);
			GameController.this.hits.setText("" + hits);
		}

		@Override
		public void gameOver() {
			stopGame();
		}
		
	}
}
