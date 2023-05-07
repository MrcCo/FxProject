package rs.mrcco.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;

public class ButtonFactory {
    private static final DropShadow DROP_SHADOW = new DropShadow();
    private static final int DEFAULT_MAX_WIDTH = 90;
    private static final int DEFAULT_MAX_HEIGHT = 10;
    public static Button createDefaultButton(String title, EventHandler<ActionEvent> eventHandler) {

        var button = new Button(title);

        button.setStyle("-fx-font: 16 cambria; -fx-base: #ffa500;");
        button.setMaxSize(DEFAULT_MAX_WIDTH, DEFAULT_MAX_HEIGHT);
        button.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> button.setEffect(DROP_SHADOW));
        button.addEventHandler(MouseEvent.MOUSE_EXITED, e -> button.setEffect(null));
        button.setOnAction(eventHandler);

        return button;

    }

}
