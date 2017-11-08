import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.util.Duration;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

public class GameLoop {
    private Snake snake;
    private Painter painter;
    private final Timeline timeline = new Timeline();
    private Map<String, Label> labels;
    private KeyFrame frame;
    boolean crashed;
    boolean running;

    GameLoop(Snake snake, Painter painter,  Map<String, Label> labels) {
        this.snake = snake;
        this.painter = painter;
        this.labels = labels;
        init();
        painter.paintElements();
        labels.get("scoreLabel").setText("Record = " + snake.getScore());
        labels.get("timeLabel").setText("Time = " + (System.currentTimeMillis() - snake.getTimeStamp()) / 1000);
    }

    void init(){
        frame = new KeyFrame(Duration.millis(Config.STEP_TIME), event -> {
            painter.removeElements();
            snake.step();
            painter.paintElements();
            labels.get("scoreLabel").setText("Record = " + snake.getScore());
            labels.get("timeLabel").setText("Time = " + (System.currentTimeMillis() - snake.getTimeStamp()) / 1000);
            crashed = snake.isCrashed();
            if(crashed){
                try {
                    writeScoreFile(snake.getScore(), (int)(System.currentTimeMillis() - snake.getTimeStamp()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                timeline.stop();
            }
        });
        timeline.getKeyFrames().add(frame);
        timeline.setCycleCount(Animation.INDEFINITE);
    }

    private void writeScoreFile(int score, int time) throws IOException {
        File file = new File("C:\\Users\\walen\\Desktop\\Snake\\highscore.txt");
        FileWriter writer = new FileWriter(file, true);
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        writer.write(format.format(date) + " " + score + " " + time + " "
                + Config.OBSTACLES_ENABLED + System.getProperty("line.separator"));
        writer.close();
    }



    void start() {
        if(!running){
            timeline.play();
            snake.setScore(0);
            snake.setTimeStamp(System.currentTimeMillis());
            running = true;
        }
    }


    void stop() {

        if(running){
            timeline.stop();
            running = false;
        }
    }

}





