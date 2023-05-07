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
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;
import rs.mrcco.effects.*;

public class Project extends Application {
	Image image;
	ImageView iv = new ImageView();
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
		iv.setImage(initialImage);
		iv.setFitHeight(800);
		iv.setFitWidth(1320);

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
				iv.setImage(image);
				iv.setFitHeight(1024);
				iv.setFitHeight(720);
				iv.setPreserveRatio(true);
				iv.setSmooth(true);
				iv.setCache(true);

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

		mainPane.setCenter((iv));
		var backButtonImage = new Image("http://cssnerd.com/wp-content/uploads/2013/03/Screen-Shot-2013-03-09-at-22.13.01-600x367.png");
		var backButtonIMageView = new ImageView();
		backButtonIMageView.setFitHeight(30);
		backButtonIMageView.setFitWidth(70);
		backButtonIMageView.setImage(backButtonImage);
		Button back = new Button();
		DropShadow shadow = new DropShadow();
		back.setStyle("-fx-font: 16 cambria; -fx-base: #ffa500;");
		back.setMaxSize(70, 30);
		back.setGraphic(backButtonIMageView);
		back.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> back.setEffect(shadow));

		back.addEventHandler(MouseEvent.MOUSE_EXITED, e -> back.setEffect(null));
		back.setOnAction(arg0 -> {
			if (i  > 1) {
				iv.setImage(previousImages[--i]);

			} else {
				iv.setImage(previousImages[0]);
				i = 0;
			}
			iv.setPreserveRatio(true);
			iv.setSmooth(true);
			iv.setCache(true);
		});
		toolBar.getItems().add(back);

		var negativeButton = new Button("Negative");
		negativeButton.setMaxSize(90, 10);
		negativeButton.setOnAction(arg0 -> {

			Image newImage = negativeTest.effect(previousImages[i]);
			previousImages[++i] = newImage;
			iv.setImage(newImage);
			iv.setPreserveRatio(true);
			iv.setSmooth(true);
			iv.setCache(true);

		});
		toolBar.getItems().add(negativeButton);

		negativeButton.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> negativeButton.setEffect(shadow));
		negativeButton.addEventHandler(MouseEvent.MOUSE_EXITED, e -> negativeButton.setEffect(null));

		var grayscaleButton = new Button("Grayscale");
		grayscaleButton.setMaxSize(90, 10);
		grayscaleButton.setOnAction(arg0 -> {

			Image newImage = grayscaleTest.effect(previousImages[i]);
			previousImages[++i] = newImage;
			iv.setImage(newImage);
			iv.setPreserveRatio(true);
			iv.setSmooth(true);
			iv.setCache(true);

		});
		toolBar.getItems().add(grayscaleButton);

		grayscaleButton.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> grayscaleButton.setEffect(shadow));

		grayscaleButton.addEventHandler(MouseEvent.MOUSE_EXITED, e -> grayscaleButton.setEffect(null));

		Button thresholdButton = new Button("Threshold");
		thresholdButton.setMaxSize(90, 10);
		thresholdButton.setOnAction(arg0 -> {

			Image newImage = thresholdTest.effect(previousImages[i]);
			previousImages[++i] = newImage;
			iv.setImage(newImage);
			iv.setPreserveRatio(true);
			iv.setSmooth(true);
			iv.setCache(true);

		});
		toolBar.getItems().add(thresholdButton);

		thresholdButton.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> thresholdButton.setEffect(shadow));
		thresholdButton.addEventHandler(MouseEvent.MOUSE_EXITED, e -> thresholdButton.setEffect(null));

		Button solarisButton = new Button("Solaris");
		solarisButton.setMaxSize(90, 10);
		solarisButton.setOnAction(arg0 -> {

			Image newImage = solarisTest.effect(previousImages[i]);
			previousImages[++i] = newImage;
			iv.setImage(newImage);
			iv.setPreserveRatio(true);
			iv.setSmooth(true);
			iv.setCache(true);

		});
		toolBar.getItems().add(solarisButton);

		solarisButton.addEventHandler(MouseEvent.MOUSE_ENTERED,
				e -> solarisButton.setEffect(shadow));

		solarisButton.addEventHandler(MouseEvent.MOUSE_EXITED,
				e -> solarisButton.setEffect(null));

		Button gammaButton = new Button("Gamma");
		gammaButton.setMaxSize(90, 10);
		gammaButton.setOnAction(arg0 -> {

			Image newImage = redTest.effect(previousImages[i]);
			previousImages[++i] = newImage;
			iv.setImage(newImage);
			iv.setPreserveRatio(true);
			iv.setSmooth(true);
			iv.setCache(true);

		});
		toolBar.getItems().add(gammaButton);

		gammaButton.addEventHandler(MouseEvent.MOUSE_ENTERED,
				e -> gammaButton.setEffect(shadow));

		gammaButton.addEventHandler(MouseEvent.MOUSE_EXITED,
				e -> gammaButton.setEffect(null));

		Button edgesButton = new Button("Edges");
		edgesButton.setMaxSize(90, 10);
		edgesButton.setOnAction(arg0 -> {

			Image newImage = edgesTest.effect(previousImages[i]);
			previousImages[++i] = newImage;
			iv.setImage(newImage);

			//iv.setFitHeight(800);
			//iv.setFitWidth(1320);
			iv.setPreserveRatio(true);
			iv.setSmooth(true);
			iv.setCache(true);

		});
		toolBar.getItems().add(edgesButton);

		edgesButton.addEventHandler(MouseEvent.MOUSE_ENTERED,
				e -> edgesButton.setEffect(shadow));

		edgesButton.addEventHandler(MouseEvent.MOUSE_EXITED,
				e -> edgesButton.setEffect(null));

		
		//horizontal mirror
		Button verticalMirrorButton = new Button("Mirror1");
		verticalMirrorButton.setMaxSize(90, 10);
		verticalMirrorButton.setOnAction(arg0 -> {

			iv.setImage(verticalMirror(image));
			iv.setFitHeight(800);
			iv.setFitWidth(1320);
			iv.setPreserveRatio(true);
			iv.setSmooth(true);
			iv.setCache(true);

		});
		toolBar.getItems().add(verticalMirrorButton);

		verticalMirrorButton.addEventHandler(MouseEvent.MOUSE_ENTERED,
				e -> verticalMirrorButton.setEffect(shadow));

		verticalMirrorButton.addEventHandler(MouseEvent.MOUSE_EXITED,
				e -> verticalMirrorButton.setEffect(null));

		//vertical mirror
		Button horizontalMirrorButton = new Button("Mirror2");
		horizontalMirrorButton.setMaxSize(90, 10);
		horizontalMirrorButton.setOnAction(arg0 -> {

			iv.setImage(horizontalMirror(image));
			iv.setFitHeight(800);
			iv.setFitWidth(1320);
			iv.setPreserveRatio(true);
			iv.setSmooth(true);
			iv.setCache(true);

		});
		toolBar.getItems().add(horizontalMirrorButton);

		horizontalMirrorButton.addEventHandler(MouseEvent.MOUSE_ENTERED,
				e -> horizontalMirrorButton.setEffect(shadow));

		horizontalMirrorButton.addEventHandler(MouseEvent.MOUSE_EXITED,
				e -> horizontalMirrorButton.setEffect(null));

		Button sharpenButton = new Button("Sharpen");
		sharpenButton.setMaxSize(90, 10);
		sharpenButton.setOnAction(arg0 -> {

			iv.setImage(sharpen(image));
			iv.setFitHeight(800);
			iv.setFitWidth(1320);
			iv.setPreserveRatio(true);
			iv.setSmooth(true);
			iv.setCache(true);

		});
		toolBar.getItems().add(sharpenButton);

		sharpenButton.addEventHandler(MouseEvent.MOUSE_ENTERED,
				e -> sharpenButton.setEffect(shadow));

		sharpenButton.addEventHandler(MouseEvent.MOUSE_EXITED,
				e -> sharpenButton.setEffect(null));

		Button blurButton = new Button("Blur");
		blurButton.setMaxSize(90, 10);
		blurButton.setOnAction(arg0 -> {

			iv.setImage(blur(image));
			iv.setFitHeight(800);
			iv.setFitWidth(1320);
			iv.setPreserveRatio(true);
			iv.setSmooth(true);
			iv.setCache(true);

		});
		toolBar.getItems().add(blurButton);

		blurButton.addEventHandler(MouseEvent.MOUSE_ENTERED,
				e -> blurButton.setEffect(shadow));

		blurButton.addEventHandler(MouseEvent.MOUSE_EXITED,
				e -> blurButton.setEffect(null));

		var makeRedderButton = new Button("Redden");
		makeRedderButton.setMaxSize(90, 10);
		makeRedderButton.setOnAction(arg0 -> {

			Image newImage = redTest.effect(previousImages[i]);
			previousImages[++i] = newImage;

			iv.setImage(newImage);
			iv.setPreserveRatio(true);
			iv.setSmooth(true);
			iv.setCache(true);

		});
		toolBar.getItems().add(makeRedderButton);

		makeRedderButton.addEventHandler(MouseEvent.MOUSE_ENTERED,
				e -> makeRedderButton.setEffect(shadow));

		makeRedderButton.addEventHandler(MouseEvent.MOUSE_EXITED,
				e -> makeRedderButton.setEffect(null));

		Button sepiaButton = new Button("Sepia");
		sepiaButton.setMaxSize(90, 10);
		sepiaButton.setOnAction(arg0 -> {
			iv.setImage(sepia(image));
			iv.setFitHeight(800);
			iv.setFitWidth(1320);
			iv.setPreserveRatio(true);
			iv.setSmooth(true);
			iv.setCache(true);

		});
		toolBar.getItems().add(sepiaButton);

		sepiaButton.addEventHandler(MouseEvent.MOUSE_ENTERED,
				e -> sepiaButton.setEffect(shadow));

		sepiaButton.addEventHandler(MouseEvent.MOUSE_EXITED,
				e -> sepiaButton.setEffect(null));

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



	private Image blur(Image image) {
		PixelReader pixelReader = image.getPixelReader();
		WritableImage wImage = new WritableImage((int) this.image.getWidth(), (int) this.image.getHeight());
		PixelWriter pixelWriter = wImage.getPixelWriter();
		for (int readY = 4; readY < this.image.getHeight() - 4; readY++) {
			for (int readX = 4; readX < this.image.getWidth() - 4; readX++) {
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

	

	private Image verticalMirror(Image image) {
		PixelReader pixelReader = image.getPixelReader();
		WritableImage wImage = new WritableImage((int) this.image.getWidth(),
				(int) this.image.getHeight());
		PixelWriter pixelWriter = wImage.getPixelWriter();
		for (int readY = 1; readY < this.image.getHeight() - 1; readY++) {
			for (int readX = 1; readX < this.image.getWidth() - 1; readX++) {
				Color color = pixelReader.getColor(readX, readY);
				pixelWriter.setColor(readX, (int) (this.image.getHeight() - readY),
						color);

			}
		}
		i = i + 1;
		previousImages[i] = wImage;
		return wImage;
	}

	private Image horizontalMirror(Image image) {
		PixelReader pixelReader = image.getPixelReader();
		WritableImage wImage = new WritableImage((int) this.image.getWidth(),
				(int) this.image.getHeight());
		PixelWriter pixelWriter = wImage.getPixelWriter();
		for (int readY = 1; readY < this.image.getHeight() - 1; readY++) {
			for (int readX = 1; readX < this.image.getWidth() - 1; readX++) {
				Color color = pixelReader.getColor(readX, readY);
				pixelWriter.setColor((int) (this.image.getWidth() - readX), readY,
						color);

			}
		}
		i = i + 1;
		previousImages[i] = wImage;
		return wImage;
	}


	private Image sepia(Image image) {
		PixelReader pixelReader = image.getPixelReader();
		WritableImage wImage = new WritableImage((int) this.image.getWidth(),
				(int) this.image.getHeight());
		PixelWriter pixelWriter = wImage.getPixelWriter();
		for (int readY = 0; readY < this.image.getHeight(); readY++) {
			for (int readX = 0; readX < this.image.getWidth(); readX++) {
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
