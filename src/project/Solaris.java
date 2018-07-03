package project;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class Solaris implements Effect {

	@Override
	public Image effect(Image image) {
		PixelReader pixelReader = image.getPixelReader();
		WritableImage wImage = new WritableImage((int) image.getWidth(),
				(int) image.getHeight());
		Color c = new Color(0, 0, 0, 0);
		double k, h, j;
		PixelWriter pixelWriter = wImage.getPixelWriter();
		for (int readY = 0; readY < image.getHeight(); readY++) {
			for (int readX = 0; readX < image.getWidth(); readX++) {
				Color color = pixelReader.getColor(readX, readY);
				if (color.getRed() > 0.5)
					h = color.getRed();
				else
					h = 1 - color.getRed();
				if (color.getBlue() > 0.5)
					k = color.getBlue();
				else
					k = 1 - color.getBlue();
				if (color.getGreen() > 0.5)
					j = color.getGreen();
				else
					j = 1 - color.getGreen();
				c = new Color(h, k, j, color.getOpacity());
				pixelWriter.setColor(readX, readY, c);
			}
		}
		//i = i + 1;
		//potezi[i] = wImage;
		return wImage;
	}

}
