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

/**
 * @author      Alex Kaariainen <alex.kaariainen@valpo.edu>
 * @version     1.0
 * @since       0.3
 */

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


    /**
     *
     * Creates a pop-up style error message. Message displayed is the string passed to it.
     *
     * @param message        Error message displayed to the user.
     *
     * @author      Alex Kaariainen <alex.kaariainen@valpo.edu>
     * @version     0.3
     * @since       0.3
     */
    public static void popupError(String message){
        error.setContentText(message);
        error.show();
    }

    /**
     *
     * Creates a pop-up style alert message. Message displayed is the string passed to it.
     *
     * @param message        Error message displayed to the user.
     *
     * @author      Alex Kaariainen <alex.kaariainen@valpo.edu>
     * @version     0.3
     * @since       0.3
     */
    public static void popupAlert(String message){
        info.setContentText(message);
        info.show();
    }

    /**
     *
     * Creates a pop-up style confirmation message. Message displayed is the string passed to it. Returns true if they
     * 'confirm', false if they 'cancel'.
     *
     * @return               True if user 'confirmed', false if user 'canceled'
     * @param message        Error message displayed to the user.
     *
     * @author      Alex Kaariainen <alex.kaariainen@valpo.edu>
     * @version     0.3
     * @since       0.3
     */
    public static boolean popupConfirmation(String message){
        confirm.setContentText(message);
        return confirm.showAndWait().get()==ButtonType.OK;
    }

    /**
     *
     * Creates a custom dialog message used to create a new file.
     *
     * @return      Returns an Optional Pair of two strings, canvas height and width.
     *
     * @author      Alex Kaariainen <alex.kaariainen@valpo.edu>
     * @version     0.3
     * @since       0.3
     * @deprecated 0.5 Made useless after new file functionality was removed.
     */
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

    /**
     *
     * Creates a custom dialog message used to resize the current canvas.
     *
     * @return      Returns an Optional Pair of two strings, canvas height and width.
     *
     * @author      Alex Kaariainen <alex.kaariainen@valpo.edu>
     * @version     0.3
     * @since       0.3
     * @deprecated 0.5 Made useless after resizing the canvas functionality was removed.
     */
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

    /**
     *
     * Creates a popup dialog to get desired user font size.
     *
     * @return      Returns the font size
     *
     * @author      Alex Kaariainen <alex.kaariainen@valpo.edu>
     * @version     1.0
     * @since       0.9
     * @deprecated  1.0
     */
    public static int popupFontSize(){
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Font Size");
        dialog.setHeaderText("Enter new font size pixels.");
        dialog.setContentText("Font Size (px):");
        Optional<String> result = dialog.showAndWait();
        return Integer.valueOf(result.get());
    }

    /**
     *
     * Creates a popup dialog to get desired user text.
     *
     * @return      Returns the user message to be displayed
     *
     * @author      Alex Kaariainen <alex.kaariainen@valpo.edu>
     * @version     1.0
     * @since       0.9
     */
    public static String popupText(){
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Text");
        dialog.setHeaderText("Enter the text to display.");
        dialog.setContentText("Text:");
        Optional<String> result = dialog.showAndWait();
        return result.toString();
    }
}
