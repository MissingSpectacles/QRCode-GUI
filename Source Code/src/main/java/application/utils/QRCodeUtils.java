/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.utils;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.GlobalHistogramBinarizer;
import com.google.zxing.qrcode.QRCodeReader;
import com.google.zxing.qrcode.QRCodeWriter;

import java.awt.image.BufferedImage;

import static com.google.zxing.BarcodeFormat.QR_CODE;

/**
 * A class which purpose is to help in Encoding to QR Code and Decoding from QR
 * Code
 */
public class QRCodeUtils {

    //region Encode

    /**
     * Creates a QR Code image with size {@code width} x {@code height} with
     * {@code text} as the content.
     *
     * @param text   the content of the QR Code image
     * @param width  the width of the QR Code image
     * @param height the height of the QR Code image
     * @return the QR Code image
     * @throws WriterException if {@code QRCodeWriter} class from {@code ZXing}
     *                         goes wrong while encoding
     */
    public static BufferedImage encode(String text, int width, int height)
            throws WriterException {
        BitMatrix qrCodeBitMatrix = createBitMatrix(text, width, height);
        return MatrixToImageWriter.toBufferedImage(qrCodeBitMatrix);
    }

    private static BitMatrix createBitMatrix(String text, int width, int height)
            throws WriterException {
        return new QRCodeWriter().encode(text, QR_CODE, width, height);
    }
    //endregion

    //region Decode

    /**
     * Takes a QR Code image as input and tries to decode it in order to get the
     * content.
     *
     * @param img the QR Code image
     * @return a {@code String} which is the text contained in the QR Code image
     * @throws Exception if {@code QRCodeReader} class from {@code ZXing} goes
     *                   wrong while decoding
     */
    public static String decode(BufferedImage img) throws Exception {
        BinaryBitmap binaryBitmap = createBinaryBitmap(img);
        return new QRCodeReader().decode(binaryBitmap).getText();
    }

    private static BinaryBitmap createBinaryBitmap(BufferedImage img) {
        return new BinaryBitmap(
                new GlobalHistogramBinarizer(
                        new BufferedImageLuminanceSource(img)));
    }
    //endregion

}
