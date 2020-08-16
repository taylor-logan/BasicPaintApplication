package homework5;

//importing all of the javafx things
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TextField;

public class PolylineDrawer extends Application {
	//instance variables for the class
	private ColorPicker colorpicker; //a color picker
	private Polyline[] lines = new Polyline[1000]; //an array to hold all of the polylines
	private double[] points; //an array to hold the initial X and Y points
	private int linecount = 0; //keeping up with how many lines have been added to the scene
	private Group root; //main group
	private Button reset; // reset button to clear the scene
	private Group root1; //holds polylines as they are added
	private VBox root2; //holds button and color picker
	private TextField size;
	private Label size1;
	private int linesize;
	
	public void start(Stage primaryStage)
	{
		colorpicker = new ColorPicker(Color.BLACK); //initializing color picker to black
		colorpicker.setTooltip(new Tooltip("Change the drawing color"));
		
		reset = new Button("Reset"); //initializing button and setting button handler
		reset.setOnAction(this::processButtonPress);
		reset.setTooltip(new Tooltip("Clear the page of all markings"));
		
		size = new TextField();
		size.setOnAction(this::processChangeSize);
		size.setMaxWidth(50);
		
		size1 = new Label("Line Size:");
		
		root = new Group(); // initializing groups and VBox's to organize the elements
		root1 = new Group();
		root2 = new VBox();
		
		
		//setting up VBox and main root
		root2.setSpacing(10);
		root2.getChildren().add(colorpicker);
		root2.getChildren().add(reset);
		root2.getChildren().addAll(size1, size);
		root.getChildren().addAll(root1, root2);
		
		//creating the scene
		Scene scene = new Scene (root, 500, 500);
		
		//setting the scene based on mouse functions
		scene.setOnMousePressed(this::processMouseClick);
		scene.setOnMouseDragged(this::processMouseDrag);
		
		//setting the stage
		primaryStage.setTitle("Java Scribble");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	//launching the application
	public static void main(String[] args) {
		
		launch(args);
	}
	
	//creating a new polyline on a mouse click
	public void processMouseClick(MouseEvent event)
	{
		double[] points = {event.getX(), event.getY()};
		Polyline line = new Polyline(points);
		line.setStroke(colorpicker.getValue());
		line.setStrokeWidth(linesize);
		root1.getChildren().add(line); //adds polyline to root1
		lines[linecount] = line; //adding the polyline to the array to use later
		linecount++; //incremmenting to be ready for next mouse click
	}
	
	public void processMouseDrag(MouseEvent event)
	{
		//accessing current polyline to add the points of the mouse as it is dragged across the screen
		lines[linecount - 1].getPoints().addAll(event.getX(), event.getY());
	}
	
	//clearing the scene when the clear button is pressed
	public void processButtonPress(ActionEvent event)
	{
		//only clearing the root containing the polylines
		root1.getChildren().clear();
	}
	
	public void processChangeSize(ActionEvent event)
	{
		linesize = Integer.parseInt(size.getText());
	}
}
