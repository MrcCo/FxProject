package project;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class Red implements Effect {

	@Override
	public Image effect(Image image) {
		PixelReader pixelReader = image.getPixelReader();
		WritableImage wImage = new WritableImage((int) image.getWidth(),
				(int) image.getHeight());
		Color c = new Color(0, 0, 0, 0);
		PixelWriter pixelWriter = wImage.getPixelWriter();
		for (int readY = 0; readY < image.getHeight(); readY++) {
			for (int readX = 0; readX < image.getWidth(); readX++) {
				Color color = pixelReader.getColor(readX, readY);
				if (color.getRed() + 0.5 <= 1) {
					c = new Color((color.getRed() + 0.5), color.getGreen(),
							color.getBlue(), color.getOpacity());
				} else {
					c = new Color(1, color.getGreen(), color.getBlue(),
							color.getOpacity());
				}
				pixelWriter.setColor(readX, readY, c);
			}
		}
		//i = i + 1;
		//potezi[i] = wImage;
		return wImage;
	}

}
