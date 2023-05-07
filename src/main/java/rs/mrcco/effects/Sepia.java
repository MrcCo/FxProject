package rs.mrcco.effects;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class Sepia implements Effect {
    @Override
    public Image effect(Image image) {
        PixelReader pixelReader = image.getPixelReader();
        WritableImage wImage = new WritableImage((int) image.getWidth(),
                (int) image.getHeight());
        PixelWriter pixelWriter = wImage.getPixelWriter();
        for (int readY = 0; readY < image.getHeight(); readY++) {
            for (int readX = 0; readX < image.getWidth(); readX++) {
                Color color = pixelReader.getColor(readX, readY);

                Color c = new Color(
                        Math.min((color.getRed() * 0.393 + color.getGreen()
                                * 0.769 + color.getBlue() * 0.189), 1),
                        Math.min(color.getRed() * 0.349 + color.getGreen()
                                * 0.686 + color.getBlue() * 0.168, 1),
                        Math.min(color.getBlue() * 0.131 + color.getRed()
                                * 0.272 + color.getGreen() * 0.534, 1),
                        color.getOpacity());

                pixelWriter.setColor(readX, readY, c);
            }
        }
        return wImage;
    }
}
