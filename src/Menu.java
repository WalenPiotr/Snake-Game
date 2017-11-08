import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

class Menu {
    private Scene menuScene;
    private Map<String, Button> menuButtons;
    private Director director;

    Menu(Director director) {
        this.director = director;
        new Graphics();
        new EventHandlers();
        director.setMenuScene(menuScene);
    }

    private class Graphics{

        Graphics(){
            initializeFrame();
        }

        private void initializeFrame() {
            VBox vBox = new VBox();
            initializeButtons();
            vBox.getChildren().addAll(menuButtons.values());
            BorderPane borderPane = new BorderPane();
            borderPane.setCenter(vBox);
            borderPane.setMinSize(Config.WINDOW_SIZE, Config.WINDOW_SIZE);
            menuScene = new Scene(borderPane);
        }

        private void initializeButtons(){
            ArrayList<String> buttonNames = new ArrayList<>(Arrays.asList(
                    "Play",
                    "Settings",
                    "High Scores",
                    "Exit"));
            menuButtons = new HashMap<>();
            buttonNames.forEach(name -> menuButtons.put(name, new Button(name + " [" + name.charAt(0) + "]")));
            menuButtons.forEach((name, button) -> {
                VBox.setVgrow(button, Priority.ALWAYS);
                button.setMaxWidth(Double.MAX_VALUE);
                button.setMaxHeight(Double.MAX_VALUE);
                button.setFocusTraversable(false);
                button.setFont(Font.font(50));
            });
        }

    }

    private class EventHandlers{

        EventHandlers(){
            registerButtonHandler();
            registerKeyHandler();
        }
        private void registerButtonHandler() {
            menuButtons.get("Play").addEventHandler(MouseEvent.MOUSE_CLICKED, e -> director.openGameScene());
            menuButtons.get("Settings").addEventHandler(MouseEvent.MOUSE_CLICKED, e -> director.openSettingsScene());
            menuButtons.get("High Scores").addEventHandler(MouseEvent.MOUSE_CLICKED, e -> director.openHighScoresScene());
            menuButtons.get("Exit").addEventHandler(MouseEvent.MOUSE_CLICKED, e -> director.exit());
        }

        private void registerKeyHandler() {
            menuScene.setOnKeyPressed(event -> {
                KeyCode keyCode = event.getCode();
                if (keyCode.equals(KeyCode.P)) director.openGameScene();
                if (keyCode.equals(KeyCode.E)) director.exit();
                if (keyCode.equals(KeyCode.H)) director.openHighScoresScene();
                if (keyCode.equals(KeyCode.S)) director.openSettingsScene();
            });
        }


    }


}
