package popup;

/*
*@author: Alex Kaariainen
*popup.java
*Version 0.5 Beta
*Description: Creates popup's/messages/alerts/dialogs
*/

import java.util.Optional;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

public class Popup {
    private static final Alert error = new Alert(Alert.AlertType.ERROR);
    private static final Alert info = new Alert(Alert.AlertType.INFORMATION);
    private static final Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
    
    static{
        error.setHeaderText(null);
        error.setTitle("Error");
        info.setHeaderText(null);
        info.setTitle("Information");
        confirm.setHeaderText(null);
        confirm.setTitle("Confirmation");
    }
            
    public static void popupError(String message){
        error.setContentText(message);
        error.show();
    }
    
    public static void popupAlert(String message){
        info.setContentText(message);
        info.show();
    }
    
    public static boolean popupConfirmation(String message){
        confirm.setContentText(message);
        return confirm.showAndWait().get()==ButtonType.OK;
    }
    
    //Custom dialog for a new file
    public static Optional<Pair<String, String>> newFileAlert(){
        //array to store canvas width and height
        int[] data = new int[2];
        
        //custom created dialog alert
        Dialog<Pair<String,String>> dialog = new Dialog<>();
        dialog.setTitle("New Canvas");
        
        //create buttons
        ButtonType createButtType = new ButtonType ("Create",ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(createButtType, ButtonType.CANCEL);
        
        //create width and height fields
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        TextField width = new TextField();
        width.setPromptText("Width");
        TextField height = new TextField();
        height.setPromptText("Height");
        grid.add(new Label("Width (px):"), 0, 0);
        grid.add(width, 1, 0);
        grid.add(new Label("Height (px):"), 0, 1);
        grid.add(height, 1, 1);
        
        //error check user input  add more here..TODO
        Node confirmButton = dialog.getDialogPane().lookupButton(createButtType);
        confirmButton.setDisable(true);

        boolean widthTextProperty = false;
        boolean heightTextProperty = false;
        width.textProperty().addListener((observable, oldValue, newValue) -> {
            confirmButton.setDisable(newValue.trim().isEmpty());
            });
        height.textProperty().addListener((observable, oldValue, newValue) -> {
            confirmButton.setDisable(newValue.trim().isEmpty());
            });
        width.getCharacters();

        if(widthTextProperty && heightTextProperty){
            confirmButton.setDisable(false);

        }
        dialog.getDialogPane().setContent(grid);
        
        // Request focus on the width field by default. TODO consider removing
        Platform.runLater(() -> width.requestFocus());
        
        //convert the result to a width-height pair when the create button is clicked
        dialog.setResultConverter(dialogButton -> {
        if (dialogButton == createButtType) {
            return new Pair<>(width.getText(), height.getText());
            }
        return null;
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();
    return result;
    }
    
    //Custom dialog for a new file
    public static Optional<Pair<String, String>> resizeCanvas(){
        //array to store canvas width and height
        int[] data = new int[2];
        
        //custom created dialog alert
        Dialog<Pair<String,String>> dialog = new Dialog<>();
        dialog.setTitle("New Canvas Size");
        
        //create buttons
        ButtonType createButtType = new ButtonType ("Create",ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(createButtType, ButtonType.CANCEL);
        
        //create width and height fields
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        TextField width = new TextField();
        width.setPromptText("Width");
        TextField height = new TextField();
        height.setPromptText("Height");
        grid.add(new Label("Width (px):"), 0, 0);
        grid.add(width, 1, 0);
        grid.add(new Label("Height (px):"), 0, 1);
        grid.add(height, 1, 1);
        
        //error check user input  add more here..TODO
        Node confirmButton = dialog.getDialogPane().lookupButton(createButtType);
        confirmButton.setDisable(true);
        width.textProperty().addListener((observable, oldValue, newValue) -> {
            confirmButton.setDisable(newValue.trim().isEmpty());
            });
        height.textProperty().addListener((observable, oldValue, newValue) -> {
            confirmButton.setDisable(newValue.trim().isEmpty());
            });

        dialog.getDialogPane().setContent(grid);
        
        // Request focus on the width field by default. TODO consider removing
        Platform.runLater(() -> width.requestFocus());
        
        //convert the result to a width-height pair when the create button is clicked
        dialog.setResultConverter(dialogButton -> {
        if (dialogButton == createButtType) {
            return new Pair<>(width.getText(), height.getText());
            }
        return null;
        });

        Optional<Pair<String, String>> result = dialog.showAndWait();
    return result;
    }

    //line width dialog
    public static int popupLineWidth(){
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Line Width");
        dialog.setHeaderText("Enter new line width in pixels.");
        dialog.setContentText("Line Width (px):");
        Optional<String> result = dialog.showAndWait();
        return Integer.valueOf(result.get());
    }

    //line width dialog
    public static int popupFontSize(){
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Font Size");
        dialog.setHeaderText("Enter new font size pixels.");
        dialog.setContentText("Font Size (px):");
        Optional<String> result = dialog.showAndWait();
        return Integer.valueOf(result.get());
    }

    //line width dialog
    public static int popupTextSize(){
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Font Size");
        dialog.setHeaderText("Enter new font size pixels.");
        dialog.setContentText("Font Size (px):");
        Optional<String> result = dialog.showAndWait();
        return Integer.valueOf(result.get());
    }

    public static String popupText(){
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Text");
        dialog.setHeaderText("Enter the text to display.");
        dialog.setContentText("Text:");
        Optional<String> result = dialog.showAndWait();
        return result.toString();
        //return result.get();// TODO <-- this should work
    }
}
