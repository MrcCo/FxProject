package rs.mrcco;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;
import rs.mrcco.effects.*;
import rs.mrcco.gui.ButtonFactory;

public class Project extends Application {

	Image image;
	ImageView imageView = new ImageView();
	int i = 0;
	
	Image[] previousImages = new Image[100];

	//available effects
	Red redTest = new Red();
	Negativ negativeTest = new Negativ();
	Grayscale grayscaleTest = new Grayscale();
	Threshold thresholdTest = new Threshold();
	Solaris solarisTest = new Solaris();
	Edges edgesTest = new Edges();
	
	
	@Override
	public void start(Stage stage) {

		var mainPane = new BorderPane();
		var menuBar = new MenuBar();
		var fileMenu = new Menu("File");
		var loadMenuItem = new MenuItem("Open image");
		var save = new MenuItem("Save image");
		menuBar.getMenus().add(fileMenu);
		menuBar.setStyle(" -fx-font: 16 cambria; -fx-base: #ffa500;");
		fileMenu.getItems().add(loadMenuItem);
		fileMenu.getItems().add(save);
		mainPane.setTop(menuBar);

		var toolBar = new ToolBar();
		toolBar.setOrientation(Orientation.VERTICAL);
		toolBar.setStyle(" -fx-font: 16 cambria; -fx-base:#ffa500;");
		mainPane.setRight(toolBar);

		var initialImage = new Image("http://oi61.tinypic.com/123ljdx.jpg");
		imageView.setImage(initialImage);
		imageView.setFitHeight(800);
		imageView.setFitWidth(1320);

		loadMenuItem.setOnAction(arg0 -> {
			FileChooser fileChooser = new FileChooser();
			FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter(
					"JPG files (*.jpg)", "*.JPG");
			fileChooser.getExtensionFilters().addAll(extFilterJPG);
			File file1 = fileChooser.showOpenDialog(null);
			String path = file1.getAbsolutePath();
			try {
				InputStream inputStream = new FileInputStream(path);
				image = new Image(inputStream);
				previousImages[0] = image;
				imageView.setImage(image);
				imageView.setFitHeight(1024);
				imageView.setFitHeight(720);
				imageView.setPreserveRatio(true);
				imageView.setSmooth(true);
				imageView.setCache(true);

			} catch (FileNotFoundException ex) {
				Logger.getLogger(Project.class.getName()).log(
						Level.SEVERE, null, ex);
				image = null;
			}
		});

		// todo - fixme
		save.setOnAction(arg0 -> {
			FileChooser fileChooser = new FileChooser();

			// Set extension filter
			FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
					"JPG files (*.jpg)", "*.JPG");
			fileChooser.getExtensionFilters().add(extFilter);

			// Show save file dialog
			File file = fileChooser.showSaveDialog(stage);

			if (file != null) {

				saveToFile("Hello", file);

			}
		});

		mainPane.setCenter((imageView));

		var backButton = ButtonFactory.createDefaultButton("Back", e -> {
			if (i  > 1) {
				imageView.setImage(previousImages[--i]);

			} else {
				imageView.setImage(previousImages[0]);
				i = 0;
			}
		});
		toolBar.getItems().add(backButton);

		var negativeButton = ButtonFactory.createDefaultButton("Negative",e -> {

			Image newImage = negativeTest.effect(previousImages[i]);
			previousImages[++i] = newImage;
			imageView.setImage(newImage);

		});
		toolBar.getItems().add(negativeButton);

		var grayscaleButton = ButtonFactory.createDefaultButton("Grayscale", e-> {

			Image newImage = grayscaleTest.effect(previousImages[i]);
			previousImages[++i] = newImage;
			imageView.setImage(newImage);

		});
		toolBar.getItems().add(grayscaleButton);

		var thresholdButton = ButtonFactory.createDefaultButton("Threshold", e -> {

			Image newImage = thresholdTest.effect(previousImages[i]);
			previousImages[++i] = newImage;
			imageView.setImage(newImage);

		});
		toolBar.getItems().add(thresholdButton);

		var solarisButton = ButtonFactory.createDefaultButton("Solaris", e -> {

			Image newImage = solarisTest.effect(previousImages[i]);
			previousImages[++i] = newImage;
			imageView.setImage(newImage);

		});
		toolBar.getItems().add(solarisButton);


		var gammaButton = ButtonFactory.createDefaultButton("Gamma", e -> {

			Image newImage = redTest.effect(previousImages[i]);
			previousImages[++i] = newImage;
			imageView.setImage(newImage);

		});
		toolBar.getItems().add(gammaButton);

		var edgesButton = ButtonFactory.createDefaultButton("Edges", e -> {

			Image newImage = edgesTest.effect(previousImages[i]);
			previousImages[++i] = newImage;
			imageView.setImage(newImage);

		});
		toolBar.getItems().add(edgesButton);

