package rs.mrcco.effects;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class Blur implements Effect {
    @Override
    public Image effect(Image image) {
        PixelReader pixelReader = image.getPixelReader();
        WritableImage wImage = new WritableImage((int) image.getWidth(), (int) image.getHeight());
        PixelWriter pixelWriter = wImage.getPixelWriter();
        for (int readY = 4; readY < image.getHeight() - 4; readY++) {
            for (int readX = 4; readX < image.getWidth() - 4; readX++) {
                Color color = pixelReader.getColor(readX, readY);

                Color c1 = pixelReader.getColor(readX - 4, readY - 4);
                Color c2 = pixelReader.getColor(readX - 3, readY - 3);
                Color c3 = pixelReader.getColor(readX - 2, readY - 2);
                Color c4 = pixelReader.getColor(readX - 1, readY - 1);
                Color c5 = pixelReader.getColor(readX + 1, readY + 1);
                Color c6 = pixelReader.getColor(readX + 2, readY + 2);
                Color c7 = pixelReader.getColor(readX + 3, readY + 3);
                Color c8 = pixelReader.getColor(readX + 4, readY + 4);

                Color c = new Color(
                        Math.min(Math.max(
                                (color.getRed() + c1.getRed() + c2.getRed()
                                        + c3.getRed() + c4.getRed()
                                        + c5.getRed() + c6.getRed()
                                        + c7.getRed() + c8.getRed()) / 9, 0), 1),
                        Math.min(Math.max(
                                        (color.getBlue() + c1.getBlue() + c2.getBlue()
                                                + c3.getBlue() + c4.getBlue()
                                                + c5.getBlue() + c6.getBlue()
                                                + c7.getBlue() + c8.getBlue()) / 9, 0),
                                1), Math.min(Math.max(
                        (color.getGreen() + c1.getGreen()
                                + c2.getGreen() + c3.getGreen()
                                + c4.getGreen() + c5.getGreen()
                                + c6.getGreen() + c7.getGreen() + c8
                                .getGreen()) / 9, 0), 1), color
                        .getOpacity());

                pixelWriter.setColor(readX, readY, c);
            }
        }

        return wImage;
    }
}
