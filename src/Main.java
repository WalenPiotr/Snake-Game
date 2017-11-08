import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Config.load();
        initializeStage(primaryStage);
        initializeComponents(primaryStage);
        primaryStage.show();

    }

    private void initializeComponents(Stage stage) {
        Director director = new Director(stage);
        new Menu(director);
        new Game(director);
        new HighScores(director);
        new Settings(director);
        director.openMenuScene();
    }

    private void initializeStage(Stage stage) {
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("Snake");
        stage.setResizable(false);
    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }


}
