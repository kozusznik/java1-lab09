package lab;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *  Class <b>App</b> - extends class Application and it is an entry point of the program
 * @author     Java I
 */
public class App extends Application {

	public static void main(String[] args) {
		launch(args);
	}
	
	private Canvas canvas;
	private GameController controller;
	@Override
	public void start(Stage primaryStage) {
		try {
			//Construct a main window with a canvas.  
			
			
			Group root = new Group();
			canvas = new Canvas(800, 600);
			root.getChildren().add(canvas);
			Scene scene = new Scene(root);
			
			
			primaryStage.setScene(scene);
			primaryStage.resizableProperty().set(false);
			primaryStage.setTitle("Java 1 - 6th laboratory");
			primaryStage.show();
			controller = new GameController(canvas);
			controller.startGame();
			//Exit program when main window is closed
			primaryStage.setOnCloseRequest(this::exitProgram);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	
	private void exitProgram(WindowEvent evt) {
		controller.stopGame();
		System.exit(0);
	}
}