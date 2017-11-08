import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

import java.util.*;

import static java.util.Map.entry;

class Game {

    private Scene gameScene;
    private Snake snake = new Snake();
    private Painter painter = new Painter(snake);
    private Map<String, Label> labels = new LinkedHashMap<>();
    private Director director;
    private GameLoop gameLoop;
    private Map<String, Button> buttons = new LinkedHashMap<>();


    Game(Director director) {
        this.director = director;
        new Graphics();
        new EventHandlers();
        director.setGameScene(gameScene);
        gameLoop = new GameLoop(snake, painter, labels);
    }


    private class Graphics{

        Graphics(){
            initializeFrame();
        }
        private void initializeFrame() {
            Rectangle[][] rectangles = painter.getRectangles();
            GridPane gridPane = new GridPane();
            initializeGridPane(gridPane);
            initializeButtons();
            HBox hBox1 = new HBox();
            hBox1.getChildren().addAll(buttons.values());
            initializeLabels();
            HBox hBox2 = new HBox();
            hBox2.getChildren().addAll(labels.values());
            VBox vBox = new VBox();
            vBox.getChildren().add(hBox2);
            vBox.getChildren().add(hBox1);
            vBox.getChildren().add(gridPane);
            BorderPane borderPane = new BorderPane();
            borderPane.setCenter(vBox);
            addRectangles(gridPane, rectangles);
            gameScene = new Scene(borderPane);
        }

        private void initializeLabels(){
            List<String> labelNames = Arrays.asList("scoreLabel", "timeLabel");
            labelNames.forEach(name -> labels.put(name, new Label(name)));
            labels.forEach((name, label) -> {
                label.setFont(Font.font(20));
                label.setAlignment(Pos.CENTER);
                label.setMinWidth(Config.WINDOW_SIZE / 2);
                label.setMinHeight(50);
            });
        }

        private void initializeButtons() {
            List<String> buttonNames = Arrays.asList(
                    "Menu",
                    "Start",
                    "Reset",
                    "Exit"
            );
            buttonNames.forEach(name ->
                    buttons.put(name, new Button(name + " [" + name.charAt(0) + "]")));
            buttons.forEach((name, button) -> {
                HBox.setHgrow(button, Priority.ALWAYS);
                button.setMaxWidth(Double.MAX_VALUE);
                button.setMinHeight(50);
                button.setFocusTraversable(false);
                button.setFont(Font.font(20));
            });
        }

        private void addRectangles(GridPane gridPane, Rectangle[][] rectangles) {
            Insets insets = new Insets(Config.INSETS_SIZE);
            for (int columnIndex = 0; columnIndex < Config.COLUMNS; columnIndex++) {
                for (int rowIndex = 0; rowIndex < Config.ROWS; rowIndex++) {
                    gridPane.add(rectangles[columnIndex][rowIndex], columnIndex, rowIndex);
                    GridPane.setMargin(rectangles[columnIndex][rowIndex], insets);
                }
            }
        }

        private void initializeGridPane(GridPane gridPane) {
            ColumnConstraints column = new ColumnConstraints();
            RowConstraints row = new RowConstraints();
            column.setPrefWidth(Config.CELL_SIZE);
            row.setPrefHeight(Config.CELL_SIZE);
            List<RowConstraints> rowConstraintsList = Collections.nCopies(Config.ROWS, row);
            List<ColumnConstraints> columnConstraintsList = Collections.nCopies(Config.COLUMNS, column);
            gridPane.getRowConstraints().addAll(rowConstraintsList);
            gridPane.getColumnConstraints().addAll(columnConstraintsList);
        }
    }

    private class EventHandlers {
        private Action action = new Action();
        private class Action {
            void start() {
                gameLoop.start();
            }

            void reset() {
                painter.removeElements();
                snake.initialize();
                painter.paintElements();
                gameLoop.stop();
            }

            void exit() {
                director.exit();
            }

            void openMenu() {
                director.openMenuScene();
                painter.removeElements();
                snake.initialize();
                painter.paintElements();
                gameLoop.stop();
            }

        }
        private Map<KeyCode, Direction> keyCodeDirectionMap = Map.ofEntries(
                entry(KeyCode.UP, Direction.NORTH),
                entry(KeyCode.DOWN, Direction.SOUTH),
                entry(KeyCode.LEFT, Direction.WEST),
                entry(KeyCode.RIGHT, Direction.EAST)
        );
        private Map<Direction, Direction> oppositeDirectionMap = Map.ofEntries(
                entry(Direction.SOUTH, Direction.NORTH),
                entry(Direction.NORTH, Direction.SOUTH),
                entry(Direction.EAST, Direction.WEST),
                entry(Direction.WEST, Direction.EAST)
        );

        EventHandlers() {
            registerButtonHandler();
            registerKeyHandler();
        }

        private void registerButtonHandler() {
            buttons.get("Menu").addEventHandler(MouseEvent.MOUSE_CLICKED, event -> action.openMenu());
            buttons.get("Start").addEventHandler(MouseEvent.MOUSE_CLICKED, event -> action.start());
            buttons.get("Reset").addEventHandler(MouseEvent.MOUSE_CLICKED, event -> action.reset());
            buttons.get("Exit").addEventHandler(MouseEvent.MOUSE_CLICKED, event -> action.exit());
        }

        private void registerKeyHandler() {
            gameScene.setOnKeyPressed(event -> {
                KeyCode keyCode = event.getCode();
                if (keyCodeDirectionMap.containsKey(keyCode)) {
                    if (snake.getDirection() != oppositeDirectionMap.get(keyCodeDirectionMap.get(keyCode)))
                        snake.setNewDirection(keyCodeDirectionMap.get(keyCode));
                }
                if (keyCode.equals(KeyCode.R)) action.reset();
                if (keyCode.equals(KeyCode.M)) action.openMenu();
                if (keyCode.equals(KeyCode.S)) action.start();
                if (keyCode.equals(KeyCode.E)) action.exit();
            });
        }
    }

}
