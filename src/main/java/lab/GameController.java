package lab;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;

public class GameController {

	private World world;
	private Canvas canvas;
	private AnimationTimer animationTimer;
	
	public GameController(Canvas canvas) {
		this.canvas = canvas;
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
		animationTimer.start();
	}


	public void stopGame() {
		animationTimer.stop();
	}
	
	private void drawScene(double deltaT) {
		world.draw(canvas);
		world.simulate(deltaT);
	}

	
}
