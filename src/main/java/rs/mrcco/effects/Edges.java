package rs.mrcco.effects;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

public class Edges implements Effect {

	@Override
	public Image effect(Image image) {

		PixelReader pixelReader = image.getPixelReader();
		WritableImage wImage = new WritableImage((int) image.getWidth(), (int) image.getHeight());
		Color c = new Color(0, 0, 0, 0);
		PixelWriter pixelWriter = wImage.getPixelWriter();
		for (int readY = 1; readY < image.getHeight() - 1; readY++) {
			for (int readX = 1; readX < image.getWidth() - 1; readX++) {
				Color color = pixelReader.getColor(readX, readY);

				Color c1 = pixelReader.getColor(readX - 1, readY - 1);
				Color c2 = pixelReader.getColor(readX - 1, readY);
				Color c3 = pixelReader.getColor(readX - 1, readY + 1);
				Color c4 = pixelReader.getColor(readX, readY - 1);
				Color c5 = pixelReader.getColor(readX, readY + 1);
				Color c6 = pixelReader.getColor(readX + 1, readY + 1);
				Color c7 = pixelReader.getColor(readX + 1, readY);
				Color c8 = pixelReader.getColor(readX + 1, readY - 1);
				c = new Color(
						Math.min(Math.max(color.getRed() * 8 - c1.getRed() - c2.getRed() - c3.getRed() - c4.getRed()
								- c5.getRed() - c6.getRed() - c7.getRed() - c8.getRed(), 0), 1),
						Math.min(Math.max(color.getBlue() * 8 - c1.getBlue() - c2.getBlue() - c3.getBlue()
								- c4.getBlue() - c5.getBlue() - c6.getBlue() - c7.getBlue() - c8.getBlue(), 0), 1),
						Math.min(Math.max(color.getGreen() * 8 - c1.getGreen() - c2.getGreen() - c3.getGreen()
								- c4.getGreen() - c5.getGreen() - c6.getGreen() - c7.getGreen() - c8.getGreen(), 0), 1),
						color.getOpacity());

				pixelWriter.setColor(readX, readY, c);
			}
		}

		return wImage;
	}

}
