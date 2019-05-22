package application;

import com.google.zxing.WriterException;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.awt.image.BufferedImage;

import static application.utils.QRCodeUtils.encode;
import static javafx.fxml.FXMLLoader.load;

public class App extends javafx.application.Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = load(getClass().getResource("/views/Scene.fxml"));

        Image fxIcon = createAppIcon();
        stage.getIcons().add(fxIcon);

        stage.setTitle("QR Code Writer and Reader");
        stage.setScene(new Scene(root));
        stage.show();
    }

    private Image createAppIcon() throws WriterException {
        BufferedImage icon = encode(
                "QR Code Writer and Reader", 500, 500);
        return SwingFXUtils.toFXImage(icon, null);
    }

}
