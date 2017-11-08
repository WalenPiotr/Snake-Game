import javafx.scene.Scene;
import javafx.stage.Stage;

class Director {
    private Stage stage;

    private Scene menuScene;
    private Scene highScoresScene;
    private Scene gameScene;
    private Scene settingsScene;
    Director(Stage stage) {
        this.stage = stage;
    }

    void setSettingsScene(Scene settingsScene) {
        this.settingsScene = settingsScene;
    }

    void setMenuScene(Scene menuScene) {
        this.menuScene = menuScene;
    }

    void setHighScoreScene(Scene highScoresScene) {
        this.highScoresScene = highScoresScene;
    }

    void setGameScene(Scene gameScene) {
        this.gameScene = gameScene;
    }

    void openGameScene() {
        Config.load();
        stage.setScene(gameScene);
    }

    void openHighScoresScene() {
        stage.setScene(highScoresScene);
    }

    void openMenuScene() {
        stage.setScene(menuScene);
    }

    void openSettingsScene() {
        stage.setScene(settingsScene);
    }

    void exit() {
        stage.close();
    }

}
