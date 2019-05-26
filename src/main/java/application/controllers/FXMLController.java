package application.controllers;

import com.google.zxing.WriterException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;

import java.awt.datatransfer.StringSelection;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import static application.utils.GUIUtils.*;
import static application.utils.QRCodeUtils.decode;
import static application.utils.QRCodeUtils.encode;
import static java.awt.Toolkit.getDefaultToolkit;
import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;
import static javafx.embed.swing.SwingFXUtils.toFXImage;
import static javafx.scene.control.Alert.AlertType.ERROR;
import static javafx.scene.control.Alert.AlertType.INFORMATION;
import static javax.imageio.ImageIO.read;
import static javax.imageio.ImageIO.write;

@SuppressWarnings("WeakerAccess")
public class FXMLController implements Initializable {

    //region FXML Fields
    @FXML
    private TextField qrCodeTextInput;

    @FXML
    private ImageView generatedQrCodeImageView;

    @FXML
    private ImageView readQrCodeImageView;

    @FXML
    private Button showResultButton;
    //endregion

    //region Initialize
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setAllImagesAndOutputsToDefault();
    }

    private void setAllImagesAndOutputsToDefault() {
        try {
            BufferedImage qrCodeImg = encode("Sample Text", 500, 500);
            WritableImage fxImg = toFXImage(qrCodeImg, null);
            generatedQrCodeImageView.setImage(fxImg);
            readQrCodeImageView.setImage(fxImg);
        } catch (WriterException ex) {
            showCustomNotification(ERROR, ex.getMessage());
        }
    }
    //endregion

    //region Generate QR Code
    @FXML
    private void generateQrCode(ActionEvent event) {
        try {
            getTextThenProcessItIfNotNullAndNotEmpty();
        } catch (Exception ex) {
            showCustomNotification(ERROR, ex.getMessage());
        }
    }

    private void getTextThenProcessItIfNotNullAndNotEmpty() throws Exception {
        String text = qrCodeTextInput.getText();
        if (text != null && !text.isEmpty()) {
            createAndShowQrCodeImage(text);
        }
    }
    //endregion

    private void createAndShowQrCodeImage(String text) throws Exception {
        BufferedImage qrCodeImg = encode(text, 500, 500);
        generatedQrCodeImageView.setImage(toFXImage(qrCodeImg, null));
    }

    //region Save QR Code
    @FXML
    private void saveQrCode(ActionEvent event) {
        getSavePathForQrCodeImageFromUser()
                .ifPresent(this::getImageAndSaveItTo);
    }

    private void getImageAndSaveItTo(File savePath) {
        BufferedImage img = extractImageFromImageView(generatedQrCodeImageView);
        trySaveImage(img, savePath);
    }

    private void trySaveImage(BufferedImage img, File savePath) {
        try {
            boolean saved = write(img, "png", savePath);
            showSuccessNotificationIfSaved(saved);
        } catch (IOException ex) {
            showCustomNotification(ERROR, ex.getMessage());
        }
    }
    //endregion

    private void showSuccessNotificationIfSaved(boolean saved) {
        AlertType alertType = saved ? INFORMATION : ERROR;
        String msg = saved ? "Image saved successfully." : "Image not saved.";
        showCustomNotification(alertType, msg);
    }

    //region Choose QR Code to be Decoded
    @FXML
    private void chooseQrCode(ActionEvent event) {
        getQrCodeFilePathToBeDecoded()
                .ifPresent(this::decodeTheImageFileOpenedFrom);
    }

    private void decodeTheImageFileOpenedFrom(File filePath) {
        Optional<BufferedImage> image = openFile(filePath);

        if (image.isPresent()) {
            showQrCodeImageAndTheContent(image.get());
        } else {
            showCustomNotification(ERROR, "File is not an image.");
        }
    }

    private Optional<BufferedImage> openFile(File filePath) {
        try {
            return ofNullable(read(filePath));
        } catch (IOException ex) {
            showCustomNotification(ERROR, ex.getMessage());
        }
        return empty();
    }

    private void showQrCodeImageAndTheContent(BufferedImage img) {
        readQrCodeImageView.setImage(toFXImage(img, null));
        showResultButton.fire();
    }
    //endregion

    //region Show Decoded Result
    @FXML
    private void showQrCodeDecodedResult(ActionEvent event) {
        BufferedImage img = extractImageFromImageView(readQrCodeImageView);
        decodeAndShowResultAsNotification(img);
    }

    private void decodeAndShowResultAsNotification(BufferedImage img) {
        try {
            String decodedContent = decode(img);
            showCustomNotification(INFORMATION, decodedContent);
        } catch (Exception ex) {
            showCustomNotification(ERROR, "Image is not a valid QR Code.");
            setAllImagesAndOutputsToDefault();
        }
    }
    //endregion

    //region Copy Result Text to Clipboard
    @FXML
    private void copyResultTextToClipboard(ActionEvent event) {
        try {
            copyDecodedTextThenNotifyUser();
        } catch (Exception ex) {
            showCustomNotification(ERROR, "Could not be copied to clipboard.");
        }
    }

    private void copyDecodedTextThenNotifyUser() throws Exception {
        BufferedImage img = extractImageFromImageView(readQrCodeImageView);
        String text = decode(img);
        copyTextToClipboard(text);
        showCustomNotification(INFORMATION, "Text copied to clipboard.");
    }

    private void copyTextToClipboard(String text) {
        getDefaultToolkit()
                .getSystemClipboard()
                .setContents(new StringSelection(text), null);
    }
    //endregion

}
