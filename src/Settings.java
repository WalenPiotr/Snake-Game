import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Settings {
    Director director;
    Scene settingsScene;
    Map<String, Button> buttons = new LinkedHashMap<>();

    Settings(Director director) {
        this.director = director;
        new Graphics();
        new EventHandlers();
        director.setSettingsScene(settingsScene);
    }

    private class Graphics {
        Graphics() {
            initializeFrame();
        }

        private void initializeFrame() {
            VBox vBox = new VBox();
            BorderPane borderPane = new BorderPane();
            borderPane.setMinSize(Config.WINDOW_SIZE, Config.WINDOW_SIZE);
            borderPane.setCenter(vBox);
            settingsScene = new Scene(borderPane);
            initializeButtons();
            HBox hBox = new HBox();

            hBox.getChildren().addAll(buttons.values());
            vBox.getChildren().add(hBox);
        }

        private void initializeButtons() {
            List<String> buttonNames = Arrays.asList(
                    "Menu",
                    "Default",
                    "Exit"
            );
            buttonNames.forEach(name ->
                    buttons.put(name, new Button(name + " [" + name.charAt(0) + "]")));
            buttons.forEach((String name, Button button) -> {
                HBox.setHgrow(button, Priority.ALWAYS);
                button.setFocusTraversable(false);
                button.setFont(Font.font(20));
                button.setMaxWidth(Double.MAX_VALUE);
                button.setMinHeight(50);
            });
        }
    }


    private class EventHandlers {

        EventHandlers() {
            registerButtons();
            registerKeys();
        }

        private void registerButtons() {
            buttons.get("Menu").addEventHandler(MouseEvent.MOUSE_CLICKED, event -> director.openMenuScene());
            buttons.get("Exit").addEventHandler(MouseEvent.MOUSE_CLICKED, event -> director.exit());
            buttons.get("Default").addEventHandler(MouseEvent.MOUSE_CLICKED, event -> System.out.println("Default Placeholder"));
        }

        private void registerKeys() {
            settingsScene.setOnKeyPressed(event -> {
                KeyCode keyCode = event.getCode();
                if (keyCode.equals(KeyCode.M)) director.openMenuScene();
                if (keyCode.equals(KeyCode.E)) director.exit();
                if (keyCode.equals(KeyCode.D)) System.out.println("Default Placeholder");
            });
        }


    }
}
