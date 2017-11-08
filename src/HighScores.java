import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

import java.util.*;

class HighScores {
    private Scene highScoreScene;
    private TableView<Record> table = new TableView<>();
    private ObservableList<Record> recordList = ScoreIO.getRecords();
    private Map<String, Button> tableButtons = new LinkedHashMap<>();
    private Director director;


    HighScores(Director director) {
        this.director = director;
        new Graphics();
        new EventHandlers();
        director.setHighScoreScene(highScoreScene);
    }

    private class Graphics{
        Graphics(){
            initializeFrame();
        }
        private void initializeFrame() {
            initializeButtons();
            HBox hBox = new HBox();
            hBox.getChildren().addAll(tableButtons.values());

            initializeTable();
            BorderPane tablePane = new BorderPane();
            tablePane.setCenter(table);

            VBox vBox = new VBox();
            vBox.getChildren().addAll(hBox, tablePane);

            BorderPane borderPane = new BorderPane();
            borderPane.setCenter(vBox);

            highScoreScene = new Scene(borderPane, Config.WINDOW_SIZE, Config.WINDOW_SIZE);
            highScoreScene.getStylesheets().add("Styles.css");
        }

        private void initializeButtons() {
            List<String> buttonNames = Arrays.asList(
                    "Menu",
                    "Clear",
                    "Refresh",
                    "Exit"
            );
            buttonNames.forEach(name ->
                    tableButtons.put(name, new Button(name + " [" + name.charAt(0) + "]")));
            tableButtons.forEach((String name, Button button) -> {
                HBox.setHgrow(button, Priority.ALWAYS);
                button.setMinHeight(50);
                button.setMaxWidth(Double.MAX_VALUE);
                button.setFocusTraversable(false);
                button.setFont(Font.font(20));
            });
        }

        private void initializeTable(){
            table.setPrefHeight(Config.WINDOW_SIZE);
            table.setMaxWidth(Config.WINDOW_SIZE);
            table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

            TableColumn<Record, Date> dateColumn = new TableColumn<>("date");
            dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
            dateColumn.setMinWidth(table.getMaxWidth() * .4);

            TableColumn<Record, Integer> scoreColumn = new TableColumn<>("score");
            scoreColumn.setCellValueFactory(new PropertyValueFactory<>("score"));
            scoreColumn.setMinWidth(table.getMaxWidth() * .2);

            TableColumn<Record, Integer> timeColumn = new TableColumn<>("time");
            timeColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
            timeColumn.setMinWidth(table.getMaxWidth() * .2);

            TableColumn<Record, Boolean> obstaclesEnabledColumn = new TableColumn<>("obstacles");
            obstaclesEnabledColumn.setCellValueFactory(new PropertyValueFactory<>("OBSTACLES_ENABLED"));
            obstaclesEnabledColumn.setMinWidth(table.getMaxWidth() * .2);
            table.getColumns().add(dateColumn);
            table.getColumns().add(timeColumn);
            table.getColumns().add(scoreColumn);
            table.getColumns().add(obstaclesEnabledColumn);
            table.setItems(recordList);
        }
    }


    private class EventHandlers {
        Action action = new Action();
        private class Action {

            void openMenu() {
                director.openMenuScene();
            }

            void exit() {
                director.exit();
            }

            void clearTable() {
                ScoreIO.clearScores();
                refreshTable();
                ScoreIO.loadScores();
            }

            void refreshTable() {
                ScoreIO.loadScores();
                table.setItems(ScoreIO.getRecords());
            }
        }


        EventHandlers() {
            registerButtons();
            registerKeys();
        }

        private void registerButtons() {
            tableButtons.get("Menu").addEventHandler(MouseEvent.MOUSE_CLICKED, e -> action.openMenu());
            tableButtons.get("Clear").addEventHandler(MouseEvent.MOUSE_CLICKED, e -> action.clearTable());
            tableButtons.get("Refresh").addEventHandler(MouseEvent.MOUSE_CLICKED, e -> action.refreshTable());
            tableButtons.get("Exit").addEventHandler(MouseEvent.MOUSE_CLICKED, e -> action.exit());

        }

        private void registerKeys() {
            highScoreScene.setOnKeyPressed(event -> {
                KeyCode keyCode = event.getCode();
                if (keyCode.equals(KeyCode.E)) action.exit();
                if (keyCode.equals(KeyCode.C)) action.clearTable();
                if (keyCode.equals(KeyCode.R)) action.refreshTable();
                if (keyCode.equals(KeyCode.M)) action.openMenu();
            });
        }

    }


}
