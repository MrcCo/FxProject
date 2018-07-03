package project;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Projekat extends Application {
	File fajl;
	Image image;
	WritableImage wImage;
	ImageView iv = new ImageView();
	int i = 0;
	
	//prevous moves
	Image[] potezi = new Image[100];

	//available effects
	Red redTest = new Red();
	Gamma gammaTest = new Gamma();
	Negativ negativeTest = new Negativ();
	Grayscale  grayscaleTest = new Grayscale();
	Threshold thresholdTest = new Threshold();
	Solaris solarisTest = new Solaris();
	Edges edgesTest = new Edges();
	
	
	@Override
	public void start(Stage stage) throws Exception {
		BorderPane glavna = new BorderPane();
		MenuBar menubar = new MenuBar();
		Menu file = new Menu("File");
		MenuItem ucitaj = new MenuItem("Open image");
		MenuItem save = new MenuItem("Save image");
		menubar.getMenus().add(file);
		menubar.setStyle(" -fx-font: 16 cambria; -fx-base: #ffa500;");
		file.getItems().add(ucitaj);
		file.getItems().add(save);
		glavna.setTop(menubar);
		ToolBar alati = new ToolBar();
		alati.setOrientation(Orientation.VERTICAL);
		alati.setStyle(" -fx-font: 16 cambria; -fx-base:#ffa500;");
		glavna.setRight(alati);
		Image welcom = new Image("http://oi61.tinypic.com/123ljdx.jpg");
		iv.setImage(welcom);
		iv.setFitHeight(800);
		iv.setFitWidth(1320);
		ucitaj.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				FileChooser fileChooser = new FileChooser();
				FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter(
						"JPG files (*.jpg)", "*.JPG");
				fileChooser.getExtensionFilters().addAll(extFilterJPG);
				File file1 = fileChooser.showOpenDialog(null);
				String path = file1.getAbsolutePath();
				try {
					InputStream inputStream = new FileInputStream(path);
					image = new Image(inputStream);
					potezi[0] = image;
					iv.setImage(image);
					iv.setFitHeight(1024);
					iv.setFitHeight(720);
					iv.setPreserveRatio(true);
					iv.setSmooth(true);
					iv.setCache(true);

				} catch (FileNotFoundException ex) {
					Logger.getLogger(Projekat.class.getName()).log(
							Level.SEVERE, null, ex);
					image = null;
				}
			}

		});

		save.setOnAction(new EventHandler<ActionEvent>() {
			// ovaj deo ne radi
			@Override
			public void handle(ActionEvent arg0) {
				FileChooser fileChooser = new FileChooser();

				// Set extension filter
				FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
						"JPG files (*.jpg)", "*.JPG");
				fileChooser.getExtensionFilters().add(extFilter);

				// Show save file dialog
				File file = fileChooser.showSaveDialog(stage);

				if (file != null) {

					SaveFile("Hello", file);

				}
			}
		});

		glavna.setCenter((iv));
		Image imback = new Image(
				"http://cssnerd.com/wp-content/uploads/2013/03/Screen-Shot-2013-03-09-at-22.13.01-600x367.png");
		ImageView ivback = new ImageView();
		ivback.setFitHeight(30);
		ivback.setFitWidth(70);
		ivback.setImage(imback);
		Button back = new Button();
		DropShadow shadow = new DropShadow();
		back.setStyle("-fx-font: 16 cambria; -fx-base: #ffa500;");
		back.setMaxSize(70, 30);
		back.setGraphic(ivback);
		back.addEventHandler(MouseEvent.MOUSE_ENTERED,
				new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent e) {
						back.setEffect(shadow);
					}
				});

		back.addEventHandler(MouseEvent.MOUSE_EXITED,
				new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent e) {
						back.setEffect(null);
					}
				});
		back.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				if (i  > 1) {
					iv.setImage(potezi[--i]);
					//iv.setFitHeight(800);
					//iv.setFitWidth(1320);
					iv.setPreserveRatio(true);
					iv.setSmooth(true);
					iv.setCache(true);

				} else {
					iv.setImage(potezi[0]);
					i = 0;
					//iv.setFitHeight(800);
					//iv.setFitWidth(1320);
					iv.setPreserveRatio(true);
					iv.setSmooth(true);
					iv.setCache(true);

				}
			}
		});
		alati.getItems().add(back);

		Button negativ = new Button("Negativ");
		negativ.setMaxSize(90, 10);
		negativ.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {

				Image newImage = negativeTest.effect(potezi[i]);
				potezi[++i] = newImage;		
				iv.setImage(newImage);
				
				//iv.setFitHeight(800);
				//iv.setFitWidth(1320);
				iv.setPreserveRatio(true);
				iv.setSmooth(true);
				iv.setCache(true);

			}
		});
		alati.getItems().add(negativ);

		negativ.addEventHandler(MouseEvent.MOUSE_ENTERED,
				new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent e) {
						negativ.setEffect(shadow);
					}
				});

		negativ.addEventHandler(MouseEvent.MOUSE_EXITED,
				new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent e) {
						negativ.setEffect(null);
					}
				});

		Button grayscale = new Button("Grayscale");
		grayscale.setMaxSize(90, 10);
		grayscale.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {

				Image newImage = grayscaleTest.effect(potezi[i]);
				potezi[++i] = newImage;		
				iv.setImage(newImage);
				//iv.setFitHeight(800);
				//iv.setFitWidth(1320);
				iv.setPreserveRatio(true);
				iv.setSmooth(true);
				iv.setCache(true);

			}
		});
		alati.getItems().add(grayscale);

		grayscale.addEventHandler(MouseEvent.MOUSE_ENTERED,
				new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent e) {
						grayscale.setEffect(shadow);
					}
				});

		grayscale.addEventHandler(MouseEvent.MOUSE_EXITED,
				new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent e) {
						grayscale.setEffect(null);
					}
				});

		Button threshold = new Button("Threshold");
		threshold.setMaxSize(90, 10);
		threshold.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {

				Image newImage = thresholdTest.effect(potezi[i]);
				potezi[++i] = newImage;		
				iv.setImage(newImage);
				
				//iv.setFitHeight(800);
				//iv.setFitWidth(1320);
				iv.setPreserveRatio(true);
				iv.setSmooth(true);
				iv.setCache(true);

			}
		});
		alati.getItems().add(threshold);

		threshold.addEventHandler(MouseEvent.MOUSE_ENTERED,
				new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent e) {
						threshold.setEffect(shadow);
					}
				});

		threshold.addEventHandler(MouseEvent.MOUSE_EXITED,
				new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent e) {
						threshold.setEffect(null);
					}
				});

		Button solaris = new Button("Solaris");
		solaris.setMaxSize(90, 10);
		solaris.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {

				Image newImage = solarisTest.effect(potezi[i]);
				potezi[++i] = newImage;		
				iv.setImage(newImage);
				
				//iv.setFitHeight(800);
				//iv.setFitWidth(1320);
				iv.setPreserveRatio(true);
				iv.setSmooth(true);
				iv.setCache(true);

			}
		});
		alati.getItems().add(solaris);

		solaris.addEventHandler(MouseEvent.MOUSE_ENTERED,
				new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent e) {
						solaris.setEffect(shadow);
					}
				});

		solaris.addEventHandler(MouseEvent.MOUSE_EXITED,
				new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent e) {
						solaris.setEffect(null);
					}
				});

		Button gamma = new Button("Gamma");
		gamma.setMaxSize(90, 10);
		gamma.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {

				Image newImage = redTest.effect(potezi[i]);
				potezi[++i] = newImage;		
				iv.setImage(newImage);
				
				//iv.setFitHeight(800);
				//iv.setFitWidth(1320);
				iv.setPreserveRatio(true);
				iv.setSmooth(true);
				iv.setCache(true);

			}
		});
		alati.getItems().add(gamma);

		gamma.addEventHandler(MouseEvent.MOUSE_ENTERED,
				new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent e) {
						gamma.setEffect(shadow);
					}
				});

		gamma.addEventHandler(MouseEvent.MOUSE_EXITED,
				new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent e) {
						gamma.setEffect(null);
					}
				});

		Button edges = new Button("Ivice");
		edges.setMaxSize(90, 10);
		edges.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {

				Image newImage = edgesTest.effect(potezi[i]);
				potezi[++i] = newImage;		
				iv.setImage(newImage);
				
				//iv.setFitHeight(800);
				//iv.setFitWidth(1320);
				iv.setPreserveRatio(true);
				iv.setSmooth(true);
				iv.setCache(true);

			}
		});
		alati.getItems().add(edges);

		edges.addEventHandler(MouseEvent.MOUSE_ENTERED,
				new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent e) {
						edges.setEffect(shadow);
					}
				});

		edges.addEventHandler(MouseEvent.MOUSE_EXITED,
				new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent e) {
						edges.setEffect(null);
					}
				});

		
		//horizontal mirror
		Button mirror1 = new Button("Mirror1");
		mirror1.setMaxSize(90, 10);
		mirror1.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {

				iv.setImage(Mirror1(image));
				iv.setFitHeight(800);
				iv.setFitWidth(1320);
				iv.setPreserveRatio(true);
				iv.setSmooth(true);
				iv.setCache(true);

			}
		});
		alati.getItems().add(mirror1);

		mirror1.addEventHandler(MouseEvent.MOUSE_ENTERED,
				new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent e) {
						mirror1.setEffect(shadow);
					}
				});

		mirror1.addEventHandler(MouseEvent.MOUSE_EXITED,
				new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent e) {
						mirror1.setEffect(null);
					}
				});

		//vertical mirror
		Button mirror2 = new Button("Mirror2");
		mirror2.setMaxSize(90, 10);
		mirror2.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {

				iv.setImage(Mirror2(image));
				iv.setFitHeight(800);
				iv.setFitWidth(1320);
				iv.setPreserveRatio(true);
				iv.setSmooth(true);
				iv.setCache(true);

			}
		});
		alati.getItems().add(mirror2);

		mirror2.addEventHandler(MouseEvent.MOUSE_ENTERED,
				new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent e) {
						mirror2.setEffect(shadow);
					}
				});

		mirror2.addEventHandler(MouseEvent.MOUSE_EXITED,
				new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent e) {
						mirror2.setEffect(null);
					}
				});

		Button izostri = new Button("Izoštri");
		izostri.setMaxSize(90, 10);
		izostri.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {

				iv.setImage(Izostri(image));
				iv.setFitHeight(800);
				iv.setFitWidth(1320);
				iv.setPreserveRatio(true);
				iv.setSmooth(true);
				iv.setCache(true);

			}
		});
		alati.getItems().add(izostri);

		izostri.addEventHandler(MouseEvent.MOUSE_ENTERED,
				new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent e) {
						izostri.setEffect(shadow);
					}
				});

		izostri.addEventHandler(MouseEvent.MOUSE_EXITED,
				new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent e) {
						izostri.setEffect(null);
					}
				});

		Button blur = new Button("Blur");
		blur.setMaxSize(90, 10);
		blur.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {

				iv.setImage(Blur(image));
				iv.setFitHeight(800);
				iv.setFitWidth(1320);
				iv.setPreserveRatio(true);
				iv.setSmooth(true);
				iv.setCache(true);

			}
		});
		alati.getItems().add(blur);

		blur.addEventHandler(MouseEvent.MOUSE_ENTERED,
				new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent e) {
						blur.setEffect(shadow);
					}
				});

		blur.addEventHandler(MouseEvent.MOUSE_EXITED,
				new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent e) {
						blur.setEffect(null);
					}
				});

		Button pocrveni = new Button("Pocrveni");
		pocrveni.setMaxSize(90, 10);
		pocrveni.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {

				Image newImage = redTest.effect(potezi[i]);
				potezi[++i] = newImage;
				
				iv.setImage(newImage);
				iv.setPreserveRatio(true);
				iv.setSmooth(true);
				iv.setCache(true);

			}
		});
		alati.getItems().add(pocrveni);

		pocrveni.addEventHandler(MouseEvent.MOUSE_ENTERED,
				new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent e) {
						pocrveni.setEffect(shadow);
					}
				});

		pocrveni.addEventHandler(MouseEvent.MOUSE_EXITED,
				new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent e) {
						pocrveni.setEffect(null);
					}
				});

		Button sepia = new Button("Sepia");
		sepia.setMaxSize(90, 10);
		sepia.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				iv.setImage(Sepia(image));
				iv.setFitHeight(800);
				iv.setFitWidth(1320);
				iv.setPreserveRatio(true);
				iv.setSmooth(true);
				iv.setCache(true);

			}
		});
		alati.getItems().add(sepia);

		sepia.addEventHandler(MouseEvent.MOUSE_ENTERED,
				new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent e) {
						sepia.setEffect(shadow);
					}
				});

		sepia.addEventHandler(MouseEvent.MOUSE_EXITED,
				new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent e) {
						sepia.setEffect(null);
					}
				});

		Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
		stage.setX(primaryScreenBounds.getMinX());
		stage.setY(primaryScreenBounds.getMinY());
		stage.setWidth(primaryScreenBounds.getWidth());
		stage.setHeight(primaryScreenBounds.getHeight());
		Scene scena = new Scene(glavna);

		stage.setScene(scena);
		stage.show();

	}

	private Image Izostri(Image slika) {
		PixelReader pixelReader = slika.getPixelReader();
		WritableImage wImage = new WritableImage((int) image.getWidth(),
				(int) image.getHeight());
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
				c = new Color(Math.min(
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
		//wImage = (WritableImage) Grayscale(wImage);
		potezi[i] = wImage;
		return wImage;
	}



	private Image Blur(Image slika) {
		PixelReader pixelReader = slika.getPixelReader();
		WritableImage wImage = new WritableImage((int) image.getWidth(),
				(int) image.getHeight());
		Color c = new Color(0, 0, 0, 0);
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

				c = new Color(
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
		//wImage = (WritableImage) Grayscale(wImage);
		potezi[i] = wImage;
		return wImage;
	}

	

	private Image Mirror1(Image slika) {
		PixelReader pixelReader = slika.getPixelReader();
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
		potezi[i] = wImage;
		return wImage;
	}

	private Image Mirror2(Image slika) {
		PixelReader pixelReader = slika.getPixelReader();
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
		potezi[i] = wImage;
		return wImage;
	}


	private Image Sepia(Image slika) {
		PixelReader pixelReader = slika.getPixelReader();
		WritableImage wImage = new WritableImage((int) image.getWidth(),
				(int) image.getHeight());
		Color c = new Color(0, 0, 0, 0);
		PixelWriter pixelWriter = wImage.getPixelWriter();
		for (int readY = 0; readY < image.getHeight(); readY++) {
			for (int readX = 0; readX < image.getWidth(); readX++) {
				Color color = pixelReader.getColor(readX, readY);

				c = new Color(
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
		potezi[i] = wImage;
		return wImage;
	}


	private void SaveFile(String content, File file) {
		try {
			FileWriter fileWriter = null;

			fileWriter = new FileWriter(file);
			fileWriter.write(content);
			fileWriter.close();
		} catch (IOException ex) {
			Logger.getLogger(Projekat.class.getName()).log(Level.SEVERE, null,
					ex);
		}

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Application.launch(args);
	}
}
