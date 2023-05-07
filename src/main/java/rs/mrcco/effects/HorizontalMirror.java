package rs.mrcco.effects;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class HorizontalMirror implements Effect {
    @Override
    public Image effect(Image image) {
        PixelReader pixelReader = image.getPixelReader();
        WritableImage wImage = new WritableImage((int) image.getWidth(),
                (int) image.getHeight());
        PixelWriter pixelWriter = wImage.getPixelWriter();
        for (int readY = 1; readY < image.getHeight() - 1; readY++) {
            for (int readX = 1; readX < image.getWidth() - 1; readX++) {
                Color color = pixelReader.getColor(readX, readY);
                pixelWriter.setColor(readX, (int) (image.getHeight() - readY),
                        color);

            }
        }
        return wImage;
    }
}
