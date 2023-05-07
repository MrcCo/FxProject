package rs.mrcco.effects;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import rs.mrcco.effects.Effect;

public class Threshold implements Effect {

	@Override
	public Image effect(Image image) {
		PixelReader pixelReader = image.getPixelReader();
		WritableImage wImage = new WritableImage((int) image.getWidth(), (int) image.getHeight());
		Color c = new Color(0, 0, 0, 0);
		PixelWriter pixelWriter = wImage.getPixelWriter();
		for (int readY = 0; readY < image.getHeight(); readY++) {
			for (int readX = 0; readX < image.getWidth(); readX++) {
				Color color = pixelReader.getColor(readX, readY);

				if (color.getBlue() > 0.5 && color.getGreen() > 0.5 && color.getRed() > 0.5)
					c = Color.BLACK;
				else {
					c = Color.WHITE;
				}
				pixelWriter.setColor(readX, readY, c);
			}
		}
		// i = i + 1;
		// potezi[i] = wImage;
		return wImage;
	}

}
