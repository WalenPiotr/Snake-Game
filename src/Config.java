import javafx.scene.paint.Paint;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

class Config {
    static final int WINDOW_SIZE = 600;
    static final int ROWS = 15;
    static final int COLUMNS = 15;

    static final int CELL_SIZE = WINDOW_SIZE/ROWS;
    static final int RECTANGLE_ARC_HEIGHT = (int) (0.3*CELL_SIZE);
    static final int RECTANGLE_ARC_WIDTH = (int) (0.3*CELL_SIZE);
    static final int INSETS_SIZE = 2;

    static final Paint snakePaint = Paint.valueOf("OLIVEDRAB");
    static final Paint foodPaint = Paint.valueOf("RED");
    static final Paint backgroundPaint = Paint.valueOf("WHITE");
    static final Paint obstaclePaint = Paint.valueOf("GREY");
    static final Paint headPaint = Paint.valueOf("SIENNA");

    static final int MAX_Y = ROWS - 1;
    static final int MAX_X = COLUMNS - 1;
    static final int INITIAL_Y_POSITION = MAX_Y / 2;
    static final int INITIAL_X_POSITION = MAX_X / 2;
    static final int INITIAL_SNAKE_LENGTH = 5;
    static final int NUMBER_OF_APPLES = 5;

    static final long STEP_TIME = 150;
    static boolean OBSTACLES_ENABLED = false;


    private static final File FILE = new File("C:\\Users\\walen\\Desktop\\Snake\\settings.txt");
    static void load(){
        Properties mySettings = new Properties();
        try {
            mySettings.load(new FileInputStream(FILE));
            String input = mySettings.getProperty("OBSTACLES_ENABLED", "false");
            OBSTACLES_ENABLED = Boolean.parseBoolean(input);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void save(){

    }


}
