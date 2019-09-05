/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package newbostontutorials;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 *
 * @author Jonah
 */
public class NewBostonTutorials extends Application {
    
Stage window;
Scene scene1, scene2;

    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        window = primaryStage;
        
        Label label1 = new Label("Welcome to the first scene");
        Button button1 = new Button("Go to scene 2");
        button1.setOnAction(e -> window.setScene(scene2)); // Window is the main window, parent of everything
                                                           // This makes it go from scene1 to scene2
        // Layout1 - children are laid out in a vertical column
        VBox layout1 = new VBox(20); // Stacks all objects on top of eachother
        layout1.getChildren().addAll(label1, button1);
        scene1 = new Scene(layout1, 200, 200);

        // thats it for the first scene, we created 2 objects, alabel and a button
        // added an action for the button, and told how to arrange


        //Button 2
        Button button2 = new Button("Go back to scene 1");
        button2.setOnAction(e -> window.setScene(scene1));

        // Layout 2
        StackPane layout2 = new StackPane();
        layout2.getChildren().add(button2);
        scene2 = new Scene(layout2, 600, 300);

        window.setScene(scene1);
        window.setTitle("Put your title hur");
        window.show();
        
        // Storing user-input data
        // https://stackoverflow.com/questions/55441589/how-to-store-input-data-from-gui-into-arraylist
    }    
}
