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
import javafx.embed.swing.SwingFXUtils;
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
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;
import rs.mrcco.effects.*;
import rs.mrcco.gui.ButtonFactory;

import javax.imageio.ImageIO;
import javax.swing.*;

public class Project extends Application {

    int currentImage = 0;
    Image[] previousImages = new Image[100];

    //available effects
    Red redTest = new Red();
    Negativ negativeTest = new Negativ();
    Grayscale grayscaleTest = new Grayscale();
    Threshold thresholdTest = new Threshold();
    Solaris solarisTest = new Solaris();
    Edges edgesTest = new Edges();
    Blur blur = new Blur();
    HorizontalMirror horizontalMirror = new HorizontalMirror();
    VerticalMirror verticalMirror = new VerticalMirror();
    Sharpen sharpen = new Sharpen();
    Sepia sepia = new Sepia();

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
        var imageView = new ImageView();
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
                previousImages[0] = new Image(inputStream);
                imageView.setImage(previousImages[0]);
                imageView.setFitHeight(1024);
                imageView.setFitHeight(720);
                imageView.setPreserveRatio(true);
                imageView.setSmooth(true);
                imageView.setCache(true);

            } catch (FileNotFoundException ex) {
                Logger.getLogger(Project.class.getName()).log(
                        Level.SEVERE, null, ex);
            }
        });

        // todo - fixme
        save.setOnAction(arg0 -> {
            FileChooser fileChooser = new FileChooser();

            // Set extension filter
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                    "JPG files (*.jpg)", "*.jpg");
            fileChooser.getExtensionFilters().add(extFilter);

            // Show save file dialog
            File file = fileChooser.showSaveDialog(stage);

            if (file != null) {

                saveToFile(previousImages[currentImage], file);

            }
        });

        mainPane.setCenter((imageView));

        var backButton = ButtonFactory.createDefaultButton("Back", e -> {
            if (currentImage > 1) {
                imageView.setImage(previousImages[--currentImage]);

            } else {
                imageView.setImage(previousImages[0]);
                currentImage = 0;
            }
        });
        toolBar.getItems().add(backButton);

        var negativeButton = ButtonFactory.createDefaultButton("Negative", e -> {

            Image newImage = negativeTest.effect(previousImages[currentImage]);
            previousImages[++currentImage] = newImage;
            imageView.setImage(newImage);

        });
        toolBar.getItems().add(negativeButton);

        var grayscaleButton = ButtonFactory.createDefaultButton("Grayscale", e -> {

            Image newImage = grayscaleTest.effect(previousImages[currentImage]);
            previousImages[++currentImage] = newImage;
            imageView.setImage(newImage);

        });
        toolBar.getItems().add(grayscaleButton);

        var thresholdButton = ButtonFactory.createDefaultButton("Threshold", e -> {

            Image newImage = thresholdTest.effect(previousImages[currentImage]);
            previousImages[++currentImage] = newImage;
            imageView.setImage(newImage);

        });
        toolBar.getItems().add(thresholdButton);

        var solarisButton = ButtonFactory.createDefaultButton("Solaris", e -> {

            Image newImage = solarisTest.effect(previousImages[currentImage]);
            previousImages[++currentImage] = newImage;
            imageView.setImage(newImage);

        });
        toolBar.getItems().add(solarisButton);


        var gammaButton = ButtonFactory.createDefaultButton("Gamma", e -> {

            Image newImage = redTest.effect(previousImages[currentImage]);
            previousImages[++currentImage] = newImage;
            imageView.setImage(newImage);

        });
        toolBar.getItems().add(gammaButton);

        var edgesButton = ButtonFactory.createDefaultButton("Edges", e -> {

            Image newImage = edgesTest.effect(previousImages[currentImage]);
            previousImages[++currentImage] = newImage;
            imageView.setImage(newImage);

        });
        toolBar.getItems().add(edgesButton);

        //horizontal mirror
        var verticalMirrorButton = ButtonFactory.createDefaultButton("Horizontal", e -> {

            Image newImage = horizontalMirror.effect(previousImages[currentImage]);
            previousImages[++currentImage] = newImage;
            imageView.setImage(newImage);

        });
        toolBar.getItems().add(verticalMirrorButton);


        //vertical mirror
        var horizontalMirrorButton = ButtonFactory.createDefaultButton("Vertical", e -> {

            Image newImage = verticalMirror.effect(previousImages[currentImage]);
            previousImages[++currentImage] = newImage;
            imageView.setImage(newImage);

        });
        toolBar.getItems().add(horizontalMirrorButton);

        var sharpenButton = ButtonFactory.createDefaultButton("Sharpen", e -> {

            Image newImage = sharpen.effect(previousImages[currentImage]);
            previousImages[++currentImage] = newImage;
            imageView.setImage(newImage);

        });
        toolBar.getItems().add(sharpenButton);

        var blurButton = ButtonFactory.createDefaultButton("Blur", e -> {

            Image newImage = blur.effect(previousImages[currentImage]);
            previousImages[++currentImage] = newImage;
            imageView.setImage(newImage);

        });
        toolBar.getItems().add(blurButton);


        var makeRedderButton = ButtonFactory.createDefaultButton("Redden", e -> {

            Image newImage = redTest.effect(previousImages[currentImage]);
            previousImages[++currentImage] = newImage;
            imageView.setImage(newImage);

        });
        toolBar.getItems().add(makeRedderButton);


        Button sepiaButton = ButtonFactory.createDefaultButton("Sepia", e -> {
            Image newImage = sepia.effect(previousImages[currentImage]);
            previousImages[++currentImage] = newImage;
            imageView.setImage(newImage);
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



    private void saveToFile(Image content, File file) {
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(content, null), "jpg", file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
