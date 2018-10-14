/*
 *@author: Alex Kaariainen
 *paint.java
 *Version 0.3
 *Description: Handles std Java code required for an fxml app
 */

package main;
//import com.sun.javaws.Main;
import java.net.URL;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javax.swing.ImageIcon;

public class Paint extends Application {

    @FXML
    Canvas currCanvas;

    public static Stage mainStage;
    public static Scene mainScene;

    @Override
    public void start(Stage stage) throws Exception {
        try{

            System.out.println("Attempting to load fxml class: FXMLDocument.fxml");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLDocument.fxml"));
            Parent root = loader.load();
            System.out.println("FXML Document loaded successfully.");

            // Scene scene = new Scene(root, )
            Scene scene = new Scene(root, 800, 600);
            stage.setMinHeight(600);
            stage.setMinWidth(800);
            stage.setMaxHeight(600);
            stage.setMaxWidth(800);
            System.out.println("balls1");
            root.setStyle("-fx-background-color: gainsboro;");
            //stage.getIcons().add(new Image(("icon-32-32.png")));//set's the program icon
            stage.setTitle("Pain(t)");
            System.out.println("balls2");

            //public references to stage and scene
            mainStage=stage;
            mainScene=scene;
            System.out.println("balls3");

            stage.setScene(scene);
            stage.show();
        }
        catch(Exception e){
            System.out.println("CRITICAL ERROR: PAINT.JAVA START FAILURE");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}

