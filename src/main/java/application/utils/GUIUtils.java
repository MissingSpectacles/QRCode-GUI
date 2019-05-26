/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.utils;

import javafx.scene.control.Alert;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Optional;

import static java.util.Optional.ofNullable;
import static javafx.embed.swing.SwingFXUtils.fromFXImage;
import static javafx.scene.control.ButtonType.CLOSE;

/**
 * A helper Class to shorten the code in {@code FXMLController.java}
 */
public class GUIUtils {

    private static final FileChooser PREPARED_FILE_CHOOSER = new FileChooser();

    static {
        PREPARED_FILE_CHOOSER.getExtensionFilters()
                .add(new ExtensionFilter("Image Files",
                        "*.png", "*.jpg", "*.jpeg", "*.bmp"));
    }

    //region Show Custom Notification

    /**
     * Creates a dialog which contains a logo based on {@code type}, a
     * {@code message}, and a {@code ButtonType.CLOSE} to close the dialog
     *
     * @param type    one of: {@code AlertType.CONFIRMATION, AlertType.ERROR,
     *                AlertType.INFORMATION, AlertType.NONE, AlertType.WARNING}
     * @param message the {@code String} message to be displayed
     */
    public static void showCustomNotification(Alert.AlertType type, String message) {
        new Alert(type, message, CLOSE).show();
    }
    //endregion

    //region Get Save Path For QR Code Image From User

    /**
     * Shows a {@code FileChooser} for user to choose a save location and file's
     * name
     *
     * @return an {@code Optional} which contains the file's absolute path or
     * null if user cancels or closes the {@code FileChooser} dialog
     */
    public static Optional<File> getSavePathForQrCodeImageFromUser() {
        prepareFileChooserForSaving();
        File savePath = PREPARED_FILE_CHOOSER.showSaveDialog(new Stage());
        return ofNullable(savePath);
    }

    private static void prepareFileChooserForSaving() {
        PREPARED_FILE_CHOOSER.setTitle("Save QR Code");
        PREPARED_FILE_CHOOSER.setInitialFileName("My-QR-Code");
    }
    //endregion

    //region Get QR Code File Path to Be Decoded

    /**
     * Shows a {@code FileChooser} for user to choose a file
     *
     * @return an {@code Optional} which contains the file's absolute path or
     * null if user cancels or closes the {@code FileChooser} dialog
     */
    public static Optional<File> getQrCodeFilePathToBeDecoded() {
        prepareFileChooserForOpening();
        File filePath = PREPARED_FILE_CHOOSER.showOpenDialog(new Stage());
        return ofNullable(filePath);
    }

    private static void prepareFileChooserForOpening() {
        PREPARED_FILE_CHOOSER.setTitle("Open QR Code");
    }
    //endregion

    //region Extract Image From Image View

    /**
     * Extracts the image contained in a JavaFX {@code ImageView} variable
     *
     * @param imgV the JavaFX {@code ImageView} variable whose image is to be
     *             extracted
     * @return the extracted image as {@code BufferedImage}
     */
    public static BufferedImage extractImageFromImageView(ImageView imgV) {
        return fromFXImage(imgV.getImage(), null);
    }
    //endregion

}