		//horizontal mirror
		var verticalMirrorButton = ButtonFactory.createDefaultButton("Mirror1", e -> {

			imageView.setImage(verticalMirror());

		});
		toolBar.getItems().add(verticalMirrorButton);


		//vertical mirror
		var horizontalMirrorButton = ButtonFactory.createDefaultButton("Mirror2", e -> {

			imageView.setImage(horizontalMirror());

		});
		toolBar.getItems().add(horizontalMirrorButton);

		var sharpenButton = ButtonFactory.createDefaultButton("Sharpen", e  -> {

			imageView.setImage(sharpen(image));

		});
		toolBar.getItems().add(sharpenButton);

		Button blurButton = ButtonFactory.createDefaultButton("Blur", e -> {

			imageView.setImage(blur());

		});
		toolBar.getItems().add(blurButton);


		var makeRedderButton = ButtonFactory.createDefaultButton("Redden",e  -> {

			Image newImage = redTest.effect(previousImages[i]);
			previousImages[++i] = newImage;
			imageView.setImage(newImage);

		});
		toolBar.getItems().add(makeRedderButton);


		Button sepiaButton = ButtonFactory.createDefaultButton("Sepia", e -> {
			imageView.setImage(sepia());
		});
		toolBar.getItems().add(sepiaButton);


		Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
		stage.setX(primaryScreenBounds.getMinX());
		stage.setY(primaryScreenBounds.getMinY());
		stage.setWidth(primaryScreenBounds.getWidth());
		stage.setHeight(primaryScreenBounds.getHeight());

		var scene = new Scene(mainPane);
		stage.setScene(scene);
		stage.show();

	}

	private Image sharpen(Image image) {
		PixelReader pixelReader = image.getPixelReader();
		WritableImage wImage = new WritableImage((int) this.image.getWidth(),
				(int) this.image.getHeight());
		PixelWriter pixelWriter = wImage.getPixelWriter();
		for (int readY = 1; readY < this.image.getHeight() - 1; readY++) {
			for (int readX = 1; readX < this.image.getWidth() - 1; readX++) {
				Color color = pixelReader.getColor(readX, readY);

				Color c1 = pixelReader.getColor(readX - 1, readY - 1);
				Color c2 = pixelReader.getColor(readX - 1, readY);
				Color c3 = pixelReader.getColor(readX - 1, readY + 1);
				Color c4 = pixelReader.getColor(readX, readY - 1);
				Color c5 = pixelReader.getColor(readX, readY + 1);
				Color c6 = pixelReader.getColor(readX + 1, readY + 1);
				Color c7 = pixelReader.getColor(readX + 1, readY);
				Color c8 = pixelReader.getColor(readX + 1, readY - 1);
				Color c = new Color(Math.min(
						Math.max(
								color.getRed() * 9 - c1.getRed() - c2.getRed()
										- c3.getRed() - c4.getRed()
										- c5.getRed() - c6.getRed()
										- c7.getRed() - c8.getRed(), 0), 1),
						Math.min(
								Math.max(
										color.getBlue() * 9 - c1.getBlue()
												- c2.getBlue() - c3.getBlue()
												- c4.getBlue() - c5.getBlue()
												- c6.getBlue() - c7.getBlue()
												- c8.getBlue(), 0), 1),
						Math.min(
								Math.max(
										color.getGreen() * 9 - c1.getGreen()
												- c2.getGreen() - c3.getGreen()
												- c4.getGreen() - c5.getGreen()
												- c6.getGreen() - c7.getGreen()
												- c8.getGreen(), 0), 1),
						color.getOpacity());

				pixelWriter.setColor(readX, readY, c);
			}
		}
		i = i + 1;
		previousImages[i] = wImage;
		return wImage;
	}



	private Image blur() {
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
		i = i + 1;
		previousImages[i] = wImage;
		return wImage;
	}

	

	private Image verticalMirror() {
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
		i = i + 1;
		previousImages[i] = wImage;
		return wImage;
	}

	private Image horizontalMirror() {
		PixelReader pixelReader = image.getPixelReader();
		WritableImage wImage = new WritableImage((int) image.getWidth(),
				(int) image.getHeight());
		PixelWriter pixelWriter = wImage.getPixelWriter();
		for (int readY = 1; readY < image.getHeight() - 1; readY++) {
			for (int readX = 1; readX < image.getWidth() - 1; readX++) {
				Color color = pixelReader.getColor(readX, readY);
				pixelWriter.setColor((int) (image.getWidth() - readX), readY,
						color);

			}
		}
		i = i + 1;
		previousImages[i] = wImage;
		return wImage;
	}


	private Image sepia() {
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
		i = i + 1;
		previousImages[i] = wImage;
		return wImage;
	}


	private void saveToFile(String content, File file) {
		try(FileWriter fileWriter = new FileWriter(file)) {
			fileWriter.write(content);
		} catch (IOException ex) {
			// ignore exception for now
		}

	}

	public static void main(String[] args) {
		Application.launch(args);
	}
}
