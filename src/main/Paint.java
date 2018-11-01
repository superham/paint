package main;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import popup.Popup;

/**
 *
 * This is the main class for the paint application.
 *
 * @author      Alex Kaariainen <alex.kaariainen@valpo.edu>
 * @version     1.0
 * @since       0.1
 */

public class Paint extends Application {

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
            root.setStyle("-fx-background-color: gainsboro;");
            stage.getIcons().add(new Image(("file:assets/icon-32-32.png")));//set's the program icon
            stage.setTitle("Pain(t)");

            //public references to stage and scene
            mainStage=stage;
            mainScene=scene;

            stage.setScene(scene);
            stage.show();
        }
        catch(Exception e){
            System.out.println("CRITICAL ERROR: PAINT.JAVA START FAILURE");
            Popup.popupError("CRITICAL ERROR: PAINT.JAVA START FAILURE");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}

